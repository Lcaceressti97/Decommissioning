import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ControlOrder } from 'src/app/models/ControlOrder';
import { SimcardPadre } from 'src/app/models/SimcardPadre';
import { SimcardControlFileService } from 'src/app/services/simcard-control-file.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-see-order-modal',
  templateUrl: './see-order-modal.component.html',
  styleUrls: ['./see-order-modal.component.css']
})
export class SeeOrderModalComponent implements OnInit {
  @Input() idSimcardPadre: number = null;
  @Input() formData: any;

  seeOrderForm: FormGroup;
  loadingIndicator: boolean = true;
  orderId: number;
  statusOrder: string;

  constructor(private simcardControlFileService: SimcardControlFileService, private activeModal: NgbActiveModal, public utilService: UtilService, private fb: FormBuilder) { }


  ngOnInit(): void {
    this.getOrderByIdPadre();
    this.initForm();
  }

  initForm() {
    this.seeOrderForm = this.fb.group({
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
  getOrderByIdPadre() {
    const idSimcardPadre = this.idSimcardPadre;

    this.simcardControlFileService.getOrderByIdPadre(idSimcardPadre).subscribe((res) => {
      if (res.status === 200) {
        let response = res.body.data as ControlOrder;
        if (response) {
          this.orderId = response.id;
          this.statusOrder = response.status;
          this.seeOrderForm.reset();
          this.seeOrderForm.patchValue({
            suppliersId: response?.idSupplier,
            supplierName: response?.supplierName,
            noOrder: response?.noOrder,
            userName: response?.userName,
            customerName: response?.customerName,
            initialImsi: response?.initialImsi,
            initialIccd: response?.initialIccd,
            orderQuantity: response?.orderQuantity,
            fileName: response?.fileName,
            created: response?.created,
            customer: response?.customerName,
            hlr: response?.hlr,
            batch: response?.batch,
            key: response?.key,
            type: response?.type,
            art: response?.art,
            graphic: response?.graphic,
            model: response?.model,
            versionSize: response?.versionSize,
            status: response?.status,
          });
          // Desglose de campos
          const typeArray = response?.type?.split(" ");
          if (typeArray && typeArray.length >= 1) {
            this.seeOrderForm.patchValue({
              type: typeArray[0],
              typeInput1: typeArray[1]
            });
          }

          const artArray = response?.art?.split(" ");
          if (artArray && artArray.length >= 1) {
            this.seeOrderForm.patchValue({
              art: artArray[0],
              artInput1: artArray[1]
            });
          }

          const graphicArray = response?.graphic?.split(" ");
          if (graphicArray && graphicArray.length >= 1) {
            this.seeOrderForm.patchValue({
              graphic: graphicArray[0],
              graphicInput1: graphicArray[1]
            });
          }

          const modelArray = response?.model?.split(" ");
          if (modelArray && modelArray.length >= 1) {
            this.seeOrderForm.patchValue({
              model: modelArray[0],
              modelInput1: modelArray[1]
            });
          }

          const versionSizeArray = response?.versionSize?.split(" ");
          if (versionSizeArray && versionSizeArray.length >= 3) {
            this.seeOrderForm.patchValue({
              versionSize: versionSizeArray[0],
              versionSizeInput1: versionSizeArray[1],
              versionSizeInput2: versionSizeArray[2],
              versionSizeInput3: versionSizeArray[3],
            });
          }
          this.loadingIndicator = false;
        } else {
          console.error("La respuesta del servidor no contiene datos.");
        }
      } else {
        this.utilService.showNotificationError(res.status);
      }
    });
  }

  updateStatusDetail() {
    Swal.fire({
      html: `<h5>¿Está seguro de procesar esta orden?</h5>`,
      showCancelButton: true,
      customClass: {
        confirmButton: "btn btn-round bg-lemon",
        cancelButton: "btn btn-round btn-danger",
      },
      reverseButtons: true,
      confirmButtonText: "Sí, procesar",
      buttonsStyling: false,
    }).then((result) => {
      if (result.value) {
        this.simcardControlFileService.updateStatusDetail(this.idSimcardPadre).subscribe(
          () => {
            this.utilService.showNotification(0, "Orden procesada con éxito");

            this.closeModal();
          },
          (error) => {
            console.error(error);
          }
        );
      }
    });
  }

  updateStatusOrder() {
    this.simcardControlFileService.updateStatusOrder(this.orderId).subscribe(
      () => {
        //this.closeModal();
      },
      (error) => {
        console.error(error);
      }
    );
  }
  

  //* UTILS
  getTitle() {
    return "Detalle del Pedido:";
  }

  closeModal() {
    this.activeModal.close();
  }
}
