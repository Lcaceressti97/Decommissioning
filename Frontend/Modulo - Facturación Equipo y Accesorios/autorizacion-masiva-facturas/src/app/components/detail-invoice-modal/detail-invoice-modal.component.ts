import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Billing, InvoiceDetail } from 'src/app/models/InvoicesModel';
import { UtilService } from 'src/app/services/util.service';
import { Buttons } from 'src/types';

@Component({
  selector: 'app-detail-invoice-modal',
  templateUrl: './detail-invoice-modal.component.html',
  styleUrls: ['./detail-invoice-modal.component.css']
})
export class DetailInvoiceModalComponent implements OnInit {

  @Output() messageEvent = new EventEmitter<Billing>();
  @Input() billing: Billing;
  @Input() button: Buttons;


  constructor(private activeModal: NgbActiveModal, public utilsService: UtilService) { }

  ngOnInit(): void {
  }

  calculateValue(invoice: InvoiceDetail): number {
    if (this.billing.fiscalProcess === "Facturación por Reclamo de Seguros" && invoice.serieOrBoxNumber != null) {
      return invoice.amountTotal;
    } else {
      return invoice.unitPrice * invoice.quantity;
    }
  }

  //* UTILS
  getTitle() {
    if (this.button === "see") return "Detalle Factura: " + this.billing.id;
    if (this.button === "add") return "Detalle Factura: " + this.billing.id;
  }

  closeModal() {
    this.activeModal.close();
  }

}
