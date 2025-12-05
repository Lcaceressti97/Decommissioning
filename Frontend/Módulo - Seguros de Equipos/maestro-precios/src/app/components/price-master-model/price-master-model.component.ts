import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { PriceMasterModel } from 'src/app/models/model';
import { PriceMasterService } from 'src/app/services/price-master.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enum';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-price-master-model',
  templateUrl: './price-master-model.component.html',
  styleUrls: ['./price-master-model.component.css']
})
export class PriceMasterModelComponent implements OnInit {


  // Props

  // Input y Output
  @Input() button: string;
  @Input() data: PriceMasterModel;
  @Output() messageEvent = new EventEmitter<boolean>();

  // Form
  dataForm!: FormGroup;
  messages = messages;

  // Readonly
  inputReadOnly: boolean = false;

  // Dates
  creationDate: string = "";


  constructor(public utilService: UtilService, private priceMasterService: PriceMasterService, private datePipe: DatePipe, private activeModal: NgbActiveModal, private formBuilder: FormBuilder) { }

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
      inventoryType: ['', [Validators.required, Validators.maxLength(1)]],
      model: ['', [Validators.required, Validators.maxLength(6)]],
      baseCost: [0.00.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      factorCode: [0, [Validators.required, this.validarNumeroFactor]],
      price: [0.00.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      userCreated: [this.utilService.getSystemUser(), []],
      screen: ['', [Validators.required, Validators.maxLength(50)]],
      createdDate: ['', []],
      currency: ['', []],
      lempirasPrice: [0.00.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      temporaryCost: [0.00.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
    });
  }

  /**
   * Se usa solo para visualizar o modificar
   * 
   * @returns 
   */
  initFormTwo(): FormGroup {
    return this.formBuilder.group({
      inventoryType: [this.data.inventoryType, [Validators.required, Validators.maxLength(1)]],
      model: [this.data.model, [Validators.required, Validators.maxLength(6)]],
      baseCost: [this.data.baseCost.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      factorCode: [this.data.factorCode, [Validators.required, this.validarNumeroFactor]],
      price: [this.data.price.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      userCreated: [this.data.userCreated, []],
      screen: [this.data.screen, []],
      createdDate: [this.creationDate, []],
      currency: [this.data.currency, []],
      lempirasPrice: [this.data.lempirasPrice.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      temporaryCost: [this.data.temporaryCost.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
    });
  }

  validarNumeroDecimal(control) {
    const numeroDecimalRegExp = /^[0-9]+(\.[0-9]{1,2})?$/;
    if (control.value && !numeroDecimalRegExp.test(control.value)) {
      return { 'numeroDecimalInvalido': true };
    }
    return null;
  }

  validarNumeroFactor(control) {
    const numeroDecimalRegExp = /^[0-9]{1,3}$/;
    if (control.value && !numeroDecimalRegExp.test(control.value)) {
      return { 'numeroDecimalInvalido': true };
    }
    return null;
  }

  getDate() {
    this.creationDate = this.datePipe.transform(this.data.createdDate, "dd/MM/yyyy hh:mm:ss");
  }


  closeModal() {
    this.activeModal.close();
  }


  crudPriceMaster() {
    let action: string = this.button === 'add' ? "crear" : "modificar";

    Swal.fire({
      title: 'Advertencia',
      text: `¿Desea ${action} el precio?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {
        const data: PriceMasterModel = this.dataForm.value;
        data.baseCost = Number(data.baseCost);
        data.temporaryCost = Number(data.temporaryCost);
        data.factorCode = Number(data.factorCode);
        data.price = Number(data.price);
        delete data.createdDate;
        //console.log(data);


        if (this.button === 'add') {

          const VALIDATE_CREATION = await this.postPriceMaster(data);

          if (VALIDATE_CREATION) {
            this.utilService.showNotification(0, "El precio se creo exitosamente");
            this.messageEvent.emit(true);
            this.closeModal();
          }

        } else {



          const VALIDATE_UPDATION = await this.putPriceMaster(this.data.id, data);

          if (VALIDATE_UPDATION) {
            this.utilService.showNotification(0, "El precio se modifico exitosamente");

            this.messageEvent.emit(true);
            this.closeModal();

          }


        }


      }

    })
  }

  // Methods Services

  /**
 * Método que consume un servicio para crear un precio maestro
 * 
 * @param data 
 * @returns 
 */
  postPriceMaster(data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.priceMasterService.postPriceMaster(data).subscribe((response) => {

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
   * Método que consume un servicio para actualizar un precio maestro
   * 
   * @param id 
   * @param data 
   * @returns 
   */
  putPriceMaster(id: any, data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.priceMasterService.putPriceMaster(id, data).subscribe((response) => {

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
