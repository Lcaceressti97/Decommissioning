import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { InvoiceDetailComponent } from 'src/app/components/invoice-detail/invoice-detail.component';
import { BillingOneResponse, BillingResponse, ConfigParameterResponse, InventoryTypeResponse, InvoiceDetailResponse, InvoicePagesResponse, UserPermissionsResponse, UserPermissionsTwoResponse, WareHousesResponse } from 'src/app/entities/entities/invoice.entity';
import { InvoiceEquipmentAccesories } from 'src/app/model/InvoiceEquipmentAccesories';
import { Billing, ConfigParameter, ControlUserPermissions, InventoryTypeModel, StatusNames, WareHouseModel } from 'src/app/model/billing';
import { InvoiceEquipmentAccesoriesService } from 'src/app/service/invoice-equipment-accesories.service';
import { UtilService } from 'src/app/service/util.service';
import { messages } from 'src/app/util/enums';
import Swal from "sweetalert2/dist/sweetalert2.js";


@Component({
  selector: 'app-invoice-cancellation',
  templateUrl: './invoice-cancellation.component.html',
  styleUrls: ['./invoice-cancellation.component.css']
})
export class InvoiceCancellationComponent implements OnInit {

  // Props

  // Form
  searchInvoiceForm!: FormGroup;

  // Table
  messages = messages;
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
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

