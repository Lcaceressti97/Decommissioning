import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ChannelModel } from 'src/app/model/model';
import { InvoiceService } from 'src/app/services/invoice.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enum';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-channel-settings-modal',
  templateUrl: './channel-settings-modal.component.html',
  styleUrls: ['./channel-settings-modal.component.css']
})
export class ChannelSettingsModalComponent implements OnInit {

  // Props

  // Input y Output
  @Input() button: string;
  @Input() data: ChannelModel;
  @Output() messageEvent = new EventEmitter<boolean>();

  // Form
  dataForm!: FormGroup;
  messages = messages;

  // Readonly
  inputReadOnly: boolean = false;

  constructor(public utilService: UtilService, private invoiceService: InvoiceService, private activeModal: NgbActiveModal, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    if(this.button==="see"){
      this.inputReadOnly = true;
    }
    
    if(this.button==="see" || this.button==="edit"){
     
      this.dataForm = this.initFormTwo();
    }else{
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
      name: ['', [Validators.required, Validators.maxLength(150)]],
      description: ['', [Validators.required, Validators.maxLength(150)]],
      status: [1, [Validators.required]],
      created: ['', []],
      payUpFrontNumber: [0, [Validators.required]],
      nonFiscalNote: [0, [Validators.required]],
      reserveSerialNumber: [0, [Validators.required]],
      releaseSerialNumber: [0, [Validators.required]],
      inventoryDownload: [0, [Validators.required]],
      generateTrama: [0, [Validators.required]],
      logs: [0, [Validators.required]],
    });
  }

  /**
   * Se usa solo para visualizar o modificar
   * 
   * @returns 
   */
  initFormTwo(): FormGroup {
    return this.formBuilder.group({
      name: [this.data.name, [Validators.required, Validators.maxLength(150)]],
      description: [this.data.description, [Validators.required, Validators.maxLength(150)]],
      status: [this.data.status, [Validators.required]],
      created: [this.data.created, []],
      payUpFrontNumber: [this.data.payUpFrontNumber, [Validators.required]],
      nonFiscalNote: [this.data.nonFiscalNote, [Validators.required]],
      reserveSerialNumber: [this.data.reserveSerialNumber, [Validators.required]],
      releaseSerialNumber: [this.data.releaseSerialNumber, [Validators.required]],
      inventoryDownload: [this.data.inventoryDownload, [Validators.required]],
      generateTrama: [this.data.generateTrama, [Validators.required]],
      logs: [this.data.logs, [Validators.required]],
    });
  }

  closeModal() {
    this.activeModal.close();
  }


  async crudChannel() {

    let action: string = this.button === 'add' ? "crear" : "modificar";

    Swal.fire({
      title: 'Advertencia',
      text: `¿Desea ${action} el canal de facturación?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {
        let data: ChannelModel = this.dataForm.value;

        if (this.button === 'add') {

          const VALIDATE_CREATION = await this.addChannel(data);

          if (VALIDATE_CREATION) {
            this.utilService.showNotification(0, "El canal de facturación se creo exitosamente");
            this.messageEvent.emit(true);
            this.closeModal();
          }

        } else {

          data.status = Number(data.status);
          delete data.created;
          // console.log(data);

          const VALIDATE_UPDATION = await this.updateChannel(this.data.id, data);

          if (VALIDATE_UPDATION) {
            this.utilService.showNotification(0, "El canal de facturación se modifico exitosamente");

            this.messageEvent.emit(true);
            this.closeModal();

          }

        }
      }

    })
  }

  // Methods Services

  /**
 * Método que consume un servicio para crear un servicio de facturación
 * 
 * @param data 
 * @returns 
 */
  addChannel(data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.invoiceService.postChannel(data).subscribe((response) => {

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
   * Método que consume un servicio para actualizar un servicio de facturación
   * 
   * @param id 
   * @param data 
   * @returns 
   */
  updateChannel(id: any, data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.invoiceService.putChannel(id, data).subscribe((response) => {

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
