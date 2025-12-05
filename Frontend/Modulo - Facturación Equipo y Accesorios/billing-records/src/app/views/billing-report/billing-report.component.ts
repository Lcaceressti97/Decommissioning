import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { BillingResponse, ConfigParameterResponse, InvoiceEquipmentAccesoriesEntity } from 'src/app/entities/InvoiceEquipmentAccesoriesEntity';
import { Billing, ConfigParameter, InvoiceEquipmentAccesories } from 'src/app/models/InvoiceEquipmentAccesories';
import { BillingReportService } from 'src/app/services/billing-report.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';
import { ExcelService } from 'src/app/services/excel.service';
import { getDetailBillingReport } from 'src/app/utils/BillingReportPdf';
import { PdfService } from 'src/app/services/pdf.service';

@Component({
  selector: 'app-billing-report',
  templateUrl: './billing-report.component.html',
  styleUrls: ['./billing-report.component.css']
})
export class BillingReportComponent implements OnInit {

  // Filter
  invoiceStatus: number = null;

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
  public dropDownListStatus = [
    { id: 0, itemName: "Pendientes" }, { id: 1, itemName: "Autorizadas" }, { id: 2, itemName: "Emitidas" }, { id: 4, itemName: "Con Número Fiscal" }, { id: 5, itemName: "Anuladas Sin Emitir" }, { id: 6, itemName: "Anuladas Con Número Fiscal" },
  ];

  public selectedItemStatus = [];


  constructor(private billingReportService: BillingReportService,
    public utilService: UtilService,
    private excelService: ExcelService,
    private pdfService: PdfService) { }

  filterForm = new FormGroup({
    invoiceStatus: new FormControl("", []),
    warehouse: new FormControl("", []),
    agency: new FormControl("", []),
    createdBy: new FormControl("", [])
  });

  ngOnInit(): void {



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

    this.getAllParameters();
  }

  //* COMPONENTS


  // PARAMETERS
  async getAllParameters() {

    await this.configparametersById(1000);

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



  exportData() {

    if (this.selectedItems.length > 0) {
      if (this.selectedItems[0].id == 1) {
        const dataExcel = this.rows.map(objeto => {
          return { NoFactura: objeto.invoiceNo, TipoFactura: objeto.invoiceType, Estado: objeto.status, Cliente: objeto.customer, Vendedor: objeto.seller, Company: objeto.agency, FechaCreacion: objeto.created, FechaVence: objeto.dueDate, Autorizador: objeto.authorizingUser };
        });

        const arrayDeArrays: any[][] = dataExcel.map(obj => Object.values(obj));

        this.dataExport = arrayDeArrays;

        const columnNames = ['No. Factura', 'Tipo de Factura', 'Estado', 'Cliente', 'Vendedor', 'Compañia', 'Fecha Creación', 'Fecha Vencimiento', 'Autorizado Por'];

        const title = this.invoiceStatus === null ? "Detalle Reporte Facturación Equipo y Accesorios" : this.invoiceStatus === -1 ? "Detalle Reporte Facturación Equipo y Accesorios Error" : this.invoiceStatus === 0 ? "Detalle Reporte Facturación Equipo y Accesorios Pendientes" : this.invoiceStatus === 1 ? "Detalle Reporte Facturación Equipo y Accesorios Autorizadas" : this.invoiceStatus === 2 ? "Detalle Reporte Facturación Equipo y Accesorios Emitidas" : this.invoiceStatus === 4 ? "Detalle Reporte Facturación Equipo y Accesorios Fiscal" : this.invoiceStatus === 5 ? "Detalles Reporte Facturación Equipo y Accesorios Anuladas Sin Emitir" : "Detalles Reporte Facturación Equipo y Accesorios Anuladas Con Número Fiscal";

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

}
