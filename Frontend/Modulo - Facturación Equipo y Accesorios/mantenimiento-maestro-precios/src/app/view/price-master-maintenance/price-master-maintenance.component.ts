import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PriceMasterModelComponent } from 'src/app/components/price-master-model/price-master-model.component';
import { PriceMasterPagesResponse, PriceMasterResponse } from 'src/app/entity/response';
import { PriceMasterModel } from 'src/app/model/model';
import { EquipmentAccesoriesService } from 'src/app/services/equipment-accesories.service';
import { UtilService } from 'src/app/services/util.service';
import { Converter2Xlsx } from 'src/app/utils/convert2xlsx/converter2xlsx.class';
import { messages } from 'src/app/utils/enums';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-price-master-maintenance',
  templateUrl: './price-master-maintenance.component.html',
  styleUrls: ['./price-master-maintenance.component.css']
})
export class PriceMasterMaintenanceComponent implements OnInit {

  // Props

  // Table
  messages = messages;
  loadingIndicator: boolean = true;
  resultsPerPage: number = 20;
  searchedValue: string = "";
  rows: PriceMasterModel[] = [];
  rows2: PriceMasterModel[] = [];

  // Propiedades de paginación
  currentPage = 0;
  pageSize = 20;
  totalElements = 0;
  totalPages = 0;

  consultForm!: FormGroup;

  //* STYLES
  labelClasses = "col-form-label text-dark col-sm-2 col-md-2 col-lg-2";
  inputClasses = "my-auto col-sm-2 col-md-4 col-lg-8";
  inputClassesTwo = "my-auto col-sm-12 col-md-2 col-lg-2";
  inputClassesConsult = "my-auto";

  constructor(public utilService: UtilService, private equipmentAccesoriesService: EquipmentAccesoriesService, private modalService: NgbModal, private readonly fb: FormBuilder) { }

  ngOnInit(): void {
    this.consultForm = this.initForm();

    this.resultsPerPage = 20;
    this.pageSize = 20;
    this.currentPage = 0;

    this.openLoading('Cargando Registros...');
    this.getPricesMasterPromise().finally(() => this.closeLoading());
  }


  // Methods

  initForm(): FormGroup {
    return this.fb.group({
      model: ['', [Validators.required]],
    })
  }
  /**
  * Nos ayuda a filtrar, es decir: nos ayuda a buscar
  * valores que están en la tabla
  * 
  */
  search(): void {
    this.rows = this.rows2.filter((row) => {
      return JSON.stringify(row)
        .toLowerCase()
        .includes(this.searchedValue.toString()
          .toLowerCase());
    });
  }

  getTotalText() {
    return this.rows2.length == 1 ? "Registro" : "Registros";
  }

  reloadRows() {
    //this.getModelsAsEbs();
    this.refreshTable();
  }

  /**
  * Método para abrir modales según la acción
  * 
  * @param button 
  * @param row 
  */
  openModal(button: string, row: PriceMasterModel = null) {

    const modalRef = this.modalService.open(PriceMasterModelComponent, {
      size: "lg"
    });

    modalRef.componentInstance.button = button;
    modalRef.componentInstance.data = row;
    modalRef.componentInstance.messageEvent.subscribe((reload: boolean) => {

      if (reload) {
        this.reloadRows();
      }

    });

  }

  private openLoading(title = 'Cargando Registros...') {
    return Swal.fire({
      title,
      allowOutsideClick: false,
      onBeforeOpen: () => {
        Swal.showLoading();
      }
    });
  }

  private closeLoading() { Swal.close(); }

  // Método para manejar el cambio de página
  onPageChange(event: any) {
    this.openLoading('Cargando Registros...');

    setTimeout(async () => {
      try {
        this.currentPage = event.offset;
        await this.getPricesMasterPromise();
      } finally {
        this.closeLoading();
      }
    }, 0);
  }

