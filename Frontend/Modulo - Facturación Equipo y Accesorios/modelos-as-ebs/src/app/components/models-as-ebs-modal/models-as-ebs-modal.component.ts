import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ModelAsEbsModel } from 'src/app/models/model';
import { ModelsAsEbsService } from 'src/app/services/models-as-ebs.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enum';
import Swal from "sweetalert2/dist/sweetalert2.js";


@Component({
  selector: 'app-models-as-ebs-modal',
  templateUrl: './models-as-ebs-modal.component.html',
  styleUrls: ['./models-as-ebs-modal.component.css']
})
export class ModelsAsEbsModalComponent implements OnInit {

  // Props

  // Input y Output
  @Input() button: string;
  @Input() data: ModelAsEbsModel;
  @Output() messageEvent = new EventEmitter<boolean>();

  // Form
  dataForm!: FormGroup;
  messages = messages;

  // Readonly
  inputReadOnly: boolean = false;
  constructor(public utilService: UtilService, private modelsAsEbsService: ModelsAsEbsService, private activeModal: NgbActiveModal, private formBuilder: FormBuilder) { }


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
      codMod: ['', [Validators.required, Validators.maxLength(50)]],
      codEbs: ['', [Validators.required, Validators.maxLength(50)]],
      subBod: ['', [Validators.required, Validators.maxLength(50)]],
      newMod: ['', [Validators.required, Validators.maxLength(50)]],
      name: ['', [Validators.required, Validators.maxLength(50)]],

    });
  }

  /**
   * Se usa solo para visualizar o modificar
   * 
   * @returns 
   */
  initFormTwo(): FormGroup {
    return this.formBuilder.group({
      codMod: [this.data.codMod, [Validators.required, Validators.maxLength(50)]],
      codEbs: [this.data.codEbs, [Validators.required, Validators.maxLength(50)]],
      subBod: [this.data.subBod, [Validators.required, Validators.maxLength(50)]],
      newMod: [this.data.newMod, [Validators.required, Validators.maxLength(50)]],
      name: [this.data.name, [Validators.required, Validators.maxLength(50)]],

    });
  }

  closeModal() {
    this.activeModal.close();
  }


  async crudModelAsEbs() {

    let action: string = this.button === 'add' ? "crear" : "modificar";

    Swal.fire({
      title: 'Advertencia',
      text: `¿Desea ${action} el modelo?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {
        let data: ModelAsEbsModel = this.dataForm.value;

        if (this.button === 'add') {

          const VALIDATE_CREATION = await this.postModelsAsEbs(data);

          if (VALIDATE_CREATION) {
            this.utilService.showNotification(0, "El modelo se creo exitosamente");
            this.messageEvent.emit(true);
            this.closeModal();
          }

        } else {

          const VALIDATE_UPDATION = await this.putModelsAsEbs(this.data.id, data);

          if (VALIDATE_UPDATION) {
            this.utilService.showNotification(0, "El modelo se modifico exitosamente");

            this.messageEvent.emit(true);
            this.closeModal();

          }

        }
      }

    })
  }

  // Methods Services

  /**
 * Método que consume un servicio para crear un modelo
 * 
 * @param data 
 * @returns 
 */
  postModelsAsEbs(data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.modelsAsEbsService.postModelsAsEbs(data).subscribe((response) => {

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
   * Método que consume un servicio para actualizar un modelo
   * 
   * @param id 
   * @param data 
   * @returns 
   */
  putModelsAsEbs(id: any, data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.modelsAsEbsService.putModelsAsEbs(id, data).subscribe((response) => {

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
