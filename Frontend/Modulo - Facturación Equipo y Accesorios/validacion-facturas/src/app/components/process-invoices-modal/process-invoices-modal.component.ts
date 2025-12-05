import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { InvoiceEquipmentAccesories } from 'src/app/models/InvoiceEquipmentAccesories';
import { InvoiceValidationService } from 'src/app/services/invoice-validation.service';
import { UtilService } from 'src/app/services/util.service';
import { Buttons } from 'src/types';

@Component({
  selector: 'app-process-invoices-modal',
  templateUrl: './process-invoices-modal.component.html',
  styleUrls: ['./process-invoices-modal.component.css']
})
export class ProcessInvoicesModalComponent implements OnInit {

  @Output() messageEvent = new EventEmitter<InvoiceEquipmentAccesories>();
  @Input() invoiceEquipmentAccesories: InvoiceEquipmentAccesories;
  @Input() button: Buttons;

  constructor(private invoiceValidationService: InvoiceValidationService, private activeModal: NgbActiveModal, public utilService: UtilService) { }

  processInvoiceForm = new FormGroup({
    documentNumber: new FormControl("", [Validators.required]),
  });

  ngOnInit(): void {
  }

  updateDocumentNo() {
    const id = this.invoiceEquipmentAccesories.id;
    const documentNumber = this.processInvoiceForm.value.documentNumber;

    this.invoiceValidationService.updateDocumentNo(id, documentNumber).subscribe(
      () => {
        this.utilService.showNotification(
          0,
          "Número de Documento actualizado con éxito"
        );
        this.activeModal.close();
      },
      error => {
        console.error(error);
      }
    );
  }

  //* UTILS
  getTitle() {
    if (this.button === "see") return "Procesar Facturas";
  }

  closeModal() {
    this.activeModal.close();
  }

}
