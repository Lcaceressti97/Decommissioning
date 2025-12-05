import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AdministrationOffersHistoricalComponent } from 'src/app/components/administration-offers-historical/administration-offers-historical.component';
import { AdministrationOffersModalComponent } from 'src/app/components/administration-offers-modal/administration-offers-modal.component';
import { AdministrationOffersResponse } from 'src/app/entities/response';
import { AdministrationOffersModel } from 'src/app/models/AdministrationOffersModel';
import { AdministrationOffersService } from 'src/app/services/administration-offers.service';
import { ExcelService } from 'src/app/services/excel.service';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-administration-offers',
  templateUrl: './administration-offers.component.html',
  styleUrls: ['./administration-offers.component.css']
})
export class AdministrationOffersComponent implements OnInit {

  // Props

  // Table
  rows: AdministrationOffersModel[] = [];
  rows2: AdministrationOffersModel[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  typeValue: string = "";

  // Export
  dataExport: any = null;

  constructor(public utilService: UtilsService, private modalService: NgbModal, private administrationOffersService: AdministrationOffersService, private datePipe: DatePipe, private excelService: ExcelService) { }

  ngOnInit(): void {
    this.getAdministrationOffers();
  }


  // Methods

  search() {
    this.rows = this.rows2.filter((rowsSimcardControl) => {
      return JSON.stringify(rowsSimcardControl)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  getTotalText() {
    return this.rows.length == 1 ? "Registro" : "Registros";
  }

  // Modal
  openModalCrud(data: AdministrationOffersModel, action: string) {

    // Se abre la modal
    const modalRef = this.modalService.open(AdministrationOffersModalComponent, {
      size: "lg"
    });

    // Se pasa el input
    modalRef.componentInstance.data = data;
    modalRef.componentInstance.action = action;
    modalRef.componentInstance.messageEvent.subscribe((status: boolean) => {

      if (status) {
        this.getAdministrationOffers();
      }

    });

  }

  openModalHistorical(data: AdministrationOffersModel) {

    // Se abre la modal
    const modalRef = this.modalService.open(AdministrationOffersHistoricalComponent, {
      size: "xl"
    });

    // Se pasa el input
    modalRef.componentInstance.data = data;
    modalRef.componentInstance.messageEvent.subscribe((status: boolean) => {

      if (status) {
        this.getAdministrationOffers();
      }

    });

  }


  // Rest

  /**
   * Método que consume un servicio para obtener los proveedores
   * 
   */
  getAdministrationOffers() {
    this.administrationOffersService.getAdministrationOffers().subscribe((response) => {

      if (response.status === 200) {

        this.rows = [];
        this.rows2 = [];

        const providerResponse = response.body as AdministrationOffersResponse;

        providerResponse.data.map((dataOk) => {

          let dto: AdministrationOffersModel = dataOk;
          dto.status = dto.status === 1 ? 'Activo' : 'Inactivo';
          dto.createDate = this.formatDate(dto?.createDate);
          this.rows.push(dto);

        });

        this.rows = [...this.rows];
        this.rows2 = [...this.rows];
        this.loadingIndicator = false;

        if (this.rows.length > 0) {
          this.utilService.showNotification(0, "Datos cargados");
        }

      }

    });
  }

  async generateExcel() {

    // Es la infomración que aparece en la tabla
    const dataExcel = this.rows.map(objeto => {

      const dateCreated = this.datePipe.transform(objeto.createDate, "dd/MM/yyyy hh:mm:ss")

      return { offeringId: objeto.offeringId, chargeCode: objeto.chargeCode, itemName: objeto.itemName, userName: objeto.userName, status: objeto.status, createDate: dateCreated };
    });



    const arrayDeArrays: any[][] = dataExcel.map(obj => Object.values(obj));

    this.dataExport = arrayDeArrays;

    const columnNames = ['Offering Id', 'Charge Code', 'Item Name', 'Usuario', 'Estado', 'Fecha Creación'];



    const title: string = `CRF_${"Charge Code"}_` + this.datePipe.transform(new Date(), "dd-MM-yyyy");

    this.excelService.generateExcel(this.dataExport, title, columnNames);


  }

  formatDate(date: string | null): string {
    if (!date) {
      return '';
    }

    const formattedDate = new Date(date);
    const day = formattedDate.getDate().toString().padStart(2, '0');
    const month = (formattedDate.getMonth() + 1).toString().padStart(2, '0');
    const year = formattedDate.getFullYear();
    let hours = formattedDate.getHours();
    const ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12;
    hours = hours ? hours : 12;
    const minutes = formattedDate.getMinutes().toString().padStart(2, '0');
    const seconds = formattedDate.getSeconds().toString().padStart(2, '0');

    return `${day}/${month}/${year} ${hours}:${minutes}:${seconds} ${ampm}`;
  }



}
