import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ResponseWithOutData } from 'src/app/entities/invoice.entity';
import { Billing, ExoneradoCorportive } from 'src/app/models/billing';
import { InvoiceService } from 'src/app/services/invoice.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-load-oce',
  templateUrl: './load-oce.component.html',
  styleUrls: ['./load-oce.component.css']
})
export class LoadOceComponent implements OnInit {

  // Props
  // Input y Output
  @Output() messageEvent = new EventEmitter<boolean>();
  @Input() billing: Billing;
  messages = messages;

  // Form
  dataExo!: FormGroup;

  // Select
  hiddenExo: boolean = true;
  typeClient: number = 1;

  constructor(private activeModal: NgbActiveModal, private invoiceService: InvoiceService, public utilsService: UtilService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.dataExo = this.initForm();
  }

  initForm(): FormGroup {

    return this.formBuilder.group({
      diplomaticCardNo: ["", [Validators.required]],
      correlativeOrdenExemptNo: ["", []],
      correlativeCertificateExoNo: ["", []]
    });

  }

  closeModal() {
    this.activeModal.close();
  }

  /**
   * Método que controla las validaciones de los inputs
   * 
   * @param event 
   */
  changeType(event) {
    //console.log(event.target.value);
    const type: any = event.target.value;

    this.typeClient = type;

    const diplomaticCardNoControl = this.dataExo.get('diplomaticCardNo') as FormControl;
    const correlativeOrdenExemptNoControl = this.dataExo.get('correlativeOrdenExemptNo') as FormControl;
    const correlativeCertificateExoNoControl = this.dataExo.get('correlativeCertificateExoNo') as FormControl;
    
    if(type==1){
      this.hiddenExo = true;
      diplomaticCardNoControl.setValidators([Validators.required]);
      diplomaticCardNoControl.setValue('');

      correlativeOrdenExemptNoControl.clearValidators();
      correlativeOrdenExemptNoControl.setValue('');
      correlativeCertificateExoNoControl.clearValidators();
      correlativeCertificateExoNoControl.setValue('');

    }else{
      this.hiddenExo = false;
      correlativeOrdenExemptNoControl.setValidators([Validators.required]);
      correlativeOrdenExemptNoControl.setValue('');
      correlativeCertificateExoNoControl.setValidators([Validators.required]);
      correlativeCertificateExoNoControl.setValue('');

      diplomaticCardNoControl.clearValidators();
      diplomaticCardNoControl.setValue('');
    }

    diplomaticCardNoControl.updateValueAndValidity();
    correlativeOrdenExemptNoControl.updateValueAndValidity();
    correlativeCertificateExoNoControl.updateValueAndValidity();

  }

  /**
 * Método encargado de actualizar la factura a exonerada
 * 
 */
  async updateDataExonerada() {

    Swal.fire({
      title: 'Advertencia',
      text: "¿Desea exonerar esta factura?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {
        const data: ExoneradoCorportive = this.dataExo.value;

        if(this.typeClient==1){
          delete data.correlativeOrdenExemptNo;
          delete data.correlativeCertificateExoNo;

          
          const VALIDATE_EXO = this.putUpdateExoneradoSimpleClient(this.billing.id, data);

          if (VALIDATE_EXO) {
            this.utilsService.showNotification(0, `La factura con id ${this.billing.id} fue exonerada exitosamente, a continuación se volverá a cargar la página`);
  
            this.messageEvent.emit(true);
            this.closeModal();
  
          }
          

        }else{
          delete data.diplomaticCardNo;
          
          const VALIDATE_EXO = this.putUpdateExonerado(this.billing.id, data);

          if (VALIDATE_EXO) {
            this.utilsService.showNotification(0, `La factura con id ${this.billing.id} fue exonerada exitosamente, a continuación se volverá a cargar la página`);
  
            this.messageEvent.emit(true);
            this.closeModal();
  
          }
          
        }

        console.log(data);

      }

    });

  }


  /**
 * Método que consume un servicio para actualizar la factura a exonerada
 * si es un cliente normal
 * 
 * @param id 
 * @returns 
 */
  putUpdateExoneradoSimpleClient(id: any, data: any): Promise<boolean> {

    return new Promise((resolve, reject) => {

      this.invoiceService.putUpdateExoneradoSimpleClient(id, data).subscribe((response) => {

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

    /**
   * Método que consume un servicio para actualizar la factura a exonerada
   * si es un corporativo
   * 
   * @param id 
   * @returns 
   */
    putUpdateExonerado(id:any, data: any ): Promise<boolean> {
    
      return new Promise((resolve, reject)=>{
  
        this.invoiceService.putUpdateExonerado(id, data).subscribe((response)=>{
  
          if(response.status === 200){
  
            resolve(true);
  
          }else{
            resolve(false);
          }
  
        }, (error)=> {
          resolve(false);
        });
  
      });
  
    }


}
