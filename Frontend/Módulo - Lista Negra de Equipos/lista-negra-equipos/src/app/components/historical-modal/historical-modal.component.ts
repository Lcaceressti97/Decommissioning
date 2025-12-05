import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { NgbActiveModal, NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { UtilService } from "src/app/services/util.service";
import { Buttons, DropdownSettingsI } from "src/types";
import { DetailHistoricalModalComponent } from "../detail-historical-modal/detail-historical-modal.component";
import { HistoricalEntity } from "src/app/entities/Historical";
import { Historical } from "src/app/models/Historical";
import { HistoricalDetail } from "src/app/models/HistoricalDetail";
import { HistoricalDetailEntity } from "src/app/entities/HistoricalDetailEntity";
import { HistoricalDetailService } from "src/app/services/historical-detail.service";

@Component({
  selector: 'app-historical-modal',
  templateUrl: './historical-modal.component.html',
  styleUrls: ['./historical-modal.component.css']
})
export class HistoricalModalComponent implements OnInit {

  @Output() messageEvent = new EventEmitter<Historical>();
  @Input() historical: Historical;
  @Input() button: Buttons;

  loadingIndicator: boolean = true;
  detailHistorical: HistoricalDetail[] = [];
  rowsHistoricalDetail: HistoricalDetail[] = [];
  resultsPerPage: number = 10;
  searchedValue: string = "";

  constructor(private activeModal: NgbActiveModal, private modalService: NgbModal,
    private historicalDetailService: HistoricalDetailService, public utilService: UtilService) { }

  ngOnInit(): void {
    this.getHistoricalDetailsByEsn(this.historical.esn);
  }

  //* COMPONENTS
  getHistoricalDetailsByEsn(esnImei: any) {

    this.historicalDetailService.getHistoricalDetailsByEsn(esnImei).subscribe((res) => {
      if (res.status === 200) {
        this.rowsHistoricalDetail = [];
        this.detailHistorical = [];
        
        let response = res.body.data as HistoricalDetailEntity[];
        response.map((v, k) => {
          let dto = {} as HistoricalDetail;
          dto.id = v?.id;
          dto.esnImei = v?.esnImei;
          dto.ivesn = v?.ivesn;
          dto.operador = v?.operador;
          dto.tipobloqueo = v?.tipobloqueo;
          dto.telefono = v?.telefono;
          dto.anexo = v?.anexo;
          dto.technologyType = v?.technologyType;
          dto.simcard = v?.simcard;
          dto.nombreUsuarioTransaccion = v?.nombreUsuarioTransaccion;
          dto.identidadUsuarioTransaccion = v?.identidadUsuarioTransaccion;
          dto.direccionUsuarioTransaccion = v?.direccionUsuarioTransaccion;
          dto.telefonoUsuarioTransaccion = v?.telefonoUsuarioTransaccion;
          dto.motivoBloqueo = v?.motivoBloqueo;
          dto.createdDate = this.formatDate(v?.createdDate);
          dto.nombreDesbloqueante = v?.nombreDesbloqueante;
          dto.identidadDesbloqueante = v?.identidadDesbloqueante;
          dto.direccionDesbloqueante = v?.direccionDesbloqueante;
          dto.telefonoDesbloqueante = v?.telefonoDesbloqueante;
          dto.motivoDesbloqueo = v?.motivoDesbloqueo;
          dto.fechaDesbloqueo = this.formatDate(v?.fechaDesbloqueo);
          dto.usuarioTransaccion = v?.usuarioTransaccion;
          dto.pantallaTransaccion = v?.pantallaTransaccion;
          dto.status = v?.status;
          dto.estadoBajaAlta = v?.estadoBajaAlta;
          dto.fechaBloqueo = this.formatDate(v?.fechaBloqueo);
          dto.modelo = v?.modelo;


          this.rowsHistoricalDetail.push(dto);
        });

        this.loadingIndicator = false;
        this.rowsHistoricalDetail = [...this.rowsHistoricalDetail];
        this.detailHistorical = [...this.detailHistorical];
      } else {
        this.utilService.showNotificationError(res.status);
      }
    });
  }

  see(row: any) {
    this.openModal("see", row);
  }


  openModal(button: Buttons, row: Historical = null) {

    const modalRef = this.modalService.open(DetailHistoricalModalComponent, { size: 'xl', backdrop: 'static' });
    modalRef.componentInstance.rowsHistoricalDetail = row;
    modalRef.componentInstance.button = button;
    modalRef.componentInstance.messageEvent.subscribe((historical: Historical) => {
    });
  }

  //* UTILS
  searchHistorical() {
    this.rowsHistoricalDetail = this.rowsHistoricalDetail.filter((rowsHistoricalDetail) => {
      return JSON.stringify(rowsHistoricalDetail)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  getTotalText() {
    return this.rowsHistoricalDetail.length == 1 ? "Registro" : "Registros";
  }

  reloadRows() {
    this.getHistoricalDetailsByEsn(this.historical.esn);
  }
  getTitle() {
    if (this.button === "see") return "Histórico:";
  }

  closeModal() {
    this.activeModal.close();
  }

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
