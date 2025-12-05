import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CustomerModel } from 'src/app/models/model';
import { SimCardService } from 'src/app/services/sim-card.service';

import { UtilService } from 'src/app/services/util.service';

import { messages } from 'src/app/utils/enum';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-client-modal',
  templateUrl: './client-modal.component.html',
  styleUrls: ['./client-modal.component.css']
})
export class ClientModalComponent implements OnInit {

  // Props

  // Input | Output
  @Input() data: CustomerModel;
  @Input() action: string;
  @Output() messageEvent = new EventEmitter<boolean>();

  // Form
  form: FormGroup;
  messages = messages;
  readonly: boolean = false;

  constructor(public utilService: UtilService, private simcardService: SimCardService, private activeModal: NgbActiveModal, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.validateShow();
  }

    // Methods
    closeModal() {
      this.activeModal.close();
    }
  
    /**
  * Método que nos ayuda a visualizar el tipo de acción para el formulario
  * 
  */
    validateShow() {
  
      if (this.action === 'add') {
        this.form = this.initForm();
      } else {
        this.form = this.initFormTwo();
      }
  
      if (this.action === "see") {
        this.readonly = true;
      }
  
    }

    initForm(): FormGroup {
      return this.formBuilder.group({
        code: [0, [Validators.required]],
        hlr: [0, [Validators.required]],
        clientName: ["", [Validators.required, Validators.maxLength(50)]],
        descriptionHrl: ["", [Validators.required, Validators.maxLength(150)]],
      });
    }

    initFormTwo(): FormGroup {
      return this.formBuilder.group({
        code: [this.data.code, [Validators.required]],
        hlr: [this.data.hlr, [Validators.required]],
        clientName: [this.data.clientName, [Validators.required, Validators.maxLength(50)]],
        descriptionHrl: [this.data.descriptionHrl, [Validators.required, Validators.maxLength(150)]],
      });
    }
  
    // Operación
    crudArt() {
  
      const title: string = this.action === 'add' ? "crear" : "actualizar"
  
      Swal.fire({
  
        title: 'Advertencia',
        text: `¿Desea ${title} este cliente?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#002e6e',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Aceptar'
      }).then(async (result: any) => {
  
        if (result.value) {
  
          let data: CustomerModel = this.form.value;
  
          if (this.action === "add") {
            const VALIDATE_ADD = await this.postClient(data);
  
            if (VALIDATE_ADD) {
  
              this.utilService.showNotification(0, "Cliente creado con exito");
  
              this.messageEvent.emit(true);
              this.closeModal();
            }
  
          } else {
  
            const VALIDATE_UPDATE = await this.putClient(this.data.id, data);
  
            if (VALIDATE_UPDATE) {
  
              this.utilService.showNotification(0, "Cliente actualizado con exito");
  
              this.messageEvent.emit(true);
              this.closeModal();
            }
  
          }
  
        }
  
      });
  
    }
  
  
    //Rest
    postClient(data: any): Promise<boolean> {
      return new Promise((resolve, reject) => {
  
        // Consumir el servicio
        this.simcardService.postClient(data).subscribe((response) => {
  
          if (response.status === 200) {
  
            resolve(true);
  
          } else {
            resolve(false);
          }
  
        }, (error) => {
  
          resolve(false);
        })
  
      });
  
    }
  
    putClient(id: any, data: any): Promise<boolean> {
      return new Promise((resolve, reject) => {
  
        // Consumir el servicio
        this.simcardService.putClient(id, data).subscribe((response) => {
  
          if (response.status === 200) {
  
            resolve(true);
  
          } else {
            resolve(false);
          }
  
        }, (error) => {
  
          resolve(false);
        })
  
      });
  
    }
  

}
