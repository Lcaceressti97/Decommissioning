import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CancelReasonResponse, InventoryTypeResponse, InvoiceDetailResponse, TypeUserResponse, UserPermissionsResponse, WareHousesResponse } from 'src/app/entities/entities/invoice.entity';
import { InvoiceEquipmentAccesories } from 'src/app/model/InvoiceEquipmentAccesories';
import { Billing,InvoiceDetail, CancelReasonModel, ConfigParameter, ControlCancellation, ControlUserPermissions, DataCancel, InventoryTypeModel, SelectModel, TypeUserModel, WareHouseModel } from 'src/app/model/billing';
import { InvoiceEquipmentAccesoriesService } from 'src/app/service/invoice-equipment-accesories.service';
import { UtilService } from 'src/app/service/util.service';
import { messages } from 'src/app/util/enums';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-invoice-detail',
  templateUrl: './invoice-detail.component.html',
  styleUrls: ['./invoice-detail.component.css']
})
export class InvoiceDetailComponent implements OnInit {

  // Props


  // Input y Output
  @Input() billing: Billing;
  @Input() option: string;
  @Input() parameter: ConfigParameter[];
  @Input() parameters1002: string[];
  @Input() dayCancel: string;
  @Input() controlUserPermissionsTypeUser: ControlUserPermissions = null;
  @Output() messageEvent = new EventEmitter<boolean>();
  messages = messages;

  // Permissions
  controlUserPermissions: ControlUserPermissions = null;

  // Valores de validación
  typeTransaction: string = "";
  identityTransaction: number = 2;
  minChar: number = 5;
  messageMinChar: string = ``;

  //invoiceDetail: InvoiceDetail[] = [];

  // TypeUser | InventoryType | Warehouse
  typeUser: TypeUserModel = {};
  inventoryTypeModel: InventoryTypeModel[] = [];
  wareHouseModel: WareHouseModel[] = [];
  cancelReasonModel: CancelReasonModel[] = [];

  // Valores individuales de los select
  warehouseCode: any = null;
  inventoryType: any = null;
  validateInventory: boolean = true;
  validateWarehouse: boolean = true;
  cancelReason: any = null;

  // Form
  searchForm!: FormGroup;

  // Selects

  // Inventory
  public dropdownSettingsInventory = {};
  public dropdownListInventory: SelectModel[] = [];
  public selectedItemsInventory = [];

  // Bodega
  public dropdownSettingsWarehouse = {};
  public dropdownListWarehouse: SelectModel[] = [];
  public selectedItemsWarehouse = [];


  // Motivo
  public dropdownSettingsReason = {};
  public dropdownListReason: SelectModel[] = [];
  public selectedItemsReason = [];

  // Name Warehouse | Name Inventory
  nameWarehouse: string = "";
  nameInventory: string = "";

  // Hidden
  hiddenWarehouse: boolean = false;
  hiddenInventory: boolean = false;
  hiddenReason: boolean = false;

  constructor(private activeModal: NgbActiveModal, public utilService: UtilService, private invoiceService: InvoiceEquipmentAccesoriesService, private formBuilder: FormBuilder, private datePipe: DatePipe) { }

