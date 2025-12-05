import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { NgbActiveModal, NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { ImeiControlFileEntity } from "src/app/entities/ImeiControlFileEntity";
import { ImeiControlFile } from "src/app/models/ImeiControlFile";
import { ExcelService } from "src/app/services/excel.service";
import { ImsiControlFileService } from "src/app/services/imei-control-file.service";
import { UtilService } from "src/app/services/util.service";
import { Buttons, DropdownSettingsI } from "src/types";
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-historical-imei-modal',
  templateUrl: './historical-imei-modal.component.html',
  styleUrls: ['./historical-imei-modal.component.css']
})
export class HistoricalImeiModalComponent implements OnInit {


  @Output() messageEvent = new EventEmitter<ImeiControlFile>();
  @Input() imeiControlFile: ImeiControlFile;
  @Input() button: Buttons;

  loadingIndicator: boolean = true;
  imeiHistorical: ImeiControlFile[] = [];
  rowsImeiHistorical: ImeiControlFile[] = [];
  resultsPerPage: number = 10;
  searchedValue: string = "";
  dataExport;

  constructor(private imsiControlFileService: ImsiControlFileService, private excelService: ExcelService, private activeModal: NgbActiveModal,
    public utilService: UtilService) { }

  ngOnInit(): void {
    this.getHistoricalByPhone(this.imeiControlFile.phone);
  }

  getHistoricalByPhone(phone: any) {

    this.imsiControlFileService.getByPhone(phone).subscribe((res) => {
      if (res.status === 200) {
        this.rowsImeiHistorical = [];
        this.imeiHistorical = [];

        let response = res.body.data as ImeiControlFileEntity[];
        response.map((v, k) => {
          let dto = {} as ImeiControlFile;
          dto.id = v?.id;
          dto.phone = phone;
          dto.imei = v?.imei;
          dto.imsi = v?.imsi;
          dto.status = v?.status;
          dto.createdDate = this.formatDate(v?.createdDate);
          this.rowsImeiHistorical.push(dto);
        });

        this.loadingIndicator = false;
        this.rowsImeiHistorical = [...this.rowsImeiHistorical];
        this.imeiHistorical = [...this.imeiHistorical];
      } else {
        this.utilService.showNotificationError(res.status);
      }
    });
  }

  exportData() {
    const dataExcel = this.rowsImeiHistorical.map(objeto => {
      const estado = objeto.status === 1 ? 'Activo' : 'Inactivo';
      return { telefono: objeto.phone, imei: objeto.imei, imsi: objeto.imsi, fecha: objeto.createdDate, estado: estado };
    });

    const arrayDeArrays: any[][] = dataExcel.map(obj => Object.values(obj));

    this.dataExport = arrayDeArrays;

    const columnNames = ['Teléfono', 'IMEI', 'IMSI', 'Fecha', 'Estado'];

    this.excelService.generarExcel(this.dataExport, 'Detalle Control IMEI', columnNames);
  }


  //* UTILS
  searchHistorical() {
    this.rowsImeiHistorical = this.rowsImeiHistorical.filter((rowsImeiHistorical) => {
      return JSON.stringify(rowsImeiHistorical)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  getTotalText() {
    return this.rowsImeiHistorical.length == 1 ? "Registro" : "Registros";
  }

  reloadRows() {
    this.getHistoricalByPhone(this.imeiControlFile.phone);
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
