import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ParametersHistoricalModel } from 'src/app/models/ParametersHistoricalModel';
import { ParametersModel } from 'src/app/models/ParametersModel';
import { ParametersService } from 'src/app/services/parameters.service';
import { UtilsService } from 'src/app/services/utils.service';
import { messages } from 'src/app/utils/enum';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-parameters-modal',
  templateUrl: './parameters-modal.component.html',
  styleUrls: ['./parameters-modal.component.css']
})
export class ParametersModalComponent implements OnInit {


  // Props

  // Input | Output
  @Input() data: ParametersModel;
  @Input() action: string;
  @Output() messageEvent = new EventEmitter<boolean>();

  // Form
  form: FormGroup;
  fileName: string = "Seleccionar";
  file: File = null;
  messages = messages;
  readonly: boolean = false;

  constructor(public utilService: UtilsService, private parametersService: ParametersService, private activeModal: NgbActiveModal, private formBuilder: FormBuilder) { }

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
      name: ["", [Validators.required]],
      value: ["", [Validators.required, Validators.maxLength(100)]],
      description: ["", [Validators.required, Validators.maxLength(100)]],
      userName: ["", []],
      status: ["", [Validators.maxLength(150)]],

    });
  }

  /**
   * Método encargado de construir el formulario reactivo
   * 
   */
  initFormTwo(): FormGroup {
    const statusValue = this.data.status === "Activo" ? "1" : "0";
    const userName = this.utilService.getSystemUser();
    const date = new Date();
    const createDate = this.formatDate(date);

    return this.formBuilder.group({
      idParameter: [this.data.id, [Validators.required, Validators.maxLength(100)]],
      name: [this.data.name, [Validators.required, Validators.maxLength(100)]],
      value: [this.data.value, [Validators.required, Validators.maxLength(150)]],
      description: [this.data.description, [Validators.required, Validators.maxLength(100)]],
      userName: [userName, [Validators.required, Validators.maxLength(100)]],
      status: [statusValue, [Validators.maxLength(150)]],
      createDate: [createDate, [Validators.maxLength(150)]],

    });
  }

  /**
   * Método que nos ayuda a controlar el archivo que se va a cargar
   * 
   * @param event 
   */
  selectFile(event: any) {
    const fileName: string = event.target.files[0]?.name;
    this.fileName = fileName;
    this.file = event.target.files[0];
  }

  // Operación
  crudParameter() {

    const title: string = this.action === 'add' ? "crear" : "actualizar"

    Swal.fire({

      title: 'Advertencia',
      text: `¿Desea ${title} este?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {
        let data: ParametersHistoricalModel = this.form.value;
        data.userName = this.utilService.getSystemUser();

        if (this.action === "add") {
          const VALIDATE_ADD = await this.postParameterHistorical(data);

          if (VALIDATE_ADD) {

            this.utilService.showNotification(0, "Parámetro creado con éxito");

            this.messageEvent.emit(true);
            this.closeModal();
          }

        } else {

          const VALIDATE_UPDATE = await this.putParameters(this.data.id, data);
          const VALIDATE_ADD = await this.postParameterHistorical(data);

          if (VALIDATE_UPDATE && VALIDATE_ADD) {

            this.utilService.showNotification(0, "Parámetro actualizado con éxito");

            this.messageEvent.emit(true);
            this.closeModal();
          }

        }

      }

    });

  }


  //Rest
  postParameterHistorical(data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      delete data.createDate;

      // Consumir el servicio
      this.parametersService.postParameterHistorical(data).subscribe((response) => {

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

  putParameters(id: any, data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consumir el servicio
      this.parametersService.putParameters(id, data).subscribe((response) => {

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

  formatDate(date: Date): string {
    const options: Intl.DateTimeFormatOptions = {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit',
      hour12: true,
    };

    const formattedDate = new Intl.DateTimeFormat('en-US', options).format(date);

    // Ajustar el formato para obtener el delimitador deseado
    return formattedDate.replace(',', '').replace(/\//g, '/').replace(' AM', ' AM').replace(' PM', ' PM');
  }


}
