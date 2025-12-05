import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ProviderModel } from 'src/app/model/model';
import { SimCardService } from 'src/app/service/sim-card.service';

import { UtilService } from 'src/app/service/util.service';

import { messages } from 'src/app/utils/enum';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-provider-crud',
  templateUrl: './provider-crud.component.html',
  styleUrls: ['./provider-crud.component.css']
})
export class ProviderCrudComponent implements OnInit {


  // Props

  // Input | Output
  @Input() data: ProviderModel;
  @Input() action: string;
  @Output() messageEvent = new EventEmitter<boolean>();

  // Form
  form: FormGroup;
  fileName: string = "Seleccionar";
  file: File = null;
  validateFIle: boolean = false;
  messages = messages;
  readonly: boolean = false;

  constructor(public utilService: UtilService, private simcardService: SimCardService, private activeModal: NgbActiveModal, private formBuilder: FormBuilder) { }

  ngOnInit(): void {

    this.validateShow();

  }

  // Methods

  closeModal() {
    this.activeModal.close();
  }

  /**
   * No está en uso
   * 
   */
  abrirPDF() {
    const pdfURL = this.data.baseFile;
    window.open(pdfURL, '_blank');
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
      supplierNo: ["", [Validators.required]],
      supplierName: ["", [Validators.required, Validators.maxLength(150)]],
      address: ["", [Validators.required, Validators.maxLength(150)]],
      phone: ["", [Validators.required, Validators.maxLength(50), this.validateNumber]],
      postalMail: ["", [Validators.required, Validators.maxLength(50)]],
      emailSupplier: ["", [Validators.required, Validators.email]],
      email: ["", [Validators.required, Validators.email]],
      baseFile: [null, []],
      subject: ["", [Validators.required, Validators.maxLength(100)]],
      textEmail: ["", [Validators.required, Validators.maxLength(250)]],
      initialIccd: ["0", [Validators.required, this.validateNumber, Validators.maxLength(10)]],
      key: ["0", [Validators.required, this.validateNumber]],
    });
  }

  /**
   * Método encargado de construir el formulario reactivo
   * 
   */
  initFormTwo(): FormGroup {
    return this.formBuilder.group({
      supplierNo: [this.data.supplierNo, [Validators.required]],
      supplierName: [this.data.supplierName, [Validators.required, Validators.maxLength(150)]],
      address: [this.data.address, [Validators.required, Validators.maxLength(150)]],
      phone: [this.data.phone, [Validators.required, Validators.maxLength(50), this.validateNumber]],
      postalMail: [this.data.postalMail, [Validators.required, Validators.maxLength(50)]],
      emailSupplier: [this.data.emailSupplier, [Validators.required, Validators.email]],
      email: [this.data.email, [Validators.required, Validators.email]],
      baseFile: [null, []],
      subject: [this.data.subject, [Validators.required, Validators.maxLength(100)]],
      textEmail: [this.data.textEmail, [Validators.required, Validators.maxLength(250)]],
      initialIccd: [this.data.initialIccd, []],
      key: [this.data.key, [Validators.required, this.validateNumber]],
    });
  }

  validateNumber(control) {
    const numeroDecimalRegExp = /^[0-9]+$/;

    const numero: string = control.value != null ? control.value.toString() : " ";

    if (!numeroDecimalRegExp.test(control.value) || numero.includes('.')) {
      return { 'numeroDecimalInvalido': true };
    }
    return null;
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
    
    /**
     * Validamos si selecciono un archivo o no
     * 
     */
    if(this.file){
      
      /**
       * Validamos si el tamaño del archivo no paso los 10MB que se representan en bytes
       * 
       */
      if(this.file.size>10585760){
        this.validateFIle = true;
      }else{
        this.validateFIle = false;

      }

    }else{
      this.validateFIle = false;
    }
    //2097152 bytes = 2MB
    //2199024  bytes = 2.1MB
    //10585760 bytes  bytes = 10MB

  }


  /**
 * Método encargado de terminar de completar el IMSI y ICCD
 * 
 * @param value 
 * @param length 
 */
  aaddZeroInImsiAndIccd(value: string, length: string): string {

    const longitud = parseInt(length);

    // Verificar si la longitud de la cadena es menor que la longitud especificada
    while (value.length < longitud) {
      value = '0' + value; // Agregar ceros a la izquierda hasta alcanzar la longitud deseada
    }
    return value;

  }

  // Operación
  crudArt() {

    const title: string = this.action === 'add' ? "crear" : "actualizar";
    const titleSwall: string = this.action === 'add' ? "Creando..." : "Actualizando...";

    Swal.fire({

      title: 'Advertencia',
      text: `¿Desea ${title} este proveedor?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {

        Swal.fire({
          title: titleSwall,
          allowOutsideClick: false,
          onBeforeOpen: () => {
            Swal.showLoading();
          }
        });

        let data: ProviderModel = this.form.value;
        //data.initialImsi = data.initialImsi.toString();
        data.initialIccd = data.initialIccd.toString();
        data.key = data.key.toString();
        data.phone = data.phone.toString();

        if (this.action === "add") {

          /**
           * Eliminamos o no el valor del archivo
           * 
           */
          if (this.file == null) {
            delete data.baseFile;
          } else {
            data.baseFile = await this.convertToBase64(this.file);
          }

          //console.log(data);


          const VALIDATE_ADD = await this.postProvider(data);

          if (VALIDATE_ADD) {

            this.utilService.showNotification(0, "Proveedor creado con exito");
            Swal.close();
            this.messageEvent.emit(true);
            this.closeModal();
          }

          Swal.close();

        } else {

          /**
          * Eliminamos o no el valor del archivo
          * 
          */
          if (this.file == null) {
            delete data.baseFile;
          } else {
            data.baseFile = await this.convertToBase64(this.file);
          }

          //console.log(data);

          const VALIDATE_UPDATE = await this.putProvider(this.data.id, data);

          if (VALIDATE_UPDATE) {

            this.utilService.showNotification(0, "Proveedor actualizado con exito");
            Swal.close();
            this.messageEvent.emit(true);
            this.closeModal();
          }

          Swal.close();


        }

      }

    });

  }

  /**
   * Convertimos el archivo a base64
   * 
   */
  convertToBase64(file: File): Promise<string> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        const base64String = reader.result as string;
        //console.log(base64String);
        resolve(base64String);
      };
      reader.onerror = (error) => {
        resolve(null);
      };

    });
  }


  //Rest
  postProvider(data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consumir el servicio
      this.simcardService.postProvider(data).subscribe((response) => {

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

  putProvider(id: any, data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consumir el servicio
      this.simcardService.putProvider(id, data).subscribe((response) => {

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



}
