import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { BillingPagesResponse, BillingResponse, BrancheOffices, BrancheOfficesResponse, ConfigParameterResponse, InvoiceEquipmentAccesoriesEntity, IssuingUser, IssuingUserResponse, WareHouseModel, WareHousesResponse } from 'src/app/entities/InvoiceEquipmentAccesoriesEntity';
import { Billing, ConfigParameter, FilterModel, InvoiceEquipmentAccesories, ItemSelect } from 'src/app/models/InvoiceEquipmentAccesories';
import { BillingReportService } from 'src/app/services/billing-report.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';
import { ExcelService } from 'src/app/services/excel.service';
import { getDetailBillingReport } from 'src/app/utils/BillingReportPdf';
import { PdfService } from 'src/app/services/pdf.service';
import { DatePipe } from '@angular/common';
import { InvoiceService } from 'src/app/services/invoice.service';
import { InvoiceDetailComponent } from 'src/app/components/invoice-detail/invoice-detail.component';
import Swal from 'sweetalert2';
import { compareDesc, format, subDays } from 'date-fns';

@Component({
  selector: 'app-billing-report',
  templateUrl: './billing-report.component.html',
  styleUrls: ['./billing-report.component.css']
})
export class BillingReportComponent implements OnInit {

  // Filter - No está en uso
  invoiceStatus: number = null;
  idBranchOffices: any = null;
  wareHouseCode: any = null;
  emitter: any = null;

  // Filter en uso
  statusArray: number[] = [];
  agenciesArray: string[] = []; // Sucursales
  wareHouseArray: string[] = []; // Sucursales
  territories: string[] = []; // Territorios

