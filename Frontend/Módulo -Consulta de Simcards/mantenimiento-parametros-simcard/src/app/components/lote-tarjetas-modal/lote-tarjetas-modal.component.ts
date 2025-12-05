import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CardPrepagoModel } from 'src/app/models/model';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enum';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-lote-tarjetas-modal',
  templateUrl: './lote-tarjetas-modal.component.html',
  styleUrls: ['./lote-tarjetas-modal.component.css']
})
export class LoteTarjetasModalComponent implements OnInit {

  // Props

  // Input | Output
  @Input() data: CardPrepagoModel = null;
  @Input() action: string = null;
  @Output() messegaEvent = new EventEmitter<boolean>();

  // Form
  form: FormGroup;
  messages = messages;
  readonly: boolean = false;

  constructor(public utilService: UtilService, private activeModal: NgbActiveModal, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    console.log(this.data)
    console.log(this.action)
    this.validateShow();
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
    console.log(this.form);
  }

  if (this.action === "see") {
    this.readonly = true;
  }

}

  closeModal(){
    this.activeModal.close();
  }

  // Methods
  initForm(): FormGroup {
    return  this.formBuilder.group({
      model: ["", [Validators.required, Validators.maxLength(6)]],
      lin: ["", [Validators.required,  Validators.maxLength(3)]],
      description: ["", [Validators.required,  Validators.maxLength(150)]],
      value: ["", Validators.required],
      act: [""],
    });
  }

  // Methods
  initFormTwo(): FormGroup {
    return this.formBuilder.group({
      model: [this.data.model, [Validators.required, Validators.maxLength(6)]],
      lin: [this.data.lin, [Validators.required,  Validators.maxLength(3)]],
      description: [this.data.description, [Validators.required,  Validators.maxLength(150)]],
      value: [this.data.value, Validators.required],
      act: [this.data.act],
    });
  }



}
