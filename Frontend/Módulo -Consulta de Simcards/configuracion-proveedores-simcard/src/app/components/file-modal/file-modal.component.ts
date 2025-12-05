import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ProviderModel } from 'src/app/model/model';
import { SimCardService } from 'src/app/service/sim-card.service';

import { UtilService } from 'src/app/service/util.service';

import { messages } from 'src/app/utils/enum';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-file-modal',
  templateUrl: './file-modal.component.html',
  styleUrls: ['./file-modal.component.css']
})
export class FileModalComponent implements OnInit {

  // Props

  // Input | Output
  @Input() data: ProviderModel;
  @Output() messageEvent = new EventEmitter<boolean>();

  // Form
  form: FormGroup;
  fileName: string = "Seleccionar";
  file: File = null;
  messages = messages;
  updateShow: boolean = true;

  constructor(public utilService: UtilService, private simcardService: SimCardService, private activeModal: NgbActiveModal, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.form = this.initForm();
    console.log(this.data);
  }

  closeModal() {
    this.activeModal.close();
  }

  initForm(): FormGroup {
    return this.formBuilder.group({
      baseFile: ["", [Validators.required]]
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
    //console.log(this.file);
    //console.log(this.fileName);

  }

  updateFile(option:number){

    if(option===1){
      this.updateShow= false;
    }else{
      this.updateShow= true;
      this.form.patchValue({
        baseFile: ""
      });
      this.fileName = "Seleccionar";
    }

  }

}
