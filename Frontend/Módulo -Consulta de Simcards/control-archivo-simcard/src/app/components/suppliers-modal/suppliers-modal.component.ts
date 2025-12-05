import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SuppliersEntity } from 'src/app/entities/SuppliersEntity';
import { SimcardControl } from 'src/app/models/SimcardControl';
import { Suppliers } from 'src/app/models/Suppliers';
import { SimcardControlFileService } from 'src/app/services/simcard-control-file.service';
import { UtilService } from 'src/app/services/util.service';
import { Buttons } from 'src/types';


@Component({
  selector: 'app-suppliers-modal',
  templateUrl: './suppliers-modal.component.html',
  styleUrls: ['./suppliers-modal.component.css']
})
export class SuppliersModalComponent implements OnInit {

  @Output() messageEvent = new EventEmitter<SimcardControl>();
  @Input() simcardControl: SimcardControl;
  @Input() button: Buttons;
  @Input() idSimcardPadre: number = null;
  @Input() idSimcardControl: number = null;

  suppliers: Suppliers[] = [];
  rowsSuppliers: Suppliers[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  typeValue: string = "";
  loading: boolean;
  param: any
  mostrarTabla = false;
  infoCargado = false;

  constructor(private simcardControlFileService: SimcardControlFileService, private modalService: NgbModal,
    public utilService: UtilService,
    private activeModal: NgbActiveModal,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.getSuppliers();
  }

  getSuppliers() {
    this.simcardControlFileService.getSuppliers().subscribe((res) => {
      if (res.status === 200) {
        this.rowsSuppliers = [];
        this.suppliers = [];

        let response = res.body.data as SuppliersEntity[];
        response.map((v, k) => {
          let dto = {} as Suppliers;
          dto.id = v.id;
          dto.supplierName = v?.supplierName;
          dto.phone = v?.phone;
          dto.email = v?.email;
          dto.status = v?.status;
          dto.created = this.formatDate(v?.created);
          this.rowsSuppliers.push(dto);
        });

        this.loadingIndicator = false;
        this.rowsSuppliers = [...this.rowsSuppliers];
        this.suppliers = [...this.suppliers];
      } else {
        this.utilService.showNotificationError(res.status);
      }
    });
  }



  updateSuppliersSimcardPadre(id: number) {
    const idSimcardPadre = this.idSimcardPadre;

    this.simcardControlFileService.updateSuppliersSimcardPadre(idSimcardPadre, id).subscribe(
      () => {
        this.utilService.showNotification(
          0,
          "Proveedor actualizado con éxito"
        );

        this.activeModal.close();
        
      },
      error => {
        console.error(error);
      }
    );
  }

  updateSuppliersSimcardControl(id: number) {

    this.simcardControlFileService.updateSupplierSimcardControl(this.idSimcardControl, id).subscribe(
      () => {
        this.activeModal.close();
      },
      error => {
        console.error(error);
      }
    );
  }
  


  //* UTILS
  searchSuppliers() {
    this.rowsSuppliers = this.rowsSuppliers.filter((rowsSuppliers) => {
      return JSON.stringify(rowsSuppliers)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  getTotalText() {
    return this.rowsSuppliers.length == 1 ? "Registro" : "Registros";
  }

  reloadRows() {
    this.getSuppliers();
  }

  getTitle() {
    return "Proveedores:";
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

    return `${dia}/${mes}/${año} ${horas}:${minutos}:${segundos}`;
  }


}
