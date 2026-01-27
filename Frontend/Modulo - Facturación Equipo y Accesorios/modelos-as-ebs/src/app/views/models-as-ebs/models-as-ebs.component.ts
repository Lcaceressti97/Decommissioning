import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModelsAsEbsModalComponent } from 'src/app/components/models-as-ebs-modal/models-as-ebs-modal.component';
import { ModelAsEbsPagesResponse, ModelAsEbsResponse } from 'src/app/entities/response';
import { ModelAsEbsModel } from 'src/app/models/model';
import { ModelsAsEbsService } from 'src/app/services/models-as-ebs.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-models-as-ebs',
  templateUrl: './models-as-ebs.component.html',
  styleUrls: ['./models-as-ebs.component.css']
})
export class ModelsAsEbsComponent implements OnInit {

  // Props

  // Table
  loadingIndicator: boolean = true;
  resultsPerPage: number = 20;
  searchedValue: string = "";
  rows: ModelAsEbsModel[] = [];
  rows2: ModelAsEbsModel[] = [];

  // Propiedades de paginación
  currentPage = 0;
  pageSize = 20;
  totalElements = 0;
  totalPages = 0;

  consultForm!: FormGroup;

  inputClassesConsult = "my-auto";

  constructor(public utilService: UtilService, private modelsAsEbsService: ModelsAsEbsService, private modalService: NgbModal, private readonly fb: FormBuilder) { }

  ngOnInit() {
    this.consultForm = this.initForm();

    this.resultsPerPage = 20;
    this.pageSize = 20;
    this.currentPage = 0;

    this.openLoading('Cargando Registros...');
    this.getModelsAsEbs().finally(() => this.closeLoading());
  }


  // Methods

  initForm(): FormGroup {
    return this.fb.group({
      model: ['', [Validators.required]],
    })
  }

  // Methods Screen
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

  async refreshTable() {
    // Limpiar el valor del formulario
    this.consultForm.get('model')?.setValue('');

    this.pageSize = 20;
    this.currentPage = 0;

    // Mostrar el loading
    Swal.fire({
      title: 'Cargando Registros ...',
      allowOutsideClick: false,
      onBeforeOpen: () => {
        Swal.showLoading();
      }
    });

    // Llamar a la función para obtener todos los registros
    await this.getModelsAsEbs();

    // Cerrar el loading
    Swal.close();
  }

  /**
 * Método para abrir modales según la acción
 * 
 * @param button 
 * @param row 
 */
  openModal(button: string, row: ModelAsEbsModel = null) {

    const modalRef = this.modalService.open(ModelsAsEbsModalComponent, {
      size: "md"
    });

    modalRef.componentInstance.data = row;
    modalRef.componentInstance.button = button;
    modalRef.componentInstance.messageEvent.subscribe((reload: boolean) => {

      if (reload) {
        this.reloadRows();
      }

    });

  }

  // Methods Services

  getModelAsEbsByCodEbs(model: any) {
    this.modelsAsEbsService.getModelAsEbsByCodEbs(model).subscribe((response) => {

      if (response.status === 200) {

        this.rows = [];
        this.rows2 = [];

        const priceMasterResponse = response.body as ModelAsEbsResponse;

        this.currentPage = priceMasterResponse.data.length;
        this.pageSize = priceMasterResponse.data.length;
        this.totalElements = priceMasterResponse.data.length;
        this.totalPages = priceMasterResponse.data.length;
        priceMasterResponse.data.map((dataOk) => {

          let dto: ModelAsEbsModel = dataOk;

          this.rows.push(dto);

        });

        this.rows = [...this.rows];
        this.rows2 = [...this.rows];
        this.loadingIndicator = false;

        if (this.rows.length > 0) {
          this.utilService.showNotification(0, "Datos cargados");
        } else {
          this.utilService.showNotification(1, "No se encontraron datos para el código ebs ingresado.!!");
          this.rows = [];
          this.rows2 = [];
        }

      } else {
        this.utilService.showNotification(1, "No se encontraron datos para el código ebs ingresado.!!");
        this.rows = [];
        this.rows2 = [];

      }

    }, (error) => {
      this.utilService.showNotification(1, "No se encontraron datos para el código ebs ingresado.!!");
      this.rows = [];
      this.rows2 = [];
      this.currentPage = 0;
      this.pageSize = 0;
      this.totalElements = 0;
      this.totalPages = 0;
    });
  }

  // Método para manejar el cambio de página
  onPageChange(event: any) {
    this.openLoading('Cargando Registros...');

    setTimeout(async () => {
      try {
        this.currentPage = event.offset;
        await this.getModelsAsEbs();
      } finally {
        this.closeLoading();
      }
    }, 0);
  }


  /**
* Método encargado de obtener los registros de las facturas
* 
*/
  getModelsAsEbs(): Promise<boolean> {
    this.loadingIndicator = true;

    return new Promise((resolve) => {
      this.modelsAsEbsService.getModelsAsEbs(this.currentPage, this.pageSize).subscribe({
        next: (response) => {
          if (response.status === 200) {
            this.rows = [];
            this.rows2 = [];

            const modelAsEbsResponse = response.body as ModelAsEbsPagesResponse;

            this.totalElements = modelAsEbsResponse.data.totalElements;
            this.totalPages = modelAsEbsResponse.data.totalPages;
            this.currentPage = modelAsEbsResponse.data.number;

            modelAsEbsResponse.data.content.forEach((item) => {
              this.rows.push(item as ModelAsEbsModel);
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


  async onResultsPerPageChange(size: number) {
    this.openLoading('Cargando Registros...');

    this.resultsPerPage = +size;
    this.pageSize = +size;
    this.currentPage = 0;

    await this.getModelsAsEbs();
    this.closeLoading();
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

  confirmDelete(row: ModelAsEbsModel) {
    Swal.fire({
      title: 'Eliminar registro',
      text: `¿Desea eliminar el modelo ${row.codMod}?`,
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

    this.modelsAsEbsService.deleteModelAsEbs(id).subscribe({
      next: async (res) => {
        Swal.close();

        if (res.status === 200 && res.body?.code === 1) {

          this.utilService.showNotification(0, 'Registro eliminado');

          if (this.rows.length === 1 && this.currentPage > 0) {
            this.currentPage--;
          }

          await this.getModelsAsEbs();

        } else {
          this.utilService.showNotification(
            1,
            res.body?.description || 'No se pudo eliminar el registro'
          );
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


}
