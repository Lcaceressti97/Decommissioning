import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DetailInvoiceModalComponent } from 'src/app/components/detail-invoice-modal/detail-invoice-modal.component';
import { PrintInvoiceComponent } from 'src/app/components/print-invoice/print-invoice.component';
import { SendMailModalComponent } from 'src/app/components/send-mail-modal/send-mail-modal.component';
import { InvoiceEquipmentAccesoriesEntity } from 'src/app/entities/InvoiceEquipmentAccesoriesEntity';
import { InvoiceEquipmentAccesories } from 'src/app/models/InvoiceEquipmentAccesories';
import { ConsultationInvoiceService } from 'src/app/services/consultation-invoice.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';
import { Buttons } from 'src/types';
import { compareDesc, format, subDays } from 'date-fns';
import { InvoiceService } from 'src/app/services/invoice.service';
import { Billing, ConfigParameter, StatusNames } from 'src/app/models/billing';
import { BillingResponse, ConfigParameterResponse, InvoicePagesResponse } from 'src/app/entities/invoice.entity';
import { LoadOceCorporativeComponent } from 'src/app/components/load-oce-corporative/load-oce-corporative.component';
import { LoadOceComponent } from 'src/app/components/load-oce/load-oce.component';
import Swal from "sweetalert2/dist/sweetalert2.js";
import { PrinterService } from 'src/app/services/printer.service';
import ConectorPluginV3 from 'src/app/utils/plugin-print';
import { CorreoModalComponent } from 'src/app/components/correo-modal/correo-modal.component';

@Component({
  selector: 'app-consultation-invoice',
  templateUrl: './consultation-invoice.component.html',
  styleUrls: ['./consultation-invoice.component.css']
})
export class ConsultationInvoiceComponent implements OnInit {

  // Props
  dateForm!: FormGroup;
  invalidDate: boolean = false;

  // Table
  invoiceEquipmentAccesories: InvoiceEquipmentAccesories[] = [];
  rowsInvoiceEquipmentAccesories: InvoiceEquipmentAccesories[] = [];

  // Actual
  rows: Billing[] = [];
  rows2: Billing[] = [];
  page = 0;
  size = 2000;

  // Rows para 

  rowsError: Billing[] = [];
  rowsPending: Billing[] = [];
  rowsAuthorized: Billing[] = [];
  rowsIssued: Billing[] = [];
  rowsFiscal: Billing[] = [];
  rowsCancelWithoutIssued: Billing[] = [];
  rowsCancelWithIssued: Billing[] = [];

  // Propiedades de paginación
  currentPage = 0;
  pageSize = 20;
  totalElements = 0;
  totalPages = 0;

  // Parameters
  // Invoice Status
  parametersInvoiceStatus: ConfigParameter[] = [];
  statusNames: StatusNames = {};
  parameters: ConfigParameter[] = [];

  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  typeValue: string = "";
  messages = messages;
  loading: boolean;
  param: any
  mostrarTabla = false;
  infoCargado = false;
  isFilteringByDate: boolean = false;


  validateShowStatus: boolean = true; // Variable que controla el comportamiento del select mostrar facturas
  exemption: number;
  type: string = '0';
  public dropdownSettings = {};
  public dropdownList = [
    {
      id: 1,
      itemName: "Tipo de Factura"
    },
    {
      id: 2,
      itemName: "Número de Prefactura"
    },
    {
      id: 3,
      itemName: "Cuenta de Facturación"
    },
    {
      id: 4,
      itemName: "Teléfono"
    }
  ];
  public selectedItems = [];
  private sub: any;
  private isInitialLoad = true;

  selectStatus: number = 0;
  public dropdownSettingsStatus = {};
  public dropdownListStatus = [
    {
      id: -1,
      itemName: "Error"
    },
    {
      id: 0,
      itemName: "Pendientes"
    },
    {
      id: 1,
      itemName: "Autorizadas"
    },
    {
      id: 2,
      itemName: "Emitidas"
    },
    {
      id: 5,
      itemName: "Anuladas Sin Emitir"
    },
    {
      id: 6,
      itemName: "Anuladas Con Emisión ó Número Fiscal"
    }
  ];
  public selectedItemsStatus = [{
    id: 0,
    itemName: "Pendientes"
  }];

  //* STYLES
  labelClasses = "col-form-label text-dark col-sm-2 col-md-2 col-lg-2";
  inputClasses = "my-auto col-sm-2 col-md-4 col-lg-2";
  inputClassesTwo = "my-auto col-sm-12 col-md-2 col-lg-2";

