import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HistoricalImeiModalComponent } from 'src/app/components/historical-imei-modal/historical-imei-modal.component';
import { ImeiControlFileEntity } from 'src/app/entities/ImeiControlFileEntity';
import { ImeiControlFile } from 'src/app/models/ImeiControlFile';
import { ImsiControlFileService } from 'src/app/services/imei-control-file.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';
import { Buttons, DropdownSettingsI } from 'src/types';

@Component({
  selector: 'app-imei-control-file',
  templateUrl: './imei-control-file.component.html',
  styleUrls: ['./imei-control-file.component.css']
})
export class ImeiControlFileComponent implements OnInit {

  imeiControl: ImeiControlFile[] = [];
  rowsImeiControl: ImeiControlFile[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  typeValue: string = "";
  messages = messages;
  loading: boolean;
  param: any
  mostrarTabla = false;
  infoCargado = false;

  type: string = '0';
  public dropdownSettings = {};
  public dropdownList = [
    {
      id: 1,
      itemName: "Nro. Teléfono"
    },
    {
      id: 2,
      itemName: "IMEI"
    }
  ];
  public selectedItems = [];
  private sub: any;
  private isInitialLoad = true;

  //* STYLES
  labelClasses = "col-form-label text-dark col-sm-2 col-md-2 col-lg-2 font-size-large";
  inputClasses = "my-auto col-sm-6 col-md-6 col-lg-4";

  constructor(private imsiControlFileService: ImsiControlFileService,
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
        itemName: "Nro. Teléfono"
      },
      {
        id: 2,
        itemName: "IMEI"
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
            this.getByPhoneOrImei(this.type, this.param);
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
          this.getAllControlImei();
        }
      }
    })
  }

  loadPermisos() {
    let subs = sessionStorage.getItem('sub').split(",")
    let permisos = subs[2]
    return permisos.split('_')
  }

  //* COMPONENTS

  getAllControlImei() {

    this.imsiControlFileService.getImeiControlFile().subscribe((res) => {
      if (res.status === 200) {
        this.loadingIndicator = false;
        this.rowsImeiControl = [];
        this.imeiControl = [];
        let response = res.body.data as ImeiControlFileEntity[];

        response.map((v, k) => {
          let dto = {} as ImeiControlFile;
          dto.id = v.id;
          dto.phone = v.phone;
          dto.imei = v.imei;
          dto.status = v.status;
          dto.createdDate = this.formatDate(v?.createdDate);
          this.rowsImeiControl.push(dto);
        });
        this.loadingIndicator = false;
        this.rowsImeiControl = [...this.rowsImeiControl];
        this.imeiControl = [...this.imeiControl];

      } else {
        this.utilService.showNotificationError(res.status);
      }
    });
  }

  getByPhoneOrImei(type, value) {
    this.imsiControlFileService.getByPhoneOrImei(type, value).subscribe((res) => {
      if (res.status === 200) {
        this.loadingIndicator = false;
        let data = res.body.data as ImeiControlFileEntity[];
        if (data.length > 0) {
          this.imeiControl = data.map((v) => {
            let dto = {} as ImeiControlFile;
            dto.id = v.id;
            dto.phone = v.phone;
            dto.imei = v.imei;
            dto.status = v.status;
            dto.createdDate = this.formatDate(v?.createdDate);
            return dto;
          });
          this.rowsImeiControl = [...this.imeiControl];
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

  openModal(button: Buttons, row: ImeiControlFile = null) {

    const modalRef = this.modalService.open(HistoricalImeiModalComponent, { size: 'xl', backdrop: 'static' });
    modalRef.componentInstance.imeiControlFile = row;
    modalRef.componentInstance.button = button;
    modalRef.componentInstance.messageEvent.subscribe((imeiControlFile: ImeiControlFile) => {
    });
  }

  //* UTILS
  searchImeiControlFile() {
    this.rowsImeiControl = this.rowsImeiControl.filter((imsiControlFile) => {
      return JSON.stringify(imsiControlFile)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  getTotalText() {
    return this.rowsImeiControl.length == 1 ? "Registro" : "Registros";
  }

  reloadRows() {
    this.getAllControlImei();
  }

  onItemSelect(ev) {
    this.infoCargado = false;
    this.mostrarTabla = false;
    this.type = this.selectedItems[0]['id'];
    console.log(this.type)
    this.router.navigateByUrl('/mov' + '/0/' + this.type)
      .finally(() => { this.reFresh() })
  }

  OnItemDeSelect(ev) {
    this.router.navigateByUrl('/mov' + '/' + this.param + '/0')
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
