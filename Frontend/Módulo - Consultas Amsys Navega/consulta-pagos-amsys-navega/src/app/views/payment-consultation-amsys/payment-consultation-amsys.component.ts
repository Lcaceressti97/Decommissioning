import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NavegaPayment } from 'src/app/models/NavegaPayment';
import { TableNameModel } from 'src/app/models/TableNameModel';
import { PaymentsNavegaResponse } from 'src/app/models/response';
import { PaymentConsultationAmsysService } from 'src/app/services/payment-consultation-amsys.service';
import { PdfGeneratorService } from 'src/app/services/pdf-generator.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-payment-consultation-amsys',
  templateUrl: './payment-consultation-amsys.component.html',
  styleUrls: ['./payment-consultation-amsys.component.css']
})
export class PaymentConsultationAmsysComponent implements OnInit {

  navegaPayment: NavegaPayment[] = [];
  rowsNavega: NavegaPayment[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  typeValue: string = "";
  private sub: any;
  currentPage = 0;
  pageSize = 2000;
  detailOptions: any[] = [];
  selectedDetailOption: TableNameModel | null = null;
  startDate: string = "";
  endDate: string = "";

  //* STYLES
  labelClasses = "col-form-label text-dark col-sm-2 col-md-2 col-lg-2 font-size-large";
  inputClasses = "my-auto col-sm-2 col-md-4 col-lg-3";

  constructor(private paymentConsultationAmsysService: PaymentConsultationAmsysService,
    private pdfGeneratorService: PdfGeneratorService,
    public utilService: UtilService,
    private activatedRoute: ActivatedRoute) {

    this.activatedRoute.queryParams.subscribe(params => {
      this.sub = JSON.stringify(params['sub']);

      if (this.sub)
        sessionStorage.setItem('sub', this.sub)
    });
  }

  ngOnInit(): void {
    let permissions = this.loadPermissions();
    this.getAllPayments();
  }

  //* COMPONENTS
  loadPermissions() {
    let subs = sessionStorage.getItem('sub').split(",")
    let permissions = subs[2]
    return permissions.split('_')
  }


  /**
   * Método que consume un servicio para obtener los proveedores
   * 
   */
  getAllPayments() {
    this.paymentConsultationAmsysService.getAllPayments().subscribe((response) => {

      if (response.status === 200) {

        this.navegaPayment = [];
        this.rowsNavega = [];

        const providerResponse = response.body as PaymentsNavegaResponse;

        providerResponse.data.map((dataOk) => {

          let dto: NavegaPayment = dataOk;
          dto.paymentDate = this.formatDate(dataOk?.paymentDate);

          this.navegaPayment.push(dto);

        });

        this.navegaPayment = [...this.navegaPayment];
        this.rowsNavega = [...this.navegaPayment];
        this.loadingIndicator = false;

        if (this.navegaPayment.length > 0) {
          this.utilService.showNotification(0, "Datos cargados");
        }

      }

    });
  }



  onDateChange() {
    if (this.startDate && this.endDate) {
      const startDate = new Date(this.startDate);
      const endDate = new Date(this.endDate);

      if (startDate > endDate) {
        this.utilService.showNotificationMsj("Fecha de Inicio Debe Ser Mayor o Igual a Fecha de Fin.!!");
        this.loadingIndicator = true;
        this.navegaPayment = [];
        this.rowsNavega = [];
        return;
      }
    }
    this.loadingIndicator = true;
    this.navegaPayment = [];
    this.rowsNavega = [];
    this.getPaymentsByDateRange();
    this.updateRowsBasedOnType();
  }


  getPaymentsByDateRangea() {
    this.loadingIndicator = true;

    if (this.startDate && this.endDate) {
      this.paymentConsultationAmsysService.getPaymentsByDateRange(
        this.startDate,
        this.endDate
      ).subscribe((res) => {
        this.handleBalanceResponse(res, this.typeValue.toLowerCase());
        this.updateRowsBasedOnType();
      });

    }
  }

  getPaymentsByDateRange() {
    this.loadingIndicator = true;

    if (this.startDate && this.endDate) {
      this.paymentConsultationAmsysService.getPaymentsByDateRange(
        this.startDate,
        this.endDate
      ).subscribe(
        (res) => {
          this.handleBalanceResponse(res, this.typeValue.toLowerCase());
          this.rowsNavega = [...this.navegaPayment];
          this.loadingIndicator = false;

          if (this.navegaPayment.length === 0) {
            this.utilService.showNotification(1, "No se encontraron datos con el rango de fecha seleccionado");
          }
        },
        (error) => {
          // Manejar el error
          this.loadingIndicator = false;
          this.navegaPayment = [];
          this.rowsNavega = [];
        }
      );
    } else {
      // Cuando no se hayan seleccionado fechas, resetear las listas
      this.loadingIndicator = false;
      this.navegaPayment = [];
      this.rowsNavega = [];
    }
  }


  private handleBalanceResponse(res: any, type: string) {
    this.navegaPayment = res.data.map((v: any) => {
      let dto = {} as NavegaPayment;
      dto.id = v.id;
      dto.navegaCode = v.navegaCode;
      dto.productCode = v.productCode;
      dto.currency = v.currency;
      dto.paymentAmount = v.paymentAmount;
      dto.bank = v.bank;
      dto.bankAuthorization = v.bankAuthorization;
      dto.exchangeRate = v.exchangeRate;
      dto.paymentDate = this.formatDate(v?.paymentDate);
      dto.idDeaOrganization = v.idOrganization;
      dto.ebsAccount = v.ebsAccount;
      dto.transactionSts = v.transactionSts;
      return dto;
    });

    this.rowsNavega = [...this.navegaPayment];
    this.loadingIndicator = false;


  }


  async cancellationPayment(row: any) {
    const idPayment = row.id;

    Swal.fire({
      title: 'Advertencia',
      text: "¿Desea Anular Este Pago?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {
      if (result.value) {
        this.paymentConsultationAmsysService.cancelPayment(idPayment).subscribe(
          () => {
            Swal.fire('Pago Anulado', '', 'success');
            this.reloadRows();
          },
          (error) => {
            Swal.fire('Error', 'No se pudo anular el pago', 'error');
            console.error('Error al cancelar el pago:', error);
          }
        );
      }
    });
  }
  generatePDFNavega() {
    this.pdfGeneratorService.generatePdfNavega(this.rowsNavega, this.formatDatePDF(this.startDate), this.formatDatePDF(this.endDate));
  }


  //* UTILS
  searchPayment() {
    this.rowsNavega = this.rowsNavega.filter((payment) => {
      return JSON.stringify(payment)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }


  updateRowsBasedOnType() {
    this.rowsNavega = [...this.navegaPayment];

  }


  getTotalText() {
    return this.rowsNavega.length == 1 ? "Registro" : "Registros";
  }

  reloadRows() {
    this.getAllPayments();
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


  formatDatePDF(date: string | null): string {
    if (!date) {
      return '';
    }

    const formattedDate = new Date(date);
    const day = formattedDate.getDate().toString().padStart(2, '0');
    const month = (formattedDate.getMonth() + 1).toString().padStart(2, '0');
    const year = formattedDate.getFullYear();

    return `${day}/${month}/${year}`;
  }
}
