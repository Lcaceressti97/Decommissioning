import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { InvoiceCreationModalComponent } from 'src/app/components/invoice-creation-modal/invoice-creation-modal.component';
import { InvoiceDetailComponent } from 'src/app/components/invoice-detail/invoice-detail.component';
import { BillingResponse, BillingServicesResponse, BranchResponse, ChannelResponse, ConfigParameterResponse, ExchangerateResponse, InventoryTypeResponse, InvoicePagesResponse, InvoiceTypeResponse, SubWarehouseResponse, UserPermissionsResponse, WarehouseResponse } from 'src/app/entity/response';
import { Billing, BillingServicesModel, Branche, ChannelModel, ConfigParameter, ControlUserPermissions, InventoryTypeModel, InvoiceDetail, InvoiceTypesModel, ItemSelect, StatusNames, SubWarehouseModel, WareHouseModel } from 'src/app/models/model';
import { InvoiceService } from 'src/app/services/invoice.service';
import { UtilService } from 'src/app/services/util.service';

@Component({
  selector: 'app-invoice-creation',
  templateUrl: './invoice-creation.component.html',
  styleUrls: ['./invoice-creation.component.css']
})
export class InvoiceCreationComponent implements OnInit {

  // Props

  // Table
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  rows: Billing[] = [];
  rows2: Billing[] = [];
  statusNames: StatusNames = {};
  parametersInvoiceStatus: ConfigParameter[] = [];

  // Inputs for Child
  billingServices: BillingServicesModel[] = []; // Servicios de Facturación
  invoiceTypes: string[] = []; // Tipos de Facturas
  branchOffices: Branche[] = []; // Sucursales
  inventoryTypeModel: InventoryTypeModel[] = []; // Tipos de inventarios
  subWarehouseModel: SubWarehouseModel[] = []; // Sub-bodegas
  taxPorcentage: number = 0; // Porcenjate del impuesto parametrizado
  exchangeRate: number = 0.0000; // Tasa de cambio
  equipmentLineId: number = 0;
  nameFinalConsumer: string = "";
  rtnFinalConsumer: string = "";

  // Disabled button
  disabledCreate: boolean = true;
  public dropDownListWareHouse: ItemSelect[] = [];
  public dropDownListInventoty: ItemSelect[] = [];

  // Propiedades de paginación
  currentPage = 0;
  pageSize = 20;
  totalElements = 0;
  totalPages = 0;

  // Permissions
  controlUserPermissions: ControlUserPermissions = null;

  constructor(public utilService: UtilService, private modalService: NgbModal, private invoiceService: InvoiceService) { }

  async ngOnInit() {

    await this.getBilling(true, this.utilService.getSystemUser());

    // Consultar toda la data para la construcción de una factura
    await this.getExchangerate();
    await this.getInventoryType();
    await this.getWareHouses();
    await this.getSubWareHouse();
    await this.getInvoiceType();
    await this.getBranches();
    await this.getBillingServiceByPagination(0, 2000);
    await this.configparametersById(999);
    await this.getControlUserPermissions();
    this.disabledCreate = false;

  }

  // Methods

  // Methods Screen
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

  getTotalText() {
    return this.rows2.length == 1 ? "Registro" : "Registros";
  }

  reloadRows() {
    //this.getChannels();
  }


  /**
* Método para abrir modales según la acción
*
* @param button
* @param row
*/
  openModalCreation() {

    const modalRef = this.modalService.open(InvoiceCreationModalComponent, {
      size: "xl"
    });

    modalRef.componentInstance.billingServices = this.billingServices;
    modalRef.componentInstance.invoiceTypes = this.invoiceTypes;
    modalRef.componentInstance.branchOffices = this.branchOffices;
    modalRef.componentInstance.inventoryTypeModel = this.dropDownListInventoty;
    modalRef.componentInstance.warehouseModel = this.dropDownListWareHouse;
    modalRef.componentInstance.subWarehouseModel = this.subWarehouseModel;
    modalRef.componentInstance.taxPorcentage = this.taxPorcentage;
    modalRef.componentInstance.equipmentLineId = this.equipmentLineId;
    modalRef.componentInstance.nameFinalConsumer = this.nameFinalConsumer;
    modalRef.componentInstance.rtnFinalConsumer = this.rtnFinalConsumer;
    modalRef.componentInstance.exchangeRate = this.exchangeRate;
    modalRef.componentInstance.controlUserPermissions = this.controlUserPermissions;
    modalRef.componentInstance.messageEvent.subscribe((invoice: any) => {

      this.rows.push(invoice);
      this.rows = [...this.rows];
      this.rows2 = [...this.rows];
      this.utilService.showNotification(0, "Factura creada exitosamente");

    });

  }

