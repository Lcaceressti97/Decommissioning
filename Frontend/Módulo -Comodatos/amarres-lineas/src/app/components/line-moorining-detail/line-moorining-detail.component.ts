import { Component, OnInit,Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { MooringBillingModel, ParametersModel } from 'src/app/model/model';
import { ComodatosService } from 'src/app/services/comodatos.service';

import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enum';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-line-moorining-detail',
  templateUrl: './line-moorining-detail.component.html',
  styleUrls: ['./line-moorining-detail.component.css']
})
export class LineMooriningDetailComponent implements OnInit {

  // Props
  // Input y Output
  @Input() data: MooringBillingModel;
  @Input() parameters: ParametersModel;
  @Input() option: string;
  @Output() messageEvent = new EventEmitter<boolean>();

  dataForm!: FormGroup;
  readonlyInput: boolean = false;
  messages = messages;

  constructor(private activeModal: NgbActiveModal, public utilService: UtilService, private comodatosService: ComodatosService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    

    if(this.option!== "add"){
      this.data.cmdStatus = this.data.cmdStatus ==="Activo" ? "A" : this.data.cmdStatus ==="Vencido" ? "V" : "C";
      this.data.mooring = this.data.mooring==="Sí" ? 1 : 0;
      this.dataForm= this.initForm();
    }else{
      this.dataForm = this.initFormCreate();
    }

    if(this.option==="see"){
      this.readonlyInput = true;
    }

    
  }

  closeModal(){
    this.activeModal.close();
  }

  initForm(): FormGroup {
    return this.formBuilder.group({
      correlativeCmd: [this.data.correlativeCmd, [Validators.required, Validators.pattern('[0-9]{1,8}')]],
      correlativeMooringCmd:[this.data.correlativeMooringCmd, [Validators.required, Validators.pattern('[0-9]{1,3}')]],
      subscriberId:[this.data.subscriberId,  Validators.maxLength(30)],
      monthsOfPermanence:[this.data.monthsOfPermanence, Validators.pattern('[0-9]{1,2}')],
      cmdStatus: [this.data.cmdStatus, Validators.required],
      userName:[this.data.userName, [Validators.required, Validators.maxLength(50)]],
      supervisorUser:[this.data.supervisorUser, [Validators.required, Validators.maxLength(50)]],
      userMooring:[this.data.userMooring, [Validators.required, Validators.maxLength(50)]],
      userCancelled:[this.data.userCancelled],
      dateOfAdmission: [this.data.dateOfAdmission],
      dueDate:[this.data.dueDate],
      invoiceLocation:[this.data.invoiceLocation, [Validators.required, Validators.pattern('[0-9]{1,2}')]],
      invoiceSubLocity:[this.data.invoiceSubLocity],
      invoiceType:[this.data.invoiceType, [Validators.required, Validators.maxLength(3)]],
      invoiceReading: [this.data.invoiceReading, [Validators.required, Validators.maxLength(1)]],
      invoiceNumber:[this.data.invoiceNumber, [Validators.required, Validators.maxLength(8)]],
      inventoryType:[this.data.inventoryType, [Validators.required, Validators.maxLength(1)]],
      inventoryModel:[this.data.inventoryModel, [Validators.required, Validators.maxLength(6)]],
      teamSeries:[this.data.teamSeries, [Validators.required, Validators.maxLength(25)]],
      phoneCost:[this.data.phoneCost, [Validators.required, Validators.pattern(/^\d{1,13}(\.\d{1,2})?$/)]],
      phoneDiscount:[this.data.phoneDiscount, [Validators.required, Validators.pattern(/^\d{1,13}(\.\d{1,2})?$/)]],
      mooring:[this.data.mooring],
      promotion:[this.data.promotion],
      transactionId:[this.data.transactionId, [Validators.required, Validators.maxLength(30)]],
      customerAccount:[this.data.customerAccount, [Validators.required, Validators.maxLength(30)]],
      serviceAccount:[this.data.serviceAccount, [Validators.required, Validators.maxLength(30)]],
      billingAccount:[this.data.billingAccount, [Validators.required, Validators.maxLength(30)]],
      observations:[this.data.observations, [Validators.required, Validators.maxLength(500)]]
    });
  }

  initFormCreate(): FormGroup {
    return this.formBuilder.group({
      correlativeCmd: ['', [Validators.required, Validators.pattern('[0-9]{1,8}')]],
      correlativeMooringCmd:['', [Validators.required, Validators.pattern('[0-9]{1,3}')]],
      subscriberId:[0,  Validators.maxLength(30)],
      monthsOfPermanence:['', Validators.pattern('[0-9]{1,2}')],
      cmdStatus: ['A', Validators.required],
      userName:['', [Validators.required, Validators.maxLength(50)]],
      supervisorUser:['', [Validators.required, Validators.maxLength(50)]],
      userMooring:['', [Validators.required, Validators.maxLength(50)]],
      userCancelled:[''],
      dateOfAdmission: [''],
      dueDate:[''],
      invoiceLocation:['', [Validators.required, Validators.pattern('[0-9]{1,2}')]],
      invoiceSubLocity:[''],
      invoiceType:['', [Validators.required, Validators.maxLength(3)]],
      invoiceReading: ['', [Validators.required, Validators.maxLength(1)]],
      invoiceNumber:['', [Validators.required, Validators.maxLength(8)]],
      inventoryType:['', [Validators.required, Validators.maxLength(1)]],
      inventoryModel:['', [Validators.required, Validators.maxLength(6)]],
      teamSeries:['', [Validators.required, Validators.maxLength(25)]],
      phoneCost:['', [Validators.required, Validators.pattern(/^\d{1,13}(\.\d{1,2})?$/)]],
      phoneDiscount:['', [Validators.required, Validators.pattern(/^\d{1,13}(\.\d{1,2})?$/)]],
      mooring:[1],
      promotion:[''],
      transactionId:['', [Validators.required, Validators.maxLength(30)]],
      customerAccount:['', [Validators.required, Validators.maxLength(30)]],
      serviceAccount:['', [Validators.required, Validators.maxLength(30)]],
      billingAccount:['', [Validators.required, Validators.maxLength(30)]],
      observations:['', [Validators.required, Validators.maxLength(500)]]
    });
  }

  test(){
    const data = this.dataForm.value;
    console.log(data);
  }

}
