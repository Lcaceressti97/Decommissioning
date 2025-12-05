import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ControlOrderEntity } from 'src/app/entities/ControlOrderEntity';
import { ControlOrder } from 'src/app/models/ControlOrder';
import { SimcardControlFileService } from 'src/app/services/simcard-control-file.service';
import { UtilService } from 'src/app/services/util.service';
import { Buttons } from 'src/types';
import Swal from "sweetalert2/dist/sweetalert2.js";


@Component({
  selector: 'app-order-control-modal',
  templateUrl: './order-control-modal.component.html',
  styleUrls: ['./order-control-modal.component.css']
})
export class OrderControlModalComponent implements OnInit {

  @Output() messageEvent = new EventEmitter<ControlOrder>();
  @Input() button: Buttons;

  orderControl = [];
  rows = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  typeValue: string = "";

  mostrarTabla = false;
  infoCargado = false;


  constructor(private simcardControlFileService: SimcardControlFileService, public utilService: UtilService, private activeModal: NgbActiveModal, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.getOrderControl();
  }

  getOrderControl() {
    this.simcardControlFileService.getOrderControl().subscribe((res) => {
      if (res.status === 200) {

        let data = res.body.data as ControlOrderEntity[];
        this.orderControl = data.map((v, k) => {
          let dto = {} as ControlOrder;
          dto.id = v.id;
          dto.idSupplier = v?.idSupplier;
          dto.supplierName = v?.supplierName;
          dto.noOrder = v?.noOrder;
          dto.userName = v?.userName;
          dto.customerName = v?.customerName;

          dto.initialImsi = v?.initialImsi;
          dto.initialIccd = v?.initialIccd;
          dto.orderQuantity = v?.orderQuantity;
          dto.fileName = v?.fileName;
          dto.created = this.formatDate(v?.created);
          dto.customer = v?.customer;
          dto.hlr = v?.hlr;
          dto.batch = v?.batch;
          dto.key = v?.key;

          dto.status = v?.status;
          return dto;
        });
        this.rows = [...this.orderControl];
      } else {
        this.utilService.showNotificationError(res.status);
      }
    });
  }


  updateStatusOrder(id: number) {
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
        this.simcardControlFileService.updateStatusOrder(id).subscribe(
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
  
  

  //* UTILS
  searchOrderControl() {
    this.rows = this.orderControl.filter((orderControl) => {
      return JSON.stringify(orderControl)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  getTotalText() {
    return this.rows.length == 1 ? "Registro" : "Registros";
  }

  reloadRows() {
    this.getOrderControl();
  }

  getTitle() {
    return "Control de Pedidos";
  }

  closeModal() {
    this.activeModal.close();
  }

  formatDate(fecha: string | null): string {
    if (!fecha) {
      return '';
    }

    const fechaFormateada = new Date(fecha);
    const dia = fechaFormateada.getDate().toString().padStart(2, '0');
    const mes = (fechaFormateada.getMonth() + 1).toString().padStart(2, '0');
    const año = fechaFormateada.getFullYear();
    const horas = fechaFormateada.getHours().toString().padStart(2, '0');
    const minutos = fechaFormateada.getMinutes().toString().padStart(2, '0');
    const segundos = fechaFormateada.getSeconds().toString().padStart(2, '0');

    return `${dia}/${mes}/${año}`;
  }
}