  impresoras: any[] = [];

  constructor(private consultationInvoiceService: ConsultationInvoiceService,
    private invoiceService: InvoiceService,
    private modalService: NgbModal,
    public utilService: UtilService,
    private activatedRoute: ActivatedRoute,
    private router: Router, private readonly fb: FormBuilder, private printerService: PrinterService) {


    this.activatedRoute.queryParams.subscribe(params => {
      this.sub = JSON.stringify(params['sub']);

      if (this.sub)
        sessionStorage.setItem('sub', this.sub)
    });
  }

  async ngOnInit() {


    this.dateForm = this.initForm();
    await this.configparametersById(1000);
    //this.getAllParameters();
    //console.log(this.parametersInvoiceStatus);


    this.dropdownSettings = {
      singleSelection: true,
      text: "Seleccione un valor",
      selectAllText: "Todos",
      unSelectAllText: "Ninguno",
      enableSearchFilter: true,
      classes: ""
    };

    this.dropdownSettingsStatus = {
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
        itemName: "Tipo de Factura"
      },
      {
        id: 2,
        itemName: "Número de Prefactura"
      },
      {
        id: 3,
        itemName: "Cuenta de Facturación"
      },
      {
        id: 4,
        itemName: "Teléfono"
      }
    ];

    this.selectedItems = [];

    let permisos = this.loadPermisos();


    this.activatedRoute.params.subscribe(async (params) => {
      this.param = params['param'];
      this.type = params['type'];
      if (this.type === '1') {
        this.selectedItems = [this.dropdownList[0]];
      } else if (this.type === '2') {
        this.selectedItems = [this.dropdownList[1]];
      } else if (this.type === '3') {
        this.selectedItems = [this.dropdownList[2]];
      } else if (this.type === '4') {
        this.selectedItems = [this.dropdownList[3]];
      }


      /**
       * Condición que valida el si se agrego algo al parámetro de búsqueda
       * 
       */
      if (this.param != 0 && this.param != undefined && this.param != "undefined") {

        if (permisos.filter(x => x == 'GB').length > 0) {

          if (this.type == '1' || this.type == '2' || this.type == '3' || this.type == '4') {

            // toDO
            this.getBy(this.type, this.param);

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

          Swal.fire({
            title: 'Cargando Facturas...',
            allowOutsideClick: false,
            onBeforeOpen: () => {
              Swal.showLoading();
            }
          });
          this.getBilling(true, this.selectStatus);
          Swal.close();
        }
      }
    })


  }

  loadPermisos() {
    let subs = sessionStorage.getItem('sub').split(",")
    let permisos = subs[2]
    return permisos.split('_')
  }


  async getAllParameters() {
  }

