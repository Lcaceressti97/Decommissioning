import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ReasonModel } from 'src/app/models/model';
import { ReasonsService } from 'src/app/services/reasons.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enum';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-reasons-modal',
  templateUrl: './reasons-modal.component.html',
  styleUrls: ['./reasons-modal.component.css']
})
export class ReasonsModalComponent implements OnInit {

  // Props

  // Input y Output
  @Input() button: string;
  @Input() data: ReasonModel;
  @Output() messageEvent = new EventEmitter<boolean>();

  // Form
  dataForm!: FormGroup;
  messages = messages;

  // Readonly
  inputReadOnly: boolean = false;

  // Dates
  creationDate: string = "";

  constructor(public utilService: UtilService, private reasonService: ReasonsService, private datePipe: DatePipe, private activeModal: NgbActiveModal, private formBuilder: FormBuilder) { }

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
      reason: ['', [Validators.required]],
      description: ['', [Validators.required]],
      status: ['', []],
      createdDate: ['', []]
    });
  }

  /**
   * Se usa solo para visualizar o modificar
   * 
   * @returns 
   */
  initFormTwo(): FormGroup {
    return this.formBuilder.group({
      reason: [this.data.reason, [Validators.required]],
      description: [this.data.description, [Validators.required]],
      status: [this.data.status, []],
      createdDate: [this.creationDate, []],
    });
  }


  getDate() {
    this.creationDate = this.datePipe.transform(this.data.createdDate, "dd/MM/yyyy hh:mm:ss");
  }

  closeModal() {
    this.activeModal.close();
  }

  crudReasons() {
    let action: string = this.button === 'add' ? "crear" : "modificar";

    Swal.fire({
      title: 'Advertencia',
      text: `¿Desea ${action} el motivo?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {
        const data: ReasonModel = this.dataForm.value;
        delete data.createdDate;
        //console.log(data);


        if (this.button === 'add') {

          const VALIDATE_CREATION = await this.postReason(data);

          if (VALIDATE_CREATION) {
            this.utilService.showNotification(0, "El motivo se creo exitosamente");
            this.messageEvent.emit(true);
            this.closeModal();
          }

        } else {



          const VALIDATE_UPDATION = await this.putReason(this.data.id, data);

          if (VALIDATE_UPDATION) {
            this.utilService.showNotification(0, "El motivo se modifico exitosamente");

            this.messageEvent.emit(true);
            this.closeModal();

          }


        }


      }

    })
  }

  // Methods Services

  /**
 * Método que consume un servicio para crear una razón
 * 
 * @param data 
 * @returns 
 */
  postReason(data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.reasonService.postReason(data).subscribe((response) => {

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
   * Método que consume un servicio para actualizar una razón
   * 
   * @param id 
   * @param data 
   * @returns 
   */
  putReason(id: any, data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.reasonService.putReason(id, data).subscribe((response) => {

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
