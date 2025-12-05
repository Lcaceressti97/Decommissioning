import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UtilService } from 'src/app/services/util.service';

import { compareDesc, format, subDays } from 'date-fns';
import { ActivatedRoute, Router } from '@angular/router';
import { ItemSelect, Logs, TypeErrorModel } from 'src/app/model/model';
import { EquipmentAccessoriesLogDetailComponent } from 'src/app/components/equipment-accessories-log-detail/equipment-accessories-log-detail.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EquipmentAccessoriesService } from 'src/app/services/equipment-accessories.service';
import { LogsRangeDateResponse, LogsResponse, TypeErrorResponse } from 'src/app/entity/response';
import { ExcelService } from 'src/app/services/excel.service';


@Component({
  selector: 'app-equipment-accessories-log',
  templateUrl: './equipment-accessories-log.component.html',
  styleUrls: ['./equipment-accessories-log.component.css']
})
export class EquipmentAccessoriesLogComponent implements OnInit {

  // Props

  // Inputs
  dateForm!: FormGroup;
  invalidDate: boolean = false;

  // Table
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  rows: Logs[] = [];
  rows2: Logs[] = [];
  size: number = 2000;

  // Propiedades de paginación
  currentPage = 0;
  pageSize = 20;
  totalElements = 0;
  totalPages = 0;
  isFilteringByDate: boolean = false;

  // Select
  public dropdownSettings = {};
  public dropdownList: ItemSelect[] = [
  ];
  public selectedItems = [];

  typeErrorModel: TypeErrorModel[] = [];

  // Styles
  inputClasses = "my-auto col-sm-12 col-md-4 col-lg-4";

  // Export
  dataExcel: any = null;

  constructor(public utilService: UtilService, private readonly fb: FormBuilder, private modalService: NgbModal, private equipmentAccessoriesService: EquipmentAccessoriesService, private excelService: ExcelService) { }

  async ngOnInit() {

    this.dateForm = this.initForm();

    await this.getErrorTypeControl();

    this.dropdownSettings = {
      singleSelection: true,
      text: "Seleccione un valor",
      selectAllText: "Todos",
      unSelectAllText: "Ninguno",
      enableSearchFilter: true,
      classes: ""
    };

    this.getLogsPagination();

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

  // Métodos de formularios

  initForm(): FormGroup {
    return this.fb.group({
      dateInit: ['', [Validators.required]],
      dateEnd: ['', [Validators.required]]
    })
  }

  /**
* Este método nos ayuda a validar los input de:
* Fecha de Inicio y Fecha Final
* ¿Qué valida?
* R/. Valida que se haya seleccionado un valor del input y
* que la fecha inicial no sea superior a la final
* 
*/
  validateDates(): void {
    let dates = this.getDates();

    //console.log(dates);

    if (
      compareDesc(dates.startDate, dates.endDate) < 0 &&
      this.dateForm.get("dateEnd").value != ""
    ) {
      this.utilService.showNotificationMsj("Fecha de Inicio Debe Ser Mayor o Igual a Fecha de Fin.!!");

      this.invalidDate = true;
    } else {

      this.invalidDate = false;
    }
  }

  /**
* Este método nos devuelve un objeto con las fechas actuales
* 
* @returns 
*/
  getDates() {
    return {
      startDate: new Date(`${this.dateForm.get("dateInit").value}T00:00`),
      endDate: new Date(`${this.dateForm.get("dateEnd").value}T00:00`),
    };


  }

  /**
   * Método para buscar por rango de fecha
   * 
   */
  async onSubmit() {
    this.isFilteringByDate = true;

    const validateInvoiceDate = await this.getLogsByDateRange(this.dateForm.get("dateInit").value, this.dateForm.get("dateEnd").value);


    // Validamos si hay datos o no
    if (validateInvoiceDate) {
      this.utilService.showNotification(0, "Datos cargados");
    } else {
      this.utilService.showNotification(1, "No se encontraron datos con el rango de fecha seleccionado");
    }
  }


  // Métodos de filtración

  async onItemSelect(ev) {

    this.dateForm.get("dateInit").setValue('');
    this.dateForm.get("dateEnd").setValue('');

    const validateTypeError = await this.getLogsByTypeError(ev.id);
    // Validamos si hay datos o no
    if (validateTypeError) {
      this.utilService.showNotification(0, "Datos cargados");
    } else {
      this.utilService.showNotification(1, "No se encontraron datos con el tipo de error seleccionado");
      this.totalElements = 0;
      this.rows = [];
      this.rows = [...this.rows];
      this.rows2 = [...this.rows];
    }

  }

  OnItemDeSelect(ev) {

    this.getLogsPagination();
  }

  // Modal
  openModal(data: any) {

    const modalRef = this.modalService.open(EquipmentAccessoriesLogDetailComponent, {
      size: "lg"
    });

    modalRef.componentInstance.log = data;

  }

  refreshTable() {
    this.getLogsPagination();
    this.dateForm.get("dateInit").setValue('');
    this.dateForm.get("dateEnd").setValue('');
    //this.invalidDate =false;
    this.selectedItems = [];
  }

  exportExcel() {
    if (this.rows.length > 0) {

      const DATA_EXCEL = this.rows.map(objeto => {

        return {
          reference: objeto.reference,
          typeError: objeto.typeError,
          message: objeto.message,
          url: objeto.url,
          srt: objeto.srt
        }

      });

      const ARRAY_DE_ARRAYS: any[][] = DATA_EXCEL.map(obj => Object.values(obj));

      this.dataExcel = ARRAY_DE_ARRAYS;

      const columnNames = ['Referencia', 'Tipo de Error', 'Mensaje', 'Url', 'SRT'];

      this.excelService.generarExcel(this.dataExcel, "Bitácora de Equipos y Accesorios", columnNames);

    } else {
      this.utilService.showNotification(1, "No se visualizan registros para exportar");
    }
  }


  // Métodos para consumir servicios

  /**
   * Método que consume un servicio para obtener todos los tipos de error
   * 
   * @returns 
   */
  getErrorTypeControl(): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.equipmentAccessoriesService.getErrorTypeControl().subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {

          // Vaciamos las 
          this.dropdownList = [];

          // Mapeamos el body del response
          let typeErrorResponse = response.body as TypeErrorResponse;

          // Agregamos los valores a los rows

          typeErrorResponse.data.map((resourceMap, configError) => {

            let dto: TypeErrorModel = resourceMap;
            let item: ItemSelect = {
              id: dto.typeError,
              itemName: dto.description
            }

            this.dropdownList.push(item);


          });


          this.dropdownList = [...this.dropdownList];

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })
    });


  }


