import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { InvoiceDetailComponent } from 'src/app/components/invoice-detail/invoice-detail.component';
import { ConfigParameterResponse } from 'src/app/entities/InvoiceEquipmentAccesoriesEntity';
import { ControlCancellationPagesResponse, ControlCancellationResponse, ControlInvoicePagesResponse, ControlInvoiceResponse } from 'src/app/entities/response';
import { ConfigParameter, ControlCancellation, ControlInvoice } from 'src/app/models/model';
import { ExcelService } from 'src/app/services/excel.service';
import { InvoiceService } from 'src/app/services/invoice.service';
import { PdfService } from 'src/app/services/pdf.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';

@Component({
  selector: 'app-billing-records',
  templateUrl: './billing-records.component.html',
  styleUrls: ['./billing-records.component.css']
})
export class BillingRecordsComponent implements OnInit {

  // Props
  //
  title: string = "";
  code: number = null;
  exportar: boolean = false;

  // Table
  messages = messages;
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  rows: ControlInvoice[] = [];
  rows2: ControlInvoice[] = [];
  size: number = 2000;
  hideSelect: boolean = true;

  // Invoice Array table
  rowsCancellationWithFn: ControlInvoice[] = [];
  rowsExcelCancelWithFn: ControlInvoice[] = [];

  rowsCancellationWithOutFn: ControlInvoice[] = [];
  rowsExcelCancelWithOutFn: ControlInvoice[] = [];

  rowsControlAuth: ControlInvoice[] = [];
  rowsExcelControlAuth: ControlInvoice[] = [];

  rowsControlEmit: ControlInvoice[] = [];
  rowsExcelControlEmit: ControlInvoice[] = [];

  // Parameters
  parameters: ConfigParameter[] = [];
  parametersInvoiceCancellation: ConfigParameter[] = [];
  parametersAuthEmissions: ConfigParameter[] = [];

  // Parameters only Transaction
  paramtersTransactionAuthEmission: ConfigParameter[] = [];
  paramtersTransactionCancel: ConfigParameter[] = [];

  // Propiedades de paginación
  currentPage = 0;
  pageSize = 20;
  totalElements = 0;
  totalPages = 0;

  // Styles
  labelClasses = "col-form-label text-dark col-sm-2 col-md-2 col-lg-2";
  inputClasses = "my-auto col-sm-2 col-md-4 col-lg-3";
  inputClassesTwo = "my-auto col-sm-2 col-md-4 col-lg-2";

  // SELECT
  public dropdownSettings = {};
  public dropdownList = [
    {
      id: 1,
      itemName: "Autorizadas"
    },
    {
      id: 2,
      itemName: "Emitidas"
    },
    {
      id: 3,
      itemName: "Anuladas Sin Emitir"
    },
    {
      id: 4,
      itemName: "Anuladas Con Número Fiscal"
    }
  ];
  public selectedItems = [];
  public selectedItemsFile = [];
  dropdownListData;
  dropdownSettingsData

  // Export
  dataExport: any = null;


  constructor(private modalService: NgbModal, public utilService: UtilService, private invoiceService: InvoiceService, private excelService: ExcelService, private pdfService: PdfService, private datePipe: DatePipe) { }

  ngOnInit(): void {
    this.dropdownSettings = {
      singleSelection: true,
      text: "Seleccione un valor",
      selectAllText: "Todos",
      unSelectAllText: "Ninguno",
      enableSearchFilter: true,
      classes: ""
    };

    this.dropdownListData = [
      { id: 1, itemName: "Excel" },
      { id: 2, itemName: "Pdf" },
    ];

    this.dropdownSettingsData = {
      singleSelection: true,
      text: "Exportar a:",
      enableSearchFilter: true
    };

    this.getAllData();
  }

