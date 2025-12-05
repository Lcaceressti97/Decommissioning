import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CorporateCustomerModalComponent } from 'src/app/components/corporate-customer-modal/corporate-customer-modal.component';
import { ProcessInvoicesModalComponent } from 'src/app/components/process-invoices-modal/process-invoices-modal.component';
import { SingleCustomerComponent } from 'src/app/components/single-customer/single-customer.component';
import { InvoiceEquipmentAccesoriesEntity } from 'src/app/entities/InvoiceEquipmentAccesoriesEntity';
import { InvoiceEquipmentAccesories } from 'src/app/models/InvoiceEquipmentAccesories';
import { InvoiceValidationService } from 'src/app/services/invoice-validation.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';
import { Buttons } from 'src/types';

@Component({
  selector: 'app-invoice-validation',
  templateUrl: './invoice-validation.component.html',
  styleUrls: ['./invoice-validation.component.css']
})
export class InvoiceValidationComponent implements OnInit {

  invoiceEquipmentAccesories: InvoiceEquipmentAccesories[] = [];
  rowsInvoiceEquipmentAccesories: InvoiceEquipmentAccesories[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  typeValue: string = "";
  messages = messages;
  loading: boolean;
  param: any
  mostrarTabla = false;
  infoCargado = false;

  //* STYLES
  labelClasses = "col-form-label text-dark col-sm-2 col-md-2 col-lg-2 font-size-large";
  inputClasses = "my-auto col-sm-2 col-md-4 col-lg-3";

  constructor(private invoiceValidationService: InvoiceValidationService,
    private modalService: NgbModal,
    public utilService: UtilService,
    private activatedRoute: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.getInvoiceEquipmentAccessories();
  }

  //* COMPONENTS
  getInvoiceEquipmentAccessories() {
    this.invoiceValidationService.getInvoiceEquipmentAccessories().subscribe((res) => {
      if (res.status === 200) {
        this.rowsInvoiceEquipmentAccesories = [];
        this.invoiceEquipmentAccesories = [];

        let response = res.body.data as InvoiceEquipmentAccesoriesEntity[];
        response.map((v, k) => {
          let dto = {} as InvoiceEquipmentAccesories;
          dto.id = v.id;
          dto.invoiceNo = v?.invoiceNo;
          dto.invoiceType = v?.invoiceType;
          dto.invoiceStatus = v?.invoiceStatus;
          dto.invoiceStatusName = v?.invoiceStatusName;
          dto.billingAccount = v?.billingAccount;
          dto.subscriber = v?.subscriber;
          dto.custcode = v?.custcode;
          dto.emailaddr = v?.emailaddr;
          dto.company = v?.company;
          dto.cai = v?.cai;
          dto.customerName = v?.customerName;
          dto.customerType = v?.customerType;
          dto.documentNo = v?.documentNo;
          dto.diplomaticCardNo = v?.diplomaticCardNo;
          dto.correlativeOrdenExemptNo = v?.correlativeOrdenExemptNo;
          dto.correlativeCertificateExoNo = v?.correlativeCertificateExoNo;
          dto.exonerationStatusName = v?.exonerationStatusName;
          dto.address = v?.address;
          dto.warehouse = v?.warehouse;
          dto.agency = v?.agency;
          dto.transactionUser = v?.transactionUser;
          dto.chargeLocal = v?.chargeLocal;
          dto.chargeUsd = v?.chargeUsd;
          dto.exchangeRate = v?.exchangeRate;
          dto.tax = v?.tax;
          dto.discount = v?.discount;
          dto.created = new Date(v?.created).toLocaleString('es-US', {
            year: 'numeric',
            month: 'short',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
          });
          dto.createdBy = v?.createdBy;
          this.rowsInvoiceEquipmentAccesories.push(dto);
        });

        this.loadingIndicator = false;
        this.rowsInvoiceEquipmentAccesories = [...this.rowsInvoiceEquipmentAccesories];
        this.invoiceEquipmentAccesories = [...this.invoiceEquipmentAccesories];
      } else {
        this.utilService.showNotificationError(res.status);
      }
    });
  }

  see(row: any) {
    this.openModal("see", row);
  }

  openModal(button: Buttons, row: InvoiceEquipmentAccesories = null) {

    const modalRef = this.modalService.open(ProcessInvoicesModalComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.invoiceEquipmentAccesories = row;
    modalRef.componentInstance.button = button;
    modalRef.componentInstance.messageEvent.subscribe((invoiceEquipmentAccesories: InvoiceEquipmentAccesories) => {
    });
  }

  openModalOcep(button: Buttons, row: InvoiceEquipmentAccesories = null) {
    if (row.customerType === 'Cliente Individual') {
        const modalRef = this.modalService.open(SingleCustomerComponent, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.invoiceEquipmentAccesories = row;
        modalRef.componentInstance.button = button;
        modalRef.componentInstance.messageEvent.subscribe((invoiceEquipmentAccesories: InvoiceEquipmentAccesories) => {
        });
    } else if (row.customerType === 'Cliente Corporativo') {
        const modalRef = this.modalService.open(CorporateCustomerModalComponent, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.invoiceEquipmentAccesories = row;
        modalRef.componentInstance.button = button;
        modalRef.componentInstance.messageEvent.subscribe((invoiceEquipmentAccesories: InvoiceEquipmentAccesories) => {
        });
    }
}

  //* UTILS
  searchInvoice() {
    this.rowsInvoiceEquipmentAccesories = this.rowsInvoiceEquipmentAccesories.filter((invoiceEquipmentAccesories) => {
      return JSON.stringify(invoiceEquipmentAccesories)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  getTotalText() {
    return this.rowsInvoiceEquipmentAccesories.length == 1 ? "Registro" : "Registros";
  }

  reloadRows() {
    this.getInvoiceEquipmentAccessories();
  }

}
