import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { messages } from 'src/app/utils/enums';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import Swal from "sweetalert2/dist/sweetalert2.js";
import { Billing, CorreoModel } from 'src/app/models/billing';
import { UtilService } from 'src/app/services/util.service';
import { InvoiceService } from 'src/app/services/invoice.service';

@Component({
  selector: 'app-correo-modal',
  templateUrl: './correo-modal.component.html',
  styleUrls: ['./correo-modal.component.css']
})
export class CorreoModalComponent implements OnInit {

  // Props

  // Input Y Output
  @Input() billing: Billing;
  messages = messages;

  // Form
  form!: FormGroup;

  constructor(private activeModal: NgbActiveModal, public utilsService: UtilService, private formBuilder: FormBuilder, private invoiceService: InvoiceService) { }

  ngOnInit(): void {
    this.form = this.initForm();
  }

  // Methods

  /**
   * Método que inicializa el formulario
   * 
   */
  initForm(): FormGroup {

    return this.formBuilder.group({
      invoiceType: [this.billing.invoiceType, []],
      status: [this.billing.status, []],
      customer: [this.billing.customer, []],
      primaryIdentity: [this.billing.primaryIdentity, []],
      email: ["", [Validators.required, Validators.email]],
    });

  }

  closeModal() {
    this.activeModal.close();
  }

  /**
   * Método que tiene la lógica de enviar el correo
   * 
   */
  sendEmail() {

    Swal.fire({
      title: 'Advertencia',
      text: "¿Desea enviar la factura al correo ingresado?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {
        //const DATA_FORM: CorreoModel = this.form.value;
        //DATA_FORM.idPrefecture = this.billing.id;
        //console.log(DATA_FORM);
         const data: CorreoModel = {
          idPrefecture: this.billing.id,
          email: this.form.get('email').value,
          cashierName: this.utilsService.getSystemUser()
        } 
        console.log(data);
        const VALIDATE_CORREO = await this.postSendEmail(data);
        console.log(VALIDATE_CORREO);

        if (VALIDATE_CORREO) {
          this.utilsService.showNotification(0, `La factura con id ${this.billing.id} fue envia por correo`);

          //this.closeModal();

        }

      }

    });

  }

  // Methods Asyncrono

  /**
* Método que consume un servicio para enviar la factura por el correo
* 
* @param id 
* @returns 
*/
  postSendEmail(data: any): Promise<boolean> {

    return new Promise((resolve, reject) => {

      this.invoiceService.postSendEmail(data).subscribe((response) => {
        console.log(response);
        console.log(response.status);
        if (response.status === 200) {

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      });

    });

  }


}
