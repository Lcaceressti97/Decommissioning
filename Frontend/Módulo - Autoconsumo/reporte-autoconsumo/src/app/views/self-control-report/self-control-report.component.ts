import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ReportModel } from 'src/app/models/ReportModel';
import { SelfControlReportService } from 'src/app/services/self-control-report.service';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-self-control-report',
  templateUrl: './self-control-report.component.html',
  styleUrls: ['./self-control-report.component.css']
})
export class SelfControlReportComponent implements OnInit {


  // Props

  // Table
  rows: ReportModel[] = [];
  rows2: ReportModel[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  typeValue: string = "";

  constructor(public utilService: UtilsService, private modalService: NgbModal, private selfControlReportService: SelfControlReportService) { }

  ngOnInit(): void {
    this.getReport();
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



  // Rest

  /**
   * Método que consume un servicio para obtener los proveedores
   * 
   */
  /*   getAdministrationOffers1() {
      this.administrationOffersService.getAdministrationOffers().subscribe((response) => {
  
        if (response.status === 200) {
  
          this.rows = [];
          this.rows2 = [];
  
          const providerResponse = response.body as AdministrationOffersResponse;
  
          providerResponse.data.map((dataOk) => {
  
            let dto: AdministrationOffersModel = dataOk;
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
    } */

  getReport() {
    this.selfControlReportService.getReport().subscribe((response) => {

      // Cambia `response.status` a `response.code`
      if (response.code === 200) {

        this.rows = [];
        this.rows2 = [];

        // No necesitas hacer un casting, ya que `response` ya es del tipo `AdministrationOffersResponse`
        response.data?.map((dataOk) => {

          let dto: ReportModel = dataOk;
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
