import { DatePipe } from '@angular/common';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BillingServicesModel } from 'src/app/model/model';
import { JsonService } from 'src/app/services/json.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-billing-services-crud',
  templateUrl: './billing-services-crud.component.html',
  styleUrls: ['./billing-services-crud.component.css']
})
export class BillingServicesCrudComponent implements OnInit {

  // Props
  // Input y Output
  @Input() button: string;
  @Input() data: BillingServicesModel;
  @Output() messageEvent = new EventEmitter<boolean>();

  // Form
  dataForm!: FormGroup;
  messages = messages;

  // Readonly
  inputReadOnly: boolean = false;

  //Dates
  creationDate: string = "";
  updateDate: string = "";

  constructor(private billingService: JsonService,private activeModal: NgbActiveModal,private formBuilder: FormBuilder, private datePipe:DatePipe, public utilService: UtilService) { }

  ngOnInit(): void {

    
    if(this.button==="see"){
      this.inputReadOnly = true;
    }
    
    if(this.button==="see" || this.button==="edit"){
      this.getDate();
      this.dataForm = this.initFormTwo();
    }else{
      this.dataForm = this.initForm();
    }


  }

  initForm(): FormGroup {
    return this.formBuilder.group({
      serviceCode: ['', [Validators.required]],
      serviceName: ['', [Validators.required, Validators.maxLength(150)]],
      totalQuantity: [0.00.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      creationUser: [this.utilService.getSystemUser(), []],
      status: [1, [Validators.required]]
    });
  }

  initFormTwo(): FormGroup {
    return this.formBuilder.group({
      serviceCode: [this.data.serviceCode, [Validators.required]],
      serviceName: [this.data.serviceName, [Validators.required, Validators.maxLength(150)]],
      totalQuantity: [this.data.totalQuantity.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      creationUser: [this.data.creationUser, []],
      creationDate: [this.creationDate, []],
      modificationUser: [this.data.modificationUser, []],
      modificationDate: [this.updateDate, []],
      status: [this.data.statusCode, [Validators.required]]
    });
  }

  validarNumeroDecimal(control) {
    const numeroDecimalRegExp = /^[0-9]+(\.[0-9]{1,2})?$/;
    if (control.value && !numeroDecimalRegExp.test(control.value)) {
      return { 'numeroDecimalInvalido': true };
    }
    return null;
  }

  getDate(){
    this.creationDate = this.datePipe.transform(this.data.creationDate, "dd/MM/yyyy hh:mm:ss");
    this.updateDate = this.datePipe.transform(this.data.modificationDate, "dd/MM/yyyy hh:mm:ss");
  }

  closeModal(){
    this.activeModal.close();
  }

  async crudBillingService(){
    
    let action: string = this.button==='add' ? "crear" : "modificar"; 

    Swal.fire({
      title: 'Advertencia',
      text: `¿Desea ${action} el servicio de facturación?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any)=>{

      if(result.value){
        let data: BillingServicesModel = this.dataForm.value;
        data.totalQuantity = Number(data.totalQuantity);

        if(this.button==='add'){
          
          const VALIDATE_CREATION = await this.addBillingService(data);

          if(VALIDATE_CREATION){
            this.utilService.showNotification(0, "El servicio de facturación se creo exitosamente");
            this.messageEvent.emit(true);
            this.closeModal();
          }
          
        }else{

          data.modificationUser = this.utilService.getSystemUser();
          data.status = Number(data.status);
          delete data.creationDate;
          delete data.modificationDate;
          delete data.creationUser;
         // console.log(data);

          const VALIDATE_UPDATION = await this.updateBillingService(this.data.id, data);
          
          if(VALIDATE_UPDATION){
            this.utilService.showNotification(0, "El servicio de facturación se modifico exitosamente");

            this.messageEvent.emit(true);
            this.closeModal();

          }

        }
      }

    })
  }

  // Métodos Asyncronos

  /**
   * Método que consume un servicio para crear un servicio de facturación
   * 
   * @param data 
   * @returns 
   */
  addBillingService(data:any): Promise<boolean>{
    return new Promise((resolve, reject)=>{

      this.billingService.postBillingService(data).subscribe((response)=>{

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
   * Método que consume un servicio para actualizar un servicio de facturación
   * 
   * @param id 
   * @param data 
   * @returns 
   */
  updateBillingService(id:any, data:any): Promise<boolean>{
    return new Promise((resolve, reject)=>{

      this.billingService.putBillingService(id, data).subscribe((response)=>{

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