  initForm(): FormGroup {
    return this.fb.group({
      dateInit: ['', [Validators.required]],
      dateEnd: ['', [Validators.required]]
    })
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

  async refreshTable() {

    this.validateShowStatus = true;
    // Vaciamos las 
    this.rows = [];
    this.rows2 = [];
    this.rows = [...this.rows];
    this.rows2 = [...this.rows];
    this.dateForm.get('dateInit').setValue('');
    this.dateForm.get('dateEnd').setValue('');
    Swal.fire({
      title: 'Cargando Facturas...',
      allowOutsideClick: false,
      onBeforeOpen: () => {
        Swal.showLoading();
      }
    });

    await this.getBilling(true, this.selectStatus);

    Swal.close();
  }

  /**
   * Modal  para visualizar los datos de la factura
   * 
   * @param button 
   * @param row 
   */
  openModal(button: Buttons, row: Billing = null) {

    const modalRef = this.modalService.open(DetailInvoiceModalComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.billing = row;
    modalRef.componentInstance.button = button;
    modalRef.componentInstance.messageEvent.subscribe((billing: Billing) => {
    });
  }

  /**
   * Método que abre la modal del diseño de impresión de la factura
   * 
   * @param row 
   */
  openModalPrint(row: Billing = null) {

    const modalRef = this.modalService.open(PrintInvoiceComponent, { size: 'md', backdrop: 'static' });
    modalRef.componentInstance.billing = row;
    modalRef.componentInstance.messageEvent.subscribe((billing: any) => {
    });
  }

  /**
   * Método que abre la modal para enviar el correo
   * 
   * @param row 
   */
  openModalEmail(row: InvoiceEquipmentAccesories = null) {

    const modalRef = this.modalService.open(CorreoModalComponent, { size: 'md', backdrop: 'static' });
    modalRef.componentInstance.billing = row;

  }

  /**
   * Método que abre el modal para el proceso de exoneración
   * 
   */
  openModalOce(data: Billing) {

    const modalRef = this.modalService.open(LoadOceComponent, {
      size: 'lg', backdrop: 'static'
    });

    modalRef.componentInstance.billing = data;
    modalRef.componentInstance.messageEvent.subscribe(async (refresh: boolean) => {

      if (refresh) {
        //await this.getAllInvoicePagination(this.page, this.size,false);
        //this.getAllInvoicePagination(this.page, this.size,false);

        setTimeout(() => {
          window.location.reload();
        }, 3500);
      }

    });


  }


  //* UTILS
  searchInvoice() {
    this.rows = this.rows2.filter((invoiceEquipmentAccesories) => {
      return JSON.stringify(invoiceEquipmentAccesories)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  getTotalText() {
    return this.rowsInvoiceEquipmentAccesories.length == 1 ? "Registro" : "Registros";
  }



  /**
   * Método según el tipo de búsqueda por la barra de busqueda de Nexus
   * 
   */
  onItemSelect(ev) {
    //this.printerService.generatePdf("Ejemplo");
    this.infoCargado = false;
    this.mostrarTabla = false;
    this.type = this.selectedItems[0]['id'];
    //console.log(this.type)
    this.router.navigateByUrl('/mov' + '/0/' + this.type)
      .finally(() => { this.reFresh() })
  }

  OnItemDeSelect(ev) {
    this.router.navigateByUrl('mov' + '/' + this.param + '/0')
      .finally(() => { this.reFresh() })
  }

  /**
   * Método que nos ayuda a controlar el cambios de los registros que
   * se visualiza en la tabla según el estado
   * 
   * @param event 
   */
  async onItemSelectStatus(event) {
    this.rows = [];
    this.rows = [...this.rows];
    this.rows2 = [...this.rows];
    this.selectStatus = event.id;

    if (this.validateShowStatus) {
      Swal.fire({
        title: 'Cargando Facturas...',
        allowOutsideClick: false,
        onBeforeOpen: () => {
          Swal.showLoading();
        }
      });

      await this.getBilling(true, this.selectStatus);

      Swal.close();
    } else {

      if (this.selectStatus == -1) {
        this.rows = this.rowsError;
        this.rows = [...this.rows];
        this.rows2 = [...this.rows];
      }

      if (this.selectStatus == 0) {
        this.rows = this.rowsPending;
        this.rows = [...this.rows];
        this.rows2 = [...this.rows];
      }
      if (this.selectStatus == 1) {
        this.rows = this.rowsAuthorized;
        this.rows = [...this.rows];
        this.rows2 = [...this.rows];
      }
      if (this.selectStatus == 2) {
        this.rows = this.rowsIssued;
        this.rows = [...this.rows];
        this.rows2 = [...this.rows];
      }
      if (this.selectStatus == 5) {
        this.rows = this.rowsCancelWithoutIssued;
        this.rows = [...this.rows];
        this.rows2 = [...this.rows];
      }
      if (this.selectStatus == 6) {
        this.rows = this.rowsCancelWithIssued;
        this.rows = [...this.rows];
        this.rows2 = [...this.rows];
      }

    }



  }

  onDeSelectStatus() {
    this.rows = [];
    this.rows = [...this.rows];
    this.rows2 = [...this.rows];
  }

  public reFresh() {
    let url = this.type
    parent.postMessage(url, "*");
  }

  /**
    * Nos ayuda a filtrar, es decir: nos ayuda a buscar
    * valores que están en la tabla
    * 
    */
  search(): void {
    this.rows = this.rows2.filter((row) => {
      return JSON.stringify(row)
        .toLowerCase()
        .includes(this.searchedValue.toString()
          .toLowerCase());
    });
  }



  /**
   * Método encargado de filtrar los datos por fechas
   * 
   */
  async onSubmit() {

    Swal.fire({
      title: 'Filtrando por fechas...',
      allowOutsideClick: false,
      onBeforeOpen: () => {
        Swal.showLoading();
      }
    });

    this.validateShowStatus = false;
    this.isFilteringByDate = true;

    /*
    const dateStart = format(new Date(`${this.dateForm.get("dateInit").value}T00:00`), "dd/MM/yyyy");
    const dateFinal = format(new Date(`${this.dateForm.get("dateEnd").value}T00:00`), "dd/MM/yyyy");
    */

    const validateInvoiceDate = await this.getBillisByDateRange(this.dateForm.get("dateInit").value, this.dateForm.get("dateEnd").value);


    // Validamos si hay datos o no
    if (validateInvoiceDate) {
      this.validateShowStatus = false;
      this.utilService.showNotification(0, "Datos cargados");
    } else {
      this.utilService.showNotification(1, "No se encontraron datos con el rango de fecha seleccionado");
    }

    Swal.close();

  }


  /**
   * Método encargado de obtener los valores según el filtro que el usuario selecciono
   * 
   */
  getBy(type, value) {
    this.invoiceService.getBillisByFilter(this.currentPage, this.pageSize, type, value).subscribe((res) => {
      if (res.status === 200) {
        this.loadingIndicator = false;
        let billingResponse = res.body as InvoicePagesResponse;

        if (billingResponse.data.numberOfElements > 0) {
          this.rows = [];
          this.rows2 = [];
          this.selectedItemsStatus = [];

          // Actualizar la información de paginación
          this.totalElements = billingResponse.data.totalElements;
          this.totalPages = billingResponse.data.totalPages;
          this.currentPage = billingResponse.data.number;

          billingResponse.data.content.map((resourceMap, configErro) => {
            let dto: Billing = resourceMap;
            dto.buttonExo = false;

            dto.statusCode = dto.status;
            dto.status = dto.status === this.parametersInvoiceStatus[0].stateCode ? this.parametersInvoiceStatus[0].parameterValue : dto.status === this.parametersInvoiceStatus[1].stateCode ? this.parametersInvoiceStatus[1].parameterValue : dto.status === this.parametersInvoiceStatus[2].stateCode ? this.parametersInvoiceStatus[2].parameterValue : dto.status === this.parametersInvoiceStatus[3].stateCode ? this.parametersInvoiceStatus[3].parameterValue : dto.status === this.parametersInvoiceStatus[4].stateCode ? this.parametersInvoiceStatus[4].parameterValue : dto.status === this.parametersInvoiceStatus[5].stateCode ? this.parametersInvoiceStatus[5].parameterValue : this.parametersInvoiceStatus[7].parameterValue;

            if (dto.statusCode == 0 && (dto.exonerationStatus == 0 || dto.exonerationStatus == null)) {
              dto.buttonExo = true;
            }

            if ((dto.invoiceType === "SHO" || dto.invoiceType === "SHP") && dto.statusCode == 0) {
              dto.buttonExo = false;
            }


            if (dto.statusCode === -1) {
              this.selectedItemsStatus.push(
                {
                  id: -1,
                  itemName: "Error"
                }
              );
              this.selectStatus = -1;
            }

            if (dto.statusCode === 0) {
              this.selectedItemsStatus.push(
                {
                  id: 0,
                  itemName: "Pendientes"
                }
              );
              this.selectStatus = -0;
            }

            if (dto.statusCode === 1) {
              this.selectedItemsStatus.push(
                {
                  id: 1,
                  itemName: "Autorizadas"
                }
              );
              this.selectStatus = 1;
            }

            if (dto.statusCode === 2) {
              this.selectedItemsStatus.push(
                {
                  id: 2,
                  itemName: "Emitidas"
                }
              );
              this.selectStatus = 2;
            }

            if (dto.statusCode === 5) {
              this.selectedItemsStatus.push(
                {
                  id: 5,
                  itemName: "Anuladas Sin Emitir"
                }
              );
              this.selectStatus = 5;
            }

            if (dto.statusCode === 6) {
              this.selectedItemsStatus.push(
                {
                  id: 6,
                  itemName: "Anuladas Con Emisión ó Número Fiscal"
                }
              );
              this.selectStatus = 6;
            }


            this.rows.push(dto);
          });

          this.loadingIndicator = false;


          this.rows = [...this.rows];
          this.rows2 = [...this.rows];

          this.utilService.showNotification(0, "Datos encontrados");

        } else {
          this.utilService.showNotification(1, `No se encontraron datos con el valor ${value}`);
        }

      } else {
        this.utilService.showNotificationError(res.status);
      }
    }, (error) => {

    });
  }


  // Método para manejar el cambio de página
  async onPageChange(event: any) {
    this.currentPage = event.offset;

    // Verifica qué método de paginación se debe usar
    if (this.isFilteringByDate) {
      // Si estás filtrando por rango de fechas
      await this.getBillisByDateRange(this.dateForm.get("dateInit").value, this.dateForm.get("dateEnd").value);
    } else if (this.type) {
      // Si estás usando otro tipo de filtro
      await this.getBy(this.type, this.param);
    } else {
      // Por defecto, llama a getBilling
      await this.getBilling(true, this.selectStatus);
    }
  }
  /**
   * Método en uso
   * Consume un servicio que consume trae las facturas del día
   * 
   * @param page 
   * @param size 
   * @param showAler 
   * @returns 
   */
  getBilling(showAler?: boolean, status?: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.getBilling(this.currentPage, this.pageSize, status).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {

          // Vaciamos las 
          this.rows = [];
          this.rows2 = [];


          // Mapeamos el body del response
          let billingResponse = response.body as InvoicePagesResponse;

          // Actualizar la información de paginación
          this.totalElements = billingResponse.data.totalElements;
          this.totalPages = billingResponse.data.totalPages;
          this.currentPage = billingResponse.data.number;
          // Agregamos los valores a los rows


          // Agregamos los valores a los rows
          this.rows = billingResponse.data.content.map((resourceMap, configError) => {

            let dto: Billing = resourceMap;
            dto.buttonExo = false;
            dto.statusCode = dto.status;
            //dto.status = dto.status === this.parametersInvoiceStatus[0].stateCode ? this.parametersInvoiceStatus[0].parameterValue : dto.status === this.parametersInvoiceStatus[1].stateCode ? this.parametersInvoiceStatus[1].parameterValue : dto.status === this.parametersInvoiceStatus[2].stateCode ? this.parametersInvoiceStatus[2].parameterValue : dto.status === this.parametersInvoiceStatus[3].stateCode ? this.parametersInvoiceStatus[3].parameterValue : dto.status === this.parametersInvoiceStatus[4].stateCode ? this.parametersInvoiceStatus[4].parameterValue : dto.status === this.parametersInvoiceStatus[5].stateCode ? this.parametersInvoiceStatus[5].parameterValue : this.parametersInvoiceStatus[7].parameterValue;

            if (dto.statusCode == 0 && (dto.exonerationStatus == 0 || dto.exonerationStatus == null)) {
              dto.buttonExo = true;
            }

            if ((dto.invoiceType === "SHO" || dto.invoiceType === "SHP") && dto.statusCode == 0) {
              dto.buttonExo = false;
            }


            dto.status = this.statusNames[dto.status] || "Sin estado";
            this.rows.push(dto);
            return dto;
          });


          this.loadingIndicator = false;
          //this.rows = this.rowsPending;
          this.rows = [...this.rows];
          this.rows2 = [...this.rows];


          if (this.rows.length > 0 && showAler) {
            this.utilService.showNotification(0, "Datos cargados");
          } else {
            this.utilService.showNotification(1, "No se encontraron facturas");
          }

          resolve(true);
        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })

    })
  }

  /**
     * Método encargado de obtener los registros de las facturas por un
     * rango de fecha 
     * 
     */
  getBillisByDateRange(initDate: any, ednDate: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.getBillisByDateRange(this.currentPage, this.pageSize, initDate, ednDate).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {

          // Vaciamos las 
          this.rows = [];
          this.rows2 = [];
          this.selectedItemsStatus = [];

          // Mapeamos el body del response
          let billingResponse = response.body as InvoicePagesResponse;

          // Actualizar la información de paginación
          this.totalElements = billingResponse.data.totalElements;
          this.totalPages = billingResponse.data.totalPages;
          this.currentPage = billingResponse.data.number;

          // Agregamos los valores a los rows

          if (billingResponse.data.numberOfElements > 0) {
            this.rows = billingResponse.data.content.map((resourceMap, configError) => {

              let dto: Billing = resourceMap;
              dto.buttonExo = false;
              dto.statusCode = dto.status;
              //dto.status = dto.status === this.parametersInvoiceStatus[0].stateCode ? this.parametersInvoiceStatus[0].parameterValue : dto.status === this.parametersInvoiceStatus[1].stateCode ? this.parametersInvoiceStatus[1].parameterValue : dto.status === this.parametersInvoiceStatus[2].stateCode ? this.parametersInvoiceStatus[2].parameterValue : dto.status === this.parametersInvoiceStatus[3].stateCode ? this.parametersInvoiceStatus[3].parameterValue : dto.status === this.parametersInvoiceStatus[4].stateCode ? this.parametersInvoiceStatus[4].parameterValue : dto.status === this.parametersInvoiceStatus[5].stateCode ? this.parametersInvoiceStatus[5].parameterValue : this.parametersInvoiceStatus[7].parameterValue;

              dto.status = this.statusNames[dto.status] || "Sin estado";

              if (dto.statusCode == 0 && (dto.exonerationStatus == 0 || dto.exonerationStatus == null)) {
                dto.buttonExo = true;
              }

              if ((dto.invoiceType === "SHO" || dto.invoiceType === "SHP") && dto.statusCode == 0) {
                dto.buttonExo = false;
              }

              if (dto.statusCode === -1) {
                this.rowsError.push(dto);
              }

              if (dto.statusCode === 0) {
                this.rowsPending.push(dto);
              }

              if (dto.statusCode === 1) {
                this.rowsAuthorized.push(dto);
              }

              if (dto.statusCode === 2) {
                this.rowsIssued.push(dto);
              }

              if (dto.statusCode === 5) {
                this.rowsCancelWithoutIssued.push(dto);
              }

              if (dto.statusCode === 6) {
                this.rowsCancelWithIssued.push(dto);
              }

              //this.rows.push(dto);
              return dto;
            });

            if (this.rowsError.length > 0) {
              this.rows = this.rowsError;
              this.selectedItemsStatus.push(
                {
                  id: -1,
                  itemName: "Error"
                }
              );
              this.selectStatus = -1;
            } else {

              if (this.rowsPending.length > 0) {
                this.rows = this.rowsPending;
                this.selectedItemsStatus.push(
                  {
                    id: 0,
                    itemName: "Pendientes"
                  }
                );
                this.selectStatus = 0;
              } else {

                if (this.rowsAuthorized.length > 0) {
                  this.rows = this.rowsAuthorized;
                  this.selectedItemsStatus.push(
                    {
                      id: 1,
                      itemName: "Autorizadas"
                    }
                  );
                  this.selectStatus = 1;
                } else {

                  if (this.rowsIssued.length > 0) {
                    this.rows = this.rowsIssued;
                    this.selectedItemsStatus.push(
                      {
                        id: 2,
                        itemName: "Emitidas"
                      }
                    );
                    this.selectStatus = 2;
                  } else {
                    if (this.rowsCancelWithIssued.length > 0) {
                      this.rows = this.rowsCancelWithIssued;
                      this.selectedItemsStatus.push(
                        {
                          id: 5,
                          itemName: "Anuladas Sin Emitir"
                        }
                      );
                      this.selectStatus = 5;
                    } else {
                      if (this.rowsCancelWithoutIssued.length > 0) {
                        this.rows = this.rowsCancelWithIssued;
                        this.selectedItemsStatus.push(
                          {
                            id: 6,
                            itemName: "Anuladas Con Emitir"
                          }
                        );
                        this.selectStatus = 6;
                      } else {
                        if (this.rowsError.length > 0) {

                        } else
                          this.rows = [];
                        this.selectedItemsStatus.push(
                          {
                            id: 0,
                            itemName: "Pendientes"
                          }
                        );
                        this.selectStatus = 0;
                      }
                    }
                  }

                }
              }

            }

            this.loadingIndicator = false;
            this.rows = billingResponse.data.content;
            this.rows2 = [...this.rows];

            resolve(true);
          } else {

            resolve(false);
          }

        }

      }, (error) => {
        resolve(false);
      })
    })


  }


  /**
* Método encargado de obtener los parámetros de la pantalla
* 
*/
  configparametersById(id: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.configparametersById(id).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {

          // Vaciamos las 
          if (id === 1000) {
            this.parametersInvoiceStatus = [];

          } else {
            this.parameters = [];
          }

          // Mapeamos el body del response
          let configParameterResponse = response.body as ConfigParameterResponse;

          // Agregamos los valores a los rows

          configParameterResponse.data.map((resourceMap, configError) => {

            let dto: ConfigParameter = resourceMap;

            if (id === 1000) {
              this.parametersInvoiceStatus.push(dto);
              if (dto.parameterType == "INVOICE_STATUS") {
                this.statusNames[dto.stateCode] = dto.parameterValue;

              }

              if (dto.parameterType == "EXEMPTION_FROM_INVOICES") {
                this.exemption = Number(dto.parameterValue);
                console.log(this.exemption);
              }
            } else {
              this.parameters.push(dto);
            }

          });

          if (id === 1000) {
            this.parametersInvoiceStatus = [...this.parametersInvoiceStatus];
          } else {
            this.parameters = [...this.parameters];
          }
          //console.log(this.parametersInvoiceStatus);

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })
    });


  }



}