  async onResultsPerPageChange(size: number) {
    this.openLoading('Cargando Registros...');

    this.resultsPerPage = +size;
    this.pageSize = +size;
    this.currentPage = 0;

    await this.getPricesMasterPromise();
    this.closeLoading();
  }

  // Methods Rest

  async refreshTable() {
    this.consultForm.get('model')?.setValue('');

    this.pageSize = 20;
    this.resultsPerPage = 20;
    this.currentPage = 0;

    this.openLoading('Cargando Registros...');
    try {
      await this.getPricesMasterPromise();
    } finally {
      this.closeLoading();
    }
  }


  getPricesMasterByModel(model: any) {
    this.equipmentAccesoriesService.getPricesMasterModelByModel(model).subscribe((response) => {

      if (response.status === 200) {

        this.rows = [];
        this.rows2 = [];

        const priceMasterResponse = response.body as PriceMasterResponse;

        this.currentPage = priceMasterResponse.data.length;
        this.pageSize = priceMasterResponse.data.length;
        this.totalElements = priceMasterResponse.data.length;
        this.totalPages = priceMasterResponse.data.length;
        priceMasterResponse.data.map((dataOk) => {

          let dto: PriceMasterModel = dataOk;

          this.rows.push(dto);

        });

        this.rows = [...this.rows];
        this.rows2 = [...this.rows];
        this.loadingIndicator = false;

        if (this.rows.length > 0) {
          this.utilService.showNotification(0, "Datos cargados");
        } else {
          this.utilService.showNotification(1, "No se encontraron datos para el modelo ingresado.!!");
          this.rows = [];
          this.rows2 = [];
        }

      } else {
        this.utilService.showNotification(1, "No se encontraron datos para el modelo ingresado.!!");
        this.rows = [];
        this.rows2 = [];
      }

    }, (error) => {
      this.utilService.showNotification(1, "No se encontraron datos para el modelo ingresado.!!");
      this.rows = [];
      this.rows2 = [];
      this.currentPage = 0;
      this.pageSize = 0;
      this.totalElements = 0;
      this.totalPages = 0;
    });
  }

  /**
   * Método que consume un servicio para obtener los datos de la 
   * tabla MEA_PRICE_MASTER
   * 
   */
  getPricesMasterPromise(): Promise<boolean> {
    this.loadingIndicator = true;

    return new Promise((resolve) => {
      this.equipmentAccesoriesService.getPricesMaster(this.currentPage, this.pageSize).subscribe({
        next: (response) => {
          if (response.status === 200) {
            this.rows = [];
            this.rows2 = [];

            const priceMasterResponse = response.body as PriceMasterPagesResponse;

            this.totalElements = priceMasterResponse.data.totalElements;
            this.totalPages = priceMasterResponse.data.totalPages;
            this.currentPage = priceMasterResponse.data.number;

            priceMasterResponse.data.content.forEach((item) => {
              this.rows.push(item as PriceMasterModel);
            });

            this.rows = [...this.rows];
            this.rows2 = [...this.rows];

            if (this.rows.length > 0) this.utilService.showNotification(0, "Datos cargados");
            this.loadingIndicator = false;
            resolve(true);
          } else {
            this.loadingIndicator = false;
            resolve(false);
          }
        },
        error: () => {
          this.loadingIndicator = false;
          resolve(false);
        }
      });
    });
  }