  rows: Billing[] = [];
  rows2: Billing[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 20;
  searchedValue: string = "";
  typeValue: string = "";
  messages = messages;
  loading: boolean;
  param: any
  mostrarTabla = false;
  infoCargado = false;
  dropdownSettingsData;
  dropdownListData;
  selectedItems;
  dataExport;

  // Parameters
  parametersInvoiceStatus: ConfigParameter[] = [];

  // Form
  dateForm!: FormGroup;
  invalidDate: boolean = false;

  // Propiedades de paginación
  currentPage = 0;
  pageSize = 20;
  totalElements = 0;
  totalPages = 0;

  //* STYLES
  labelClasses = "col-form-label text-dark col-sm-2 col-md-2 col-lg-2 font-size-large";
  inputClasses = "my-auto col-sm-12 col-md-12 col-lg-12";

  // Select Angular
  // Status
  public dropsownSettingsStatus = {};
  public dropDownListStatus: ItemSelect[] = [
    { id: -1, itemName: "Error" }, { id: 0, itemName: "Pendientes" }, { id: 1, itemName: "Autorizadas" }, { id: 2, itemName: "Emitidas" }, { id: 5, itemName: "Anuladas Sin Emitir" }, { id: 6, itemName: "Anuladas Con Número Fiscal" },
  ];

  public selectedItemStatus = [];

  // Bodega
  public dropsownSettingsWareHouse = {};
  public dropDownListWareHouse: ItemSelect[] = [];

  public selectedItemWareHouse = [];

  // Sucursal
  public dropsownSettingsBrancheOffices = {};

  public dropDownListBrancheOffices: ItemSelect[] = [];

  public selectedItemBrancheOffice = [];

  // Territorios
  public dropsownSettingsTerritories = {};
  public dropDownListTerritories: ItemSelect[] = [
    { id: -1, itemName: "T1" }, { id: -2, itemName: "T2" }, { id: -3, itemName: "T3" }
  ];

  public selectedItemTerritory = [];



  constructor(private billingReportService: BillingReportService,
    public utilService: UtilService,
    private excelService: ExcelService,
    private pdfService: PdfService,
    private datePipe: DatePipe, private invoiceService: InvoiceService,
    private modalService: NgbModal, private readonly fb: FormBuilder) { }


  filterForm = new FormGroup({
    invoiceStatus: new FormControl(-2, []),
    warehouse: new FormControl("", []),
    agency: new FormControl("", []),
    createdBy: new FormControl("", [])

  });

  ngOnInit(): void {

    this.dateForm = this.initForm();

    this.setSettingsSelect();

    this.buildDataSelect();

    this.getAllParameters();
  }


  // AngularSelect
  setSettingsSelect() {
    this.dropdownListData = [
      { id: 1, itemName: "Excel" },
      { id: 2, itemName: "Pdf" },
    ];

    this.dropdownSettingsData = {
      singleSelection: true,
      text: "Exportar a:",
      enableSearchFilter: true
    };

    this.dropsownSettingsStatus = {
      singleSelection: false,
      text: "Seleccione un valor",
      selectAllText: "Seleccionar todos",
      unSelectAllText: "Deseleccionar todo",
      enableSearchFilter: true,
      classes: ""
    };

    this.dropsownSettingsWareHouse = {
      singleSelection: false,
      text: "Seleccione un valor",
      selectAllText: "Seleccionar todos",
      unSelectAllText: "Deseleccionar todo",
      enableSearchFilter: true,
      classes: ""
    };

    this.dropsownSettingsBrancheOffices = {
      singleSelection: false,
      text: "Seleccione un valor",
      selectAllText: "Seleccionar todos",
      unSelectAllText: "Deseleccionar todo",
      enableSearchFilter: true,
      classes: ""
    };

    this.dropsownSettingsTerritories = {
      singleSelection: false,
      text: "Seleccione un valor",
      selectAllText: "Seleccionar todos",
      unSelectAllText: "Deseleccionar todo",
      enableSearchFilter: true,
      classes: ""
    };


  }


  /**
   * Método encargado de consumir un servicio para obtener la información de cada uno de los select
   * 
   */
  async buildDataSelect() {
    await this.getAllBrancheOffices(this.utilService.getSystemUser());
    await this.getWareHouses(this.utilService.getSystemUser());
    //await this.getIssuingUsers();
  }

  // Sucursal

  onSelectAllBrancheOffice(items: any) {

    //console.log(items);
    this.agenciesArray = [];
    this.agenciesArray = [...this.agenciesArray];
    items.forEach(item => {

      this.agenciesArray.push(item.itemName);
    });
    //console.log(this.agenciesArray);
  }

  onDeSelectAllBrancheOffice(items: any) {
    //console.log(items);
    this.agenciesArray = [];
    this.agenciesArray = [...this.agenciesArray];
  }

  onItemSelectBrancheOffice(event: any) {

    //this.idBranchOffices = event.itemName;
    this.agenciesArray.push(event.itemName);
    //console.log(this.agenciesArray);
  }

  onItemDeSelectBrancheOffice(event: any) {
    //this.idBranchOffices = null;
    const index = this.agenciesArray.findIndex(item => item === event.itemName);

    // Verificar si se encontró el elemento
    if (index !== -1) {

      // También puedes eliminar el elemento, etc.
      this.agenciesArray.splice(index, 1);
    } else {
      //console.log(`No se encontró ningún elemento con el id ${event.itemName}`);
    }
    //console.log(this.agenciesArray);
  }

  // Bodega

  onSelectAllWareHouse(items: any) {
    //console.log(items);
    this.wareHouseArray = [];
    this.wareHouseArray = [...this.wareHouseArray];
    items.forEach(item => {

      this.wareHouseArray.push(item.id.toString());
    });
    //console.log(this.wareHouseArray);
  }

  onDeSelectAllWareHouse(items: any) {
    //console.log(items);
    this.wareHouseArray = [];
    this.wareHouseArray = [...this.wareHouseArray];
  }

  onItemSelectWareHouse(event: any) {
    //this.wareHouseCode = event.id;
    this.wareHouseArray.push(event.id);
    //console.log(this.wareHouseArray);
  }

  onItemDeSelectWareHouse(event: any) {
    //this.wareHouseCode = null;
    const index = this.wareHouseArray.findIndex(item => item === event.id);

    // Verificar si se encontró el elemento
    if (index !== -1) {

      // También puedes eliminar el elemento, etc.
      this.wareHouseArray.splice(index, 1);
    } else {
      //console.log(`No se encontró ningún elemento con el id ${event.itemName}`);
    }
    //console.log(this.wareHouseArray);
  }

  // Estado

  onSelectAllStatus(items: any) {
    //console.log(items);
    this.statusArray = [];
    this.statusArray = [...this.statusArray];
    items.forEach(item => {

      this.statusArray.push(item.id);
    });
    //console.log(this.statusArray);
  }

  onDeSelectAllStatus(items: any) {
    this.statusArray = [];
    this.statusArray = [...this.statusArray];
    //console.log(this.statusArray);
  }

  onItemSelectStatus(event: any) {

    //this.invoiceStatus = event.id;

    this.statusArray.push(event.id);
    //console.log(this.statusArray);
  }

  onItemDeSelectStatus(event: any) {
    //this.invoiceStatus = null;
    const index = this.statusArray.findIndex(item => item === event.id);

    // Verificar si se encontró el elemento
    if (index !== -1) {

      // También puedes eliminar el elemento, etc.
      this.statusArray.splice(index, 1);
    } else {
      //console.log(`No se encontró ningún elemento con el id ${event.itemName}`);
    }
    //console.log(this.statusArray);
  }


  // Territorio

  onSelectAllTerritories(items: any) {
    //console.log(items);
    this.territories = [];
    this.territories = [...this.territories];
    items.forEach(item => {

      this.territories.push(item.itemName);
    });
    //console.log(this.territories);
  }

  onDeSelectAllTerritories(items: any) {
    this.territories = [];
    this.territories = [...this.territories];
    //console.log(this.territories);
  }

  onItemSelectTerritories(event: any) {

    //this.invoiceStatus = event.id;

    this.territories.push(event.itemName);
    //console.log(this.territories);
  }

  onItemDeSelectTerritories(event: any) {
    //this.invoiceStatus = null;
    const index = this.territories.findIndex(item => item === event.itemName);

    // Verificar si se encontró el elemento
    if (index !== -1) {

      // También puedes eliminar el elemento, etc.
      this.territories.splice(index, 1);
    } else {
      //console.log(`No se encontró ningún elemento con el id ${event.itemName}`);
    }
    //console.log(this.territories);
  }



  // Emisor - No están en uso
  onItemSelectEmitter(event: any) {

    this.emitter = event.itemName;
  }
  onItemDeSelectEmitter(event: any) {
    this.emitter = null;
  }


  // PARAMETERS
  async getAllParameters() {

    await this.configparametersById(1000);

  }

  // Métodos para fechas

  initForm(): FormGroup {
    return this.fb.group({
      dateInit: ['', []],
      dateEnd: ['', []],
      territory: ['', []],
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

    if (compareDesc(dates.startDate, dates.endDate) < 0 && this.dateForm.get("dateEnd").value != "") {
      //this.utilService.showNotificationMsj("Fecha de Inicio no debe de ser mayor a la Fecha Fin y la Fecha Fin no debe de ser menor a la Fecha de Inicio.!!");

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

  private openLoading(title = 'Cargando Facturas...') {
    return Swal.fire({
      title,
      allowOutsideClick: false,
      onBeforeOpen: () => {
        Swal.showLoading();
      }
    });
  }

  private closeLoading() { Swal.close(); }


  /**
   * Método encargado de validar y realizar la filtración de datos
   * 
   */
  async getMixFilter() {

    this.openLoading('Filtrando Facturas...');


    // Variable payload
    let filterModel: FilterModel = {};
    filterModel.seller = this.utilService.getSystemUser();
    filterModel.warehouses = this.wareHouseArray;
    filterModel.status = this.statusArray;
    filterModel.agencies = this.agenciesArray;
    filterModel.territory = this.territories;

    /*
    onsole.log(this.dateForm.get("dateInit").value);
    console.log(this.dateForm.get("dateEnd").value);
    console.log(this.agenciesArray.length);
    console.log(this.wareHouseArray.length);
    console.log(this.statusArray.length);
    */

    /**
     * Validación si selecciono algún filtro
     * 
     */
    if (this.dateForm.get("dateInit").value !== "" || this.dateForm.get("dateEnd").value !== "" || this.agenciesArray.length > 0 || this.wareHouseArray.length > 0 || this.statusArray.length > 0 || this.territories.length > 0) {
      let dates = this.getDates();

      /**
       * Si selecciono las fechas entonces realizar la consulta,
       * sino se valida las fechas.
       * 
       */
      if (this.dateForm.get("dateInit").value !== "" && this.dateForm.get("dateEnd").value !== "") {
        //console.log("Selecciono las fechas")

        if (compareDesc(dates.startDate, dates.endDate) < 0 && this.dateForm.get("dateEnd").value != "") {
          this.utilService.showNotification(1, "Fecha de Inicio no debe de ser mayor a la Fecha Fin y la Fecha Fin no debe de ser menor a la Fecha de Inicio.!!");
        } else {

          // Lógica de la consulta
          //console.log("Realizar la consulta");
          filterModel.startDate = this.dateForm.get("dateInit").value;
          filterModel.endDate = this.dateForm.get("dateEnd").value;
          this.currentPage = 0;
          await this.getBillingByFilter(filterModel);
          this.closeLoading();
        }

      } else {

        /**
         * Validamos:
         * Si hay una fecha fin seleccionada y la fecha de inicio vacía se manda la alerta
         * Si hay una fecha de inicio seleccionada y la fecha fin vacía se manda la alerta
         * Si la fecha de inicio es mayor a la fecha fin se manda la alerta
         * Si la fecha fin es menor a la fecha de inicio se manda la alerta
         * 
         */
        if (this.dateForm.get("dateInit").value === "" && this.dateForm.get("dateEnd").value !== "") {
          this.utilService.showNotification(1, "La fecha de inicio no debe de estar vacía.!!");

        } else if (this.dateForm.get("dateEnd").value === "" && this.dateForm.get("dateInit").value !== "") {
          this.utilService.showNotification(1, "La fecha fin no debe de estar vacía.!!");

        } else {

          // Lógica indicando que no se selecciono las fechas
          //console.log("No selecciono las fechas")
          filterModel.startDate = "";
          filterModel.endDate = "";
          this.currentPage = 0;
          await this.getBillingByFilter(filterModel);
          this.closeLoading();
        }

      }

    } else {
      this.utilService.showNotification(1, "Debes de seleccionar por lo menos un filtro");
    }

    //console.log(filterModel);
    Swal.close();

  }



  exportExcel() {
    if (this.rows.length > 0) {
      const dataExcel = this.rows.map(objeto => {
        console.log(objeto);
        const dateCreated = this.datePipe.transform(objeto.created, "dd/MM/yyyy hh:mm:ss");
        const dateDue = this.datePipe.transform(objeto.dueDate, "dd/MM/yyyy hh:mm:ss");
        const exo = objeto.exonerationStatus === 0 ? "No" : "Sí";

        const imeis = Array.isArray(objeto.invoiceDetails)
          ? objeto.invoiceDetails
            .map(det => det?.serieOrBoxNumber)
            .filter(imei => imei !== null && imei !== undefined && imei !== '')
            .join(', ')
          : '';

        return { TipoFactura: objeto.invoiceType, NoFactura: objeto.id, Estado: objeto.status, Cliente: objeto.customer, Vendedor: objeto.seller, Company: objeto.agency, Bodega: objeto.warehouse, FechaCreacion: dateCreated, Exonerada: exo, SubTotal: objeto.subtotal, Descuento: objeto.discount, ISV: objeto.amountTax, Total: objeto.amountTotal, EmitidaPor: objeto.userIssued, Suscriptor: objeto.primaryIdentity, IMEI: imeis };
      });

      const arrayDeArrays: any[][] = dataExcel.map(obj => Object.values(obj));

      this.dataExport = arrayDeArrays;

      const columnNames = ['Tipo de Factura', 'No. Factura', 'Estado', 'Cliente', 'Vendedor', 'Sucursal', 'Bodega', 'Fecha Creación', 'Exonerada', 'SubTotal', 'Descuento', 'I.S.V', 'Total', 'Emitido Por', 'Suscriptor', 'IMEI'];

      const title = this.invoiceStatus === null ? "Detalle Reporte Facturación Equipos y Accesorios" : this.invoiceStatus === -1 ? "Detalle Reporte Facturación Equipo y Accesorios Error" : this.invoiceStatus === 0 ? "Detalle Reporte Facturación Equipos y Accesorios Pendientes" : this.invoiceStatus === 1 ? "Detalle Reporte Facturación Equipo y Accesorios Autorizadas" : this.invoiceStatus === 2 ? "Detalle Reporte Facturación Equipos y Accesorios Emitidas" : this.invoiceStatus === 4 ? "Detalle Reporte Facturación Equipos y Accesorios Fiscal" : this.invoiceStatus === 5 ? "Detalles Reporte Facturación Equipos y Accesorios Anuladas Sin Emitir" : "Detalles Reporte Facturación Equipos y Accesorios Anuladas Con Número Fiscal";

      this.excelService.generarExcel(this.dataExport, title, columnNames);
    } else {
      this.utilService.showNotification(1, "No se encuentran registros para exportar");
    }
  }

  exportData() {

    if (this.selectedItems.length > 0) {
      if (this.selectedItems[0].id == 1) {
        const dataExcel = this.rows.map(objeto => {

          console.log(objeto);
          const dateCreated = this.datePipe.transform(objeto.created, "dd/MM/yyyy hh:mm:ss");
          const dateDue = this.datePipe.transform(objeto.dueDate, "dd/MM/yyyy hh:mm:ss");
          const exo = objeto.exonerationStatus === 0 ? "No" : "Sí";

          return { NoFactura: objeto.invoiceNo, TipoFactura: objeto.invoiceType, Estado: objeto.status, Cliente: objeto.customer, Vendedor: objeto.seller, Company: objeto.agency, Bodega: objeto.warehouse, FechaCreacion: dateCreated, Exonerada: exo, SubTotal: objeto.subtotal, Descuento: objeto.discount, ISV: objeto.amountTax, Total: objeto.amountTotal, EmitidaPor: objeto.userIssued, Suscriptor: objeto.primaryIdentity };
        });

        const arrayDeArrays: any[][] = dataExcel.map(obj => Object.values(obj));

        this.dataExport = arrayDeArrays;

        const columnNames = ['No. Factura', 'Tipo de Factura', 'Estado', 'Cliente', 'Vendedor', 'Sucursal', 'Bodega', 'Fecha Creación', 'Exonerada', 'SubTotal', 'Descuento', 'I.S.V', 'Total', 'Emitdo Por', 'Suscriptor '];

        const title = this.invoiceStatus === null ? "Detalle Reporte Facturación Equipos y Accesorios" : this.invoiceStatus === -1 ? "Detalle Reporte Facturación Equipo y Accesorios Error" : this.invoiceStatus === 0 ? "Detalle Reporte Facturación Equipos y Accesorios Pendientes" : this.invoiceStatus === 1 ? "Detalle Reporte Facturación Equipo y Accesorios Autorizadas" : this.invoiceStatus === 2 ? "Detalle Reporte Facturación Equipos y Accesorios Emitidas" : this.invoiceStatus === 4 ? "Detalle Reporte Facturación Equipos y Accesorios Fiscal" : this.invoiceStatus === 5 ? "Detalles Reporte Facturación Equipos y Accesorios Anuladas Sin Emitir" : "Detalles Reporte Facturación Equipos y Accesorios Anuladas Con Número Fiscal";

        this.excelService.generarExcel(this.dataExport, title, columnNames);
      } else {
        this.pdfService.generatePdfNavega(this.rows);
      }
    } else {
      this.utilService.showNotification(1, "Seleccione una opción para poder continuar")
    }
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
    return this.rows.length == 1 ? "Registro" : "Registros";
  }

  reloadRows() {
    //this.getByFilter();
  }

  openModal(data: any) {

    const modalRef = this.modalService.open(InvoiceDetailComponent, {
      size: "lg", backdrop: "static"
    })

    modalRef.componentInstance.billing = data;


  }

  /**
* Método encargado de obtener los parámetros de la pantalla
* 
*/
  configparametersById(id: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.billingReportService.configparametersById(id).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {

          // Vaciamos las 
          if (id === 1000) {
            this.parametersInvoiceStatus = [];
          }

          // Mapeamos el body del response
          let configParameterResponse = response.body as ConfigParameterResponse;

          // Agregamos los valores a los rows

          configParameterResponse.data.map((resourceMap, configError) => {

            let dto: ConfigParameter = resourceMap;

            if (id === 1000) {
              this.parametersInvoiceStatus.push(dto);
            }

          });

          if (id === 1000) {
            this.parametersInvoiceStatus = [...this.parametersInvoiceStatus];
          }
          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })
    });


  }



  /**
   * Método encargado de obtener las sucursales
   * 
   * @returns 
   */
  getAllBrancheOffices(user: any): Promise<boolean> {

    return new Promise((resolve, reject) => {

      this.invoiceService.getBrancheOfficesByUser(user).subscribe((response) => {

        if (response.status === 200) {

          let brancheOfficesResponse = response.body as BrancheOfficesResponse;

          brancheOfficesResponse.data.map((resourceMap, configError) => {

            let dto: BrancheOffices = resourceMap;
            let item: ItemSelect = {
              id: dto.id,
              itemName: dto.name
            }

            this.dropDownListBrancheOffices.push(item);

          });

          this.dropDownListBrancheOffices = [...this.dropDownListBrancheOffices];

          // console.log(responseInvoice);
          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })

    });

  }


  /**
   * Método encargado de obtener las bodegas
   * 
   * @returns 
   */
  getWareHouses(user: any): Promise<boolean> {

    return new Promise((resolve, reject) => {

      this.invoiceService.getWareHouses(user).subscribe((response) => {

        if (response.status === 200) {

          let brancheOfficesResponse = response.body as WareHousesResponse;

          brancheOfficesResponse.data.map((resourceMap, configError) => {

            let dto: WareHouseModel = resourceMap;
            let item: ItemSelect = {
              id: dto.code,
              itemName: `${dto.code} - ${dto.name}`
            }

            this.dropDownListWareHouse.push(item);

          });

          this.dropDownListWareHouse = [...this.dropDownListWareHouse];

          // console.log(responseInvoice);
          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })

    });

  }


  async onResultsPerPageChange(size: number) {
    this.openLoading('Cargando Facturas...');
    this.resultsPerPage = +size;
    this.pageSize = +size;
    this.currentPage = 0;
    await this.getBillingByFilter(this.createFilterPayload());
    this.closeLoading();
  }


  async onPageChange(event: any) {
    this.openLoading('Cargando Facturas...');
    this.currentPage = event.offset;
    await this.getBillingByFilter(this.createFilterPayload());
    this.closeLoading();
  }


  createFilterPayload(): FilterModel {
    const filterModel: FilterModel = {};
    filterModel.seller = this.utilService.getSystemUser();
    filterModel.warehouses = this.wareHouseArray;
    filterModel.status = this.statusArray;
    filterModel.agencies = this.agenciesArray;
    filterModel.territory = this.territories;

    const dateInit = this.dateForm.get('dateInit')?.value;
    const dateEnd = this.dateForm.get('dateEnd')?.value;
    if (dateInit) filterModel.startDate = dateInit;
    if (dateEnd) filterModel.endDate = dateEnd;

    return filterModel;
  }


  /**
   * Método que consume un servicio para traer las facturas según el tipo de combinaciones
   * que el usuario haya realizado
   * 
   * @param payload 
   * @returns 
   */
  async getBillingByFilter(payload: any): Promise<boolean> {
    this.loadingIndicator = true;
    return new Promise((resolve) => {
      this.invoiceService.getBillingByFilter(this.currentPage, this.pageSize, payload).subscribe((res) => {
        if (res.status === 200) {
          this.rows = [];
          this.rows2 = [];

          const billingResponse = res.body as BillingPagesResponse;

          this.totalElements = billingResponse.data.totalElements;
          this.totalPages = billingResponse.data.totalPages;
          this.currentPage = billingResponse.data.number;

          if (billingResponse.data.numberOfElements != 0) {
            billingResponse.data.content.forEach((resourceMap) => {
              const dto: Billing = resourceMap;
              dto.status =
                this.parametersInvoiceStatus[0].stateCode === dto.status ? this.parametersInvoiceStatus[0].parameterValue :
                  this.parametersInvoiceStatus[1].stateCode === dto.status ? this.parametersInvoiceStatus[1].parameterValue :
                    this.parametersInvoiceStatus[2].stateCode === dto.status ? this.parametersInvoiceStatus[2].parameterValue :
                      this.parametersInvoiceStatus[3].stateCode === dto.status ? this.parametersInvoiceStatus[3].parameterValue :
                        this.parametersInvoiceStatus[4].stateCode === dto.status ? this.parametersInvoiceStatus[4].parameterValue :
                          this.parametersInvoiceStatus[5].stateCode === dto.status ? this.parametersInvoiceStatus[5].parameterValue :
                            this.parametersInvoiceStatus[7].parameterValue;

              this.rows.push(dto);
            });

            this.rows = [...this.rows];
            this.rows2 = [...this.rows];
            resolve(true);
            if (this.rows.length > 0) this.utilService.showNotification(0, 'Datos cargados');
          } else {
            this.rows = [];
            this.rows2 = [];
            this.utilService.showNotification(1, 'No se encontraron datos');
            resolve(false);
          }
        } else {
          this.utilService.showNotificationError(res?.status);
          this.rows = [];
          this.rows2 = [];
          resolve(false);
        }

        this.loadingIndicator = false; // <- apaga spinner tabla
      }, _ => {
        this.loadingIndicator = false;
        resolve(false);
      });
    });
  }


  /**
 * No está en uso
 * Método que consume un servicio para traer información acorde
 * a los filtros asignados
 * 
 * 
 */
  getinvoiceByFilters() {

    if (this.dateForm.get("dateInit").value === "") {
      //console.log("No se ha seleccionado un rango de fecha");
    } else {
      console.log("Si hay");
    }

    console.log(this.dateForm.get("dateInit").value);
    console.log(this.dateForm.get("dateEnd").value);

    if (this.invoiceStatus === null && this.wareHouseCode === null && this.idBranchOffices === null) {

      this.utilService.showNotification(1, "No ha seleccionado algún filtro");

    } else {
      Swal.fire({
        title: 'Filtrando Facturas...',
        allowOutsideClick: false,
        onBeforeOpen: () => {
          Swal.showLoading();
        }
      });
      this.invoiceService.getByFilter(this.invoiceStatus, this.wareHouseCode, this.idBranchOffices, this.emitter).subscribe((res) => {
        if (res.status === 200) {
          this.loadingIndicator = false;
          this.rows = [];
          this.rows2 = [];

          const billingResponse = res.body as BillingResponse;

          if (billingResponse.data.length != 0) {
            // Vaciamos las 


            billingResponse.data.map((resourceMap, configError) => {

              let dto: Billing = resourceMap;



              dto.status = this.parametersInvoiceStatus[0].stateCode === dto.status ? this.parametersInvoiceStatus[0].parameterValue : this.parametersInvoiceStatus[1].stateCode === dto.status ? this.parametersInvoiceStatus[1].parameterValue : this.parametersInvoiceStatus[2].stateCode === dto.status ? this.parametersInvoiceStatus[2].parameterValue : this.parametersInvoiceStatus[3].stateCode === dto.status ? this.parametersInvoiceStatus[3].parameterValue : this.parametersInvoiceStatus[4].stateCode === dto.status ? this.parametersInvoiceStatus[4].parameterValue : this.parametersInvoiceStatus[5].stateCode === dto.status ? this.parametersInvoiceStatus[5].parameterValue : this.parametersInvoiceStatus[6].parameterValue;



              this.rows.push(dto);

            });

            this.rows = [...this.rows];
            this.rows2 = [...this.rows];
            Swal.close();
          } else {

            this.rows = [...this.rows];
            this.rows2 = [...this.rows];
            this.utilService.showNotification(1, "No se encontraron datos");
            Swal.close();
          }



        } else {
          console.error('Unexpected API response:', res);
          this.utilService.showNotificationError(res?.status);
          this.rows = [...this.rows];
          this.rows2 = [...this.rows];
          Swal.close();
        }
      });


    }

  }


}