// Método para manejar el cambio de página
async onPageChange(event: any) {
  this.currentPage = event.offset;

  // Verifica qué método de paginación se debe usar
  if (this.isFilteringByDate) {
    // Si estás filtrando por rango de fechas
    await this.getLogsByDateRange(this.dateForm.get("dateInit").value, this.dateForm.get("dateEnd").value);
  } else if (this.selectedItems.length > 0) {
    // Si hay un tipo de error seleccionado
    const selectedTypeError = this.selectedItems[0].id; 
    await this.getLogsByTypeError(selectedTypeError);
  } else {
    await this.getLogsPagination();
  }
}

  /**
   * Método que consume un servicio para obtener los logs por paginación
   * 
   */
  getLogsPagination() {

    // Se llama e método del servicio
    this.equipmentAccessoriesService.getLogsPagination(this.currentPage, this.pageSize).subscribe((response) => {

      // Validamos si responde con un 200
      if (response.status === 200) {

        // Vaciamos las 
        this.rows = [];
        this.rows2 = [];

        // Mapeamos el body del response
        let logsResponse = response.body as LogsResponse;

        // Actualizar la información de paginación
        this.totalElements = logsResponse.data.totalElements;
        this.totalPages = logsResponse.data.totalPages;
        this.currentPage = logsResponse.data.number;

        // Agregamos los valores a los rows
        logsResponse.data.content.map((resourceMap, configError) => {

          let dto: Logs = resourceMap;

          this.dropdownList.forEach((element) => {

            if (dto.typeError === element.id) {
              dto.typeError = element.itemName;
            }

          });

          this.rows.push(dto);

        });

        this.loadingIndicator = false;
        this.rows = [...this.rows];
        this.rows2 = [...this.rows];

        if (this.rows.length > 0) {
          this.utilService.showNotification(0, "Datos cargados");
        }

      }

    }, (error) => {

    })
  }

  /**
   * Método que consume un servicio para traer los logs por rango de fecha.
   * 
   */
  getLogsByDateRange(initDate: any, ednDate: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.equipmentAccessoriesService.getLogsRangeDate(this.currentPage, this.pageSize, initDate, ednDate).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {

          // Vaciamos las 
          this.rows = [];
          this.rows2 = [];

          // Mapeamos el body del response
          let ogsRangeDateResponse = response.body as LogsResponse;

          // Actualizar la información de paginación
          this.totalElements = ogsRangeDateResponse.data.totalElements;
          this.totalPages = ogsRangeDateResponse.data.totalPages;
          this.currentPage = ogsRangeDateResponse.data.number;

          // Agregamos los valores a los rows

          if (ogsRangeDateResponse.data.numberOfElements > 0) {
            ogsRangeDateResponse.data.content.map((resourceMap, configError) => {

              let dto: Logs = resourceMap;

              this.dropdownList.forEach((element) => {

                if (dto.typeError === element.id) {
                  dto.typeError = element.itemName;
                }

              });

              this.rows.push(dto);

            });

            this.loadingIndicator = false;
            this.rows = [...this.rows];
            this.rows2 = [...this.rows];




            resolve(true);
          } else {

            resolve(false);
          }

        }

      }, (error) => {
        resolve(false);
      })
    })


  }


  /**
   * Método que consume un servicio para traer los logs por rango de fecha.
   * 
   */
  getLogsByTypeError(typeError: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.equipmentAccessoriesService.getLogsByTypeError(this.currentPage, this.pageSize, typeError).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {

          // Vaciamos las 
          this.rows = [];
          this.rows2 = [];

          // Mapeamos el body del response
          let ogsRangeDateResponse = response.body as LogsResponse;

          // Actualizar la información de paginación
          this.totalElements = ogsRangeDateResponse.data.totalElements;
          this.totalPages = ogsRangeDateResponse.data.totalPages;
          this.currentPage = ogsRangeDateResponse.data.number;
          // Agregamos los valores a los rows

          if (ogsRangeDateResponse.data.numberOfElements > 0) {
            ogsRangeDateResponse.data.content.map((resourceMap, configError) => {

              let dto: Logs = resourceMap;

              this.dropdownList.forEach((element) => {

                if (dto.typeError === element.id) {
                  dto.typeError = element.itemName;
                }

              });

              this.rows.push(dto);

            });

            this.loadingIndicator = false;
            this.rows = [...this.rows];
            this.rows2 = [...this.rows];




            resolve(true);
          } else {

            resolve(false);
          }

        }

      }, (error) => {
        resolve(false);
      })
    })


  }


}
