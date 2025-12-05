import { Component, ElementRef, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { UtilService } from 'src/app/services/util.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { compareDesc, format, subDays } from 'date-fns';
import { BrancheOffices, BrancheOfficesResponse, DataGraph, FilterModel, InvoiceByTypeAndStatus, InvoiceStatistics, InvoiceType, InvoiceTypeAndStatus, InvoicesByBranchOfficeAndStatus, ItemBranche, ItemSelect, WareHouseModel, WareHousesResponse } from 'src/app/model/model';
import { InvoiceService } from 'src/app/services/invoice.service';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { Item } from 'angular2-multiselect-dropdown';
import { PdfService } from 'src/app/services/pdf.service';
import Swal from 'sweetalert2';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-graficos-principales',
  templateUrl: './graficos-principales.component.html',
  styleUrls: ['./graficos-principales.component.css'],
  animations: [
    trigger('enterState', [
      state('void', style({
        transform: 'translateX(-100%)',
        opacity: 0
      })),
      transition(':enter', [
        animate(300, style({
          transform: 'translateX(0)',
          opacity: 1
        }))
      ])
    ]),
    trigger('endState', [
      state('void', style({
        transform: 'translateY(-100%)',
        opacity: 0
      })),
      transition(':enter', [
        animate(300, style({
          transform: 'translateY(0)',
          opacity: 1
        }))
      ])
    ])
  ]
})
export class GraficosPrincipalesComponent implements OnInit {

  // Props
  active = 1;

  // Filter
  invoiceType: string = null;
  invoiceStatus: number = null;
  idBranchOffices: number = null;

  // Filter Arrays
  typeArray: string[] = []; // Bodegas
  statusArray: number[] = [];
  branchOfficesArray: string[] = []; // Sucursales
  territories: string[] = []; // Territorios




  // Styles
  inputClasses = "my-auto col-sm-12 col-md-5 col-lg-5";

  // Form
  dateForm!: FormGroup;
  invalidDate: boolean = false;

  // Send Input
  data: InvoiceStatistics = null;
  hideGraphes: boolean = false;
  hidddenComponent: boolean = false;

  // Select

  // Status
  public dropsownSettingsStatus = {};
  public dropDownListStatus = [
    { id: -1, itemName: "Error" }, { id: 0, itemName: "Pendientes" }, { id: 1, itemName: "Autorizadas" }, { id: 2, itemName: "Emitidas" }, { id: 5, itemName: "Anuladas Sin Emitir" }, { id: 6, itemName: "Anuladas Con Número Fiscal" },
  ];

  public selectedItemStatus = [];

  // Tipo de factura
  public dropsownSettingsType = {};
  public dropDownListType = [
    { id: 1, itemName: "FC1" }, { id: 2, itemName: "FC2" }, { id: 3, itemName: "FC3" }, { id: 4, itemName: "FC4" }, { id: 5, itemName: "FS1" }, { id: 6, itemName: "FS2" }, { id: 7, itemName: "FS3" }, { id: 8, itemName: "FS4" }, { id: 9, itemName: "SHP" }, { id: 10, itemName: "SHO" }
  ];

  public selectedItemType = [];

  // Sucursal
  public dropsownSettingsBrancheOffices = {};
  public dropDownListBrancheOffices = [];
  public dropDownListBrancheOfficesTest: ItemSelect[] = [
    { id: -1, itemName: "T1" }, { id: -2, itemName: "T2" }, { id: -3, itemName: "T3" }
  ];

  public selectedItemBrancheOffices = [];

    // Territorios
    public dropsownSettingsTerritories = {};
    public dropDownListTerritories: ItemSelect[] = [
      { id: -1, itemName: "T1" }, { id: -2, itemName: "T2" }, { id: -3, itemName: "T3" }
    ];
  
    public selectedItemTerritory = [];
  
  // Export - Excel
  dataGraph: DataGraph = {};

  // Hidden
  graphGeneral: boolean = false;
  graphType: boolean = false;
  graphBrancheOffice: boolean = false;

