import { formatDate } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ChangeCodeHistoricalResponse } from 'src/app/entities/response';
import { AdministrationOffersModel } from 'src/app/models/AdministrationOffersModel';
import { ChangeCodeHistoricalModel } from 'src/app/models/ChangeCodeHistoricalModel';
import { AdministrationOffersService } from 'src/app/services/administration-offers.service';
import { PdfService } from 'src/app/services/pdf.service';
import { UtilsService } from 'src/app/services/utils.service';
import { messages } from 'src/app/utils/enum';

@Component({
  selector: 'app-administration-offers-historical',
  templateUrl: './administration-offers-historical.component.html',
  styleUrls: ['./administration-offers-historical.component.css']
})
export class AdministrationOffersHistoricalComponent implements OnInit {

  // Inputs | Outputs
  @Input() data: AdministrationOffersModel;
  @Output() messageEvent = new EventEmitter<boolean>();
  messages = messages;
  rows2: AdministrationOffersModel[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  typeValue: string = "";

  // Table
  rowsHistorical: ChangeCodeHistoricalModel[] = [];
  rowsHistorical2: ChangeCodeHistoricalModel[] = [];
  rowsSearch: any = null;


  constructor(public utilService: UtilsService,private activeModal: NgbActiveModal, private administrationOffersService: AdministrationOffersService, private pdfService: PdfService) { }

  ngOnInit(): void {
    this.getChangeCodeHistoricalByChargeId();
  }


  getChangeCodeHistoricalByChargeId() {


    // Se llama e método del servicio
    this.administrationOffersService.getChangeCodeHistoricalByChargeId(this.data.id).subscribe((response) => {

      // Validamos si responde con un 200
      if (response.status === 200) {

        // Vaciamos las 
        this.rowsHistorical = [];
        this.rowsHistorical2 = [];

        // Mapeamos el body del response
        const historicalResponse = response.body as ChangeCodeHistoricalResponse;

        // Agregamos los valores a los rows

        historicalResponse.data.map((resourceMap) => {

          let dto: ChangeCodeHistoricalModel = resourceMap;
          dto.status = dto.status === 1 ? 'Activo' : 'Inactivo';
          dto.createDate = this.formatDate(dto?.createDate);

          this.rowsHistorical.push(dto);

        });


        if (this.rowsHistorical.length > 0) {
          this.utilService.showNotification(0, "Datos cargados");
        }
      }
    });

  }

  generatePdf() {
    this.pdfService.generatePdfHistorical(this.rowsHistorical);

  }
  // Methods

  closeModal() {
    this.activeModal.close();
  }

  search() {
    this.rowsHistorical = this.rowsHistorical.filter((rowsSimcardControl) => {
      return JSON.stringify(rowsSimcardControl)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  getTotalText() {
    return this.rowsHistorical.length == 1 ? "Registro" : "Registros";
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
