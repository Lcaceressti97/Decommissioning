import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { InvoiceEquipmentAccesories } from 'src/app/models/InvoiceEquipmentAccesories';
import { InvoiceValidationService } from 'src/app/services/invoice-validation.service';
import { UtilService } from 'src/app/services/util.service';
import { Buttons } from 'src/types';

@Component({
  selector: 'app-single-customer',
  templateUrl: './single-customer.component.html',
  styleUrls: ['./single-customer.component.css']
})
export class SingleCustomerComponent implements OnInit {

  @Output() messageEvent = new EventEmitter<InvoiceEquipmentAccesories>();
  @Input() invoiceEquipmentAccesories: InvoiceEquipmentAccesories;
  @Input() button: Buttons;

  constructor(private invoiceValidationService: InvoiceValidationService, private activeModal: NgbActiveModal, public utilService: UtilService) { }

  singleCustomerForm = new FormGroup({
    diplomaticCardNumber: new FormControl("", [Validators.required]),
  });

  ngOnInit(): void {
  }

  updateSingleClient() {
    const id = this.invoiceEquipmentAccesories.id;
    const diplomaticCardNo = this.singleCustomerForm.value.diplomaticCardNumber;

    this.invoiceValidationService.updateSingleClient(id, diplomaticCardNo).subscribe(
      () => {
        this.utilService.showNotification(
          0,
          "Número de Carnet Diplomático actualizados con éxito"
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
    if (this.button === "see") return "Cargar OCEP: Cliente Individual";
  }

  closeModal() {
    this.activeModal.close();
  }

}
