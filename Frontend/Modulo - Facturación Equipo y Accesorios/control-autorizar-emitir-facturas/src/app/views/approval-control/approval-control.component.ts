import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ApprovalsModalComponent } from 'src/app/components/approvals-modal/approvals-modal.component';

import { BillingResponse, BillingResponseOne, ConfigParameterResponse, InvoicePagesResponse } from 'src/app/entities/invoice.entity';
import { InvoiceEquipmentAccesories } from 'src/app/models/InvoiceEquipmentAccesories';
import { Billing, ConfigParameter, StatusNames } from 'src/app/models/billing';
import { ApprovalControlService } from 'src/app/services/approval-control.service';
import { InvoiceService } from 'src/app/services/invoice.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';
import { Buttons } from 'src/types';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-approval-control',
  templateUrl: './approval-control.component.html',
  styleUrls: ['./approval-control.component.css']
})
export class ApprovalControlComponent implements OnInit {

  // Props
  searchInvoiceForm!: FormGroup;

  // Table
  rows: Billing[] = [];
  rows2: Billing[] = [];
  
  // Propiedades de paginación
  currentPage = 0;
  pageSize = 20;
  totalElements = 0;
  totalPages = 0;

  //* STYLES
  labelClasses = "col-form-label text-dark col-sm-2 col-md-2 col-lg-2";
  inputClasses = "my-auto col-sm-2 col-md-4 col-lg-8";
  inputClassesTwo = "my-auto col-sm-12 col-md-2 col-lg-2";

  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  typeValue: string = "";
  messages = messages;
  loading: boolean;
  param: any
  mostrarTabla = false;
  infoCargado = false;

  // Parameters
  // Invoice Status
  parametersInvoiceStatus: ConfigParameter[] = [];
  statusNames: StatusNames = {};
  parameters: ConfigParameter[] = [];
  parametersUserIssued1002: string[] = [];
  parametersUserAuth1002: string[] = [];
  //parametersMap30410 = new Map<string, string>();

  // Select
  selectStatus: number = 0;
  public dropdownSettings = {};
  public dropdownList = [
    {
      id: 0,
      itemName: "Pendientes"
    },
    {
      id: 1,
      itemName: "Autorizadas"
    }
  ];
  public selectedItems = [{
    id: 0,
    itemName: "Pendientes"
  }];

  constructor(private approvalControlService: ApprovalControlService, private modalService: NgbModal, public utilService: UtilService, private readonly fb: FormBuilder, private invoiceService: InvoiceService) { }

  async ngOnInit() {

    this.dropdownSettings = {
      singleSelection: true,
      text: "Seleccione un valor",
      selectAllText: "Todos",
      unSelectAllText: "Ninguno",
      enableSearchFilter: true,
      classes: ""
    };

    this.searchInvoiceForm = this.initForm();
    //this.getAllParameters();
    await this.configparametersById(1000);
    await this.configparametersById(1002);
    await this.configparametersById(4439);

    Swal.fire({
      title: 'Cargando Facturas...',
      allowOutsideClick: false,
      onBeforeOpen: () => {
        Swal.showLoading();
      }
    });

    this.pageSize = 20; // Establecer el tamaño de página predeterminado
    await this.getAllInvoicePagination(this.currentPage, this.pageSize, true, this.utilService.getSystemUser(), this.selectStatus);
    Swal.close();
  }



  /**
 * Primera forma de crear tus propias validaciones utilizando
 * la librería o clase FormBuilder
 * 
 */
  initForm(): FormGroup {
    return this.fb.group({
      invoiceNo: ['', [Validators.required]]
    })
  }

  getSearchInvoice() {
    const searchInvoice: InvoiceEquipmentAccesories = this.searchInvoiceForm.value;

  }

  async onItemSelect(event) {
    //console.log(event);
    // Vaciamos las 
    this.rows = [];
    this.rows2 = [];
    this.rows = [...this.rows];
    this.rows2 = [...this.rows];
    this.selectStatus = event.id;

    Swal.fire({
      title: 'Cargando Facturas...',
      allowOutsideClick: false,
      onBeforeOpen: () => {
        Swal.showLoading();
      }
    });

    await this.getAllInvoicePagination(this.currentPage, this.pageSize, true, this.utilService.getSystemUser(), this.selectStatus);

    Swal.close();

  }

  onDeSelect() {
    this.rows = [];
    this.rows = [...this.rows];
    this.rows2 = [...this.rows];
  }


