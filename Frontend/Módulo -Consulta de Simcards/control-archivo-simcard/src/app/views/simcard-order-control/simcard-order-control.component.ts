import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ControlOrderModalComponent } from 'src/app/components/control-order-modal/control-order-modal.component';
import { LoadFileComponent } from 'src/app/components/load-file/load-file.component';
import { ReadingFileComponent } from 'src/app/components/reading-file/reading-file.component';
import { OderControlResponse, OrdersControlResponse } from 'src/app/entities/response';
import { OrderControlModel } from 'src/app/models/model';
import { SimcardControlFileService } from 'src/app/services/simcard-control-file.service';
import { UtilService } from 'src/app/services/util.service';

@Component({
  selector: 'app-simcard-order-control',
  templateUrl: './simcard-order-control.component.html',
  styleUrls: ['./simcard-order-control.component.css']
})
export class SimcardOrderControlComponent implements OnInit {

  // Propiedades de paginación
  currentPage = 0;
  pageSize = 20;
  totalElements = 0;
  totalPages = 0;

  // Style
  inputClasses = "my-auto col-sm-12 col-md-3 col-lg-3";

  // Table
  rows: OrderControlModel[] = [];
  rows2: OrderControlModel[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  typeValue: string = "";

  constructor(public utilService: UtilService, private modalService: NgbModal, private simcardService: SimcardControlFileService) { }

  ngOnInit(): void {

    this.getOrdersControl();



  }

  // Methods


  // Generales
  search() {
    this.rows = this.rows2.filter((rowsSimcardControl) => {
      return JSON.stringify(rowsSimcardControl)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  reloadRows() {
    this.getOrdersControl();
  }

  getTotalText() {
    return this.rows.length == 1 ? "Registro" : "Registros";
  }


  // Modal
  openModalCrud(data: OrderControlModel, action: string) {

    // Se abre la modal
    const modalRef = this.modalService.open(ControlOrderModalComponent, {
      size: "xl"
    });

    // Se pasa el input
    modalRef.componentInstance.data = data;
    modalRef.componentInstance.action = action;
    modalRef.componentInstance.messegaEvent.subscribe((status: boolean) => {

      if (status) {
        this.getOrdersControl(this.currentPage, this.pageSize);
      }

    });

  }

  openModalFile(data: OrderControlModel, action: string) {
    // Se abre la modal
    const modalRef = this.modalService.open(LoadFileComponent, {
      size: "xl"
    });

    // Se pasa el input
    modalRef.componentInstance.data = data;
    modalRef.componentInstance.action = action;
    modalRef.componentInstance.messageEvent.subscribe((status: boolean) => {

      if (status) {
        this.getOrdersControl(this.currentPage, this.pageSize);
      }

    });
  }

  /**
   * Método que abre la modal para ver el contenido de losa archivos
   * 
   */
  openModalFileView(data: OrderControlModel) {
    // Se abre la modal
    const modalRef = this.modalService.open(ReadingFileComponent, {
      size: "xl"
    });

    // Se pasa el input
    modalRef.componentInstance.data = data;

  }


  getStatusColorClass(statusColor: string | undefined): string {
    switch (statusColor) {
      case 'E':
        return 'blue-text';
      case 'R':
        return 'green-text';
      case 'P':
        return 'green-text';
      default:
        return 'red-text';
    }
  }

  // Método para manejar el cambio de página
  async onPageChange(event: any) {
    this.currentPage = event.offset;
    await this.getOrdersControl(this.currentPage, this.pageSize);
  }

  /**
* Método que consume un servicio para obtener los proveedores
* 
*/
  getOrdersControl(page?: any, size?: any) {
    this.simcardService.getOrdersControl(page, size).subscribe((response) => {

      if (response.status === 200) {

        let ordersResponse = response.body as OrdersControlResponse;

        // Actualizar la información de paginación
        this.totalElements = ordersResponse.data.totalElements;
        this.totalPages = ordersResponse.data.totalPages;
        this.currentPage = ordersResponse.data.number;

        this.rows = ordersResponse.data.content.map((resourceMap) => {

          let dto: OrderControlModel = resourceMap;
          dto.statusCode = dto.status === null ? 'Pendiente' : dto.status === "E" ? "Procesar" : "Procesado";

          return dto;

        });

        this.loadingIndicator = false;
        this.rows = [...this.rows];
        this.rows2 = [...this.rows];

        if (this.rows.length > 0) {
          this.utilService.showNotification(0, "Datos cargados");
        }

      }

    });
  }

  /**
* Método que consume un servicio para obtener los pedidos por el iccd
* 
*/
  getOrdersControlByIccd(iccd: any) {
    this.simcardService.getOrdersControlByIccd(iccd).subscribe((response) => {

      if (response.status === 200) {

        this.rows = [];
        this.rows2 = [];

        const providerResponse = response.body as OderControlResponse;

        providerResponse.data.map((dataOk) => {

          let dto: OrderControlModel = dataOk;
          dto.statusCode = dto.status === null ? 'Pendiente' : dto.status === "E" ? "Procesar" : "Procesado";

          this.rows.push(dto);

        });

        this.rows = [...this.rows];
        this.rows2 = [...this.rows];
        this.loadingIndicator = false;

        if (this.rows.length > 0) {
          this.utilService.showNotification(0, "Datos cargados");
        } else {
          this.utilService.showNotification(1, "No se encontraron datos");
        }

      }

    });
  }


  /**
* Método que consume un servicio para obtener los pedidos por el imsi
* 
*/
  getOrdersControlByImsi(imsi: any) {
    this.simcardService.getOrdersControlByImsi(imsi).subscribe((response) => {

      if (response.status === 200) {

        this.rows = [];
        this.rows2 = [];

        const providerResponse = response.body as OderControlResponse;

        providerResponse.data.map((dataOk) => {

          let dto: OrderControlModel = dataOk;
          dto.statusCode = dto.status === null ? 'Pendiente' : dto.status === "E" ? "Procesar" : "Procesado";

          this.rows.push(dto);

        });

        this.rows = [...this.rows];
        this.rows2 = [...this.rows];
        this.loadingIndicator = false;

        if (this.rows.length > 0) {
          this.utilService.showNotification(0, "Datos cargados");
        } else {
          this.utilService.showNotification(1, "No se encontraron datos");
        }

      }

    });
  }

}