  constructor(public utilService: UtilService, private readonly fb: FormBuilder, private invoiceService: InvoiceService, private pdfService: PdfService) { }

  async ngOnInit() {
    this.dateForm = this.initForm();

    this.dropsownSettingsStatus = {
      singleSelection: false,
      text: "Seleccione un valor",
      selectAllText: "Seleccionar todos",
      unSelectAllText: "Deseleccionar todo",
      enableSearchFilter: true,
      classes: ""
    };

    this.dropsownSettingsType = {
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

    await this.getAllBrancheOfficesByUser(this.utilService.getSystemUser());

  }

  initForm(): FormGroup {
    return this.fb.group({
      dateInit: ['', [Validators.required]],
      dateEnd: ['', [Validators.required]],
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

    if (
      compareDesc(dates.startDate, dates.endDate) < 0 &&
      this.dateForm.get("dateEnd").value != ""
    ) {
      //this.utilService.showNotificationMsj("Fecha de Inicio Debe Ser Mayor o Igual a Fecha de Fin.!!");

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

  // Select Sucursal

  onSelectAllBrancheOffice(items: any) {
    //console.log(items);
    this.branchOfficesArray = [];
    this.branchOfficesArray = [...this.branchOfficesArray];
    items.forEach(item => {

      this.branchOfficesArray.push(item.itemName);
    });
    //console.log(this.branchOfficesArray);
  }

  onDeSelectAllBrancheOffice(items: any) {
    //console.log(items);
    this.branchOfficesArray = [];
    this.branchOfficesArray = [...this.branchOfficesArray];
    //console.log(this.branchOfficesArray);
  }

  onItemSelectBrancheOffice(event: any) {

    this.idBranchOffices = event.id;
    this.branchOfficesArray.push(event.itemName);
    //console.log(this.branchOfficesArray);

  }

  onItemDeSelectBrancheOffice(event: any) {

    this.idBranchOffices = null;
    const index = this.branchOfficesArray.findIndex(item => item === event.itemName);

    // Validamos si encontro el elemento
    if (index !== -1) {
      this.branchOfficesArray.splice(index, 1);

    }
    //console.log(this.branchOfficesArray);

  }

  // Select Tipo

  onSelectAllType(items: any) {
    //console.log(items);
    this.typeArray = [];
    this.typeArray = [...this.typeArray];
    items.forEach(item => {

      this.typeArray.push(item.itemName);
    });
    //console.log(this.typeArray);
  }

  onDeSelectAllType(items: any) {
    //console.log(items);
    this.typeArray = [];
    this.typeArray = [...this.typeArray];
    //console.log(this.typeArray);
  }

  onItemSelectType(event) {
    this.invoiceType = event.itemName;
    this.typeArray.push(event.itemName);
    //console.log(this.typeArray);
  }

  onItemDeSelectType(event) {
    this.invoiceType = null;
    const index = this.typeArray.findIndex(item => item === event.itemName);

    // Validamos si encontro el elemento
    if (index !== -1) {
      this.typeArray.splice(index, 1);

    }
    //console.log(this.typeArray);
  }


  // Select de Estado

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
    //console.log(items);
    this.statusArray = [];
    this.statusArray = [...this.statusArray];
    //console.log(this.statusArray);
  }

  onItemSelectStatus(event) {

    //console.log(event);
    this.invoiceStatus = event.id;
    this.statusArray.push(event.id);
    //console.log(this.statusArray);

  }

  onItemDeSelectStatus(event) {
    this.invoiceStatus = null;
    const index = this.statusArray.findIndex(item => item === event.id);

    // Validamos si existe
    if (index !== -1) {
      this.statusArray.splice(index, 1);
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

  changeTab(option: number) {
    if (option === 1) {
      this.graphGeneral = true;
      this.graphType = false;
      this.graphBrancheOffice = false;
    }
    if (option === 2) {
      this.graphGeneral = false;
      this.graphType = true;
      this.graphBrancheOffice = false;
    }

    if (option === 3) {
      this.graphGeneral = false;
      this.graphType = false;
      this.graphBrancheOffice = true;
    }

  }


  /**
   * No está en uso
   * Método que ejecuta la acción para buscar por el 
   * rango de fechas
   * 
   */
  async onSubmit() {
    this.hideGraphes = false;
    const validateInvoiceDate = await this.getBillisByDateRange(this.dateForm.get("dateInit").value, this.dateForm.get("dateEnd").value);


    // Validamos si hay datos o no
    if (validateInvoiceDate) {
      this.utilService.showNotification(0, "Gráficos cargados");
      this.hideGraphes = true;
      this.graphGeneral = true;
      this.graphType = false;
      this.graphBrancheOffice = false;
      this.hidddenComponent = true;
      this.active = 1;
    } else {
      this.hideGraphes = true;
      this.hidddenComponent = false;
      this.graphGeneral = false;
      this.graphType = false;
      this.graphBrancheOffice = false;
      //this.utilService.showNotification(1, "No se encontraron datos con el rango de fecha seleccionado");
    }
  }

  /**
   * Método encargado de validar y realizar la filtración
   * de datos
   * 
   */
  async getFilterMix() {
    Swal.fire({
      title: 'Filtrando...',
      allowOutsideClick: false,
      onBeforeOpen: () => {
        Swal.showLoading();
      }
    });

    // Variable payload
    let filterModel: FilterModel = {};
    //filterModel.seller = this.utilService.getSystemUser();
    filterModel.status = this.statusArray;
    filterModel.agencies = this.branchOfficesArray;
    filterModel.invoiceType = this.typeArray;
    filterModel.territories = this.territories;

    /**
     * Validamos si selecciono algún filtro
     * 
     */
    if (this.dateForm.get("dateInit").value !== "" || this.dateForm.get("dateEnd").value !== "" || this.statusArray.length > 0 || this.branchOfficesArray.length > 0 || this.typeArray.length > 0 || this.territories.length>0) {

      let dates = this.getDates();
      //console.log(dates);

      /**
       * Validamos si selecciona las fechas entonces realizar
       * la consulta, sino se valida las fechas
       * 
       */
      if (this.dateForm.get("dateInit").value !== "" && this.dateForm.get("dateEnd").value !== "") {

        /**
         * Validamos si la fecha de inicio es menor a la final y que
         * la fecha final no esté vacia
         * 
         */
        if (compareDesc(dates.startDate, dates.endDate) < 0 && this.dateForm.get("dateEnd").value != "") {
          this.utilService.showNotification(1, "Fecha de Inicio no debe de ser mayor a la Fecha Fin y la Fecha Fin no debe de ser menor a la Fecha de Inicio.!!");
        } else {

          // Lógica de la consulta
          //console.log("Realizar la consulta");
          filterModel.startDate = this.dateForm.get("dateInit").value;
          filterModel.endDate = this.dateForm.get("dateEnd").value;
          
          this.hideGraphes = false;
          const validateInvoiceDate = await this.getBillingByFilter(filterModel);
      
      
          // Validamos si hay datos o no
          if (validateInvoiceDate) {
            this.utilService.showNotification(0, "Gráficos cargados");
            this.hideGraphes = true;
            this.graphGeneral = true;
            this.graphType = false;
            this.graphBrancheOffice = false;
            this.hidddenComponent = true;
            this.active = 1;
          } else {
            this.hideGraphes = true;
            this.hidddenComponent = false;
            this.graphGeneral = false;
            this.graphType = false;
            this.graphBrancheOffice = false;
            //this.utilService.showNotification(1, "No se encontraron datos con el rango de fecha seleccionado");
          }

          

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
          
          this.hideGraphes = false;
          const validateInvoiceDate = await this.getBillingByFilter(filterModel);
      
      
          // Validamos si hay datos o no
          if (validateInvoiceDate) {
            this.utilService.showNotification(0, "Gráficos cargados");
            this.hideGraphes = true;
            this.graphGeneral = true;
            this.graphType = false;
            this.graphBrancheOffice = false;
            this.hidddenComponent = true;
            this.active = 1;
          } else {
            this.hideGraphes = true;
            this.hidddenComponent = false;
            this.graphGeneral = false;
            this.graphType = false;
            this.graphBrancheOffice = false;
            //this.utilService.showNotification(1, "No se encontraron datos con el rango de fecha seleccionado");
          }


        }

      }


    } else {
      this.utilService.showNotification(1, "Debes de seleccionar por lo menos un filtro");
    }

    Swal.close();

  }


  exportExcel() {

    let images: string[] = [];

    const CANVAS = document.getElementById('graf_pastel') as HTMLCanvasElement;
    const IMAGE_DATA = CANVAS.toDataURL(); // Convertir el canvas a imagen
    images.push(IMAGE_DATA);

    const canvas2 = document.getElementById('graf_seller') as HTMLCanvasElement;
    const imageData2 = canvas2.toDataURL(); // Convertir el canvas a imagen
    images.push(imageData2);

    const canvas3 = document.getElementById('graf_type') as HTMLCanvasElement;
    const imageData3 = canvas3.toDataURL(); // Convertir el canvas a imagen
    images.push(imageData3);

    const canvas4 = document.getElementById('graf_status') as HTMLCanvasElement;
    const imageData4 = canvas4.toDataURL(); // Convertir el canvas a imagen
    images.push(imageData4);

    const canvas5 = document.getElementById('graf_branche_offices') as HTMLCanvasElement;
    const imageData5 = canvas5.toDataURL(); // Convertir el canvas a imagen
    images.push(imageData5);

    // Seteando valores para la data del excel

    // Seteamos los valores generales
    this.dataGraph.invoiceGeneral = {
      unauthorizedInvoices: this.data.unauthorizedInvoices,
      authorizedInvoices: this.data.authorizedInvoices,
      issuedInvoices: this.data.issuedInvoices,
      invoicesWithTaxNumber: this.data.invoicesWithTaxNumber,
      canceledInvoicesWithoutIssued: this.data.canceledInvoicesWithoutIssued,
      canceledInvoicesWithTaxNumber: this.data.canceledInvoicesWithTaxNumber,
      errorInvoice: this.data.errorInvoice,
    }

    //console.log(this.dataGraph.invoiceGeneral);


    // Seteamos por el tipo de factura
    this.dataGraph.invoiceType = this.data.invoicesByType as InvoiceType;

    // Seteamos los valores Tipo y Estado
    const INVOICE_BY_BRANCHE_OFFICES_AND_STATUS = this.data.invoicesByTypeAndStatus as InvoiceByTypeAndStatus[];

    let invoiceTypeAndStatus: InvoiceTypeAndStatus[] = [];

    let typeFC1: InvoiceTypeAndStatus = {
      type: "FC1",
      invoiceGeneral: {
        unauthorizedInvoices: 0,
        authorizedInvoices: 0,
        issuedInvoices: 0,
        invoicesWithTaxNumber: 0,
        canceledInvoicesWithoutIssued: 0,
        canceledInvoicesWithTaxNumber: 0,
        errorInvoice: 0
      }
    }
    let typeFC2: InvoiceTypeAndStatus = {
      type: "FC2",
      invoiceGeneral: {
        unauthorizedInvoices: 0,
        authorizedInvoices: 0,
        issuedInvoices: 0,
        invoicesWithTaxNumber: 0,
        canceledInvoicesWithoutIssued: 0,
        canceledInvoicesWithTaxNumber: 0,
        errorInvoice: 0
      }
    }
    let typeFC3: InvoiceTypeAndStatus = {
      type: "FC3",
      invoiceGeneral: {
        unauthorizedInvoices: 0,
        authorizedInvoices: 0,
        issuedInvoices: 0,
        invoicesWithTaxNumber: 0,
        canceledInvoicesWithoutIssued: 0,
        canceledInvoicesWithTaxNumber: 0,
        errorInvoice: 0
      }
    }
    let typeFC4: InvoiceTypeAndStatus = {
      type: "FC4",
      invoiceGeneral: {
        unauthorizedInvoices: 0,
        authorizedInvoices: 0,
        issuedInvoices: 0,
        invoicesWithTaxNumber: 0,
        canceledInvoicesWithoutIssued: 0,
        canceledInvoicesWithTaxNumber: 0,
        errorInvoice: 0
      }
    }

    INVOICE_BY_BRANCHE_OFFICES_AND_STATUS.forEach((element) => {

      let dto: InvoiceTypeAndStatus = {};

      if (element.invoiceType === "FC1") {

        switch (element.status) {
          case 'unauthorizedInvoices':
            typeFC1.invoiceGeneral.unauthorizedInvoices = element.count;
            break;
          case 'authorizedInvoices':
            typeFC1.invoiceGeneral.authorizedInvoices = element.count;
            break;
          case 'issuedInvoices':
            typeFC1.invoiceGeneral.issuedInvoices = element.count;
            break;
          case 'invoicesWithTaxNumber':
            typeFC1.invoiceGeneral.invoicesWithTaxNumber = element.count;
            break;
          case 'canceledInvoicesWithoutIssued':
            typeFC1.invoiceGeneral.canceledInvoicesWithoutIssued = element.count;
            break;
          case 'canceledInvoicesWithTaxNumber':
            typeFC1.invoiceGeneral.canceledInvoicesWithTaxNumber = element.count;
            break;
          case 'errorInvoice':
            typeFC1.invoiceGeneral.errorInvoice = element.count;
            break;

          default:

        }

      }

      if (element.invoiceType === "FC2") {

        switch (element.status) {
          case 'unauthorizedInvoices':
            typeFC2.invoiceGeneral.unauthorizedInvoices = element.count;
            break;
          case 'authorizedInvoices':
            typeFC2.invoiceGeneral.authorizedInvoices = element.count;
            break;
          case 'issuedInvoices':
            typeFC2.invoiceGeneral.issuedInvoices = element.count;
            break;
          case 'invoicesWithTaxNumber':
            typeFC2.invoiceGeneral.invoicesWithTaxNumber = element.count;
            break;
          case 'canceledInvoicesWithoutIssued':
            typeFC2.invoiceGeneral.canceledInvoicesWithoutIssued = element.count;
            break;
          case 'canceledInvoicesWithTaxNumber':
            typeFC2.invoiceGeneral.canceledInvoicesWithTaxNumber = element.count;
            break;
          case 'errorInvoice':
            typeFC2.invoiceGeneral.errorInvoice = element.count;
            break;

          default:

        }

      }

      if (element.invoiceType === "FC3") {

        switch (element.status) {
          case 'unauthorizedInvoices':
            typeFC3.invoiceGeneral.unauthorizedInvoices = element.count;
            break;
          case 'authorizedInvoices':
            typeFC3.invoiceGeneral.authorizedInvoices = element.count;
            break;
          case 'issuedInvoices':
            typeFC3.invoiceGeneral.issuedInvoices = element.count;
            break;
          case 'invoicesWithTaxNumber':
            typeFC3.invoiceGeneral.invoicesWithTaxNumber = element.count;
            break;
          case 'canceledInvoicesWithoutIssued':
            typeFC3.invoiceGeneral.canceledInvoicesWithoutIssued = element.count;
            break;
          case 'canceledInvoicesWithTaxNumber':
            typeFC3.invoiceGeneral.canceledInvoicesWithTaxNumber = element.count;
            break;
          case 'errorInvoice':
            typeFC3.invoiceGeneral.errorInvoice = element.count;
            break;

          default:

        }

      }

      if (element.invoiceType === "FC4") {

        switch (element.status) {
          case 'unauthorizedInvoices':
            typeFC4.invoiceGeneral.unauthorizedInvoices = element.count;
            break;
          case 'authorizedInvoices':
            typeFC1.invoiceGeneral.authorizedInvoices = element.count;
            break;
          case 'issuedInvoices':
            typeFC4.invoiceGeneral.issuedInvoices = element.count;
            break;
          case 'invoicesWithTaxNumber':
            typeFC4.invoiceGeneral.invoicesWithTaxNumber = element.count;
            break;
          case 'canceledInvoicesWithoutIssued':
            typeFC4.invoiceGeneral.canceledInvoicesWithoutIssued = element.count;
            break;
          case 'canceledInvoicesWithTaxNumber':
            typeFC4.invoiceGeneral.canceledInvoicesWithTaxNumber = element.count;
            break;
          case 'errorInvoice':
            typeFC4.invoiceGeneral.errorInvoice = element.count;
            break;

          default:

        }

      }

    });

    invoiceTypeAndStatus.push(typeFC1);
    invoiceTypeAndStatus.push(typeFC4);
    invoiceTypeAndStatus.push(typeFC4);
    invoiceTypeAndStatus.push(typeFC4);

    //console.log(invoiceTypeAndStatus);

  }


  generatePDF(): void {
    const CANVAS = document.getElementById('graf_pastel') as HTMLCanvasElement;
    const IMAGE_DATA = CANVAS.toDataURL(); // Convertir el canvas a imagen

    const canvas2 = document.getElementById('graf_seller') as HTMLCanvasElement;
    const imageData2 = canvas2.toDataURL(); // Convertir el canvas a imagen

    const canvas3 = document.getElementById('graf_type') as HTMLCanvasElement;
    const imageData3 = canvas3.toDataURL(); // Convertir el canvas a imagen

    const canvas4 = document.getElementById('graf_status') as HTMLCanvasElement;
    const imageData4 = canvas4.toDataURL(); // Convertir el canvas a imagen

    const canvas5 = document.getElementById('graf_branche_offices') as HTMLCanvasElement;
    const imageData5 = canvas5.toDataURL(); // Convertir el canvas a imagen

    this.pdfService.generatePDFTwo(IMAGE_DATA, imageData2, imageData3, imageData4, imageData5, this.data, this.dateForm.get("dateInit").value, this.dateForm.get("dateEnd").value);
  }


  getCountByStatus(element: any): number {
    let retorno: number = 0;
    switch (element.status) {
      case 'unauthorizedInvoices':
        retorno = element.count;
        break;
      case 'authorizedInvoices':
        retorno = element.count;
        break;
      case 'issuedInvoices':
        retorno = element.count;
        break;
      case 'invoicesWithTaxNumber':
        retorno = element.count;
        break;
      case 'canceledInvoicesWithoutIssued':
        retorno = element.count;
        break;
      case 'canceledInvoicesWithTaxNumber':
        retorno = element.count;
        break;
      case 'errorInvoice':
        retorno = element.count;
        break;

      default:

    }

    return retorno;
  }

  getBillisByDateRange(initDate: any, endDate: any): Promise<boolean> {

    return new Promise((resolve, reject) => {

      this.invoiceService.getBillisByFilter(initDate, endDate, this.idBranchOffices, this.invoiceType, this.invoiceStatus).subscribe((response) => {

        if (response.status === 200) {
          this.data = null;

          let responseInvoice = response.body as InvoiceStatistics;

          this.data = responseInvoice;

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
   * Método que consume un servicio para obtener
   * los valores que se reflejan en los gráficos
   * 
   * @param payload 
   * @returns 
   */
  getBillingByFilter(payload:any): Promise<boolean> {

    return new Promise((resolve, reject) => {

      this.invoiceService.getBillingByFilter(payload).subscribe((response) => {

        if (response.status === 200) {
          this.data = null;

          let responseInvoice = response.body as InvoiceStatistics;

          this.data = responseInvoice;

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
   * Método encargado de obtener las sucursales, segun
   * el usuario
   * 
   * 
   * @returns 
   */
  getAllBrancheOfficesByUser(user: any): Promise<boolean> {

    return new Promise((resolve, reject) => {

      this.invoiceService.getBrancheOfficesByUser(user).subscribe((response) => {

        if (response.status === 200) {
          this.data = null;

          let brancheOfficesResponse = response.body as BrancheOfficesResponse;

          brancheOfficesResponse.data.map((resourceMap, configError) => {

            let dto: BrancheOffices = resourceMap;
            let item: ItemBranche = {
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





}
