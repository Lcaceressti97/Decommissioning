import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TransactionHistoryDetailComponent } from 'src/app/components/transaction-history-detail/transaction-history-detail.component';
import { TransactionHistoryModel } from 'src/app/models/TransactionHistoryModel';
import { TransactionHistoryService } from 'src/app/services/transaction-history.service';
import { UtilsService } from 'src/app/services/utils.service';
import { compareDesc, format, subDays } from 'date-fns';
import { TransactionHistoryResponse } from 'src/app/entities/response';
import { DatePipe } from '@angular/common';
import { ExcelService } from 'src/app/services/excel.service';
import Swal from "sweetalert2/dist/sweetalert2.js";
import { PdfService } from 'src/app/services/pdf.service';

@Component({
  selector: 'app-transaction-history',
  templateUrl: './transaction-history.component.html',
  styleUrls: ['./transaction-history.component.css']
})
export class TransactionHistoryComponent implements OnInit {

  // PARAMS
  param: any = 0;
  type: any = 0;

  inputClasses1 = "my-auto col-sm-2 col-md-2 col-lg-2";
  inputClasses = "my-auto";

  // Table
  rows: TransactionHistoryModel[] = [];
  rows2: TransactionHistoryModel[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  typeValue: string = "";
  private sub: any;
  mostrarTabla = false;
  infoCargado = false;
  dateForm!: FormGroup;
  invalidDate: boolean = false;
  transactionHistoryData: any[] = [];

  // Export
  dataExport: any = null;

  public dropsownSettings = {};
  public dropDownList = [
    {
      id: 1,
      itemName: "Cuenta de Facturación"
    },
    {
      id: 2,
      itemName: "Nro. Teléfono"
    },
    {
      id: 3,
      itemName: "Ciclo"
    }
  ];
  public selectedItem = [];

  constructor(public utilService: UtilsService, private modalService: NgbModal, private transactionHistoryService: TransactionHistoryService, private activatedRoute: ActivatedRoute,
    private router: Router, private readonly fb: FormBuilder, private datePipe: DatePipe, private excelService: ExcelService,private pdfService: PdfService) {
    this.activatedRoute.queryParams.subscribe(params => {
      this.sub = JSON.stringify(params['sub']);

      if (this.sub)
        sessionStorage.setItem('sub', this.sub)
    });
  }

  ngOnInit(): void {
    this.dateForm = this.initForm();
    this.dropsownSettings = {
      singleSelection: true,
      text: "Seleccione un valor",
      selectAllText: "Todos",
      unSelectAllText: "Ninguno",
      enableSearchFilter: true,
      classes: ""
    };

    this.dropDownList = [
      {
        id: 1,
        itemName: "Cuenta de Facturación"
      },
      {
        id: 2,
        itemName: "Nro. Teléfono"
      },
      {
        id: 3,
        itemName: "Ciclo"
      }
    ];

    this.selectedItem = [];
    let permisos = this.loadPermisos();
    this.activatedRoute.params.subscribe(params => {
      this.param = params['param'];
      this.type = params['type'];
      if (this.type === '1') {
        this.selectedItem = [this.dropDownList[0]];
      } else if (this.type === '2') {
        this.selectedItem = [this.dropDownList[1]];
      } else if (this.type === '3') {
        this.selectedItem = [this.dropDownList[2]];
      }
      if (this.param != 0 && this.param != undefined && this.param != "undefined") {
        if (permisos.filter(x => x == 'GB').length > 0) {
          if (this.type == '1' || this.type == '2' || this.type == '3') {
            this.getTransactionHistoryByFilter(this.param, this.type);
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
        this.getTransactionHistory();
      }
    });
  }


  initForm(): FormGroup {
    return this.fb.group({
      dateInit: ['', [Validators.required]],
      dateEnd: ['', [Validators.required]]
    })
  }

  loadPermisos() {
    let subs = sessionStorage.getItem('sub').split(",")
    let permisos = subs[2]
    return permisos.split('_')
  }


  /**
 * Este método nos ayuda a validar los input de:
 * Fecha de Inicio y Fecha Final
 * ¿Qué valida?
 * R/. Valida que se haya seleccionado un valor del input y
 * que la fecha inicial no sea superior a la final
 * 
 */
  validateDates(): void {
    let dates = this.getDates();

    //console.log(dates);

    if (
      compareDesc(dates.startDate, dates.endDate) < 0 &&
      this.dateForm.get("dateEnd").value != ""
    ) {
      this.utilService.showNotificationMsj("Fecha de Inicio Debe Ser Mayor o Igual a Fecha de Fin.!!");

      this.invalidDate = true;
    } else {

      this.invalidDate = false;
    }
  }

  /**
* Este método nos devuelve un objeto con las fechas actuales
* 
* @returns 
*/
  getDates() {
    return {
      startDate: new Date(`${this.dateForm.get("dateInit").value}T00:00`),
      endDate: new Date(`${this.dateForm.get("dateEnd").value}T00:00`),
    };


  }

  // Modal
  openModalDetail(data: TransactionHistoryModel, action: string) {

    // Se abre la modal
    const modalRef = this.modalService.open(TransactionHistoryDetailComponent, {
      size: "lg"
    });

    // Se pasa el input
    modalRef.componentInstance.data = data;
    modalRef.componentInstance.action = action;
    modalRef.componentInstance.messageEvent.subscribe((status: boolean) => {

      if (status) {
        this.getTransactionHistory();
      }

    });

  }
  // Rest

  /**
   * Método que consume un servicio para obtener los proveedores
   * 
   */
  getTransactionHistory() {
    this.transactionHistoryService.getTransactionHistory().subscribe((response) => {

      if (response.status === 200) {

        this.rows = [];
        this.rows2 = [];

        const providerResponse = response.body as TransactionHistoryResponse;

        providerResponse.data.map((dataOk) => {

          let dto: TransactionHistoryModel = dataOk;
          dto.status = dto.status === 0 ? 'Pendiente' :
            dto.status === -1 ? 'Error' :
              dto.status === 1 ? 'Se extrajo y se validó el descuento' :
                dto.status === 2 ? 'Freededuction Realizado' :
                  'Estado desconocido';
          dto.created = this.formatDate(dto?.created);
          this.rows.push(dto);

        });

        this.rows = [...this.rows];
        this.rows2 = [...this.rows];
        this.loadingIndicator = false;

        if (this.rows.length > 0) {
          this.utilService.showNotification(0, "Datos cargados");
        }

      }

    });
  }


  /**
  * Método encargado de filtrar los datos por fechas
  * 
  */
  getTransactionHistoryByDateRange() {
    this.loadingIndicator = true;
    this.transactionHistoryService.getTransactionHistoryByDateRange(this.dateForm.get("dateInit").value, this.dateForm.get("dateEnd").value).subscribe((response) => {
      if (response.status === 200) {
        this.rows = response.body.data;
        this.rows2 = [...this.rows];
        this.loadingIndicator = false;
        if (this.rows.length > 0) {
          this.utilService.setMsg('¡Datos cargados con éxito!', '', 2000, 'success', 'toast-top-right');
        } else {
          this.utilService.setMsg('¡No se encontraron datos con el rango de fecha seleccionado!', '', 2000, 'info', 'toast-top-right');
        }
      } else {
        this.loadingIndicator = false;
        this.utilService.setMsg('¡Error al cargar los datos!', '', 2000, 'error', 'toast-top-right');
      }
    }, (error) => {
      this.loadingIndicator = false;
      this.utilService.setMsg('¡Error al cargar los datos!', '', 2000, 'error', 'toast-top-right');
    });
  }

  /**
    * Método que consume un servicio para traer la información ya sea por el imsi
    * ó iccd
    * 
    * @param value 
    * @param type 
    * @returns 
    */
  getTransactionHistoryByFilter(value: any, type: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      this.transactionHistoryService.getTransactionHistoryByFilter(value, type).subscribe((response) => {

        if (response.status === 200) {
          this.rows = [];
          this.rows2 = [];

          const dataResponse = response.body as TransactionHistoryResponse;

          dataResponse.data.map((dataOk) => {

            let dto: TransactionHistoryModel = dataOk;
            dto.status = dto.status === 0 ? 'Pendiente' :
              dto.status === -1 ? 'Error' :
                dto.status === 1 ? 'Se extrajo y se validó el descuento' :
                  dto.status === 2 ? 'Freededuction Realizado' :
                    'Estado desconocido';
            dto.created = this.formatDate(dto?.created);
            this.rows.push(dto);

          });
          this.rows = [...this.rows];
          this.rows2 = [...this.rows];

          if (this.rows.length > 0) {
            this.utilService.showNotification(0, "Registros encontrados");
          }

          resolve(true);

        } else {
          this.rows = [];
          this.rows2 = [];
          this.rows = [...this.rows];
          this.rows2 = [...this.rows];
          resolve(false);
        }

      }, (error) => {

        if (error.status === 400) {
          this.utilService.showNotification(1, `No se encontró el registro ${this.param}`);
        } else {
          this.utilService.setMsg('Ocurrió un error al realizar la búsqueda.', '', 2000, 'danger', 'toast-top-right');
        }
        this.rows = [];
        this.rows2 = [];
        this.rows = [...this.rows];
        this.rows2 = [...this.rows];
        resolve(false);
      });

    });
  }


  // Methods

  generatePdf() {
    this.pdfService.generatePdfHistorical(this.rows);

  }

  async generateExcel() {

    // Es la infomración que aparece en la tabla
    const dataExcel = this.rows.map(objeto => {


      return { cycle: objeto.cycle, chargeCode: objeto.chargeCodeType, acctCode: objeto.acctCode, priIdentOfSubsc: objeto.priIdentOfSubsc, status: objeto.status, itemName: objeto.itemName, created: objeto.created };
    });



    const arrayDeArrays: any[][] = dataExcel.map(obj => Object.values(obj));

    this.dataExport = arrayDeArrays;

    const columnNames = ['Ciclo', 'Charge Code', 'Cuenta Fact.', 'Suscriptor Origen', 'Charge Code Status', 'Item Name', 'Fecha Creación'];


    const title: string = `CRF_${"Historico"}_` + this.datePipe.transform(new Date(), "dd-MM-yyyy");

    this.excelService.generateExcel(this.dataExport, title, columnNames);


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

  onItemSelect(ev) {
    this.infoCargado = false;
    this.mostrarTabla = false;
    this.type = this.selectedItem[0]['id'];
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