  confirmDelete(row: PriceMasterModel) {
    Swal.fire({
      title: 'Eliminar registro',
      text: `¿Desea eliminar el precio para el modelo ${row.model}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33'
    }).then((result) => {

      const confirmed = result.isConfirmed === true || result.value === true;

      if (confirmed) {
        this.deleteRow(row.id);
      }
    });
  }

  deleteRow(id: number) {
    Swal.fire({
      title: 'Eliminando ...',
      allowOutsideClick: false,
      onBeforeOpen: () => {
        Swal.showLoading();
      }
    });

    this.equipmentAccesoriesService.deletePriceMaster(id).subscribe({
      next: async (res) => {
        Swal.close();

        if (res.status === 200 && (res.body?.code === 1 || res.body == null)) {
          this.utilService.showNotification(0, 'Registro eliminado');

          if (this.rows.length === 1 && this.currentPage > 0) {
            this.currentPage--;
          }

          await this.getPricesMasterPromise();
        } else {
          this.utilService.showNotification(1, res.body?.description || 'No se pudo eliminar el registro');
        }
      },
      error: (err) => {
        Swal.close();
        console.error('DELETE error =>', err);
        this.utilService.showNotifyError(
          err.status,
          err?.error?.description || 'Error al eliminar el registro'
        );
      }
    });
  }

  fetchData(): Promise<PriceMasterModel[]> {
    const allData: PriceMasterModel[] = [];
    const fetchTotalPages = Math.floor(this.totalElements / 2000);
    let fetchCurrentPage = 0;

    const fetchPage = (page: number): Promise<void> => {
      return new Promise((resolve, reject) => {
        this.equipmentAccesoriesService.getPricesMaster(page, 2000).subscribe({
          next: (response) => {
            allData.push(...response.body.data.content);

            if (fetchCurrentPage < fetchTotalPages) {
              fetchCurrentPage++;
              fetchPage(fetchCurrentPage).then(resolve).catch(reject);
            } else {
              resolve(); // Todas las páginas han sido procesadas
            }
          },
          error: (err: HttpErrorResponse) => {
            this.utilService.showNotification(
              1,
              'No se pudo generar el reporte'
            );
            reject(err);
          },
        });
      });
    };

    return new Promise((resolve, reject) => {
      fetchPage(0)
        .then(() => resolve(allData))
        .catch(reject);
    });
  }

  getReport() {
    Swal.fire({
      title: 'Generando reporte, por favor espere ...',
      allowOutsideClick: false,
      onBeforeOpen: () => {
        Swal.showLoading();
      },
    });

    this.fetchData()
      .then((result) => {
        Swal.close();
        const columnWidths = [
          { wch: 8 }, // Ancho para la columna B
          { wch: 17 }, // Ancho para la columna C
          { wch: 15 }, // Ancho para la columna D
          { wch: 46 }, // Ancho para la columna E
          { wch: 20 }, //Ancho para la columna F
          { wch: 13 }, //Ancho para la columna G
          { wch: 12 }, //Ancho para la columna H
          { wch: 15 }, //Ancho para la columna I
          { wch: 15 }, //Ancho para la columna J
          { wch: 22 }, //Ancho para la columna K
          { wch: 10 }, //Ancho para la columna L
          { wch: 15 }, //Ancho para la columna M
          { wch: 16 }, //Ancho para la columna N
          { wch: 16 }, //Ancho para la columna O
          { wch: 20 }, //Ancho para la columna P
          { wch: 18 }, //Ancho para la columna Q
          { wch: 16 }, //Ancho para la columna R
        ];
        Converter2Xlsx.Create(
          result.map((item) => {
            return {
              Id: item.id,
              'Tipo de inventario': item.inventoryType,
              Modelo: item.model,
              Descripcion: item.description,
              'Costo base': item.baseCost,
              'Codigo factor': item.factorCode,
              Precio: item.price,
              'Usuario creador': item.userCreated,
              'Pantalla': item.screen,
              Creado: item.created,
              Moneda: item.currency,
              'Convertido a Lps': item.convertLps,
              'Precio en Lps': item.priceLps,
              'Ultimo costo': item.lastCost,
              'Costo temporal': item.costTemporary,
              'Precio cambio esn': item.priceChangeEsn,
              Esn: item.esn,
              'Precio esn': item.priceEsn,
              'Precio esn en LPS': item.priceLpsEsn,
            };
          }),
          columnWidths
        );
      })
      .catch((err) => {
        this.utilService.showNotification(1, 'Error al cargar los datos');
      });
  }
}
