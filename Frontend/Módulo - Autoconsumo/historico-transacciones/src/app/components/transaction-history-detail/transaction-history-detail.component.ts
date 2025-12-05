import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TransactionHistoryModel } from 'src/app/models/TransactionHistoryModel';
import { TransactionHistoryService } from 'src/app/services/transaction-history.service';
import { UtilsService } from 'src/app/services/utils.service';
import { messages } from 'src/app/utils/enum';
import { StrModalComponent } from '../str-modal/str-modal.component';

@Component({
  selector: 'app-transaction-history-detail',
  templateUrl: './transaction-history-detail.component.html',
  styleUrls: ['./transaction-history-detail.component.css']
})
export class TransactionHistoryDetailComponent implements OnInit {

  // Props

  // Input | Output
  @Input() data: TransactionHistoryModel;
  @Input() action: string;
  @Output() messageEvent = new EventEmitter<boolean>();

  // Form
  form: FormGroup;
  fileName: string = "Seleccionar";
  file: File = null;
  messages = messages;
  readonly: boolean = false;

  constructor(public utilService: UtilsService, private bankConfigurationService: TransactionHistoryService, private activeModal: NgbActiveModal, private formBuilder: FormBuilder, private modalService: NgbModal) { }

  ngOnInit(): void {

    this.validateShow();

  }

  // En el componente padre
  openSrtModal() {

    // Se abre la modal
    const modalRef = this.modalService.open(StrModalComponent, {
      size: "lg"
    });

    // Se pasa el input
    modalRef.componentInstance.dataSrt = this.data;

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
      cycle: ["", [Validators.required]],
      chargeCode: ["", [Validators.required, Validators.maxLength(100)]],
      acctCode: ["", [Validators.required, Validators.maxLength(150)]],
      subscriberOrigin: ["", [Validators.required, Validators.maxLength(100)]],
      itemName: ["", [Validators.required, Validators.maxLength(100)]],
      transactionId: ["", [Validators.required, Validators.maxLength(100)]],
      businessUnit: ["", [Validators.required, Validators.maxLength(50)]],
      item: ["", [Validators.required, Validators.maxLength(5)]],
      productId: ["", [Validators.maxLength(150)]],
      subscriber: ["", [Validators.maxLength(150)]],
      amount: ["", [Validators.required, Validators.maxLength(150)]],
      discount: ["", [Validators.required]],
      status: ["", [Validators.required]],
      chargeCodeStatus: ["", [Validators.required]],
      message: ["", [Validators.required, Validators.maxLength(150)]],

    });
  }

  /**
   * Método encargado de construir el formulario reactivo
   * 
   */
  initFormTwo(): FormGroup {
    const statusValue = this.data.status === "Activo" ? "1" : "0";
    const chargeCodeStatusValue = this.data.status === "Activo" ? "1" : "0";

    return this.formBuilder.group({
      cycle: [this.data.cycle, [Validators.required]],
      chargeCodeType: [this.data.chargeCodeType, [Validators.required, Validators.maxLength(100)]],
      acctCode: [this.data.acctCode, [Validators.required, Validators.maxLength(150)]],
      subscriberOrigin: [this.data.subscriberOrigin, [Validators.required, Validators.maxLength(100)]],
      itemName: [this.data.itemName, [Validators.required, Validators.maxLength(100)]],
      id: [this.data.id, [Validators.required, Validators.maxLength(100)]],
      businessUnit: [this.data.businessUnit, [Validators.required, Validators.maxLength(50)]],
      item: [this.data.item, [Validators.required, Validators.maxLength(150)]],
      productId: [this.data.productId, [Validators.maxLength(150)]],
      priIdentOfSubsc: [this.data.priIdentOfSubsc, [Validators.maxLength(150)]],
      feeItemAmount: [this.data.feeItemAmount, [Validators.required]],
      discountedAmount: [this.data.discountedAmount, [Validators.required]],
      status: [statusValue, [Validators.required, Validators.maxLength(150)]],
      chargeCodeStatus: [chargeCodeStatusValue, [Validators.required]],
      message: [this.data.message, [Validators.required, Validators.maxLength(150)]],
      created: [this.data.created, [Validators.required, Validators.maxLength(150)]],

    });
  }


  // Operación
  /*   crudBank() {
      const title: string = this.action === 'add' ? "crear" : "actualizar"
  
      Swal.fire({
  
        title: 'Advertencia',
        text: `¿Desea ${title} este banco?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#002e6e',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Aceptar'
      }).then(async (result: any) => {
  
        if (result.value) {
  
          let data: BankModel = this.form.value;
  
          if (this.action === "add") {
            const VALIDATE_ADD = await this.postProvider(data);
  
            if (VALIDATE_ADD) {
  
              this.utilService.showNotification(0, "Banco creado con éxito");
  
              this.messageEvent.emit(true);
              this.closeModal();
            }
  
          } else {
  
            const VALIDATE_UPDATE = await this.putProvider(this.data.id, data);
  
            if (VALIDATE_UPDATE) {
  
              this.utilService.showNotification(0, "Banco actualizado con éxito");
  
              this.messageEvent.emit(true);
              this.closeModal();
            }
  
          }
  
        }
  
      });
  
    } */




  formatDate(date: string | null): string {
    if (!date) {
      return '';
    }

    const formattedDate = new Date(date);
    const day = formattedDate.getDate().toString().padStart(2, '0');
    const month = (formattedDate.getMonth() + 1).toString().padStart(2, '0');
    const year = formattedDate.getFullYear();
    let hours = formattedDate.getHours();
    const ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12;
    hours = hours ? hours : 12;
    const minutes = formattedDate.getMinutes().toString().padStart(2, '0');
    const seconds = formattedDate.getSeconds().toString().padStart(2, '0');

    return `${day}/${month}/${year} ${hours}:${minutes}:${seconds} ${ampm}`;
  }


}

