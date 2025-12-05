import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { EquipmentInsuranceModel } from 'src/app/models/EquipmentInsuranceModel';
import { SafeControlEquipmentService } from 'src/app/services/safe-control-equipment.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-safe-control-equipment-modal',
  templateUrl: './safe-control-equipment-modal.component.html',
  styleUrls: ['./safe-control-equipment-modal.component.css']
})
export class SafeControlEquipmentModalComponent implements OnInit {

  // Props

  // Input | Output
  @Input() data: EquipmentInsuranceModel;
  @Input() action: string;
  @Output() messageEvent = new EventEmitter<boolean>();

  // Form
  form!: FormGroup;
  fileName: string = "Seleccionar";
  file: File = null;
  messages = messages;
  readonly: boolean = false;

  constructor(public utilService: UtilService, private safeControlEquipmentService: SafeControlEquipmentService, private datePipe: DatePipe, private activeModal: NgbActiveModal, private formBuilder: FormBuilder) { }

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


  // Form
  /**
   * Método encargado de construir el formulario reactivo
   * 
   */
  initForm(): FormGroup {
    return this.formBuilder.group({
      transactionCode: ["", [Validators.required, Validators.maxLength(5)]],
      userAs: [this.utilService.getSystemUser(), []],
      dateConsultation: ['', [Validators.required]],
      customerAccount: ["", [Validators.required, Validators.maxLength(30)]],
      serviceAccount: ["", [Validators.required, Validators.maxLength(30)]],
      billingAccount: ["", [Validators.required, Validators.maxLength(30)]],
      phoneNumber: ["", [Validators.required, Validators.maxLength(12), Validators.pattern('^[0-9]+$')]],
      equipmentModel: ["", [Validators.required, Validators.maxLength(6)]],
      esn: ["", [Validators.required, Validators.maxLength(15), Validators.pattern('^[0-9]+$')]],
      originAs: [""],
      inventoryTypeAs: [""],
      originTypeAs: [""],
      dateContract: ['', [Validators.required]],
      startDate: ['', [Validators.required]],
      endDate: ['', [Validators.required]],
      dateInclusion: ['', [Validators.required]],
      insuranceRate: [0.00.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      period: ["", [Validators.required, Validators.pattern('^[0-9]+$')]],
      insuranceRate2: [0.00.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      period2: ["", [Validators.required, Validators.pattern('^[0-9]+$')]],
      insuranceRate3: [0.00.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      period3: ["", [Validators.required, Validators.pattern('^[0-9]+$')]],
      insuranceStatus: [""],
      subscriber: ["", [Validators.maxLength(30), Validators.pattern('^[0-9]+$')]],

    });
  }

  validarNumeroDecimal(control) {
    const numeroDecimalRegExp = /^[0-9]+(\.[0-9]{1,2})?$/;
    if (control.value && !numeroDecimalRegExp.test(control.value)) {
      return { 'numeroDecimalInvalido': true };
    }
    return null;
  }
  /**
   * Método encargado de construir el formulario reactivo
   * 
   */
  initFormTwo(): FormGroup {
    //const statusValue = this.data.status === "Activo" ? "1" : "0";

    return this.formBuilder.group({
      transactionCode: [this.data.transactionCode, [Validators.required, Validators.maxLength(5)]],
      userAs: [this.data.userAs, []],
      dateConsultation: [this.datePipe.transform(this.data.dateConsultation, 'yyyy-MM-dd HH:mm:ss'), [Validators.required]],

      customerAccount: [this.data.customerAccount, [Validators.required, Validators.maxLength(30)]],
      serviceAccount: [this.data.serviceAccount, [Validators.required, Validators.maxLength(30)]],
      billingAccount: [this.data.billingAccount, [Validators.required, Validators.maxLength(30)]],
      phoneNumber: [this.data.phoneNumber, [Validators.required, Validators.maxLength(12), Validators.pattern('^[0-9]+$')]],
      equipmentModel: [this.data.equipmentModel, [Validators.required, Validators.maxLength(6)]],
      esn: [this.data.esn, [Validators.required, Validators.maxLength(15), Validators.pattern('^[0-9]+$')]],
      originAs: [this.data.originAs, []],
      inventoryTypeAs: [this.data.inventoryTypeAs, []],
      originTypeAs: [this.data.originTypeAs, []],
      dateContract: [this.datePipe.transform(this.data.dateContract, 'yyyy-MM-dd HH:mm:ss'), [Validators.required]],
      startDate: [this.datePipe.transform(this.data.startDate, 'yyyy-MM-dd HH:mm:ss'), [Validators.required]],
      endDate: [this.datePipe.transform(this.data.endDate, 'yyyy-MM-dd HH:mm:ss'), [Validators.required]],
      dateInclusion: [this.datePipe.transform(this.data.dateInclusion, 'yyyy-MM-dd HH:mm:ss'), [Validators.required]],
      insuranceRate: [this.data.insuranceRate.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      period: [this.data.period, [Validators.required, Validators.pattern('^[0-9]+$')]],
      insuranceRate2: [this.data.insuranceRate2.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      period2: [this.data.period2, [Validators.required, Validators.pattern('^[0-9]+$')]],
      insuranceRate3: [this.data.insuranceRate3.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      period3: [this.data.period3, [Validators.required, Validators.pattern('^[0-9]+$')]],
      insuranceStatus: [""],
      subscriber: [this.data.subscriber, [Validators.maxLength(30), Validators.pattern('^[0-9]+$')]],

    });
  }


  // Operación
  crudEquipmentInsurance() {

    const title: string = this.action === 'add' ? "crear" : "actualizar"

    Swal.fire({

      title: 'Advertencia',
      text: `¿Desea ${title} este seguro?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {
        const data: EquipmentInsuranceModel = this.form.value;

        data.dateContract = this.datePipe.transform(data.dateContract, 'yyyy-MM-ddTHH:mm:ss.SSS');
        data.dateConsultation = this.datePipe.transform(data.dateConsultation, 'yyyy-MM-ddTHH:mm:ss.SSS');
        data.startDate = this.datePipe.transform(data.startDate, 'yyyy-MM-ddTHH:mm:ss.SSS');
        data.endDate = this.datePipe.transform(data.endDate, 'yyyy-MM-ddTHH:mm:ss.SSS');
        data.dateInclusion = this.datePipe.transform(data.dateInclusion, 'yyyy-MM-ddTHH:mm:ss.SSS');

        if (this.action === "add") {
          const VALIDATE_ADD = await this.postEquipmentInsurance(data);

          if (VALIDATE_ADD) {

            this.utilService.showNotification(0, "Seguro creado con éxito");

            this.messageEvent.emit(true);
            this.closeModal();
          }

        } else {

          const VALIDATE_UPDATE = await this.putEquipmentInsurance(this.data.id, data);

          if (VALIDATE_UPDATE) {

            this.utilService.showNotification(0, "Seguro actualizado con éxito");

            this.messageEvent.emit(true);
            this.closeModal();
          }

        }

      }

    });

  }


  //Rest
  postEquipmentInsurance(data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consumir el servicio
      this.safeControlEquipmentService.postEquipmentInsurance(data).subscribe((response) => {

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

  putEquipmentInsurance(id: any, data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consumir el servicio
      this.safeControlEquipmentService.putEquipmentInsurance(id, data).subscribe((response) => {

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