  // Método para manejar el cambio de página
  async onPageChange(event: any) {
    this.currentPage = event.offset;
    this.loadingIndicator = true;

    try {
      let success = false;

      // Llamar al método correspondiente según el tipo de consulta seleccionado
      switch (this.code) {
        case 1:
          success = await this.getControlAuthByStatus(1);
          if (success) {
            this.rows = [...this.rowsControlAuth];
            this.rows2 = [...this.rowsControlAuth];
          }
          break;
        case 2:
          success = await this.getControlEmissionByStatus(2);
          if (success) {
            this.rows = [...this.rowsControlEmit];
            this.rows2 = [...this.rowsControlEmit];
          }
          break;
        case 3:
          success = await this.getControlCancellationWithFnByStatus(1);
          if (success) {
            this.rows = [...this.rowsCancellationWithFn];
            this.rows2 = [...this.rowsCancellationWithFn];
          }
          break;
        case 4:
          success = await this.getControlCancellationWithOutFnByStatus(2);
          if (success) {
            this.rows = [...this.rowsCancellationWithOutFn];
            this.rows2 = [...this.rowsCancellationWithOutFn];
          }
          break;
      }

      if (!success) {
        this.utilService.showNotification(1, "Error al cargar los datos de la página.");
      }
    } catch (error) {
      this.utilService.showNotification(1, "Error al cargar los datos de la página.");
    } finally {
      this.loadingIndicator = false;
    }
  }