  async ngOnInit() {
    this.warehouseCode = this.billing.warehouse;
    this.inventoryType = this.billing.inventoryType;
    this.dropdownSettingsInventory = {
      singleSelection: true,
      text: "Seleccione un valor",
      selectAllText: "Todos",
      unSelectAllText: "Ninguno",
      enableSearchFilter: true,
      classes: ""
    };
    this.dropdownSettingsWarehouse = {
      singleSelection: true,
      text: "Seleccione un valor",
      selectAllText: "Todos",
      unSelectAllText: "Ninguno",
      enableSearchFilter: true,
      classes: ""
    };
    this.dropdownSettingsReason = {
      singleSelection: true,
      text: "Seleccione un valor",
      selectAllText: "Todos",
      unSelectAllText: "Ninguno",
      enableSearchFilter: true,
      classes: ""
    };
    //console.log(this.parameters1002);
    //console.log(this.dayCancel);
    //console.log(this.parameter);
    //console.log(this.controlUserPermissionsTypeUser);

    const userName = this.utilService.getSystemUser();
    //this.searchForm = this.initForm();

    //console.log(this.billing.statusCode);

    if (this.option === "emit") {


      /**
       * Condición que nos dice si es una anulación sin emitir o
       * con emitir
       * 
       */
      if (this.billing.statusCode === 1 || this.billing.statusCode === 0) {
        this.identityTransaction = 1;
        await this.buildValidate();
        await this.getPermissionWithOutFn(userName);
      } else {
        this.identityTransaction = 2;
        await this.buildValidate();
        await this.getPermissionWithFn(userName);

      }
    }

    if (this.billing.statusCode === 1 || this.billing.statusCode === 0) {
      this.hiddenInventory = false;
      this.hiddenWarehouse = false;
      this.hiddenReason = false;

    } else {
      this.hiddenInventory = true;
      this.hiddenWarehouse = true;
      this.hiddenReason = true;
    }

    this.messageMinChar = `Solo se permite un mínimo de ${this.minChar} caracteres`;
    await this.getTypeUser(this.controlUserPermissionsTypeUser.typeUser);
    await this.getCancelReason();
    await this.getInventoryType();
    await this.getWareHouses();
    /**
     * Condición que nos ayuda a mostrar el select o el valor en duro en la
     * modal
     * 
     */
    if (this.billing.statusCode === 1 || this.billing.statusCode === 0) {

      const index = this.wareHouseModel.findIndex(item => item.code === this.billing.warehouse);
      const WAREHOUSE = this.wareHouseModel[index];

      const indexTwo = this.inventoryTypeModel.findIndex(item => item.type === this.billing.inventoryType);
      const INVENTORY = this.inventoryTypeModel[indexTwo];

      this.nameWarehouse = `${WAREHOUSE.code} - ${WAREHOUSE.name}`;
      this.nameInventory = `${INVENTORY.type} - ${INVENTORY.longDescription}`;
    } else {

      const index = this.wareHouseModel.findIndex(item => item.code === this.billing.warehouse);

      if (index !== -1) {
        const WAREHOUSE = this.wareHouseModel[index];
        let selectDto: SelectModel = {
          id: this.billing.warehouse,
          itemName: `${this.billing.warehouse} - ${WAREHOUSE.name}`
        }

        this.selectedItemsWarehouse.push(selectDto);
        const WAREHOUSE_CONTROL = this.searchForm.get('warehouse') as FormControl;

        WAREHOUSE_CONTROL.updateValueAndValidity();
      }

      const indexTwo = this.inventoryTypeModel.findIndex(item => item.type === this.billing.inventoryType);


      if (indexTwo !== -1) {
        const INVENTORY = this.inventoryTypeModel[indexTwo];
        let selectDto: SelectModel = {
          id: this.billing.inventoryType,
          itemName: `${INVENTORY.type} - ${INVENTORY.longDescription}`
        }

        this.selectedItemsInventory.push(selectDto);
        const INVENTORY_CONTROL = this.searchForm.get('inventoryType') as FormControl;

        INVENTORY_CONTROL.updateValueAndValidity();

      }


      const indexReason = this.cancelReasonModel.findIndex(item => item.inventoryType === this.billing.inventoryType);
      /* 
      
            if (indexReason !== -1) {
              const REASON = this.cancelReasonModel[indexReason];
              let selectDto: SelectModel = {
                id: REASON.inventoryType,
                itemName: `${REASON.inventoryType} - ${REASON.reasonDesc}`
              }
      
              this.selectedItemsReason.push(selectDto);
              const REASON_CONTROL = this.searchForm.get('cancelReason') as FormControl;
      
              REASON_CONTROL.updateValueAndValidity();
      
            } */

    }

  }

  calculateValue(invoice: InvoiceDetail): number {
    if (this.billing.fiscalProcess === "Facturación por Reclamo de Seguros" && invoice.serieOrBoxNumber != null) {
      return 0.00;
    } else {
      return invoice.unitPrice * invoice.quantity;
    }
  }