  // Parameters Invoice Status
  parametersInvoiceStatus: ConfigParameter[] = [];
  statusNames: StatusNames = {};
  parameters: ConfigParameter[] = [];
  parameters1002: string[] = [];
  dayCancel: string = "";
  controlUserPermissions: ControlUserPermissions = null;

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
    },
    {
      id: 2,
      itemName: "Emitidas"
    }
  ];
  public selectedItems = [{
    id: 0,
    itemName: "Pendientes"
  }];

  constructor(private modalService: NgbModal, public utilService: UtilService, private readonly fb: FormBuilder, private invoiceService: InvoiceEquipmentAccesoriesService) { }

  async ngOnInit() {

    this.searchInvoiceForm = this.initForm();

    this.dropdownSettings = {
      singleSelection: true,
      text: "Seleccione un valor",
      selectAllText: "Todos",
      unSelectAllText: "Ninguno",
      enableSearchFilter: true,
      classes: ""
    };

    //this.getAllParameters();
    await this.configparametersById(1000);
    await this.configparametersById(1002);
    await this.configparametersById(4440);
    Swal.fire({
      title: 'Cargando Facturas...',
      allowOutsideClick: false,
      onBeforeOpen: () => {
        Swal.showLoading();
      }
    });
    await this.getControlUserPermissions(this.utilService.getSystemUser());

    await this.getAllInvoicePagination(this.utilService.getSystemUser(), this.selectStatus);
    Swal.close();
    //console.log(this.controlUserPermissions);
  }

  async getAllParameters() {

    await this.configparametersById(1000);
    await this.configparametersById(1002);
    await this.configparametersById(4440);
    //console.log(this.parameters);

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
    //console.log(searchInvoice);
  }

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

  /**
   * Método encargado de refrescar los datos de la tabla
   * 
   */
  async reloadRows() {
    // Vaciamos las 
    this.rows = [];
    this.rows2 = [];
    this.rows = [...this.rows];
    this.rows2 = [...this.rows];
    this.searchInvoiceForm.get('invoiceNo').setValue('');
    Swal.fire({
      title: 'Cargando Facturas...',
      allowOutsideClick: false,
      onBeforeOpen: () => {
        Swal.showLoading();
      }
    });

    await this.getAllInvoicePagination(this.utilService.getSystemUser(), this.selectStatus);

    Swal.close();
  }

  async onItemSelect(event) {
    //console.log(event);
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

    await this.getAllInvoicePagination(this.utilService.getSystemUser(), this.selectStatus);

    Swal.close();




  }

  onDeSelect() {
    this.rows = [];
    this.rows = [...this.rows];
    this.rows2 = [...this.rows];
  }

  /**
   * Método que abre la modal pasando varios inputs necesario para la
   * funcionalidad de anulación
   * 
   */
  openModal(data: any, option: any) {

    const modalRef = this.modalService.open(InvoiceDetailComponent, {
      size: "lg", backdrop: "static"
    })

    modalRef.componentInstance.billing = data;
    modalRef.componentInstance.option = option;
    modalRef.componentInstance.parameter = this.parameters;
    modalRef.componentInstance.parameters1002 = this.parameters1002;
    modalRef.componentInstance.dayCancel = this.dayCancel;
    modalRef.componentInstance.controlUserPermissionsTypeUser = this.controlUserPermissions;

    modalRef.componentInstance.messageEvent.subscribe((status: boolean) => {
      if (status === true) {
        this.utilService.showNotification(0, "Factura anulada exitosamente");
        this.reloadRows();
      }
    });

  }

  // Método para manejar el cambio de página
  async onPageChange(event: any) {
    this.currentPage = event.offset;
    await this.getAllInvoicePagination( this.utilService.getSystemUser(), this.selectStatus);
  }


  /**
 * Método encargado de obtener los registros de las facturas
 * 
 */
  getAllInvoicePagination(seller?: any, status?: any): Promise<boolean> {

    return new Promise((resolve, reject) => {

      // Se llama e método del servicio
      this.invoiceService.getInvoicePagination(this.currentPage, this.pageSize, seller, status).subscribe((response) => {

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

          this.rows = billingResponse.data.content.map((resourceMap, configError) => {

            let dto: Billing = resourceMap;
            dto.statusCode = dto.status;
            //dto.status = dto.status === this.parametersInvoiceStatus[0].stateCode ? this.parametersInvoiceStatus[0].parameterValue : dto.status === this.parametersInvoiceStatus[1].stateCode ? this.parametersInvoiceStatus[1].parameterValue : dto.status === this.parametersInvoiceStatus[2].stateCode ? this.parametersInvoiceStatus[2].parameterValue : dto.status === this.parametersInvoiceStatus[3].stateCode ? this.parametersInvoiceStatus[3].parameterValue : dto.status === this.parametersInvoiceStatus[4].stateCode ? this.parametersInvoiceStatus[4].parameterValue : this.parametersInvoiceStatus[5].parameterValue;

            //dto.status = this.selectStatus == 0 ? "Pendiente" : this.selectStatus==1 ? "Autorizada" : this.selectStatus == 2 ? "Emitida" : "Sin estado";

            dto.status = this.statusNames[dto.status] || "Sin estado";
            this.rows.push(dto);
            return dto;
          });

          //console.log(this.rowsPending);
          //console.log(this.rowsAuthorized);
          //console.log(this.rowsIssued);
          this.loadingIndicator = false;
          this.rows = [...this.rows];
          this.rows2 = [...this.rows];

          if (this.rows.length > 0) {
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

  async findById(id) {
    if (id > 0) {
      const validateFind = await this.getInvoiceById(id);

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

    } else {
      this.utilService.showNotification(1, "Solo se puede buscar con números positivos");
    }
  }


  /**
* Método encargado de obtener la factura por id
* 
*/
  getInvoiceById(id: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.getInvoiceById(id).subscribe((response) => {
        // Validamos si responde con un 200
        if (response.status === 200) {
          // Vaciamos las
          this.rows = [];
          this.rows2 = [];
          this.selectedItems = [];


          // Mapeamos el body del response
          let billingResponse = response.body as BillingOneResponse;

          //console.log(billingResponse.data);

          // Verificamos si hay datos en la respuesta
          if (billingResponse.data) {

            let dto: Billing = billingResponse.data;
            dto.statusCode = dto.status;
            dto.status = dto.status === this.parametersInvoiceStatus[0].stateCode ? this.parametersInvoiceStatus[0].parameterValue : dto.status === this.parametersInvoiceStatus[1].stateCode ? this.parametersInvoiceStatus[1].parameterValue : dto.status === this.parametersInvoiceStatus[2].stateCode ? this.parametersInvoiceStatus[2].parameterValue : dto.status === this.parametersInvoiceStatus[3].stateCode ? this.parametersInvoiceStatus[3].parameterValue : dto.status === this.parametersInvoiceStatus[4].stateCode ? this.parametersInvoiceStatus[4].parameterValue : this.parametersInvoiceStatus[5].parameterValue;

            if (dto.statusCode === 0) {
              this.selectedItems.push({
                id: 0,
                itemName: "Pendientes"
              });
            }

            if (dto.statusCode === 1) {
              this.selectedItems.push({
                id: 1,
                itemName: "Autorizadas"
              });
            }

            if (dto.statusCode === 2) {
              this.selectedItems.push({
                id: 2,
                itemName: "Emitidas"
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

          // Agregamos los valores a los rows

          configParameterResponse.data.map((resourceMap, configError) => {

            let dto: ConfigParameter = resourceMap;

            if (id === 1000) {
              this.parametersInvoiceStatus.push(dto);
              if (dto.parameterType == "INVOICE_STATUS") {
                this.statusNames[dto.stateCode] = dto.parameterValue;

              }
            }

            if (id === 4440) {
              this.parameters.push(dto);
            }

            if (id === 1002) {

              if (dto.parameterName === "TYPE_USER_CANCELLATION") {
                this.parameters1002.push(dto.parameterValue);
              }

              if (dto.parameterName === "DAY_CANCEL_INVOICE") {
                this.dayCancel = dto.parameterValue;
              }

            }

          });

          if (id === 1000) {
            this.parametersInvoiceStatus = [...this.parametersInvoiceStatus];
          }

          if (id === 1002) {
            this.parameters1002 = [...this.parameters1002];
          }

          if (id === 4440) {
            this.parameters = [...this.parameters];
          }

          //console.log(this.parametersInvoiceStatus);
          //console.log(this.dayCancel);
          //console.log(this.parameters);


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
 * Método que consume un servicio para traer los permisos del usuario
 * 
 * @returns 
 */
  getControlUserPermissions(username: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consume el servicio
      this.invoiceService.getControlUserPermissons(username).subscribe((response) => {

        if (response.status === 200) {

          let userPermissionsResponse = response.body as UserPermissionsTwoResponse;

          this.controlUserPermissions = userPermissionsResponse.data;
          //console.log(this.controlUserPermissions);

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })

    })
  }




}