  /**
* Este método nos ayuda a buscar dentro del rows un vaor que 
* coincidan con el valor tecleado por el usuario,
* para esto se utiliza un segundo array, de esta forma se evita
* realizar una petición y simplemente se busca dentro de los campos actuales
* 
*/
  search(): void {
    this.rows = this.rows2.filter((log) => {
      return JSON.stringify(log)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  getTotalText() {
    return this.rows.length == 1 ? "Registro" : "Registros";
  }

  openModal(data: any) {

    const modalRef = this.modalService.open(InvoiceDetailComponent, {
      size: "lg", backdrop: "static"
    })

    modalRef.componentInstance.controlInvoice = data;
    modalRef.componentInstance.parameters = this.parameters;

  }

  /**
* Método que controla el cambio de datos 
* 
* @param event 
*/
  async onItemSelect(event) {
    this.title = "Facturas " + event.itemName;
    this.code = event.id;
    this.currentPage = 0; // Resetear a la primera página
    this.loadingIndicator = true;

    try {
      let success = false;
      switch (event.id) {
        case 1:
          success = await this.getControlAuthByStatus(1);
          if (success && this.rowsControlAuth.length > 0) {
            this.rows = [...this.rowsControlAuth];
            this.rows2 = [...this.rowsControlAuth];
            this.utilService.showNotification(0, "Datos cargados");
            this.exportar = true;
          } else {
            this.handleEmptyResults(event.itemName);
          }
          break;
        case 2:
          success = await this.getControlEmissionByStatus(2);
          if (success && this.rowsControlEmit.length > 0) {
            this.rows = [...this.rowsControlEmit];
            this.rows2 = [...this.rowsControlEmit];
            this.utilService.showNotification(0, "Datos cargados");
            this.exportar = true;
          } else {
            this.handleEmptyResults(event.itemName);
          }
          break;
        case 3:
          success = await this.getControlCancellationWithFnByStatus(1);
          if (success && this.rowsCancellationWithFn.length > 0) {
            this.rows = [...this.rowsCancellationWithFn];
            this.rows2 = [...this.rowsCancellationWithFn];
            this.utilService.showNotification(0, "Datos cargados");
            this.exportar = true;
          } else {
            this.handleEmptyResults(event.itemName);
          }
          break;
        case 4:
          success = await this.getControlCancellationWithOutFnByStatus(2);
          if (success && this.rowsCancellationWithOutFn.length > 0) {
            this.rows = [...this.rowsCancellationWithOutFn];
            this.rows2 = [...this.rowsCancellationWithOutFn];
            this.utilService.showNotification(0, "Datos cargados");
            this.exportar = true;
          } else {
            this.handleEmptyResults(event.itemName);
          }
          break;
      }
    } catch (error) {
      this.handleEmptyResults(event.itemName);
    } finally {
      this.loadingIndicator = false;
    }
  }

  private handleEmptyResults(itemName: string) {
    this.utilService.showNotification(1, `No se encontraron datos con el tipo de consulta: ${itemName}`);
    this.rows = [];
    this.rows2 = [];
    this.exportar = false;
  }

  onDeSelect() {
    this.rows = [];
    this.rows = [...this.rows];
    this.rows2 = [...this.rows];
    this.exportar = false;
  }

  async exportData() {

    // Validamos si se selecciono uno opción

    if (this.selectedItemsFile.length > 0) {

      if (this.exportar) {
        const data = await this.buildDataExport();

        if (this.selectedItemsFile[0].id == 1) {



          // Es la infomración que aparece en la tabla
          const dataExcel = this.rows.map(objeto => {

            const dateCreated = this.datePipe.transform(objeto.created, "dd/MM/yyyy hh:mm:ss")

            return { idPrfecture: objeto.idPrefecture, typeApproval: objeto.typeApproval, description: objeto.description, userCreate: objeto.userCreate, created: dateCreated };
          });



          /*
          // Toda la información de los tipos de consultas
          const dataExcel = data.map(objeto => {
            return { idPrfecture: objeto.idPrefecture, typeApproval: objeto.typeApproval, description: objeto.description, userCreate: objeto.userCreate, created: objeto.created };
          });
          */

          const arrayDeArrays: any[][] = dataExcel.map(obj => Object.values(obj));

          this.dataExport = arrayDeArrays;

          const columnNames = ['No. PreFactura', 'Tipo de Transacción', 'Descripción', 'Usuario Responsable', 'Fecha Creación'];

          const type = this.code === 1 ? "Autorizada" : this.code === 2 ? "Emitidas" : this.code === 3 ? "Anuladas_Sin_Emitir" : "Anulada_Con_Numero_Fiscal";

          const title: string = `CRF_${type}_` + this.datePipe.transform(new Date(), "dd-MM-yyyy");

          this.excelService.generateExcel(this.dataExport, title, columnNames);


        } else {

          //this.pdfService.generatePdf(data);
          const margin = this.code === 1 ? 10 : this.code === 2 ? 10 : this.code === 3 ? 70 : 100;

          this.pdfService.generatePdfNavegaTwo(this.rows, this.title, margin);
        }
      } else {
        this.utilService.showNotification(1, "Seleccione una opción del tipo de consulta para proceder a exportar");
      }

    } else {
      this.utilService.showNotification(1, "Seleccione una opción del tipo de formato para poder exportar");
    }
  }


  buildDataExport(): Promise<ControlInvoice[]> {

    return new Promise((resolve, reject) => {
      let buildData: ControlInvoice[] = [];

      buildData = [...this.rowsControlAuth, ...this.rowsControlEmit, ...this.rowsCancellationWithFn, ...this.rowsCancellationWithOutFn];

      resolve(buildData);
    })

  }

  /**
 * Método encargado de obtener toda la data para la pantalla
 * 
 */
  async getAllData() {

    const validateParam = await this.configparametersById(1000);
    const validateParamAuth = await this.configparametersById(4439);
    const validateParamCancel = await this.configparametersById(4440);

    if (validateParam && validateParamAuth && validateParamCancel) {

      await this.buildParametersTransaction();

      await this.getControlAuthByStatus(1);
      await this.getControlEmissionByStatus(2);
      await this.getControlCancellationWithFnByStatus(1);
      await this.getControlCancellationWithOutFnByStatus(2);


      this.hideSelect = false;

    } else {


      this.utilService.showNotification(2, "Ha ocurrido un error. Por favor, contacte al administrador del sistema");
    }


  }


  /**
   * Este método nos ayuda separar los valores de los parámetros de tipo transacción
   * 
   */
  buildParametersTransaction(): Promise<boolean> {

    return new Promise((resolve, reject) => {

      for (let i = 0; i <= this.parametersAuthEmissions.length - 1; i++) {

        if (this.parametersAuthEmissions[i].parameterType === "Tipo Transaccion") {
          this.paramtersTransactionAuthEmission.push(this.parametersAuthEmissions[i]);
        }

      }

      for (let i = 0; i <= this.parametersInvoiceCancellation.length - 1; i++) {

        if (this.parametersInvoiceCancellation[i].parameterType === "Tipo Transaccion") {
          this.paramtersTransactionCancel.push(this.parametersInvoiceCancellation[i]);
        }

      }

      resolve(true);

    });

  }


  // Methods REST

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

          // Mapeamos el body del response
          let configParameterResponse = response.body as ConfigParameterResponse;

          // Agregamos los valores a los rows

          configParameterResponse.data.map((resourceMap, configError) => {

            let dto: ConfigParameter = resourceMap;

            if (id === 1000) {
              this.parameters.push(dto);
            }
            if (id === 4440) {
              this.parametersInvoiceCancellation.push(dto);
            }
            if (id === 4439) {
              this.parametersAuthEmissions.push(dto);

            }


          });

          if (id === 1000) {
            this.parameters = [...this.parameters];
          }
          if (id === 4440) {
            this.parametersInvoiceCancellation = [...this.parametersInvoiceCancellation];
          }
          if (id === 4439) {
            this.parametersAuthEmissions = [...this.parametersAuthEmissions];
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
* Método encargado de obtener los registros de las facturas
* 
*/
  getControlAuthByStatus(status: any): Promise<boolean> {
    return new Promise((resolve, reject) => {
      this.loadingIndicator = true;
      this.invoiceService.getControlAuthEmissionByStatus(this.currentPage, this.pageSize, status)
        .subscribe({
          next: (response) => {
            if (response.status === 200) {
              this.rowsControlAuth = [];
              let controlInvoiceResponse = response.body as ControlInvoicePagesResponse;

              // Actualizar información de paginación
              this.totalElements = controlInvoiceResponse.data.totalElements;
              this.totalPages = controlInvoiceResponse.data.totalPages;

              controlInvoiceResponse.data.content.forEach(resourceMap => {
                let dto: ControlInvoice = resourceMap;
                dto.typeApproval = dto.typeApproval === this.paramtersTransactionAuthEmission[0].stateCode
                  ? this.paramtersTransactionAuthEmission[0].parameterValue
                  : this.paramtersTransactionAuthEmission[1].parameterValue;
                this.rowsControlAuth.push(dto);
              });

              this.loadingIndicator = false;
              resolve(true);
            } else {
              this.loadingIndicator = false;
              resolve(false);
            }
          },
          error: (error) => {
            this.loadingIndicator = false;
            resolve(false);
          }
        });
    });
  }



  /**
   * Método que consume un servicio para traer los registro de las facturas emitidas
   * 
   */
  getControlEmissionByStatus(status: any): Promise<boolean> {
    return new Promise((resolve, reject) => {
      this.loadingIndicator = true;
      this.invoiceService.getControlAuthEmissionByStatus(this.currentPage, this.pageSize, status)
        .subscribe({
          next: (response) => {
            if (response.status === 200) {
              this.rowsControlEmit = [];
              let controlInvoiceResponse = response.body as ControlInvoicePagesResponse;

              // Actualizar información de paginación
              this.totalElements = controlInvoiceResponse.data.totalElements;
              this.totalPages = controlInvoiceResponse.data.totalPages;
              this.currentPage = controlInvoiceResponse.data.number;

              controlInvoiceResponse.data.content.forEach(resourceMap => {
                let dto: ControlInvoice = resourceMap;
                dto.typeApproval = dto.typeApproval === this.paramtersTransactionAuthEmission[0].stateCode
                  ? this.paramtersTransactionAuthEmission[0].parameterValue
                  : this.paramtersTransactionAuthEmission[1].parameterValue;
                this.rowsControlEmit.push(dto);
              });

              this.rowsControlEmit = [...this.rowsControlEmit];
              this.rowsExcelControlEmit = [...this.rowsControlEmit];
              this.loadingIndicator = false;
              resolve(true);
            } else {
              this.loadingIndicator = false;
              resolve(false);
            }
          },
          error: (error) => {
            this.loadingIndicator = false;
            resolve(false);
          }
        });
    });
  }

  /**
   * Método que consume un servicio para traer los registros de las facturas que
   * fueron anuladas con número fiscal
   * 
   */
  getControlCancellationWithFnByStatus(status: any): Promise<boolean> {
    return new Promise((resolve, reject) => {
      this.loadingIndicator = true;
      this.invoiceService.getControlCancellationByStatus(this.currentPage, this.pageSize, status)
        .subscribe({
          next: (response) => {
            if (response.status === 200) {
              this.rowsCancellationWithFn = [];
              let controlInvoiceResponse = response.body as ControlCancellationPagesResponse;

              // Actualizar información de paginación
              this.totalElements = controlInvoiceResponse.data.totalElements;
              this.totalPages = controlInvoiceResponse.data.totalPages;
              this.currentPage = controlInvoiceResponse.data.number;

              controlInvoiceResponse.data.content.forEach(resourceMap => {
                let dto2: ControlInvoice = {};
                let dto: ControlCancellation = resourceMap;

                dto2.id = dto.id;
                dto2.created = dto.created;
                dto2.description = dto.description;
                dto2.idPrefecture = dto.idPrefecture;
                dto2.status = dto.status;
                dto2.typeApproval = dto.typeCancellation;
                dto2.userCreate = dto.userCreate;

                dto2.typeApproval = dto2.typeApproval === this.paramtersTransactionCancel[0].stateCode
                  ? this.paramtersTransactionCancel[0].parameterValue
                  : this.paramtersTransactionCancel[1].parameterValue;

                this.rowsCancellationWithFn.push(dto2);
              });

              this.rowsCancellationWithFn = [...this.rowsCancellationWithFn];
              this.rowsExcelCancelWithFn = [...this.rowsCancellationWithFn];
              this.loadingIndicator = false;
              resolve(true);
            } else {
              this.loadingIndicator = false;
              resolve(false);
            }
          },
          error: (error) => {
            this.loadingIndicator = false;
            resolve(false);
          }
        });
    });
  }

  /**
   * Método que consume un servicio para traer los registros de las facturas que
   * fueron anuladas sin haber sido emitidas
   * 
   */
  getControlCancellationWithOutFnByStatus(status: any): Promise<boolean> {
    return new Promise((resolve, reject) => {
      this.loadingIndicator = true;
      this.invoiceService.getControlCancellationByStatus(this.currentPage, this.pageSize, status)
        .subscribe({
          next: (response) => {
            if (response.status === 200) {
              this.rowsCancellationWithOutFn = [];
              let controlInvoiceResponse = response.body as ControlCancellationPagesResponse;

              this.totalElements = controlInvoiceResponse.data.totalElements;
              this.totalPages = controlInvoiceResponse.data.totalPages;
              this.currentPage = controlInvoiceResponse.data.number;

              controlInvoiceResponse.data.content.forEach(resourceMap => {
                let dto2: ControlInvoice = {};
                let dto: ControlCancellation = resourceMap;

                dto2.id = dto.id;
                dto2.created = dto.created;
                dto2.description = dto.description;
                dto2.idPrefecture = dto.idPrefecture;
                dto2.status = dto.status;
                dto2.typeApproval = dto.typeCancellation;
                dto2.userCreate = dto.userCreate;

                dto2.typeApproval = dto2.typeApproval === this.paramtersTransactionCancel[0].stateCode
                  ? this.paramtersTransactionCancel[0].parameterValue
                  : this.paramtersTransactionCancel[1].parameterValue;

                this.rowsCancellationWithOutFn.push(dto2);
              });

              this.rowsCancellationWithOutFn = [...this.rowsCancellationWithOutFn];
              this.rowsExcelCancelWithOutFn = [...this.rowsCancellationWithOutFn];
              this.loadingIndicator = false;
              resolve(true);
            } else {
              this.loadingIndicator = false;
              resolve(false);
            }
          },
          error: (error) => {
            this.loadingIndicator = false;
            resolve(false);
          }
        });
    });
  }



}