  /**
   * Nos ayuda a construir el mensaje y las validaciones que tendrá
   * el formulario para el proceso de anulación, recordar que
   * estas validaciones está parametrizado
   * 
   * @returns 
   */
  buildValidate(): Promise<boolean> {
    return new Promise((resolve, reject) => {

      for (let i = 0; i <= this.parameter.length - 1; i++) {

        if (this.identityTransaction === this.parameter[i].stateCode) {
          this.typeTransaction = this.parameter[i].parameterValue;

        }

        if (this.parameter[i].parameterType === "Validaciones") {

          if (this.parameter[i].parameterName === "MIN_LENGHT_COMMENT") {
            this.minChar = parseInt(this.parameter[i].parameterValue);
          }

        }

      }
      this.searchForm = this.initForm();
      resolve(true);


    });
  }

  /**
* Método encargado de construir el formulario reactivo
* 
*/
  initForm(): FormGroup {
    return this.formBuilder.group({
      description: ["", [Validators.required]],
      typeCancellation: [this.typeTransaction],
      inventoryType: ["", []],
      warehouse: ["", []],
      cancelReason: ["", []]
    });
  }

  /**
   * Warehouse Multiselect
   * 
   */
  onItemSelectWarehouse(event) {

    //console.log(event);
    this.warehouseCode = event.id;

    this.validateWarehouse = true;

  }

  OnItemDeSelectWarehouse(event) {
    this.warehouseCode = null;
    this.validateWarehouse = false;
    //console.log(this.validateWarehouse);
  }

  /**
   * Inventory Typpe Multiselect
   * 
   */
  onItemSelectInventory(event) {

    //console.log(event);
    this.inventoryType = event.id;
    this.validateInventory = true;
  }

  OnItemDeSelectInventory(event) {
    this.inventoryType = null;
    this.validateInventory = false;
    //console.log(this.validateInventory);
  }

  /**
 * Cancel Reason Multiselect
 * 
 */
  onItemSelectReason(event) {

    this.cancelReason = event.id;
    this.validateInventory = true;
    //this.selectedItemsReason.push(event);
    // console.log(this.selectedItemsReason);
  }

  OnItemDeSelectReason(event) {
    this.cancelReason = null;
    this.validateInventory = false;
    //this.selectedItemsReason = this.selectedItemsReason.filter(item => item.id !== event.id); // Elimina el elemento deseleccionado
    //console.log(this.selectedItemsReason); // Muestra el array actualizado
  }

