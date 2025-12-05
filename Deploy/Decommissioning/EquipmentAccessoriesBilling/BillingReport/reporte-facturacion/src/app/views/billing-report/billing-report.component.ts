import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { BillingResponse, BrancheOffices, BrancheOfficesResponse, ConfigParameterResponse, InvoiceEquipmentAccesoriesEntity, IssuingUser, IssuingUserResponse, WareHouseModel, WareHousesResponse } from 'src/app/entities/InvoiceEquipmentAccesoriesEntity';
import { Billing, ConfigParameter, InvoiceEquipmentAccesories, ItemSelect } from 'src/app/models/InvoiceEquipmentAccesories';
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

@Component({
  selector: 'app-billing-report',
  templateUrl: './billing-report.component.html',
  styleUrls: ['./billing-report.component.css']
})
export class BillingReportComponent implements OnInit {

  // Filter
  invoiceStatus: number = null;
  idBranchOffices: any = null;
  wareHouseCode: any = null;
  emitter: any = null;

  rows: Billing[] = [];
  rows2: Billing[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
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

  

  //* STYLES
  labelClasses = "col-form-label text-dark col-sm-2 col-md-2 col-lg-2 font-size-large";
  inputClasses = "my-auto col-sm-12 col-md-12 col-lg-12";

  // Select Angular
  // Status
  public dropsownSettingsStatus = {};
  public dropDownListStatus: ItemSelect[] = [
    { id: -1, itemName: "Error" }, {id: 0, itemName: "Pendientes"} , { id: 1, itemName: "Autorizadas" }, { id: 2, itemName: "Emitidas" }, { id: 4, itemName: "Factura Fiscal" }, { id: 5, itemName: "Anuladas Sin Emitir" }, { id: 6, itemName: "Anuladas Factura Fiscal" },
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

  // Emisores
  public dropsownSettingsEmitters = {};
  public dropDownListEmitters: ItemSelect[] = [];

  public selectedItemEmitter = [];


  constructor(private billingReportService: BillingReportService,
    public utilService: UtilService,
    private excelService: ExcelService,
    private pdfService: PdfService,
    private datePipe:DatePipe, private invoiceService:InvoiceService,
    private modalService: NgbModal) { }

  filterForm = new FormGroup({
    invoiceStatus: new FormControl(-2, []),
    warehouse: new FormControl("", []),
    agency: new FormControl("", []),
    createdBy: new FormControl("", [])
  });

  ngOnInit(): void {



    this.setSettingsSelect();

    this.buildDataSelect();

    this.getAllParameters();
  }

 
  // AngularSelect
  setSettingsSelect(){
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
      singleSelection: true,
      text: "Seleccione un valor",
      selectAllText: "Todos",
      unSelectAllText: "Ninguno",
      enableSearchFilter: true,
      classes: ""
    };
    
    this.dropsownSettingsWareHouse = {
      singleSelection: true,
      text: "Seleccione un valor",
      selectAllText: "Todos",
      unSelectAllText: "Ninguno",
      enableSearchFilter: true,
      classes: ""
    };

    this.dropsownSettingsBrancheOffices = {
      singleSelection: true,
      text: "Seleccione un valor",
      selectAllText: "Todos",
      unSelectAllText: "Ninguno",
      enableSearchFilter: true,
      classes: ""
    };

    this.dropsownSettingsEmitters = {
      singleSelection: true,
      text: "Seleccione un valor",
      selectAllText: "Todos",
      unSelectAllText: "Ninguno",
      enableSearchFilter: true,
      classes: ""
    };
  }


  /**
   * Método encargado de consumir un servicio para obtener la información de cada uno de los select
   * 
   */
  async buildDataSelect(){
    await this.getAllBrancheOffices();
    await this.getWareHouses();
    await this.getIssuingUsers();
  }

  // Sucursal
  onItemSelectBrancheOffice(event:any) {
  
    this.idBranchOffices = event.itemName;
  }
  onItemDeSelectBrancheOffice(event:any) {
    this.idBranchOffices = null;
  }

  // Bodega
  onItemSelectWareHouse(event:any) {
   
    this.wareHouseCode = event.id;
  }
  onItemDeSelectWareHouse(event:any) {
    this.wareHouseCode = null;
  }

  // Estado
  onItemSelectStatus(event:any) {
   
    this.invoiceStatus = event.id;
  }
  onItemDeSelectStatus(event:any) {
    this.invoiceStatus = null;
  }

  // Emisor
  onItemSelectEmitter(event:any) {
   
    this.emitter = event.itemName;
  }
  onItemDeSelectEmitter(event:any) {
    this.emitter = null;
  }


  // PARAMETERS
  async getAllParameters() {

    await this.configparametersById(1000);

  }


  /**
   * Método que consume un servicio para traer información acorde
   * a los filtros asignados
   * 
   */
  getinvoiceByFilters(){

    if(this.emitter=== null && this.invoiceStatus===null && this.wareHouseCode===null && this.idBranchOffices===null){

      this.utilService.showNotification(1,"No ha seleccionado algún filtro");

    }else{
      Swal.fire({
        title: 'Filtrando Facturas...',
        allowOutsideClick: false,
        onBeforeOpen: () => {
          Swal.showLoading();
        }
      });
      this.invoiceService.getByFilter(this.invoiceStatus, this.wareHouseCode,this.idBranchOffices , this.emitter).subscribe((res) => {
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

  getInvoiceByFilter() {
    let invoiceStatus = Number(this.filterForm.get("invoiceStatus").value);
    

    if (invoiceStatus === -2) {
      invoiceStatus = null;
    }

    this.invoiceStatus = invoiceStatus;

    const warehouse = this.filterForm.get("warehouse").value;
    const agency = this.filterForm.get("agency").value;
    const createdBy = this.filterForm.get("createdBy").value;

    if (invoiceStatus || warehouse || agency || createdBy) {
      this.loadingIndicator = true;

      this.billingReportService.getByFilter3(invoiceStatus, warehouse, agency, createdBy).subscribe((res) => {
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
          } else {

            this.rows = [...this.rows];
            this.rows2 = [...this.rows];
            this.utilService.showNotification(1, "No se encontraron datos");
          }



        } else {
          console.error('Unexpected API response:', res);
          this.utilService.showNotificationError(res?.status);
          this.rows = [...this.rows];
          this.rows2 = [...this.rows];
        }
      });
    } else {
      console.log('No filters provided');
      this.utilService.showNotification(1, "No se encontraron datos");
      this.rows = [...this.rows];
      this.rows2 = [...this.rows];
    }
  }


  exportExcel(){
    if(this.rows.length>0){
      const dataExcel = this.rows.map(objeto => {

        const dateCreated = this.datePipe.transform(objeto.created, "dd/MM/yyyy hh:mm:ss");
        const dateDue = this.datePipe.transform(objeto.dueDate, "dd/MM/yyyy hh:mm:ss");
        const exo= objeto.exonerationStatus===0 ? "No" : "Sí";

        return { NoFactura: objeto.invoiceNo, TipoFactura: objeto.invoiceType, Estado: objeto.status, Cliente: objeto.customer, Vendedor: objeto.seller, Company: objeto.agency, Bodega: objeto.warehouse, FechaCreacion: dateCreated, Exonerada: exo, SubTotal: objeto.subtotal, Descuento: objeto.discount, ISV: objeto.amountTax, Total: objeto.amountTotal, EmitidaPor: objeto.userIssued };
      });

      const arrayDeArrays: any[][] = dataExcel.map(obj => Object.values(obj));

      this.dataExport = arrayDeArrays;

      const columnNames = ['No. Factura', 'Tipo de Factura', 'Estado', 'Cliente', 'Vendedor', 'Sucursal', 'Bodega', 'Fecha Creación', 'Exonerada', 'SubTotal', 'Descuento', 'I.S.V', 'Total', 'Emitdo Por'];

      const title = this.invoiceStatus === null ? "Detalle Reporte Facturación Equipos y Accesorios" : this.invoiceStatus === -1 ? "Detalle Reporte Facturación Equipo y Accesorios Error" : this.invoiceStatus === 0 ? "Detalle Reporte Facturación Equipos y Accesorios Pendientes" : this.invoiceStatus === 1 ? "Detalle Reporte Facturación Equipo y Accesorios Autorizadas" : this.invoiceStatus === 2 ? "Detalle Reporte Facturación Equipos y Accesorios Emitidas" : this.invoiceStatus === 4 ? "Detalle Reporte Facturación Equipos y Accesorios Fiscal" : this.invoiceStatus === 5 ? "Detalles Reporte Facturación Equipos y Accesorios Anuladas Sin Emitir" : "Detalles Reporte Facturación Equipos y Accesorios Anuladas Con Número Fiscal";

      this.excelService.generarExcel(this.dataExport, title, columnNames);
    }else{
      this.utilService.showNotification(1,"No se encuentran registros para exportar");
    }
  }

  exportData() {

    if (this.selectedItems.length > 0) {
      if (this.selectedItems[0].id == 1) {
        const dataExcel = this.rows.map(objeto => {

          const dateCreated = this.datePipe.transform(objeto.created, "dd/MM/yyyy hh:mm:ss");
          const dateDue = this.datePipe.transform(objeto.dueDate, "dd/MM/yyyy hh:mm:ss");
          const exo= objeto.exonerationStatus===0 ? "No" : "Sí";

          return { NoFactura: objeto.invoiceNo, TipoFactura: objeto.invoiceType, Estado: objeto.status, Cliente: objeto.customer, Vendedor: objeto.seller, Company: objeto.agency, Bodega: objeto.warehouse, FechaCreacion: dateCreated, Exonerada: exo, SubTotal: objeto.subtotal, Descuento: objeto.discount, ISV: objeto.amountTax, Total: objeto.amountTotal, EmitidaPor: objeto.userIssued };
        });

        const arrayDeArrays: any[][] = dataExcel.map(obj => Object.values(obj));

        this.dataExport = arrayDeArrays;

        const columnNames = ['No. Factura', 'Tipo de Factura', 'Estado', 'Cliente', 'Vendedor', 'Sucursal', 'Bodega', 'Fecha Creación', 'Exonerada', 'SubTotal', 'Descuento', 'I.S.V', 'Total', 'Emitdo Por'];

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
  getAllBrancheOffices(): Promise<boolean>{

    return new Promise((resolve,reject)=>{

      this.invoiceService.getAllBrancheOffices(0,2000).subscribe((response)=>{

        if(response.status===200){
        
          let brancheOfficesResponse = response.body as BrancheOfficesResponse;

          brancheOfficesResponse.data.content.map((resourceMap, configError)=>{

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

        }else{
          resolve(false);
        }

      }, (error)=>{
        resolve(false);
      })

    });

  }


  /**
   * Método encargado de obtener las bodegas
   * 
   * @returns 
   */
  getWareHouses(): Promise<boolean>{

    return new Promise((resolve,reject)=>{

      this.invoiceService.getWareHouses().subscribe((response)=>{

        if(response.status===200){
        
          let brancheOfficesResponse = response.body as WareHousesResponse;

          brancheOfficesResponse.data.map((resourceMap, configError)=>{

            let dto: WareHouseModel = resourceMap;
            let item: ItemSelect = {
              id: dto.wineryCode,
              itemName: `${dto.wineryCode} - ${dto.name}`
            }

            this.dropDownListWareHouse.push(item);

          });

          this.dropDownListWareHouse = [...this.dropDownListWareHouse];

         // console.log(responseInvoice);
          resolve(true);

        }else{
          resolve(false);
        }

      }, (error)=>{
        resolve(false);
      })

    });

  }


  /**
   * Método encargado de obtener los usuarios que emiten facturas
   * 
   * @returns 
   */
  getIssuingUsers(): Promise<boolean>{

    return new Promise((resolve,reject)=>{

      this.invoiceService.getIssuingUsers().subscribe((response)=>{

        if(response.status===200){
        
          let brancheOfficesResponse = response.body as IssuingUserResponse;

          brancheOfficesResponse.data.map((resourceMap, configError)=>{

            let dto: IssuingUser = resourceMap;
            let item: ItemSelect = {
              id: dto.id,
              itemName: `${dto.userName}`
            }

            this.dropDownListEmitters.push(item);

          });

          this.dropDownListEmitters = [...this.dropDownListEmitters];

         // console.log(responseInvoice);
          resolve(true);

        }else{
          resolve(false);
        }

      }, (error)=>{
        resolve(false);
      })

    });

  }


}
