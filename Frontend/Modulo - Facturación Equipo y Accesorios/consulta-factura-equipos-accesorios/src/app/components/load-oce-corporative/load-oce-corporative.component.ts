import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ResponseWithOutData } from 'src/app/entities/invoice.entity';
import { Billing, ExoneradoCorportive } from 'src/app/models/billing';
import { InvoiceService } from 'src/app/services/invoice.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-load-oce-corporative',
  templateUrl: './load-oce-corporative.component.html',
  styleUrls: ['./load-oce-corporative.component.css']
})
export class LoadOceCorporativeComponent implements OnInit {

  // Props

  // Input y Output
  @Output() messageEvent = new EventEmitter<boolean>();
  @Input() billing: Billing;
  messages = messages;

  // Form
  dataExo!: FormGroup;

  constructor(private activeModal: NgbActiveModal, private invoiceService:InvoiceService, public utilsService: UtilService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    //console.log(this.billing);
    this.dataExo = this.initForm();
  }

  initForm(): FormGroup {

    return this.formBuilder.group({
      correlativeOrdenExemptNo: ["", [Validators.required]],
      correlativeCertificateExoNo: ["", [Validators.required]]
    });

  }

  /**
   * Método encargado de actualizar la factura a exonerada
   * 
   */
  async updateDataExonerada(){

    Swal.fire({
      title: 'Advertencia',
      text: "¿Desea exonerar esta factura?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if(result.value){
        const data: ExoneradoCorportive = this.dataExo.value;


        const VALIDATE_EXO = this.putUpdateExonerado(this.billing.id, data);

        if(VALIDATE_EXO){
          this.utilsService.showNotification(0, `La factura con id ${this.billing.id} fue exonerada exitosamente, a continuación se volverá a cargar la página`);

          this.messageEvent.emit(true);
          this.closeModal();

        }

      }

    });

  }

  closeModal(){
    this.activeModal.close();
  }


  /**
   * Método que consume un servicio para actualizar la factura a exonerada
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
