import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DatatableComponent } from '@swimlane/ngx-datatable';
import { HistoricalModalComponent } from 'src/app/components/historical-modal/historical-modal.component';
import { HistoricalEntity } from 'src/app/entities/Historical';
import { Historical } from 'src/app/models/Historical';
import { HistoricalService } from 'src/app/services/historical.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';
import { Buttons, DropdownSettingsI } from 'src/types';

@Component({
  selector: 'app-blacklist-page',
  templateUrl: './blacklist-page.component.html',
  styleUrls: ['./blacklist-page.component.css']
})
export class BlacklistPageComponent implements OnInit {
  @ViewChild(DatatableComponent) table: DatatableComponent;
  historical: Historical[] = [];
  rowsHistorical: Historical[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  typeValue: string = "";
  messages = messages;
  queryTypeData = [];
  selectQueryTypeData = [];
  param: any
  mostrarTabla = false;
  infoCargado = false;

  type: string = '0';
  public dropdownSettings = {};
  public dropdownList = [
    {
      id: 1,
      itemName: "Nro. de Teléfono"
    },
    {
      id: 2,
      itemName: "Serie"
    }
  ];
  public selectedItems = [];
  private sub: any;
  page: number = 0;
  size: number = 1000;
  //* STYLES
  labelClasses = "col-form-label text-dark col-sm-2 col-md-2 col-lg-2 font-size-large";
  inputClasses = "my-auto col-sm-6 col-md-6 col-lg-4";
  private isInitialLoad = true;

  constructor(private historicalService: HistoricalService,
    private modalService: NgbModal,
    public utilService: UtilService,
    private activatedRoute: ActivatedRoute,
    private router: Router) {

    this.activatedRoute.queryParams.subscribe(params => {
      this.sub = JSON.stringify(params['sub']);

      if (this.sub)
        sessionStorage.setItem('sub', this.sub)
    });
  }


  ngOnInit(): void {

    this.dropdownSettings = {
      singleSelection: true,
      text: "Seleccione un valor",
      selectAllText: "Todos",
      unSelectAllText: "Ninguno",
      enableSearchFilter: true,
      classes: ""
    };
    this.dropdownList = [
      {
        id: 1,
        itemName: "Nro. de Teléfono"
      },
      {
        id: 2,
        itemName: "Serie"
      }
    ];
    this.selectedItems = [];

    let permisos = this.loadPermisos();
    this.activatedRoute.params.subscribe(params => {
      this.param = params['param'];
      this.type = params['type'];
      if (this.type === '1') {
        this.selectedItems = [this.dropdownList[0]];
      } else if (this.type === '2') {
        this.selectedItems = [this.dropdownList[1]];
      }
      if (this.param != 0 && this.param != undefined && this.param != "undefined") {
        if (permisos.filter(x => x == 'GB').length > 0) {
          if (this.type == '1' || this.type == '2') {
            this.getHistoricalByType(this.type, this.param);
          } else {
            this.mostrarTabla = false;
            this.infoCargado = false;
            let caja = document.getElementsByClassName("c-btn")[0];
            caja.setAttribute('style', 'background-color: #fdd7d7;');
            setTimeout(function () { caja.setAttribute('style', ''); }, 3000);
            this.utilService.setMsg('¡Debe seleccionar un parametro de búsqueda!', '', 2000, 'info', 'toast-top-right');
          }
        } else {
          this.utilService.setMsg('¡No tiene permisos para realizar esta consulta!', '', 2000, 'danger', 'toast-top-right');
        }
      } else {
        if (this.isInitialLoad) {
          this.isInitialLoad = false;
          this.getAllHistorical();
        }
      }
    })
  }

  //* COMPONENTS
  loadPermisos() {
    let subs = sessionStorage.getItem('sub').split(",")
    let permisos = subs[2]
    return permisos.split('_')
  }



  getAllHistorical() {

    this.historicalService.getAllHistorical(this.page, this.size).subscribe((res) => {
      if (res.status === 200) {
        this.loadingIndicator = false;
        this.rowsHistorical = [];
        this.historical = [];
        let response = res.body.data.content as HistoricalEntity[];

        response.map((v, k) => {
          let dto = {} as Historical;
          dto.id = v?.id;
          dto.esn = v?.esn;
          dto.acctCode = v?.acctCode;
          dto.phone = v?.phone;
          dto.type = 'ESN/IMEI';
          dto.lockType = v?.lockType;
          dto.status = v?.status;
          dto.createdDate = this.formatDate(v?.createdDate);
          dto.processDate = this.formatDate(v?.processDate);
          this.rowsHistorical.push(dto);
        });
        this.loadingIndicator = false;
        this.rowsHistorical = [...this.rowsHistorical];
        this.historical = [...this.historical];
        if (this.rowsHistorical.length > 0) {
          this.utilService.showNotification(0, "Datos cargados");
        }
      } else {
        this.utilService.showNotificationError(res.status);
      }
    });
  }


  getHistoricalByType(type, value) {
    this.historicalService.getHistoricalByType(type, value).subscribe((res) => {
      if (res.status === 200) {
        this.loadingIndicator = false;
        let data = res.body.data as HistoricalEntity[];
        if (data.length > 0) {
          this.historical = data.map((v) => {
            let dto = {} as Historical;
            dto.id = v.id;
            dto.esn = v.esn;
            dto.acctCode = v.acctCode;
            dto.phone = v.phone;
            dto.type = 'ESN/IMEI';
            dto.lockType = v.lockType;
            dto.status = v?.status;
            dto.createdDate = this.formatDate(v.createdDate);
            dto.processDate = this.formatDate(v.processDate);
            return dto;
          });
          this.rowsHistorical = [...this.historical];

          this.utilService.showNotification(0, "Datos encontrados");

        } else {
          this.utilService.showNotification(1, `No se encontraron datos con el valor ${value}`);
        }
      } else {
        this.utilService.showNotificationError(res.status);
      }
    });
  }

  see(row: any) {
    this.openModal("see", row);
  }

  openModal(button: Buttons, row: Historical = null) {

    const modalRef = this.modalService.open(HistoricalModalComponent, { size: 'xl', backdrop: 'static' });
    modalRef.componentInstance.historical = row;
    modalRef.componentInstance.button = button;
    modalRef.componentInstance.messageEvent.subscribe((historical: Historical) => {
    });
  }


  //* UTILS

  searchHistorical() {
    this.rowsHistorical = this.rowsHistorical.filter((rowsHistorical) => {
      return JSON.stringify(rowsHistorical)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  getTotalText() {
    return this.rowsHistorical.length == 1 ? "Registro" : "Registros";
  }

  reloadRows() {
    this.getAllHistorical();
  }

  onItemSelect(ev) {
    this.infoCargado = false;
    this.mostrarTabla = false;
    this.type = this.selectedItems[0]['id'];
    this.router.navigateByUrl('mov' + '/0/' + this.type)
      .finally(() => { this.reFresh() })
  }

  OnItemDeSelect(ev) {
    this.router.navigateByUrl('mov' + '/' + this.param + '/0')
      .finally(() => { this.reFresh() })
  }

  public reFresh() {
    let url = this.type
    parent.postMessage(url, "*");
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
