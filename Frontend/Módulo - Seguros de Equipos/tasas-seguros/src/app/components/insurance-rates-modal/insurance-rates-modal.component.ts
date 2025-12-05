import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { InsuranceRatesModel } from 'src/app/models/model';
import { InsuranceRatesService } from 'src/app/services/insurance-rates.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-insurance-rates-modal',
  templateUrl: './insurance-rates-modal.component.html',
  styleUrls: ['./insurance-rates-modal.component.css']
})
export class InsuranceRatesModalComponent implements OnInit {

  // Props

  // Input y Output
  @Input() button: string;
  @Input() data: InsuranceRatesModel;
  @Output() messageEvent = new EventEmitter<boolean>();

  // Form
  dataForm!: FormGroup;
  messages = messages;

  // Readonly
  inputReadOnly: boolean = false;

  // Dates
  creationDate: string = "";


  constructor(public utilService: UtilService, private insuranceRatesService: InsuranceRatesService, private datePipe: DatePipe, private activeModal: NgbActiveModal, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    if (this.button === "see") {
      this.inputReadOnly = true;
    }

    if (this.button === "see" || this.button === "edit") {

      this.dataForm = this.initFormTwo();
    } else {
      this.dataForm = this.initForm();
    }
  }


  // Methods Screen

  // Methods for initial Form

  /**
   * Se usa solo para la creación
   * 
   * @returns 
   */
  initForm(): FormGroup {
    return this.formBuilder.group({
      efectiveDate: ['', [Validators.required]],
      periodNumber: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      valueFrom: [0.00.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      valueUp: [0.00.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      monthlyFee: [0.00.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      textCoverage: ['', [Validators.required, Validators.maxLength(80)]],
      model: ['', [Validators.required, Validators.maxLength(6)]],
    });
  }

  /**
   * Se usa solo para visualizar o modificar
   * 
   * @returns 
   */
  initFormTwo(): FormGroup {
    return this.formBuilder.group({
      efectiveDate: [this.datePipe.transform(this.data.efectiveDate, 'yyyy-MM-dd HH:mm:ss'), [Validators.required]],
      periodNumber: [this.data.periodNumber, [Validators.required, Validators.pattern('^[0-9]+$')]],
      valueFrom: [this.data.valueFrom.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      valueUp: [this.data.valueUp.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      monthlyFee: [this.data.monthlyFee, [Validators.required, this.validarNumeroDecimal]],
      textCoverage: [this.data.textCoverage, [Validators.required, Validators.maxLength(80)]],
      model: [this.data.model, [Validators.required, Validators.maxLength(6)]],
    });
  }

  validarNumeroDecimal(control) {
    const numeroDecimalRegExp = /^[0-9]+(\.[0-9]{1,2})?$/;
    if (control.value && !numeroDecimalRegExp.test(control.value)) {
      return { 'numeroDecimalInvalido': true };
    }
    return null;
  }


  closeModal() {
    this.activeModal.close();
  }

  crudPriceMaster() {
    let action: string = this.button === 'add' ? "crear" : "modificar";

    Swal.fire({
      title: 'Advertencia',
      text: `¿Desea ${action} la tasa de seguros?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {
        const data: InsuranceRatesModel = this.dataForm.value;
        data.efectiveDate = this.datePipe.transform(data.efectiveDate, 'yyyy-MM-ddTHH:mm:ss.SSS');


        if (this.button === 'add') {

          const VALIDATE_CREATION = await this.postInsuranceRates(data);

          if (VALIDATE_CREATION) {
            this.utilService.showNotification(0, "La tasa de seguro se creo exitosamente");
            this.messageEvent.emit(true);
            this.closeModal();
          }

        } else {



          const VALIDATE_UPDATION = await this.putInsuranceRates(this.data.id, data);

          if (VALIDATE_UPDATION) {
            this.utilService.showNotification(0, "La tasa de seguro se modifico exitosamente");

            this.messageEvent.emit(true);
            this.closeModal();

          }


        }


      }

    })
  }

  // Methods Services

  /**
 * Método que consume un servicio para crear una tasa de seguro
 * 
 * @param data 
 * @returns 
 */
  postInsuranceRates(data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.insuranceRatesService.postInsuranceRates(data).subscribe((response) => {

        if (response.status === 200) {
          resolve(true);
        } else {
          resolve(false);
        }

      }, (erro) => {
        resolve(false);
      });

    });

  }

  /**
   * Método que consume un servicio para actualizar una tasa de seguro
   * 
   * @param id 
   * @param data 
   * @returns 
   */
  putInsuranceRates(id: any, data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.insuranceRatesService.putInsuranceRates(id, data).subscribe((response) => {

        if (response.status === 200) {
          resolve(true);
        } else {
          resolve(false);
        }

      }, (erro) => {
        resolve(false);
      });

    });

  }

}