  openModal(data: any) {

    const modalRef = this.modalService.open(InvoiceDetailComponent, {
      size: "lg", backdrop: "static"
    })

    modalRef.componentInstance.billing = data;


  }

  // Services Rest
  // Methods Asyncronos


    // Método para manejar el cambio de página
    async onPageChange(event: any) {
      this.currentPage = event.offset;
      await this.getBilling(true, this.utilService.getSystemUser());
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
  getBilling(showAler?: boolean, seller?: any): Promise<boolean> {
    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.getBilling(this.currentPage, this.pageSize, seller).subscribe((response) => {
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

          // Verificamos si billingResponse.data es un arreglo
          if (Array.isArray(billingResponse.data.content)) {
            // Si es un arreglo, podemos utilizar map directamente
            this.rows = billingResponse.data.content.map((resourceMap) => {
              let dto: Billing = resourceMap;
              dto.buttonExo = false;
              dto.statusCode = "Pendiente";

              dto.status = this.statusNames[dto.status] || "Sin estado";
              return dto;
            });
          }
 /*          else if (billingResponse.data !== null && billingResponse.data !== undefined) {
            // Si no es un arreglo, debemos convertirlo a un arreglo antes de utilizar map
            this.rows = billingResponse.data.content.map((resourceMap, configError) => {
              let dto: Billing = resourceMap;
              dto.buttonExo = false;
              dto.status = dto.status === this.parametersInvoiceStatus[0].stateCode ? this.parametersInvoiceStatus[0].parameterValue : dto.status === this.parametersInvoiceStatus[1].stateCode ? this.parametersInvoiceStatus[1].parameterValue : dto.status === this.parametersInvoiceStatus[2].stateCode ? this.parametersInvoiceStatus[2].parameterValue : dto.status === this.parametersInvoiceStatus[3].stateCode ? this.parametersInvoiceStatus[3].parameterValue : dto.status === this.parametersInvoiceStatus[4].stateCode ? this.parametersInvoiceStatus[4].parameterValue : dto.status === this.parametersInvoiceStatus[5].stateCode ? this.parametersInvoiceStatus[5].parameterValue : this.parametersInvoiceStatus[7].parameterValue;

              if (dto.statusCode == 0 && (dto.exonerationStatus == 0 || dto.exonerationStatus == null)) {
                dto.buttonExo = true;
              }

              if ((dto.invoiceType === "SHO" || dto.invoiceType === "SHP") && dto.statusCode == 0) {
                dto.buttonExo = false;
              }

              dto.status = this.statusNames[dto.status] || "Sin estado";
              return dto;
            });
          } */

          this.loadingIndicator = false;
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

  getBilling1(showAler?: boolean, status?: any): Promise<boolean> {

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
 * Método que consume un servicio para obtener todos los servicios de
 * facturación
 *
 * @param page
 * @param size
 */
  getBillingServiceByPagination(page: any, size: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.invoiceService.getBillingServiceByPagination(page, size).subscribe((response) => {

        if (response.status === 200) {

          this.billingServices = [];

          let billingServiceResponse = response.body as BillingServicesResponse;

          billingServiceResponse.data.content.map((resourceMap, configError) => {

            let dto: BillingServicesModel = resourceMap;

            /**
             * Se agregan solo los servicios activos
             *
             */
            if (dto.status == 1) {
              this.billingServices.push(dto);
            }


          });

          this.billingServices = [...this.billingServices];
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
  * Método encargado de obtener los parámetros de los tipos de facturas
  *
  */
  getInvoiceType(): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.getInvoiceType().subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {

          this.invoiceTypes = [];


          // Mapeamos el body del response
          let configParameterResponse = response.body as InvoiceTypeResponse;

          // Agregamos los valores a los rows

          configParameterResponse.data.map((resourceMap, configError) => {

            let dto: InvoiceTypesModel = resourceMap;

            this.invoiceTypes.push(dto.type);


          });

          this.invoiceTypes = [...this.invoiceTypes];


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
  getBranches(): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.getBranches(0, 10000).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {

          // Vaciamos las
          this.branchOffices = [];

          // Mapeamos el body del response
          let branchResponse = response.body as BranchResponse;

          // Agregamos los valores a los rows

          branchResponse.data.content.map((resourceMap, configError) => {

            let dto: Branche = resourceMap;

            this.branchOffices.push(dto);

          });

          this.branchOffices = [...this.branchOffices];
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
   * Método que obtiene los tipos de inventario provenientes de
   * BSIM
   *
   */
  getInventoryType(): Promise<boolean> {

    return new Promise((resolve, reject) => {

      this.invoiceService.getInventoryType().subscribe((response) => {

        if (response.status === 200) {

          let brancheOfficesResponse = response.body as InventoryTypeResponse;

          brancheOfficesResponse.data.map((resourceMap, configError) => {

            let dto: InventoryTypeModel = resourceMap;
            let item: ItemSelect = {
              id: dto.id,
              code: dto.type,
              itemName: `${dto.type} - ${dto.longDescription}`
            }

            this.dropDownListInventoty.push(item);

          });

          this.dropDownListInventoty = [...this.dropDownListInventoty];

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
  getWareHouses(): Promise<boolean> {

    return new Promise((resolve, reject) => {

      this.invoiceService.getWareHouses().subscribe((response) => {

        if (response.status === 200) {

          let brancheOfficesResponse = response.body as WarehouseResponse;

          brancheOfficesResponse.data.map((resourceMap, configError) => {

            let dto: WareHouseModel = resourceMap;
            let item: ItemSelect = {
              id: dto.id,
              code: dto.code,
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

  /**
   * Método que obtiene las sub-bodegas de BSIM
   *
   */
  getSubWareHouse(): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.getSubWareHouse().subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {

          // Vaciamos las
          this.subWarehouseModel = [];

          // Mapeamos el body del response
          let subWarehouseResponse = response.body as SubWarehouseResponse;

          // Agregamos los valores a los rows

          subWarehouseResponse.data.map((resourceMap, configError) => {

            let dto: SubWarehouseModel = resourceMap;

            this.subWarehouseModel.push(dto);

          });

          this.subWarehouseModel = [...this.subWarehouseModel];
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
          //console.log(configParameterResponse);

          // Agregamos los valores a los rows

          configParameterResponse.data.map((resourceMap, configError) => {

            let dto: ConfigParameter = resourceMap;

            if (dto.parameterType == "CALCULO_INVOICE") {
              this.taxPorcentage = Number(dto.parameterValue);
            }

            if (dto.parameterType == "EQUIPMENT_LINE_ID") {
              this.equipmentLineId = Number(dto.parameterValue);
            }

            if (dto.parameterType == "NAME_FINAL_CONSUMER") {
              this.nameFinalConsumer = String(dto.parameterValue);
            }

            if (dto.parameterType == "RTN_FINAL_CONSUMER") {
              this.rtnFinalConsumer = String(dto.parameterValue);
            }

            // Parametros generales
            if (id === 1000) {

              this.parametersInvoiceStatus.push(dto);
              if (dto.parameterType == "INVOICE_STATUS") {
                this.statusNames[dto.stateCode] = dto.parameterValue;

              }
            }

          });


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
* Método encargado de obtener la tasa de cambio del día
*
*/
  getExchangerate(): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.getExchangerate().subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {

          // Mapeamos el body del response
          let echangerateResponse = response.body as ExchangerateResponse;
          //console.log(configParameterResponse);

          // Agregamos los valores a los rows
          this.exchangeRate = echangerateResponse.data.salePrice;

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
  getControlUserPermissions(): Promise<boolean> {
    return new Promise((resolve, reject) => {
      console.log(this.utilService.getSystemUser())
      // Consume el servicio
      this.invoiceService.getControlUserPermissons(this.utilService.getSystemUser()).subscribe((response) => {

        if (response.status === 200) {

          let userPermissionsResponse = response.body as UserPermissionsResponse;

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
