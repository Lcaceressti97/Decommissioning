import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DetallesLoteTarjetasModalComponent } from 'src/app/components/detalles-lote-tarjetas-modal/detalles-lote-tarjetas-modal.component';
import { LoteTarjetasModalComponent } from 'src/app/components/lote-tarjetas-modal/lote-tarjetas-modal.component';
import { CardPrepagoModel } from 'src/app/models/model';
import { UtilService } from 'src/app/services/util.service';

@Component({
  selector: 'app-mantenimiento-tarjetas',
  templateUrl: './mantenimiento-tarjetas.component.html',
  styleUrls: ['./mantenimiento-tarjetas.component.css']
})
export class MantenimientoTarjetasComponent implements OnInit {

  // Props

  // Tables
  rows: CardPrepagoModel[] = [];
  rows2: CardPrepagoModel[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  typeValue: string = "";

  constructor(public utilService: UtilService, private modalService: NgbModal) { }

  ngOnInit(): void {

    const dto: CardPrepagoModel = {
      id:1,model: "DOT080",lin: "TAR", description: "TARJETAS PRE-PAGO DOMITAL L.200 (AJA/AJB)", value: 200, act: "S", createdDate: new Date()
    }

    this.rows.push(dto);

    this.loadingIndicator = false;
    this. rows = [...this.rows];
    this. rows2 = [...this.rows];

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

  // Modal
  openModalCrud(data: CardPrepagoModel, action: string) {

    // Se abre la modal
    const modalRef = this.modalService.open(LoteTarjetasModalComponent, {
      size: "md"
    });

    // Se pasa el input
    modalRef.componentInstance.data = data;
    modalRef.componentInstance.action = action;
    modalRef.componentInstance.messegaEvent.subscribe((status: boolean) => {

      if (status) {
        //this.getModels();
      }

    })

  }

  openLotesModal(data: CardPrepagoModel) {

    // Se abre la modal
    const modalRef = this.modalService.open(DetallesLoteTarjetasModalComponent, {
      size: "xl"
    });

    // Se pasa el input
    modalRef.componentInstance.data = data;
    modalRef.componentInstance.messegaEvent.subscribe((status: boolean) => {

      if (status) {
        //this.getModels();
      }

    })

  }


}
