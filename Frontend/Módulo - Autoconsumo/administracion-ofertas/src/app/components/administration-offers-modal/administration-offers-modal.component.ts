import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AdministrationOffersModel } from 'src/app/models/AdministrationOffersModel';
import { AdministrationOffersService } from 'src/app/services/administration-offers.service';
import { UtilsService } from 'src/app/services/utils.service';
import { messages } from 'src/app/utils/enum';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-administration-offers-modal',
  templateUrl: './administration-offers-modal.component.html',
  styleUrls: ['./administration-offers-modal.component.css']
})
export class AdministrationOffersModalComponent implements OnInit {


  // Props

  // Input | Output
  @Input() data: AdministrationOffersModel;
  @Input() action: string;
  @Output() messageEvent = new EventEmitter<boolean>();

  // Form
  form: FormGroup;
  fileName: string = "Seleccionar";
  file: File = null;
  messages = messages;
  readonly: boolean = false;

  constructor(public utilService: UtilsService, private administrationOffersService: AdministrationOffersService, private activeModal: NgbActiveModal, private formBuilder: FormBuilder) { }

  ngOnInit(): void {

    this.validateShow();

  }

  // Methods

  closeModal() {
    this.activeModal.close();
  }

  /**
   * Método que nos ayuda a visualizar el tipo de acción para el formulario
   * 
   */
  validateShow() {

    if (this.action === 'add') {
      this.form = this.initForm();
    } else {
      this.form = this.initFormTwo();
    }

    if (this.action === "see") {
      this.readonly = true;
    }


  }

  // Form
  /**
   * Método encargado de construir el formulario reactivo
   * 
   */
  initForm(): FormGroup {
    return this.formBuilder.group({
      offeringId: ["", [Validators.required]],
      chargeCode: ["", [Validators.required, Validators.maxLength(100)]],
      itemName: ["", []],
      status: ["1", [Validators.maxLength(150)]],

    });
  }

  /**
   * Método encargado de construir el formulario reactivo
   * 
   */
  initFormTwo(): FormGroup {
    const statusValue = this.data.status === "Activo" ? "1" : "0";

    return this.formBuilder.group({
      offeringId: [this.data.offeringId, [Validators.required, Validators.maxLength(100)]],
      chargeCode: [this.data.chargeCode, [Validators.required, Validators.maxLength(150)]],
      itemName: [this.data.itemName, [Validators.required, Validators.maxLength(100)]],
      status: [statusValue, [Validators.maxLength(150)]],

    });
  }


  // Operación
  crudAdministrationOffers() {

    const title: string = this.action === 'add' ? "crear" : "actualizar"

    Swal.fire({

      title: 'Advertencia',
      text: `¿Desea ${title} esta oferta?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {
        this.onStatusChange({ target: { checked: this.form.get('status')?.value === '1' } });
        let data: AdministrationOffersModel = this.form.value;
        data.userName = this.utilService.getSystemUser();

        if (this.action === "add") {
          const VALIDATE_ADD = await this.postAdministrationOffers(data);

          if (VALIDATE_ADD) {

            this.utilService.showNotification(0, "Oferta creada con éxito");

            this.messageEvent.emit(true);
            this.closeModal();
          }

        } else {

          const VALIDATE_UPDATE = await this.putAdministrationOffers(this.data.id, data);

          if (VALIDATE_UPDATE) {

            // Crear un nuevo registro en el historico
            const changeCodeHistoricalData = {
              chargeCodeId: this.data.id,
              chargeCode: data.chargeCode,
              itemName: data.itemName,
              userName: this.utilService.getSystemUser(),
              status: data.status,
              action: 'Modificación'
            };
            const VALIDATE_HISTORICAL = await this.postChangeCodeHistorical(changeCodeHistoricalData);

            if (VALIDATE_HISTORICAL) {
              this.utilService.showNotification(0, "Oferta actualizada con éxito");

              this.messageEvent.emit(true);
              this.closeModal();
            }

          }

        }

      }

    });

  }


  //Rest

  fetchOfferingInfo(offeringId: any) {
    if (!offeringId) {
      this.utilService.showNotification(1, "Por favor, ingrese un Offering ID válido.");
      return;
    }

    this.administrationOffersService.getOfferingInfo(offeringId).subscribe((response: any) => {
      if (response.status === 200) {
        const offeringInfo = response.body.data;
        const jsonResponse = JSON.parse(offeringInfo.parameters.parameter[0].value);

        const itemName = jsonResponse.queryOfferingInfoResult.name;

        // Actualiza el formulario con la información de la oferta
        this.form.patchValue({
          itemName: itemName
        });
        this.utilService.showNotification(0, "Información de la oferta obtenida con éxito.");
      } else {
        console.error("Error al obtener la información de la oferta");
        this.utilService.showNotification(1, "No se pudo obtener la información de la oferta.");
        this.clearForm(); 
      }
    }, (error) => {
      console.error("Error en la llamada al servicio:", error);
      this.utilService.showNotification(1, "No se han encontrado registros para el Offering ID.");
      this.clearForm(); 
    });
  }

  // Método para limpiar los campos del formulario
  clearForm() {
    //this.form.reset(); 
    this.form.patchValue({
      itemName: ''
    });
  }
  postAdministrationOffers(data: any): Promise<any> {
    return new Promise((resolve, reject) => {

      // Consumir el servicio
      this.administrationOffersService.postAdministrationOffers(data).subscribe((response) => {

        if (response.status === 200) {
          resolve(true);

        } else {
          resolve(null);
        }

      }, (error) => {

        resolve(null);
      })

    });

  }

  putAdministrationOffers(id: any, data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consumir el servicio
      this.administrationOffersService.putAdministrationOffers(id, data).subscribe((response) => {

        if (response.status === 200) {

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {

        resolve(false);
      })

    });

  }

  postChangeCodeHistorical(data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consumir el servicio
      this.administrationOffersService.postChangeCodeHistorical(data).subscribe((response) => {

        if (response.status === 200) {

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {

        resolve(false);
      })

    });

  }

  onStatusChange(event: any) {
    this.form.get('status')?.setValue(event.target.checked ? '1' : '0');
  }


}
