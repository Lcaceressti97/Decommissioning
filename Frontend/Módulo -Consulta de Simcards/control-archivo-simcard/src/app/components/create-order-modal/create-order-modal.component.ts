import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { SimcardPadreEntity } from 'src/app/entities/SimcardPadreEntity';
import { ControlOrder } from 'src/app/models/ControlOrder';
import { SimcardPadre } from 'src/app/models/SimcardPadre';
import { SimcardControlFileService } from 'src/app/services/simcard-control-file.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from "sweetalert2/dist/sweetalert2.js";
import { take } from 'rxjs/operators';
import { EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-create-order-modal',
  templateUrl: './create-order-modal.component.html',
  styleUrls: ['./create-order-modal.component.css']
})
export class CreateOrderModalComponent implements OnInit {
  @Input() idSimcardPadre: number = null;
  @Input() formData: any;
  @Input() idSimcardControl: number = null;
  @Output() suppliersIdEvent = new EventEmitter<number>();

  
  createOrderForm: FormGroup;
  simcardPadre: SimcardPadre;
  rowsSimcardPadre: SimcardPadre[] = [];
  loadingIndicator: boolean = true;
  nextOrderNumber: string;

  constructor(private simcardControlFileService: SimcardControlFileService, private activeModal: NgbActiveModal, public utilService: UtilService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.getSimcardPadreById();
    this.getNextOrderNumber();
    this.initForm();

  }

  initForm() {
    this.createOrderForm = this.fb.group({
      suppliersId: [null, Validators.required],
      supplierName: [null, Validators.required],
      noOrder: [null, Validators.required],
      userName: [null, Validators.required],
      customerName: [null, Validators.required],
      initialImsi: [null, Validators.required],
      initialIccd: [null, Validators.required],
      orderQuantity: [null, Validators.required],
      fileName: [null, Validators.required],
      created: [null, Validators.required],
      customer: [null, Validators.required],
      hlr: [null, Validators.required],
      batch: [null, Validators.required],
      key: [null, Validators.required],
      type: [null, Validators.required],
      typeInput1: [null, Validators.required],
      art: [null, Validators.required],
      artInput1: [null, Validators.required],
      graphic: [null, Validators.required],
      graphicInput1: [null, Validators.required],
      model: [null, Validators.required],
      modelInput1: [null, Validators.required],
      versionSize: [null, Validators.required],
      versionSizeInput1: [null, Validators.required],
      versionSizeInput2: [null, Validators.required],
      versionSizeInput3: [null, Validators.required],
    });
  }

  //* COMPONENTS

  getSimcardPadreById() {
    const idSimcardPadre = this.idSimcardPadre;

    this.simcardControlFileService.getSimcardPadreById(idSimcardPadre).subscribe((res) => {
      if (res.status === 200) {
        let response = res.body.data as SimcardPadreEntity;
        if (response) {
          this.createOrderForm.reset();
          this.createOrderForm.patchValue({
            suppliersId: response?.suppliersId,
            supplierName: response?.supplierName,
            customerName: response?.customerName,
            initialImsi: response?.imsi,
            initialIccd: response?.serNb,
            fileName: response?.fileName,
            created: response?.orderDate,
            customer: response?.customerName,
            hlr: response?.profile,
            batch: response?.batch,
            userName: this.utilService.getSystemUser().toUpperCase(),
            noOrder: this.nextOrderNumber
          });

          this.loadingIndicator = false;
        } else {
          console.error("La respuesta del servidor no contiene datos.");
        }
      } else {
        this.utilService.showNotificationError(res.status);
      }
      if (!this.createOrderForm.value.suppliersId || !this.createOrderForm.value.supplierName) {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'Debe seleccionar un proveedor para crear la orden.',
        });
      } else {
        this.suppliersIdEvent.emit(this.createOrderForm.value.suppliersId);
      }
    });
  }

  getNextOrderNumber() {
    this.simcardControlFileService.getNextOrderId().pipe(take(1)).subscribe(
      (response) => {
        this.nextOrderNumber = response?.body.data;
        console.log('Next Order Number:', this.nextOrderNumber);
      },
      (error) => {
        console.error('Error fetching next order ID:', error);
      }
    );
  }

  saveOrderControl() {
    if (this.createOrderForm.valid) {
      const orderControl: ControlOrder = {
        idSimcardPadre: this.idSimcardPadre,
        idSupplier: +this.createOrderForm.value.suppliersId,
        supplierName: this.createOrderForm.value.supplierName,
        noOrder: +this.createOrderForm.value.noOrder,
        userName: this.createOrderForm.value.userName,
        customerName: this.createOrderForm.value.customerName,
        initialImsi: this.createOrderForm.value.initialImsi,
        initialIccd: this.createOrderForm.value.initialIccd,
        orderQuantity: +this.createOrderForm.value.orderQuantity,
        fileName: this.createOrderForm.value.fileName,
        created: this.createOrderForm.value.created,
        customer: this.createOrderForm.value.customer,
        hlr: this.createOrderForm.value.hlr,
        batch: this.createOrderForm.value.batch,
        key: this.createOrderForm.value.key,
        type: this.createOrderForm.value.type + " " + this.createOrderForm.value.typeInput1,
        art: this.createOrderForm.value.art + " " + this.createOrderForm.value.artInput1,
        graphic: this.createOrderForm.value.graphic + " " + this.createOrderForm.value.graphicInput1,
        model: this.createOrderForm.value.model + " " + this.createOrderForm.value.modelInput1,
        versionSize: this.createOrderForm.value.versionSize + " " + this.createOrderForm.value.versionSizeInput1 + " " + this.createOrderForm.value.versionSizeInput2 + " " + this.createOrderForm.value.versionSizeInput3,
        status: 'E'
      };

      Swal.fire({
        html: `
          <h5>Se creará una nueva orden de control. ¿Desea continuar?</h5>`,
        showCancelButton: true,
        customClass: {
          confirmButton: "btn btn-round bg-lemon",
          cancelButton: "btn btn-round btn-danger",
        },
        reverseButtons: true,
        confirmButtonText: "Continuar",
        buttonsStyling: false,
      }).then((result) => {
        if (result.value) {
          this.simcardControlFileService
            .saveOrderControl(orderControl)
            .subscribe((res) => {
              if (res.status === 200) {
                this.utilService.showNotificationSuccess("Orden guardada exitosamente");
                this.closeModal();
              } else {
                this.utilService.showNotificationError(res.status);
              }
            });
        }
      });
    } else {
      this.utilService.showNotification(1, "Por favor, complete todos los campos obligatorios.");
    }
  }

  
  updateOrderSimcardControl() {
    let id = parseInt(this.nextOrderNumber, 10);


    this.simcardControlFileService.updateOrderSimcardControl(this.idSimcardControl, id).subscribe(
      () => {
        //this.activeModal.close();
      },
      error => {
        console.error(error);
      }
    );
  }

  //* UTILS
  getTitle() {
    return "Control Pedidos Proveedores:";
  }

  closeModal() {
    this.activeModal.close();
  }
}