  async cancellationInvoice() {

    Swal.fire({
      title: 'Advertencia',
      text: "¿Desea anular esta factura?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {

        let controlCancellation: ControlCancellation = this.searchForm.value;

        controlCancellation.idPrefecture = this.billing.id;
        controlCancellation.userCreate = this.utilService.getSystemUser();
        //controlCancellation.status = this.billing.statusCode === 1 ? 1 : this.billing.statusCode === 0 ? 1 : 2;
        controlCancellation.typeCancellation = this.identityTransaction;
        controlCancellation.cancelReason = this.cancelReason;

        //console.log(controlCancellation);

        /**
         * Facturas Pendientes o Autorizadas
         * 
         */
        if (this.billing.statusCode === 1 || this.billing.statusCode === 0) {

          // Validamos si el usuario existe en la tabla
          if (this.controlUserPermissions === null) {
            this.utilService.showNotification(1, `No tiene permiso para anular la factura`);
          } else {

            // Validamos si tiene el permiso
            if (this.controlUserPermissions.permitStatus === "Y") {

              // Insertamos el registro de cancelación
              const validateInsert = await this.postControlCancellation(controlCancellation);

              if (validateInsert) {

                this.messageEvent.emit(true);
                this.closeModal();
              }

            } else {
              this.utilService.showNotification(1, `No tiene permiso para anular esta factura`);
            }

          }

        } else {

          if (this.controlUserPermissions === null) {
            this.utilService.showNotification(1, `No tiene permiso para anular la factura`);
          } else {

            let valorExiste = this.parameters1002.includes(this.typeUser.typeUser);

            /**
             * Validamos si el tipo de usuario existe dentro de los tipos de usuario que
             * pueden anular facturas emitidas
             * 
             */
            if (valorExiste) {

              /**
               * Validamos si selecciono la bodega y el tipo de inventario
               * 
               */
              if (this.validateWarehouse == true && this.validateInventory == true) {

                controlCancellation.warehouse = this.warehouseCode;
                controlCancellation.inventoryType = this.inventoryType;

                /**
                 * Si es un supervisor se valida los días habiles para anular la factura
                 * si es uno de créditos puede anular sin importar los días habiles
                 * 
                 */
                if (this.typeUser.typeUser === "SUPERVISOR") {

                  /**
                   * Validamos si tiene permiso de anular facturas
                   * 
                   */
                  if (this.controlUserPermissions.permitStatus === "Y") {
                    this.dayCancel = this.dayCancel === null ? "0" : this.dayCancel;
                    //console.log(this.dayCancel);
                    const DAYS: number = Number(this.dayCancel);

                    // Valor Fecha Actual
                    const DATE_CURRENT: Date = new Date();

                    // Fecha actual en formato
                    const DATE_CURRENT_STR: string = this.datePipe.transform(DATE_CURRENT, "yyyy-MM-dd");
                    const DATE_CURRENT_COMPARE: Date = new Date(DATE_CURRENT_STR);

                    // Fecha de emisión en formato
                    const DATE_ISSUED_STR: string = this.datePipe.transform(this.billing.dateOfIssue, "yyyy-MM-dd");

                    // Se suman los días habiles
                    const DATE_ISSUED: Date = new Date(DATE_ISSUED_STR);
                    DATE_ISSUED.setDate(DATE_ISSUED.getDate() + DAYS);

                    //console.log(DATE_CURRENT_COMPARE);
                    //console.log(DATE_ISSUED);
                    //console.log(this.billing.dateOfIssue);

                    /**
                     * Validamos si la fecha cumple con la fecha limite para poder ser anulada
                     * 
                     */
                    if (DATE_CURRENT_COMPARE <= DATE_ISSUED) {
                      Swal.fire({
                        title: 'Cancelando Factura...',
                        allowOutsideClick: false,
                        onBeforeOpen: () => {
                          Swal.showLoading();
                        }
                      });


                      const validateInsert = await this.postControlCancellation(controlCancellation);
                      //const validateInsert = true;

                      if (validateInsert) {
                        this.messageEvent.emit(true);
                        this.closeModal();
                      } else {
                        //this.utilService.showNotification(1,"No se puede anular la factura");
                      }


                      Swal.close();
                    } else {
                      this.utilService.showNotification(1, `El usuario ${this.controlUserPermissions.userName} es un ${this.typeUser.typeUser} por lo tanto no puede anular esta factura emitida porque la fecha limite caducó`);

                    }
                  } else {
                    this.utilService.showNotification(1, `No tiene permiso para anular esta factura E`);
                  }


                } else {

                  //console.log("Es un credito");
                  if (this.controlUserPermissions.permitStatus === "Y") {

                    Swal.fire({
                      title: 'Cancelando Factura...',
                      allowOutsideClick: false,
                      onBeforeOpen: () => {
                        Swal.showLoading();
                      }
                    });


                    const validateInsert = await this.postControlCancellation(controlCancellation);
                    //const validateInsert = true;

                    if (validateInsert) {
                      this.messageEvent.emit(true);
                      this.closeModal();
                    } else {
                      //this.utilService.showNotification(1,"No se puede anular la factura");
                    }


                    Swal.close();

                  } else {
                    this.utilService.showNotification(1, `No tiene permiso para anular esta factura`);
                  }

                }
              } else {
                this.utilService.showNotification(1, `Debe seleccionar una bodega y un tipo de inventario`);
              }




            } else {
              this.utilService.showNotification(1, `El usuario ${this.controlUserPermissions.userName} es un ${this.typeUser.typeUser} por lo tanto no puede anular facturas emitidas`);

            }



          }

        }
      }

    })

  }


  closeModal() {
    this.activeModal.close();
  }

  /**
 * Método que consume un servicio para traer los permisos del usuario
 * 
 * @returns 
 */
  getPermissionWithFn(username: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consume el servicio
      this.invoiceService.getPermissionWithFn(username).subscribe((response) => {

        if (response.status === 200) {

          let userPermissionsResponse = response.body as UserPermissionsResponse;

          this.controlUserPermissions = userPermissionsResponse.data[0];

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



  /**
 * Método que consume un servicio para traer los permisos del usuario
 * 
 * @returns 
 */
  getPermissionWithOutFn(username: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consume el servicio
      this.invoiceService.getPermissionWithOutFn(username).subscribe((response) => {

        if (response.status === 200) {

          let userPermissionsResponse = response.body as UserPermissionsResponse;

          this.controlUserPermissions = userPermissionsResponse.data[0];

          // console.log(this.controlUserPermissions);

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
   * Método que consume un servicio para actualizar el estado de la factura
   * 
   * @returns 
   */
  patchUpdateStatusInvoice(id: any, status: any, authorizingUser: any, authorizationDate: any, userIssued: any, dateOfIssue: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consumir el servicio
      this.invoiceService.patchUpdateStatusInvoice(id, status, authorizingUser, authorizationDate, userIssued, dateOfIssue).subscribe((response) => {

        if (response.status === 200) {

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
   * Método que consume un servicio para registrar el control de anulación
   * y emisión de las facturas
   * 
   * @returns 
   */
  postControlCancellation(data: ControlCancellation): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consumir el servicio
      this.invoiceService.postControlCancellation(data).subscribe((response) => {

        if (response.status === 200) {

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
   * Método para consumir el cancelVoucher
   * 
   * @param data 
   * @returns 
   */
  postAddVoucher(data: DataCancel): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consumir el servicio
      this.invoiceService.postCancelVoucher(data).subscribe((response) => {

        if (response.status === 200) {

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
 * Método que consume un servicio para traer la información del
 * tipo del usuario
 * 
 * @param id 
 * @returns 
 */
  getTypeUser(id: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.getTypeUser(id).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {


          // Mapeamos el body del response
          let typeUserResponse = response.body as TypeUserResponse;

          // Agregamos los valores a los rows

          this.typeUser = typeUserResponse.data;
          //console.log(this.typeUser);

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
* Método que obtiene los tipos de inventario provenientes de
* BSIM
* 
*/
  getInventoryType(): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.getInventoryType().subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {


          // Mapeamos el body del response
          let inventoryTypeResponse = response.body as InventoryTypeResponse;

          // Agregamos los valores a los rows

          inventoryTypeResponse.data.map((resourceMap, configError) => {

            let dto: InventoryTypeModel = resourceMap;
            let selectDto: SelectModel = {
              id: dto.type,
              itemName: `${dto.type} - ${dto.longDescription}`
            }

            this.dropdownListInventory.push(selectDto);
            this.inventoryTypeModel.push(dto);

          });

          this.dropdownListInventory = [...this.dropdownListInventory];
          this.inventoryTypeModel = [...this.inventoryTypeModel];
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
* Método encargado de obtener los registros de las facturas
* 
*/
  getWareHouses(): Promise<boolean> {

    return new Promise((resolve, reject) => {
      this.invoiceService.getWareHouses().subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {

          // Mapeamos el body del response
          let billingResponse = response.body as WareHousesResponse;

          // Agregamos los valores a los rows

          billingResponse.data.map((resourceMap, configError) => {

            let dto: WareHouseModel = resourceMap;
            let selectDto: SelectModel = {
              id: dto.code,
              itemName: `${dto.code} - ${dto.name}`
            }
            this.dropdownListWarehouse.push(selectDto);
            this.wareHouseModel.push(dto);

          });

          this.dropdownListWarehouse = [...this.dropdownListWarehouse];
          this.wareHouseModel = [...this.wareHouseModel];

          resolve(true);
        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })
    });

    // Se llama e método del servicio

  }


  /**
* Método que obtiene los motivo de anulacion 
* BSIM
* 
*/
  getCancelReason(): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.getCancelReason().subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {


          // Mapeamos el body del response
          let reasonResponse = response.body as CancelReasonResponse;

          // Agregamos los valores a los rows

          reasonResponse.data.map((resourceMap, configError) => {

            let dto: CancelReasonModel = resourceMap;
            let selectDto: SelectModel = {
              id: dto.inventoryType,
              itemName: `${dto.inventoryType} - ${dto.reasonDesc}`
            }

            this.dropdownListReason.push(selectDto);
            this.cancelReasonModel.push(dto);

          });

          this.dropdownListReason = [...this.dropdownListReason];
          this.cancelReasonModel = [...this.cancelReasonModel];
          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);

      });
    });

  }
}
