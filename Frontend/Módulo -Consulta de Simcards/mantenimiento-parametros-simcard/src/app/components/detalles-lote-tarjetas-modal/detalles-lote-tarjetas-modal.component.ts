import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CardPrepagoLoteModel, CardPrepagoModel } from 'src/app/models/model';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enum';
import Swal from "sweetalert2/dist/sweetalert2.js";
import { compareDesc } from 'date-fns';

@Component({
  selector: 'app-detalles-lote-tarjetas-modal',
  templateUrl: './detalles-lote-tarjetas-modal.component.html',
  styleUrls: ['./detalles-lote-tarjetas-modal.component.css']
})
export class DetallesLoteTarjetasModalComponent implements OnInit {

  // Props

  // Input | Output
  @Input() data: CardPrepagoModel = null;
  @Output() messegaEvent = new EventEmitter<boolean>();

  // Form
  form: FormGroup;
  messages = messages;
  showForm: boolean = true;
  invalidDate: boolean = false;

  // Table
  rows: CardPrepagoLoteModel[] = [];
  rows2: CardPrepagoLoteModel[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 5;
  searchedValue: string = "";
  typeValue: string = "";

  constructor(public utilService: UtilService, private activeModal: NgbActiveModal, private formBuilder: FormBuilder) { }

  ngOnInit(): void {

    this.form = this.initForm();

    const dto: CardPrepagoLoteModel = {
      id: 1, days: 3, quantity: 50000, rangeInitial: "00000000", serie: 1240024, value: 56.00, createdDate: new Date(), dueDate: new Date()

    }

    this.rows.push(dto);
    this.rows = [...this.rows];
    this.rows2 = [...this.rows];
    this.loadingIndicator = false;

  }

  search() {
    this.rows = this.rows2.filter((rowsSimcardControl) => {
      return JSON.stringify(rowsSimcardControl)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  getTotalText() {
    return this.rows.length == 1 ? "Registro" : "Registros";
  }

  closeModal() {
    this.activeModal.close();
  }

  // Methods
  initForm(): FormGroup {
    return this.formBuilder.group({
      serie: ["", [Validators.required]],
      rangeInitial: ["", [Validators.required]],
      quantity: ["", [Validators.required]],
      createdDate: ["", Validators.required],
      dueDate: ["", Validators.required],
      days: ["", Validators.required],
      value: ["", Validators.required],
    });
  }

  /**
   * Método que nos ayuda a ocultar o mostrar el formulario de creación
   * 
   */
  changeForm(option: boolean): void {

    this.showForm = option;
    this.form = this.initForm();

  }

  /**
  * Este método nos ayuda a validar los input de:
  * Fecha de Inicio y Fecha Final
  * ¿Qué valida?
  * R/. Valida que se haya seleccionado un valor del input y
  * que la fecha inicial no sea superior a la final
  * 
  */
  validateDates(): void {
    let dates = this.getDates();

    //console.log(dates);

    if (
      compareDesc(dates.startDate, dates.endDate) < 0 &&
      this.form.get("dueDate").value != ""
    ) {
      this.utilService.showNotificationMsj("Fecha Expiración debe ser mayor o igual a la Fecha Creación.!!");

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
      startDate: new Date(`${this.form.get("createdDate").value}T00:00`),
      endDate: new Date(`${this.form.get("dueDate").value}T00:00`),
    };

  }



}
