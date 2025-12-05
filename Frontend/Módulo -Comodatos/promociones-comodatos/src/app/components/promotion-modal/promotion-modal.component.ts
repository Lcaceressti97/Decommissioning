import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { PromotionDetailModel, PromotionModel } from 'src/app/model/model';
import { ComodatosService } from 'src/app/services/comodatos.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/emun';
import { compareDesc, format, subDays } from 'date-fns';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-promotion-modal',
  templateUrl: './promotion-modal.component.html',
  styleUrls: ['./promotion-modal.component.css']
})
export class PromotionModalComponent implements OnInit {

  // Props

  // Inputs | Output
  @Input() data: PromotionModel;
  @Input() action: string;
  @Output() messageEvent = new EventEmitter<boolean>();

  // Tab
  active: number = 1;

  // Tables
  rows: PromotionDetailModel[] = [];
  rows2: PromotionDetailModel[] = [];
  detail: PromotionDetailModel[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 2;
  searchedValue: string = "";
  messages = messages;

  // Form
  formGroup!: FormGroup;
  formGroupDetail!: FormGroup;

  // Readonly
  inputReadOnly: boolean = false;
  inputReadOnlyStartDate: boolean = false;
  inputReadOnlyEndDate: boolean = false;

  // Independent
  startDateStr: string = "";
  endDateStr: string = "";
  selectPermanentStr: string = "";

  // Dates
  invalidDate: boolean = false;

  // Hidden
  hiddeGeneral: boolean = false;
  hiddeDetail: boolean = true;
  hiddeButton: boolean = true;


  constructor(private activeModal: NgbActiveModal, public utilService: UtilService, private comodatosService: ComodatosService, private readonly fb: FormBuilder, private datePipe: DatePipe) { }

  ngOnInit(): void {

    this.formGroupDetail = this.initFormDetail();

    if (this.action == "see" || this.action == "edit") {
      this.formGroup = this.initFormUpdate();
      this.detail = Object.assign([...this.data.promotionsDetail]);
      this.inputReadOnlyStartDate = true;
    } else {
      this.formGroup = this.initForm();
    }

    this.formatValueDates();

  }

  // Methods

  /**
 * Método que controla la visualización de las tablas 
 * 
 * @param option 
 */
  changeTab(option: number) {

    this.resetFormDetail();

    if (option === 1) {
      this.hiddeGeneral = false;
      this.hiddeDetail = true;
    } else {
      this.hiddeGeneral = true;
      this.hiddeDetail = false;
    }

  }

  /**
   * Método encargado de dar formato a las fechas dependiendo
   * si es para actualizar o visualizar los datos
   * 
   */
  formatValueDates(): void {
    console.log(this.data);

    if (this.action == "see" || this.action == "edit") {
      this.rows = [];
      this.rows2 = [];
      this.rows = this.detail;
      this.rows = [...this.rows];
      this.rows2 = [...this.rows];
    }

    if (this.action === "see") {
      this.inputReadOnly = true;
      this.startDateStr = this.data.startDate != null ? this.formattedDate(this.data.startDate) : "";
      this.endDateStr = this.data.endDate != null ? this.formattedDate(this.data.endDate) : "";

      this.selectPermanentStr = this.data.permanentValidity == "Y" ? "Sí" : "No";


      const controlStartDate = this.formGroup.get('startDate') as FormControl;
      controlStartDate.setValue(this.startDateStr);

      const controlEndDate = this.formGroup.get('endDate') as FormControl;
      controlEndDate.setValue(this.endDateStr);


      const controlPermanentValidity = this.formGroup.get('permanentValidity') as FormControl;
      controlPermanentValidity.setValue(this.selectPermanentStr);
    }

    if (this.action === "edit") {

      const controlStartDate = this.formGroup.get('startDate') as FormControl;
      controlStartDate.setValue(this.data.startDate);

      const controlEndDate = this.formGroup.get('endDate') as FormControl;
      controlEndDate.setValue(this.data.endDate);

      if (this.data.permanentValidity == "N") {

        controlEndDate.setValidators(Validators.required);
        this.inputReadOnlyEndDate = false;

      } else {
        this.inputReadOnlyEndDate = true;
        controlEndDate.setValue("");
        controlEndDate.clearValidators();
      }
      controlEndDate.updateValueAndValidity();


    }
  }


  closeModal() {
    this.activeModal.close();
  }

  /**
* Buscardor de la tabla Activos
* 
*/
  search(): void {
    this.rows = this.rows2.filter((row) => {
      return JSON.stringify(row)
        .toLowerCase()
        .includes(this.searchedValue.toString()
          .toLowerCase());
    });
  }

  getTotalTextActive() {
    return this.rows.length == 1 ? "Registro" : "Registros";
  }


  /**
  * Formulario que se usa para la creación
  * 
  */
  initForm(): FormGroup {
    return this.fb.group({
      id: ['', []],
      promotionCode: ['', [Validators.required, Validators.maxLength(50)]],
      modelCode: ['', [Validators.required, Validators.maxLength(50)]],
      description: ['', [Validators.required, Validators.maxLength(50)]],
      corporate: ['', [Validators.required, Validators.maxLength(50)]],
      permanentValidity: ['', [Validators.required]],
      startDate: ['', [Validators.required]],
      endDate: ['', []],
    });
  }


  /**
  * Formulario que se usa para la actualización
  * 
  */
  initFormUpdate(): FormGroup {
    return this.fb.group({
      id: [this.data.id, []],
      promotionCode: [this.data.promotionCode, [Validators.required, Validators.maxLength(50)]],
      modelCode: [this.data.modelCode, [Validators.required, Validators.maxLength(50)]],
      description: [this.data.description, [Validators.required, Validators.maxLength(50)]],
      corporate: [this.data.corporate, [Validators.required, Validators.maxLength(50)]],
      permanentValidity: [this.data.permanentValidity, [Validators.required]],
      startDate: ['', [Validators.required]],
      endDate: ['', []],
      status: [1, []],
    });
  }

  /**
   * Método que inicializa un formulario para los detalles
   * 
   */
  initFormDetail(): FormGroup {
    return this.fb.group({
      id: [0, []],
      idOptional: [0, []],
      promotionCode: ['', [Validators.required, Validators.maxLength(50)]],
      modelCode: ['', [Validators.required, Validators.maxLength(50)]],
      planValue: [0, [Validators.required, this.validarNumeroDecimal]],
      monthsPermanence: [0, [Validators.required, this.validarNumero]],
      subsidyFund: [0, [Validators.required, this.validarNumeroDecimal]],
      additionalSubsidy: [0, [Validators.required, this.validarNumeroDecimal]],
      institutionalFunds: [0, [Validators.required, this.validarNumeroDecimal]],
      coopsFund: [0, [Validators.required, this.validarNumeroDecimal]],
      status: [1, []],
    });
  }

  /**
  * Método para dar formato a la fecha, se usa cuando viene del valor del input
  * 
  * @param fecha 
  * @returns 
  */
  private formatearFecha(fecha: string): Date {
    const fechaFormateada = new Date(fecha);
    return fechaFormateada;
  }

  /**
   * Se usa para dar formato a un tipo de dato, en este caso
   * se usa para mostrar el dato en el input de tipo date
   * 
   */
  private formattedDate(date: Date): string {
    const datePipe = new DatePipe('en-US');

    if (this.action == "see") {
      return datePipe.transform(date, 'dd/MM/yyyy HH:mm:ss');
    } else {
      return datePipe.transform(date, 'yyyy-MM-ddTHH:mm');
    }

  }

  validateDates(): void {
    let dates = this.getDates();

    if (compareDesc(dates.startDate, dates.endDate) < 0 && this.formGroup.get("endDate").value != "") {
      this.utilService.showNotificationMsj("Fecha de Inicio Debe Ser Mayor o Igual a Fecha de Fin.!!");

      this.invalidDate = true;
    } else {

      this.invalidDate = false;
    }
  }


  /**
  * Este método nos devuelve un objeto con las fechas actuales
  * 
  * @returns 
  */
  getDates() {
    return {
      startDate: new Date(`${this.formGroup.get("startDate").value}`),
      endDate: new Date(`${this.formGroup.get("endDate").value}`),
    };


  }

  /**
   * Método que dispara una validación que solo acepta números
   * con dos decimales opcional
   * 
   * @param control 
   * @returns 
   */
  validarNumeroDecimal(control) {
    const numeroDecimalRegExp = /^[0-9]+(\.[0-9]{1,2})?$/;
    if (control.value && !numeroDecimalRegExp.test(control.value)) {
      return { 'numeroDecimalInvalido': true };
    }
    return null;
  }

  validarNumero(control) {
    const numeroRegExp = /^\d{1,2}$/; // Expresión regular para validar un número de hasta dos dígitos
    if (control.value && !numeroRegExp.test(control.value)) {
      return { 'numeroInvalido': true }; // Retorna un objeto si el número es inválido
    }
    return null; // Retorna null si el número es válido
  }


  /**
   * Método que sirve para visualizar o no el input de la fecha final
   * 
   */
  changePermanentValidate(event) {
    const option: string = event.target.value;

    const controlEndDate = this.formGroup.get('endDate') as FormControl;

    if (option == "N") {

      controlEndDate.setValidators(Validators.required);
      this.inputReadOnlyEndDate = false;

    } else {
      this.inputReadOnlyEndDate = true;
      controlEndDate.setValue("");
      controlEndDate.clearValidators();
    }
    controlEndDate.updateValueAndValidity();
  }

  /**
   * Este método nos ayuda a actualizar los valores de los inputs del
   * formulario detalle
   * 
   * @param row 
   */
  selectRows(row: PromotionDetailModel) {

    //console.log(row);
    //this.formGroupDetail.patchValue(row);


    const clonedRow = { ...row };

    const controls = this.formGroupDetail.controls;
    Object.keys(controls).forEach(controlName => {
      const control = controls[controlName] as FormControl;

      if (clonedRow[controlName] == null || clonedRow[controlName] == "") {
        clonedRow[controlName] = null;
      }

      control.setValue(clonedRow[controlName]);
    });



    this.hiddeButton = false;
    //console.log(this.formGroupDetail.value);

  }


  /**
   * Método que resetea o limpia el formulario
   * en el apartado de detalles
   * 
   */
  resetFormDetail() {
    this.formGroupDetail.controls['id'].reset();
    this.formGroupDetail.controls['promotionCode'].reset();
    this.formGroupDetail.controls['modelCode'].reset();
    this.formGroupDetail.controls['planValue'].reset();
    this.formGroupDetail.controls['monthsPermanence'].reset();
    this.formGroupDetail.controls['subsidyFund'].reset();
    this.formGroupDetail.controls['additionalSubsidy'].reset();
    this.formGroupDetail.controls['institutionalFunds'].reset();
    this.formGroupDetail.controls['coopsFund'].reset();
    this.formGroupDetail.controls['idOptional'].reset();

    this.hiddeButton = true;

  }


  /**
   * Método que agrega el registro a la tabla
   * 
   */
  addRowDetail() {

    Swal.fire({
      title: 'Advertencia',
      text: `¿Desea agregar estos datos a la tabla?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {
        const detailForm: PromotionDetailModel = { ...this.formGroupDetail.value };

        detailForm.planValue = Number(detailForm.planValue);
        detailForm.monthsPermanence = Number(detailForm.monthsPermanence);
        detailForm.subsidyFund = Number(detailForm.subsidyFund);
        detailForm.additionalSubsidy = Number(detailForm.additionalSubsidy);
        detailForm.institutionalFunds = Number(detailForm.institutionalFunds);
        detailForm.coopsFund = Number(detailForm.coopsFund);
        detailForm.idOptional = Number(this.rows.length);
        detailForm.status = Number(detailForm.status);

        this.rows = [];
        this.rows = [...this.rows];
        this.rows2 = [...this.rows];
        this.detail.push(detailForm);
        this.rows = this.detail;
        this.rows = [...this.rows];
        this.rows2 = [...this.rows];

        this.resetFormDetail();
      }

    });


  }

  generateUUID(): string {
    let uuid = '';
    const characters = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';

    for (let i = 0; i < 36; i++) {
      if (i === 8 || i === 13 || i === 18 || i === 23) {
        uuid += '-';
      } else if (i === 14) {
        uuid += '4';
      } else if (i === 19) {
        uuid += characters.charAt(Math.floor(Math.random() * characters.length));
      } else {
        uuid += characters.charAt(Math.floor(Math.random() * characters.length));
      }
    }

    return uuid;
  }

  /**
   * Método que nos ayuda a actualizar el estado del registro
   * 
   * @param row 
   */
  updateStatusRow(status: number, row: PromotionDetailModel) {
    const title: string = status === 1 ? "activar" : "inactivar";

    Swal.fire({
      title: 'Advertencia',
      text: `¿Desea ${title} este registro?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {
        if (row.id != null) {
          const index: number = this.detail.findIndex(item => item.id === row.id);
          //console.log(index);

          if (index !== -1) {
            //console.log("Se encontro");

            this.detail[index] = row;
            this.detail[index].status = status;
            this.rows = this.detail;
            this.rows = [...this.rows];
            this.rows2 = [...this.rows];

          }

        } else {

          const index: number = this.detail.findIndex(item => item.idOptional === row.idOptional);


          if (index !== -1) {
            //console.log("Se encontro");

            this.detail[index] = row;
            this.detail[index].status = status;
            this.rows = this.detail;
            this.rows = [...this.rows];
            this.rows2 = [...this.rows];

          }
        }
      }

    });


  }

  /**
   * Método encargado de actualizar o agregar un campo en especifico del
   * arreglo que viene de input data
   * 
   */
  updateRowDetail() {

    Swal.fire({
      title: 'Advertencia',
      text: `¿Desea cambiar estos datos?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {

        const detailForm: PromotionDetailModel = { ...this.formGroupDetail.value };

        detailForm.planValue = Number(detailForm.planValue);
        detailForm.monthsPermanence = Number(detailForm.monthsPermanence);
        detailForm.subsidyFund = Number(detailForm.subsidyFund);
        detailForm.additionalSubsidy = Number(detailForm.additionalSubsidy);
        detailForm.institutionalFunds = Number(detailForm.institutionalFunds);
        detailForm.coopsFund = Number(detailForm.coopsFund);
        detailForm.status = Number(detailForm.status);
        detailForm.idOptional = Number(detailForm.idOptional);

        let promotionDetail: PromotionDetailModel = {
          id: detailForm.id,
          idOptional: detailForm.idOptional,
          promotionCode: detailForm.promotionCode,
          modelCode: detailForm.modelCode,
          planValue: detailForm.planValue,
          monthsPermanence: detailForm.monthsPermanence,
          subsidyFund: detailForm.subsidyFund,
          additionalSubsidy: detailForm.additionalSubsidy,
          institutionalFunds: detailForm.institutionalFunds,
          coopsFund: detailForm.coopsFund,
          status: detailForm.status
        }


        //console.log(detailForm);
        //console.log(promotionDetail);


        if (this.action !== "add") {

          if (promotionDetail.id == null && promotionDetail.idOptional == null) {
            //console.log("Entro al modificando cuando no existe el id")
            promotionDetail.idOptional = Number(this.rows.length);

          }

        }

        /**
         * Si es add se busca por el idOptional para actualizar 
         * el registro del array, sino se busca por el id
         * 
         */
        if (this.action === 'add') {
          const index: number = this.detail.findIndex(item => item.idOptional === promotionDetail.idOptional);
          //console.log(index);

          if (index !== -1) {
            //console.log("Se encontro");

            this.detail[index] = promotionDetail;
            this.rows = this.detail;
            this.rows = [...this.rows];
            this.rows2 = [...this.rows];

          }

        } else {

          /**
           * Si no existe el id se busca por el idOptional
           * 
           */
          if (promotionDetail.id != null) {
            const index: number = this.detail.findIndex(item => item.id === promotionDetail.id);
            //console.log(index);

            if (index !== -1) {
              //console.log("Se encontro");

              this.detail[index] = promotionDetail;
              this.rows = this.detail;
              this.rows = [...this.rows];
              this.rows2 = [...this.rows];

            }

          } else {
            const index: number = this.detail.findIndex(item => item.idOptional === promotionDetail.idOptional);


            if (index !== -1) {
              //console.log("Se encontro");

              this.detail[index] = promotionDetail;
              this.rows = this.detail;
              this.rows = [...this.rows];
              this.rows2 = [...this.rows];

            }
          }


        }



        this.resetFormDetail();
      }

    });


  }

  /**
   * Método que realiza un crud, ya sea actualizar o agregar
   * el registro
   * 
   */
  crudPromotion() {

    const title: string = this.action === 'add' ? "crear" : "actualizar"

    Swal.fire({

      title: 'Advertencia',
      text: `¿Desea ${title} esta promoción?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {

        let data: PromotionModel = this.formGroup.value;
        data.promotionsDetail = this.rows;


        if (this.action === "add") {


          const VALIDATE_ADD = await this.postPromotion(data);

          if (VALIDATE_ADD) {

            this.utilService.showNotification(0, "Promoción creada con exito");

            this.messageEvent.emit(true);
            this.closeModal();
          }


        } else {


          const VALIDATE_UPDATE = await this.putPromotion(this.data.id, data);

          if (VALIDATE_UPDATE) {

            this.utilService.showNotification(0, "Promoción actualizada con exito");

            this.messageEvent.emit(true);
            this.closeModal();
          }


        }



      }

    });

  }

  // Methos Asyncronos

  /**
   * Método encargado de consumir un servicio para crear 
   * un nuevo registro de promociones
   * 
   * @param data 
   * @returns 
   */
  postPromotion(data: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      this.comodatosService.postPromotion(data).subscribe((response) => {

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
   * Método encargado de consumir un servicio para actualizar 
   * un nuevo registro de promociones
   * 
   * @param data 
   * @returns 
   */
  putPromotion(id: any, data: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      this.comodatosService.putPromotion(id, data).subscribe((response) => {

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


}
