import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { InvoiceEquipmentAccesories } from 'src/app/models/InvoiceEquipmentAccesories';
import { InvoiceValidationService } from 'src/app/services/invoice-validation.service';
import { UtilService } from 'src/app/services/util.service';
import { Buttons } from 'src/types';

@Component({
  selector: 'app-corporate-customer-modal',
  templateUrl: './corporate-customer-modal.component.html',
  styleUrls: ['./corporate-customer-modal.component.css']
})
export class CorporateCustomerModalComponent implements OnInit {

  @Output() messageEvent = new EventEmitter<InvoiceEquipmentAccesories>();
  @Input() invoiceEquipmentAccesories: InvoiceEquipmentAccesories;
  @Input() button: Buttons;

  constructor(private invoiceValidationService: InvoiceValidationService, private activeModal: NgbActiveModal, public utilService: UtilService) { }

  corporateCustomerForm = new FormGroup({
    purchaseOrderNumber: new FormControl("", [Validators.required]),
    certificateNumberRegistration: new FormControl("", [Validators.required]),
  });

  ngOnInit(): void {
  }

  updateCorporateClient() {
    const id = this.invoiceEquipmentAccesories.id;
    const purchaseOrderNumber = this.corporateCustomerForm.value.purchaseOrderNumber;
    const certificateNumberRegistration = this.corporateCustomerForm.value.certificateNumberRegistration;

    this.invoiceValidationService.updateCorporateClient(id, purchaseOrderNumber, certificateNumberRegistration).subscribe(
      () => {
        this.utilService.showNotification(
          0,
          "Número de Correlativo y Contancia Exonerados actualizados con éxito"
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
    if (this.button === "see") return "Cargar OCEP: Cliente Coorporativo";
  }

  closeModal() {
    this.activeModal.close();
  }

}
