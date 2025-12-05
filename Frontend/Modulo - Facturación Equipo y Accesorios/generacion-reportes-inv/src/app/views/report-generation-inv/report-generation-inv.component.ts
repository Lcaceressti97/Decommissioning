import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GenerationReportInvEntity } from 'src/app/entities/GenerationReportInvEntity';
import { GenerationReportInv, ReportInvPagesResponse } from 'src/app/models/GenerationReportInv';
import { ReportGenerationService } from 'src/app/services/report-generation.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';

@Component({
  selector: 'app-report-generation-inv',
  templateUrl: './report-generation-inv.component.html',
  styleUrls: ['./report-generation-inv.component.css']
})
export class ReportGenerationInvComponent implements OnInit {


  generationReportInv: GenerationReportInv[] = [];
  rowsGenerationReportInv: GenerationReportInv[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  typeValue: string = "";
  messages = messages;
  loading: boolean;
  param: any
  mostrarTabla = false;
  infoCargado = false;

  // Propiedades de paginación
  currentPage = 0;
  pageSize = 20;
  totalElements = 0;
  totalPages = 0;

  //* STYLES
  labelClasses = "col-form-label text-dark col-sm-2 col-md-2 col-lg-2 font-size-large";
  inputClasses = "my-auto col-sm-2 col-md-4 col-lg-3";

  constructor(private reportGenerationService: ReportGenerationService,
    public utilService: UtilService,
    private activatedRoute: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.getReportGenerationInv();
  }

  // Método para manejar el cambio de página
  async onPageChange(event: any) {
    this.currentPage = event.offset;
    await this.getReportGenerationInv();
  }


  //* COMPONENTS
  getReportGenerationInv() {
    this.reportGenerationService.getReportGenerationInv(this.currentPage, this.pageSize).subscribe((res) => {
      if (res.status === 200) {
        this.rowsGenerationReportInv = [];
        this.generationReportInv = [];

        let response = res.body as ReportInvPagesResponse;

        // Actualizar la información de paginación
        this.totalElements = response.data.totalElements;
        this.totalPages = response.data.totalPages;
        this.currentPage = response.data.number;

        // Agregamos los valores a los rows

        response.data.content.map((resourceMap, configError) => {

          let dto: GenerationReportInv = resourceMap;

          this.rowsGenerationReportInv.push(dto);

        });


        this.loadingIndicator = false;
        this.rowsGenerationReportInv = [...this.rowsGenerationReportInv];
        this.generationReportInv = [...this.generationReportInv];
      } else {
        this.utilService.showNotificationError(res.status);
      }
    });
  }

  downloadReport(fileName: string, reportContent: string) {
    const decodedContent = atob(reportContent);
    const blob = new Blob([decodedContent], { type: 'text/plain' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = fileName;
    a.click();
    window.URL.revokeObjectURL(url);
  }

  //* UTILS
  searchReport() {
    this.rowsGenerationReportInv = this.rowsGenerationReportInv.filter((generationReportInv) => {
      return JSON.stringify(generationReportInv)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  getTotalText() {
    return this.rowsGenerationReportInv.length == 1 ? "Registro" : "Registros";
  }

  reloadRows() {
    this.getReportGenerationInv();
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
