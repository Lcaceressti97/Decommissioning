import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ReasonResponse } from 'src/app/entities/response';
import { DeductibleRateModel, ReasonModel } from 'src/app/models/model';
import { DeductibleRatesService } from 'src/app/services/deductible-rates.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enum';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-deductible-rates-modal',
  templateUrl: './deductible-rates-modal.component.html',
  styleUrls: ['./deductible-rates-modal.component.css']
})
export class DeductibleRatesModalComponent implements OnInit {

  // Props

  // Input y Output
  @Input() button: string;
  @Input() data: DeductibleRateModel;
  @Output() messageEvent = new EventEmitter<boolean>();

  // Form
  dataForm!: FormGroup;
  messages = messages;
  reasonsClaim: ReasonModel[] = [];

  // Readonly
  inputReadOnly: boolean = false;

  // Dates
  created: string = "";

  constructor(public utilService: UtilService, private deductibleRateService: DeductibleRatesService, private datePipe: DatePipe, private activeModal: NgbActiveModal, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.getReasonsClaim();

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
      model: ['', [Validators.required]],
      description: ['', [Validators.required]],
      firstClaim: ['', [Validators.required]],
      secondClaim: ['', [Validators.required]],
      thirdClaim: ['', [Validators.required]],
      reason: ['', [Validators.required]],
      status: ['', []],
      created: ['', []]
    });
  }

  /**
   * Se usa solo para visualizar o modificar
   * 
   * @returns 
   */
  initFormTwo(): FormGroup {
    return this.formBuilder.group({
      model: [this.data.model, [Validators.required]],
      description: [this.data.description, [Validators.required]],
      firstClaim: [this.data.firstClaim, [Validators.required]],
      secondClaim: [this.data.secondClaim, [Validators.required]],
      thirdClaim: [this.data.thirdClaim, [Validators.required]],
      reason: [this.data.reason, [Validators.required]],
      status: [this.data.status, []],
      created: [this.created, []],
    });
  }


  getDate() {
    this.created = this.datePipe.transform(this.data.created, "dd/MM/yyyy hh:mm:ss");
  }

  closeModal() {
    this.activeModal.close();
  }


/**
 * Método encargado de obtener los motivos
 * 
 */
getReasonsClaim(): Promise<boolean> {
  return new Promise((resolve, reject) => {
    // Se llama al método del servicio
    this.deductibleRateService.getReason().subscribe((response) => {

      // Validamos si responde con un 200
      if (response.status === 200) {

        // Vaciamos las razones anteriores
        this.reasonsClaim = [];

        // Mapeamos el body del response
        let reasonResponse = response.body as ReasonResponse;

        // Filtramos los datos cuyo status sea igual a 1 y los agregamos a reasonsClaim
        this.reasonsClaim = reasonResponse.data
          .filter((resource) => resource.status === 1)
          .map((resourceMap) => {
            let dto: ReasonModel = resourceMap;
            return dto;
          });

        // Actualizamos el array para reflejar los cambios en la vista
        this.reasonsClaim = [...this.reasonsClaim];
        resolve(true);

      } else {
        resolve(false);
      }

    }, (error) => {
      resolve(false);
    });
  });
}


  crudDeductibleRate() {
    let action: string = this.button === 'add' ? "crear" : "modificar";

    Swal.fire({
      title: 'Advertencia',
      text: `¿Desea ${action} la tasa deducible?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {
        const data: DeductibleRateModel = this.dataForm.value;
        delete data.created;

        if (this.button === 'add') {

          const VALIDATE_CREATION = await this.postDeductibleRate(data);

          if (VALIDATE_CREATION) {
            this.utilService.showNotification(0, "La tasa deducible se creo exitosamente");
            this.messageEvent.emit(true);
            this.closeModal();
          }

        } else {



          const VALIDATE_UPDATION = await this.putDeductibleRate(this.data.id, data);

          if (VALIDATE_UPDATION) {
            this.utilService.showNotification(0, "La tasa deducible se modifico exitosamente");

            this.messageEvent.emit(true);
            this.closeModal();

          }


        }


      }

    })
  }

  // Methods Services

  /**
 * Método que consume un servicio para crear una tasa deducible
 * 
 * @param data 
 * @returns 
 */
  postDeductibleRate(data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.deductibleRateService.postDeductibleRate(data).subscribe((response) => {

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
   * Método que consume un servicio para actualizar las tasas deducible
   * 
   * @param id 
   * @param data 
   * @returns 
   */
  putDeductibleRate(id: any, data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.deductibleRateService.putDeductibleRate(id, data).subscribe((response) => {

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
