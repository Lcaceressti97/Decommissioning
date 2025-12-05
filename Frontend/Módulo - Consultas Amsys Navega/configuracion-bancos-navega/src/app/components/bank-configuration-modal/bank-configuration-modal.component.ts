import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BankModel } from 'src/app/models/BankModel';
import { BankConfigurationService } from 'src/app/services/bank-configuration.service';
import { UtilsService } from 'src/app/services/utils.service';
import { messages } from 'src/app/utils/enum';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-bank-configuration-modal',
  templateUrl: './bank-configuration-modal.component.html',
  styleUrls: ['./bank-configuration-modal.component.css']
})
export class BankConfigurationModalComponent implements OnInit {

  // Props

  // Input | Output
  @Input() data: BankModel;
  @Input() action: string;
  @Output() messageEvent = new EventEmitter<boolean>();

  // Form
  form: FormGroup;
  fileName: string = "Seleccionar";
  file: File = null;
  messages = messages;
  readonly: boolean = false;

  constructor(public utilService: UtilsService, private bankConfigurationService: BankConfigurationService, private activeModal: NgbActiveModal, private formBuilder: FormBuilder) { }

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
      this.form.get('company').disable({ onlySelf: true });
      this.form.get('currencyCode').disable({ onlySelf: true });
      this.form.get('status').disable({ onlySelf: true });

    }


  }

  // Form
  /**
   * Método encargado de construir el formulario reactivo
   * 
   */
  initForm(): FormGroup {
    return this.formBuilder.group({
      code: ["", [Validators.required]],
      nameCompany: ["", [Validators.required, Validators.maxLength(100)]],
      bankName: ["", [Validators.required, Validators.maxLength(150)]],
      nameTransaction: ["", [Validators.required, Validators.maxLength(100)]],
      bankAccountName: ["", [Validators.required, Validators.maxLength(100)]],
      bankAccountNum: ["", [Validators.required, Validators.maxLength(100)]],
      currencyCode: ["", [Validators.required, Validators.maxLength(50)]],
      identifyingLetter: ["", [Validators.required, Validators.maxLength(5)]],
      bankAccountEbs: ["", [Validators.maxLength(150)]],
      bankCodeAmsys: ["", [Validators.maxLength(150)]],
      company: ["", [Validators.required, Validators.maxLength(150)]],
      status: ["", [Validators.required, Validators.maxLength(150)]],

    });
  }

  /**
   * Método encargado de construir el formulario reactivo
   * 
   */
  initFormTwo(): FormGroup {
    const statusValue = this.data.status === "Activo" ? "1" : "0";

    return this.formBuilder.group({
      code: [this.data.code, [Validators.required]],
      nameCompany: [this.data.nameCompany, [Validators.required, Validators.maxLength(100)]],
      bankName: [this.data.bankName, [Validators.required, Validators.maxLength(150)]],
      nameTransaction: [this.data.nameTransaction, [Validators.required, Validators.maxLength(100)]],
      bankAccountName: [this.data.bankAccountName, [Validators.required, Validators.maxLength(100)]],
      bankAccountNum: [this.data.bankAccountNum, [Validators.required, Validators.maxLength(100)]],
      currencyCode: [this.data.currencyCode, [Validators.required, Validators.maxLength(50)]],
      identifyingLetter: [this.data.identifyingLetter, [Validators.required, Validators.maxLength(150)]],
      bankAccountEbs: [this.data.bankAccountEbs, [Validators.maxLength(150)]],
      bankCodeAmsys: [this.data.bankCodeAmsys, [Validators.maxLength(150)]],
      company: [this.data.company, [Validators.required, Validators.maxLength(150)]],
      status: [statusValue, [Validators.required, Validators.maxLength(150)]],

    });
  }

  /**
   * Método que nos ayuda a controlar el archivo que se va a cargar
   * 
   * @param event 
   */
  selectFile(event: any) {
    const fileName: string = event.target.files[0]?.name;
    this.fileName = fileName;
    this.file = event.target.files[0];
  }

  // Operación
  crudBank() {

    const title: string = this.action === 'add' ? "crear" : "actualizar"

    Swal.fire({

      title: 'Advertencia',
      text: `¿Desea ${title} este banco?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {

        let data: BankModel = this.form.value;

        if (this.action === "add") {
          const VALIDATE_ADD = await this.postProvider(data);

          if (VALIDATE_ADD) {

            this.utilService.showNotification(0, "Banco creado con éxito");

            this.messageEvent.emit(true);
            this.closeModal();
          }

        } else {

          const VALIDATE_UPDATE = await this.putProvider(this.data.id, data);

          if (VALIDATE_UPDATE) {

            this.utilService.showNotification(0, "Banco actualizado con éxito");

            this.messageEvent.emit(true);
            this.closeModal();
          }

        }

      }

    });

  }


  //Rest
  postProvider(data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consumir el servicio
      this.bankConfigurationService.postBank(data).subscribe((response) => {

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

  putProvider(id: any, data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consumir el servicio
      this.bankConfigurationService.putBank(id, data).subscribe((response) => {

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