  /**
   * Método para abrir una modal
   * 
   */
  openApprovalModal(button: Buttons, row: Billing = null) {


    const modalRef = this.modalService.open(ApprovalsModalComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.billing = row;
    modalRef.componentInstance.button = button;
    modalRef.componentInstance.parameter = this.parameters;
    modalRef.componentInstance.parametersUserIssued1002 = this.parametersUserIssued1002;
    modalRef.componentInstance.parametersUserAuth1002 = this.parametersUserAuth1002;
    //modalRef.componentInstance.parameter30410 = this.parametersMap30410;
    modalRef.componentInstance.messageEvent.subscribe((status: boolean) => {
      if (button === "add") {
        if (status === true) {
          this.utilService.showNotification(0, "Factura aprobada exitosamente, en unos segundos se volvera a cargar la pantalla automaticamente");
          setTimeout(() => {
            window.location.reload();
          }, 3500);
        }
      }

      if (button === "emit") {

        if (status === true) {
          this.utilService.showNotification(0, "Factura emitida exitosamente, en unos segundos se volverá a cargar la pantalla automaticamente");
          setTimeout(() => {
            window.location.reload();
          }, 3500);
        }
      }
    });
  }

  async getAllParameters() {

    //await this.configparametersById(30410);



  }

  //* UTILS
  searchApprovalControl() {
    this.rows = this.rows2.filter((approvalControl) => {
      return JSON.stringify(approvalControl)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  getTotalText() {
    return this.rows.length == 1 ? "Registro" : "Registros";
  }

  async reloadRows() {
    // Vaciamos las 
    this.rows = [];
    this.rows2 = [];
    this.rows = [...this.rows];
    this.rows2 = [...this.rows];
    //this.getAllInvoice();
    this.searchInvoiceForm.get('invoiceNo').setValue('');

    Swal.fire({
      title: 'Cargando Facturas...',
      allowOutsideClick: false,
      onBeforeOpen: () => {
        Swal.showLoading();
      }
    });

    await this.getAllInvoicePagination(this.currentPage, this.pageSize, true, this.utilService.getSystemUser(), this.selectStatus);

    Swal.close();
  }

  async findById(id) {


    if (id > 0) {
      Swal.fire({
        title: 'Buscando factura...',
        allowOutsideClick: false,
        onBeforeOpen: () => {
          Swal.showLoading();
        }
      });
      const validateFind = await this.getInvoiceById(id);
      Swal.close();
      if (validateFind) {
        this.utilService.showNotification(0, `Se cargo la factura: ${id}`);
      } else {
        this.utilService.showNotification(1, `No se encontraron datos para la factura: ${id}`);
        this.rows = [];
        this.rows2 = [];
        this.loadingIndicator = false;
        this.rows = [...this.rows];
        this.rows2 = [...this.rows];
      }

      //console.log(validateFind);
    } else {
      this.utilService.showNotification(1, "Solo se puede buscar con números positivos");
    }
  }


    // Método para manejar el cambio de página
    async onPageChange(event: any) {
      this.currentPage = event.offset;
      await this.getAllInvoicePagination(this.currentPage, this.pageSize, false, this.utilService.getSystemUser(), this.selectStatus);
    }

  /**
     * Método encargado de obtener los registros de las facturas
     * 
     */
  getAllInvoicePagination(page?: any, size?: any, showAlert?: boolean, seller?: any, status?: any): Promise<boolean> {
    return new Promise((resolve, reject) => {
      this.invoiceService.getInvoicePagination(page, size, seller, status).subscribe((response) => {
        if (response.status === 200) {
          let billingResponse = response.body as InvoicePagesResponse;
          
          // Actualizar la información de paginación
          this.totalElements = billingResponse.data.totalElements;
          this.totalPages = billingResponse.data.totalPages;
          this.currentPage = billingResponse.data.number;
          
          // Mapear los datos como antes
          this.rows = billingResponse.data.content.map((resourceMap) => {
            let dto: Billing = resourceMap;
            dto.buttonAuth = false;
            dto.buttonEmit = false;
            dto.statusCode = dto.status;
            dto.status = this.statusNames[dto.status] || "Sin estado";

            if (dto.statusCode === 1 || (dto.invoiceType === 'FS1' || dto.invoiceType === 'FS3' || dto.invoiceType === 'FC1' || dto.invoiceType === 'FC3' || dto.invoiceType === 'SHO' || dto.invoiceType === 'SHP')) {
              dto.buttonEmit = true;
              dto.isEmitDisabled = true;
            }

            if (dto.statusCode === 0 && (dto.invoiceType === 'FC2' || dto.invoiceType === 'FC4' || dto.invoiceType === 'FS2' || dto.invoiceType === 'FS4')) {
              dto.buttonAuth = true;
              dto.isEmitDisabled = false;
            }

            if (dto.statusCode === 1 && (dto.invoiceType === 'FC2' || dto.invoiceType === 'FC4' || dto.invoiceType === 'FS2' || dto.invoiceType === 'FS4')) {
              dto.buttonEmit = true;
              dto.isEmitDisabled = false;
            }
            return dto;
          });

          this.loadingIndicator = false;
          this.rows = [...this.rows];
          this.rows2 = [...this.rows];

          if (this.rows.length > 0 && showAlert) {
            this.utilService.showNotification(0, "Datos cargados");
          }

          resolve(true);
        } else {
          resolve(false);
        }
      }, (error) => {
        resolve(false);
      });
    });
  }


  /**
* Método encargado de obtener la factura por id
* 
*/
  getInvoiceById(id: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.getBillsByIdAuthorizeIssue(id).subscribe((response) => {
        // Validamos si responde con un 200
        if (response.status === 200) {
          // Vaciamos las



          // Mapeamos el body del response
          let billingResponse = response.body as BillingResponseOne;

          //console.log(billingResponse.data);

          // Verificamos si hay datos en la respuesta
          if (billingResponse.data) {
            this.rows = [];
            this.rows2 = [];
            this.selectedItems = [];

            let dto: Billing = billingResponse.data;
            dto.buttonAuth = false;
            dto.buttonEmit = false;

            dto.statusCode = Number(dto.status);

            dto.status = dto.status === this.parametersInvoiceStatus[0].stateCode ? this.parametersInvoiceStatus[0].parameterValue : dto.status === this.parametersInvoiceStatus[1].stateCode ? this.parametersInvoiceStatus[1].parameterValue : dto.status === this.parametersInvoiceStatus[2].stateCode ? this.parametersInvoiceStatus[2].parameterValue : dto.status === this.parametersInvoiceStatus[3].stateCode ? this.parametersInvoiceStatus[3].parameterValue : dto.status === this.parametersInvoiceStatus[4].stateCode ? this.parametersInvoiceStatus[4].parameterValue : dto.status === this.parametersInvoiceStatus[5].stateCode ? this.parametersInvoiceStatus[5].parameterValue : this.parametersInvoiceStatus[6].parameterValue;


            /**
             * Validamos si va al proceso de Emision
             * 
             */
            if (dto.statusCode === 1 || (dto.invoiceType === 'FS1' || dto.invoiceType === 'FS3' || dto.invoiceType === 'FC1' || dto.invoiceType === 'FC3' || dto.invoiceType === 'SHO' || dto.invoiceType === 'SHP')) {
              dto.buttonEmit = true;
              dto.isEmitDisabled = true;
            }

            /**
             * Validamos si va al proceso de Autorización
             * 
             */
            if (dto.statusCode === 0 && (dto.invoiceType === 'FC2' || dto.invoiceType === 'FC4' || dto.invoiceType === 'FS2' || dto.invoiceType === 'FS4')) {
              dto.buttonAuth = true;
              dto.isEmitDisabled = false;
            }

            if (dto.statusCode === 1 && (dto.invoiceType === 'FC2' || dto.invoiceType === 'FC4' || dto.invoiceType === 'FS2' || dto.invoiceType === 'FS4')) {
              dto.buttonEmit = true;
              dto.isEmitDisabled = false;
            }

            if (dto.statusCode === 1) {
              this.selectedItems.push({
                id: 1,
                itemName: "Autorizadas"
              });
            }

            if (dto.statusCode === 0) {
              this.selectedItems.push({
                id: 0,
                itemName: "Pendientes"
              });
            }


            this.rows.push(dto);
            this.loadingIndicator = false;

            this.rows = [...this.rows];
            this.rows2 = [...this.rows];
            resolve(true);

          } else {
            resolve(false);

          }

        } else {

          resolve(false);
        }
      }, (error) => {
        resolve(false);
      });
    });

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
          //console.log(configParameterResponse);

          // Agregamos los valores a los rows

          configParameterResponse.data.map((resourceMap, configError) => {

            let dto: ConfigParameter = resourceMap;

            // Parametros generales
            if (id === 1000) {
              
              this.parametersInvoiceStatus.push(dto);
              if(dto.parameterType=="INVOICE_STATUS"){
                this.statusNames[dto.stateCode] = dto.parameterValue;

              }
            }

            // Parametros de tipos de usuarios autorizar y emitir
            if (id === 1002) {

              if (dto.parameterName == "TYPE_USER_EMIT") {
                this.parametersUserIssued1002.push(dto.parameterValue);
              }

              if (dto.parameterName == "TYPE_USER_AUTHORIZATION") {
                this.parametersUserAuth1002.push(dto.parameterValue);
              }

            }

            // Parametros de la pantalla
            if (id === 4439) {
              this.parameters.push(dto);
              // console.log("Entro 4439")
            }


          });

          if (id === 4439) {
            this.parameters = [...this.parameters];
          }

          if (id === 1000) {
            this.parametersInvoiceStatus = [...this.parametersInvoiceStatus];
          }

          if (id === 1002) {
            this.parametersUserAuth1002 = [...this.parametersUserAuth1002];
            this.parametersUserIssued1002 = [...this.parametersUserIssued1002];
          }


          //console.log(this.statusNames);



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
