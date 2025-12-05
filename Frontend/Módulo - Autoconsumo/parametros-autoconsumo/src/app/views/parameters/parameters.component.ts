import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ParametersHistoricalComponent } from 'src/app/components/parameters-historical/parameters-historical.component';
import { ParametersModalComponent } from 'src/app/components/parameters-modal/parameters-modal.component';
import { ParametersResponse } from 'src/app/entities/response';
import { ParametersModel } from 'src/app/models/ParametersModel';
import { ParametersService } from 'src/app/services/parameters.service';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-parameters',
  templateUrl: './parameters.component.html',
  styleUrls: ['./parameters.component.css']
})
export class ParametersComponent implements OnInit {


  // Props

  // Table
  rows: ParametersModel[] = [];
  rows2: ParametersModel[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  typeValue: string = "";

  // Export
  dataExport: any = null;

  constructor(public utilService: UtilsService, private modalService: NgbModal, private parametersService: ParametersService) { }

  ngOnInit(): void {
    this.getParameters();
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
  openModalCrud(data: ParametersModel, action: string) {

    // Se abre la modal
    const modalRef = this.modalService.open(ParametersModalComponent, {
      size: "lg"
    });

    // Se pasa el input
    modalRef.componentInstance.data = data;
    modalRef.componentInstance.action = action;
    modalRef.componentInstance.messageEvent.subscribe((status: boolean) => {

      if (status) {
        this.getParameters();
      }

    });

  }

  openModalHistorical(data: ParametersModel) {

    // Se abre la modal
    const modalRef = this.modalService.open(ParametersHistoricalComponent, {
      size: "xl"
    });

    // Se pasa el input
    modalRef.componentInstance.data = data;
    modalRef.componentInstance.messageEvent.subscribe((status: boolean) => {

      if (status) {
        this.getParameters();
      }

    });

  }


  // Rest

  /**
   * Método que consume un servicio para obtener los parametros
   * 
   */
  getParameters() {
    this.parametersService.getParameters().subscribe((response) => {

      if (response.status === 200) {

        this.rows = [];
        this.rows2 = [];

        const providerResponse = response.body as ParametersResponse;

        providerResponse.data.map((dataOk) => {

          let dto: ParametersModel = dataOk;
          dto.status = dto.status === 1 ? 'Activo' : 'Inactivo';
          dto.createdDate = this.formatDate(dto?.createdDate);
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

