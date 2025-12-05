import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ReleaseSerialDetailComponent } from 'src/app/components/release-serial-detail/release-serial-detail.component';
import { ReleaseSerialNumbersModalComponent } from 'src/app/components/release-serial-numbers-modal/release-serial-numbers-modal.component';
import { ReleaseSeriesLogPagesResponse, ReleaseSeriesLogResponse } from 'src/app/entities/response';
import { ReleaseSeriesLogModel, ReleaseSeriesRequest } from 'src/app/models/model';
import { ReleaseSerialNumbersService } from 'src/app/services/release-serial-numbers.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-release-serial-numbers',
  templateUrl: './release-serial-numbers.component.html',
  styleUrls: ['./release-serial-numbers.component.css']
})
export class ReleaseSerialNumbersComponent implements OnInit {

  // Table
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  rows: ReleaseSeriesLogModel[] = [];
  rows2: ReleaseSeriesLogModel[] = [];

  // Propiedades de paginación
  currentPage = 0;
  pageSize = 20;
  totalElements = 0;
  totalPages = 0;

  consultForm!: FormGroup;

  inputClassesConsult = "my-auto";

  constructor(public utilService: UtilService, private releaseSerialNumbersService: ReleaseSerialNumbersService, private modalService: NgbModal, private readonly fb: FormBuilder) { }

  ngOnInit(): void {
    this.consultForm = this.initForm();

    this.getAllReleaseSerialLog();

  }

  // Methods
  initForm(): FormGroup {
    return this.fb.group({
      serie: ['', [Validators.required]],
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
    this.consultForm.get('serie')?.setValue('');

    this.pageSize = 20;
    this.currentPage = 0;

    // Mostrar el loading
    Swal.fire({
      title: 'Cargando ...',
      allowOutsideClick: false,
      onBeforeOpen: () => {
        Swal.showLoading();
      }
    });

    // Llamar a la función para obtener todos los registros
    await this.getAllReleaseSerialLog();

    // Cerrar el loading
    Swal.close();
  }

  // Función para obtener el texto del estado
  getStatusText(status: any): string {
    switch (status) {
      case "C":
        return 'Completado';
      case "E":
        return 'Error';
      default:
        return 'Desconocido';
    }
  }

  // Función para obtener el color del estado
  getStatusColor(status: any): string {
    switch (status) {
      case "C":
        return 'green';
      case "E":
        return 'red';
      default:
        return 'black';
    }
  }

  getStatusColorClass(statusColor: string | undefined): string {
    switch (statusColor) {
      case 'C':
        return 'green-text';
      case 'E':
        return 'red-text';
      default:
        return 'red-text';
    }
  }

  /**
 * Método para abrir modales según la acción
 * 
 * @param button 
 * @param row 
 */
  openModal(button: string, row: ReleaseSeriesRequest = null) {

    const modalRef = this.modalService.open(ReleaseSerialNumbersModalComponent, {
      size: "lg"
    });

    modalRef.componentInstance.data = row;
    modalRef.componentInstance.button = button;
    modalRef.componentInstance.messageEvent.subscribe((reload: boolean) => {

      if (reload) {
        this.reloadRows();
      }

    });

  }

  openModalDetail(data: ReleaseSeriesLogModel = null) {

    const modalRef = this.modalService.open(ReleaseSerialDetailComponent, {
      size: "xl"
    });

    modalRef.componentInstance.dataReleaseSeriesLog = data;
    modalRef.componentInstance.messageEvent.subscribe((reload: boolean) => {

      if (reload) {
        this.reloadRows();
      }

    });

  }


  getLogBySerialNumber(serie: any) {
    this.releaseSerialNumbersService.getLogBySerialNumber(serie).subscribe((response) => {

      if (response.status === 200) {

        this.rows = [];
        this.rows2 = [];

        const logResponse = response.body as ReleaseSeriesLogResponse;

        this.currentPage = logResponse.data.length;
        this.pageSize = logResponse.data.length;
        this.totalElements = logResponse.data.length;
        this.totalPages = logResponse.data.length;
        logResponse.data.map((dataOk) => {

          let dto: ReleaseSeriesLogModel = dataOk;

          this.rows.push(dto);

        });

        this.rows = [...this.rows];
        this.rows2 = [...this.rows];
        this.loadingIndicator = false;

        if (this.rows.length > 0) {
          this.utilService.showNotification(0, "Datos cargados");
        } else {
          this.utilService.showNotification(1, "No se encontraron datos para la serie ingresada.!!");
          this.rows = [];
          this.rows2 = [];
        }

      } else {
        this.utilService.showNotification(1, "No se encontraron datos para la serie ingresada.!!");
        this.rows = [];
        this.rows2 = [];
        
      }

    }, (error) => {
      this.utilService.showNotification(1, "No se encontraron datos para la serie ingresada.!!");
      this.rows = [];
      this.rows2 = [];
      this.currentPage = 0;
      this.pageSize = 0;
      this.totalElements = 0;
      this.totalPages = 0;
    });
  }

  // Método para manejar el cambio de página
  async onPageChange(event: any) {
    this.currentPage = event.offset;
    await this.getAllReleaseSerialLog();
  }

  // Methods Services
  /**
* Método encargado de obtener los registros de la tabla de liberacion de series logs
* 
*/
  getAllReleaseSerialLog() {

    // Se llama e método del servicio
    this.releaseSerialNumbersService.getAllReleaseSerialLog(this.currentPage, this.pageSize).subscribe((response) => {

      // Validamos si responde con un 200
      if (response.status === 200) {

        // Vaciamos las 
        this.rows = [];
        this.rows2 = [];

        // Mapeamos el body del response
        let logsResponse = response.body as ReleaseSeriesLogPagesResponse;

        // Actualizar la información de paginación
        this.totalElements = logsResponse.data.totalElements;
        this.totalPages = logsResponse.data.totalPages;
        this.currentPage = logsResponse.data.number;

        // Agregamos los valores a los rows

        logsResponse.data.content.map((resourceMap, configError) => {

          let dto: ReleaseSeriesLogModel = resourceMap;

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

}
