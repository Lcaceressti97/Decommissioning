import { DatePipe } from '@angular/common';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { PriceMasterModel } from 'src/app/model/model';
import { EquipmentAccesoriesService } from 'src/app/services/equipment-accesories.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';
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
  inputReadOnlyConvert: boolean = true;

  inputReadOnlyPriceESN: boolean = true; // Lo usaremos para ocultar o no los últimos inputs

  // Dates
  creationDate: string = "";

  // SÍ / NO
  currencyOption: string = "";
  changeEsnOption: string = "";

  constructor(private activeModal: NgbActiveModal,private formBuilder: FormBuilder, private datePipe:DatePipe, public utilService: UtilService, private equipmentAccesoriesService: EquipmentAccesoriesService) { }

  ngOnInit(): void {

    if(this.button==="see" || this.button==="edit"){
      this.getDate();
      this.dataForm = this.initFormTwo();
    }else{
      this.dataForm = this.initForm();
    }

    if(this.button==="see"){
      this.inputReadOnly = true;
      this.inputReadOnlyPriceESN = this.data.priceChangeEsn=="S" ? false : true;
      this.currencyOption = this.data.convertLps=="S" ? "Sí" : "No";
      this.changeEsnOption = this.data.priceChangeEsn=="S" ? "Sí" : "No";

      const ControlCurrency = this.dataForm.get('convertLps') as FormControl;
      ControlCurrency.setValue(this.currencyOption);

      const ControlPriceLps = this.dataForm.get('priceLps') as FormControl;
      ControlPriceLps.setValue(this.data.priceLps);
      ControlPriceLps.updateValueAndValidity();

      const ControlChaneEsn = this.dataForm.get('priceChangeEsn') as FormControl;
      ControlChaneEsn.setValue(this.changeEsnOption);
    }

    if(this.button==="edit"){
      const controlPriceLps = this.dataForm.get('priceLps') as FormControl;
      const controlPriceEsn = this.dataForm.get('priceEsn') as FormControl;
      const controlPriceLpsEsn = this.dataForm.get('priceLpsEsn') as FormControl;
      const controlEsn = this.dataForm.get('esn') as FormControl;
      

      if(this.data.convertLps=="S"){
        controlPriceLps.setValidators([Validators.required, this.validarNumeroDecimal]);
        controlPriceLps.setValue(this.data.priceLps.toFixed(2));
        this.inputReadOnlyConvert = false;
      }else{
        this.inputReadOnlyConvert = true;
        controlPriceLps.setValue(0.00.toFixed(2));
        controlPriceLps.clearValidators();
      }

      if(this.data.priceChangeEsn=="S"){
        controlPriceEsn.setValidators([Validators.required, this.validarNumeroDecimal]);
        controlPriceEsn.setValue(this.data.priceEsn.toFixed(2));
        controlPriceLpsEsn.setValidators([Validators.required, this.validarNumeroDecimal]);
        controlPriceLpsEsn.setValue(this.data.priceLpsEsn.toFixed(2));
        controlEsn.setValidators([Validators.required, Validators.maxLength(50)]);
        this.inputReadOnlyPriceESN = false;
      }else{
        this.inputReadOnlyPriceESN = true;
        controlPriceEsn.setValue(0);
        controlPriceEsn.clearValidators();
        controlPriceLpsEsn.setValue(0);
        controlPriceLpsEsn.clearValidators();
        controlEsn.setValue(0);
        controlEsn.clearValidators();
      }

      controlPriceLps.updateValueAndValidity();
      controlPriceEsn.updateValueAndValidity();
      controlPriceLpsEsn.updateValueAndValidity();
      controlEsn.updateValueAndValidity();
      
      

    }
    
  }

  closeModal(){
    this.activeModal.close();
  }

  // Methods

  initForm(): FormGroup {
    return this.formBuilder.group({
      inventoryType: ['', [Validators.required, Validators.maxLength(50)]],
      model: ['', [Validators.required, Validators.maxLength(250)]],
      description: ['', [Validators.required, Validators.maxLength(250)]],
      baseCost: [0.00.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      factorCode: [0, [Validators.required, this.validarNumeroFactor]],
      price: [0.00.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      userCreated: [this.utilService.getSystemUser(), []],
      screen: ['', [Validators.required, Validators.maxLength(50)]],
      created: ['', []],
      currency: ['', [Validators.required, Validators.maxLength(50)]],
      convertLps: ['N', [Validators.required]],
      priceLps: [0.00.toFixed(2), []],
      lastCost: [0.00.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      costTemporary: [0.00.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      priceChangeEsn: ['N', []],
      priceEsn: [0.00.toFixed(2), []],
      priceLpsEsn: [0.00.toFixed(2), []],
      esn: ['', []],
    });
  }

  initFormTwo(): FormGroup {
    return this.formBuilder.group({
      inventoryType: [this.data.inventoryType, [Validators.required, Validators.maxLength(50)]],
      model: [this.data.model, [Validators.required, Validators.maxLength(250)]],
      description: [this.data.description, [Validators.required, Validators.maxLength(250)]],
      baseCost: [this.data.baseCost.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      factorCode: [this.data.factorCode, [Validators.required, this.validarNumeroFactor]],
      price: [this.data.price.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      userCreated: [this.data.userCreated, []],
      screen: [this.data.screen, [Validators.required]],
      created: [this.creationDate, []],
      currency: [this.data.currency, [Validators.required, Validators.maxLength(50)]],
      convertLps: [this.data.convertLps, [Validators.required]],
      priceLps: [0.00.toFixed(2), []],
      lastCost: [this.data.lastCost.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      costTemporary: [this.data.costTemporary.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      priceChangeEsn: [this.data.priceChangeEsn, []],
      priceEsn: [0.00.toFixed(2), []],
      priceLpsEsn: [0.00.toFixed(2), []],
      esn: [this.data.esn, []],
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

  formatearNumero(valor: number): string {
    //console.log(valor.toFixed(2));
    //console.log(Number(valor.toFixed(2)));
    return valor.toFixed(2); // Devuelve el número con dos decimales
  }

  /**
   * Método que nos ayuda a indicar si selecciono Sí se habilitan los otros inputs y sus validaciones
   * si selecciono no se inhabilitan y salen vacios
   * 
   * @param event 
   */
  changeConvertLps(event){
    const option: string = event.target.value;
    //console.log(option);
    const controlPriceLps = this.dataForm.get('priceLps') as FormControl;

    if (option == "S") {

      controlPriceLps.setValidators([Validators.required, this.validarNumeroDecimal]);
      controlPriceLps.setValue(0.00.toFixed(2));
      this.inputReadOnlyConvert = false;

    } else {
      this.inputReadOnlyConvert = true;
      controlPriceLps.setValue(0.00.toFixed(2));
      controlPriceLps.clearValidators();
    }
    controlPriceLps.updateValueAndValidity();
  }

  /**
   * Método que nos ayuda a indicar si selecciono Sí se habilitan los otros inputs y sus validaciones
   * si selecciono no se inhabilitan y salen vacios
   * 
   * @param event 
   */
  changePrecioESN(event){
    const option: string = event.target.value;
    //console.log(option);
    const controlPriceEsn = this.dataForm.get('priceEsn') as FormControl;
    const controlPriceLpsEsn = this.dataForm.get('priceLpsEsn') as FormControl;
    const controlEsn = this.dataForm.get('esn') as FormControl;

    if (option == "S") {

      controlPriceEsn.setValidators([Validators.required, this.validarNumeroDecimal]);
      controlPriceEsn.setValue(0.00.toFixed(2));
      controlPriceLpsEsn.setValidators([Validators.required, this.validarNumeroDecimal]);
      controlPriceLpsEsn.setValue(0.00.toFixed(2));
      controlEsn.setValidators([Validators.required, Validators.maxLength(50)]);
      this.inputReadOnlyPriceESN = false;

    } else {
      this.inputReadOnlyPriceESN = true;
      controlPriceEsn.setValue(0.00.toFixed(2));
      controlPriceEsn.clearValidators();
      controlPriceLpsEsn.setValue(0.00.toFixed(2));
      controlPriceLpsEsn.clearValidators();
      controlEsn.setValue('');
      controlEsn.clearValidators();
    }
    controlPriceEsn.updateValueAndValidity();
    controlPriceLpsEsn.updateValueAndValidity();
    controlEsn.updateValueAndValidity();
  }


  getDate(){
    this.creationDate = this.datePipe.transform(this.data.created, "dd/MM/yyyy hh:mm:ss");
  }

  crudPriceMaster(){
    let action: string = this.button==='add' ? "crear" : "modificar"; 

    Swal.fire({
      title: 'Advertencia',
      text: `¿Desea ${action} el precio?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any)=>{

      if(result.value){
        const data: PriceMasterModel = this.dataForm.value;
        data.baseCost = Number(data.baseCost);
        data.costTemporary = Number(data.costTemporary);
        data.factorCode = Number(data.factorCode);
        data.price = Number(data.price);
        data.priceEsn = Number(data.priceEsn);
        data.priceLps = Number(data.priceLps);
        data.priceLpsEsn = Number(data.priceLpsEsn);
        data.lastCost = Number(data.lastCost);

        delete data.created;
        //console.log(data);

        
        if(this.button==='add'){
          
          const VALIDATE_CREATION = await this.postPriceMaster(data);

          if(VALIDATE_CREATION){
            this.utilService.showNotification(0, "El precio se creo exitosamente");
            this.messageEvent.emit(true);
            this.closeModal();
          }
          
        }else{



          const VALIDATE_UPDATION = await this.putPriceMaster(this.data.id, data);
          
          if(VALIDATE_UPDATION){
            this.utilService.showNotification(0, "El precio se modifico exitosamente");

            this.messageEvent.emit(true);
            this.closeModal();

          }
          

        }
        

      }

    })
  }


    // Métodos Asyncronos

  /**
   * Método que consume un servicio para crear un precio
   * 
   * @param data 
   * @returns 
   */
  postPriceMaster(data:any): Promise<boolean>{
    return new Promise((resolve, reject)=>{

      this.equipmentAccesoriesService.postPriceMaster(data).subscribe((response)=>{

        if(response.status===200){
          resolve(true);
        }else{
          resolve(false);
        }

      }, (erro)=>{
        resolve(false);
      });

    });

  }

  /**
   * Método que consume un servicio para actualizar un precio
   * 
   * @param id 
   * @param data 
   * @returns 
   */
  putPriceMaster(id:any, data:any): Promise<boolean>{
    return new Promise((resolve, reject)=>{

      this.equipmentAccesoriesService.putPriceMaster(id, data).subscribe((response)=>{

        if(response.status===200){
          resolve(true);
        }else{
          resolve(false);
        }

      }, (erro)=>{
        resolve(false);
      });

    });

  }


}
