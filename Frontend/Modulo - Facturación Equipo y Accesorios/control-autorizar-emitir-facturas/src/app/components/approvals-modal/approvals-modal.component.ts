import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { BillingResponseOne, BranchResponse, ChannelResponse, InvoiceDetailResponse, TypeUserResponse, UserPermissionsResponse } from 'src/app/entities/invoice.entity';

import { AuthBSimModel, InvoiceDetail, Billing, Branche, ChannelModel, ConfigParameter, CorreoModel, DataVoucherService, RealeaseSerialNumberModel, RealeaseSerialResponseModel, SelectMultiModel, SerialNumber, TypeUserModel } from 'src/app/models/billing';
import { ControlAuthEmission, ControlUserPermissions } from 'src/app/models/user-control-permissons';

import { InvoiceService } from 'src/app/services/invoice.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';
import { Buttons } from 'src/types';
import Swal from "sweetalert2/dist/sweetalert2.js";
import { DatePipe } from '@angular/common';
import { PrintInvoiceComponent } from '../print-invoice/print-invoice/print-invoice.component';

@Component({
  selector: 'app-approvals-modal',
  templateUrl: './approvals-modal.component.html',
  styleUrls: ['./approvals-modal.component.css']
})
export class ApprovalsModalComponent implements OnInit {

  // Props
  // Invoice
  //invoiceDetail: InvoiceDetail[] = [];

  // Input and Output
  @Output() messageEvent = new EventEmitter<boolean>();
  @Input() billing: Billing;
  @Input() button: Buttons;
  @Input() parameter: ConfigParameter[];
  @Input() parametersUserIssued1002: string[] = [];
  @Input() parametersUserAuth1002: string[] = [];
  //@Input() parameter30410: Map<string, string>;
  messages = messages;

  // Valores de validación
  typeTransaction: string = "";
  identityTransaction: number = 2;
  minChar: number = 5;
  messageMinChar: string = ``;

  // Permissions
  controlUserPermissions: ControlUserPermissions = null;

  // TypeUser
  typeUser: TypeUserModel = {};

  // BranchesOffices
  rowsBrancheOffices: Branche[] = [];

  // Channel
  channel: ChannelModel = {}

  // Select
  showSelect: boolean = false;
  public dropdownSettings = {};
  public dropdownList: SelectMultiModel[] = [];
  public selectedItems = [];
  hiddenAgency: boolean = false;
  validateBranches: boolean = true;

  //* STYLES
  labelClasses = "col-form-label text-dark col-sm-2 col-md-2 col-lg-2";
  inputClasses = "my-auto col-sm-2 col-md-4 col-lg-2";
  inputClassesTwo = "my-auto col-sm-12 col-md-12 col-lg-12";

  // Hidden
  paymentCodeHidden: boolean = true;

  // BSIM
  authBSimModel: AuthBSimModel = {};
  realeaseSerialResponseModel: RealeaseSerialResponseModel = {};

  rows: Billing[] = [];
  rows2: Billing[] = [];

  constructor(private invoiceService: InvoiceService, private activeModal: NgbActiveModal, public utilService: UtilService, private formBuilder: FormBuilder, private datePipe: DatePipe, private modalService: NgbModal) { }

  //*  --------------- FORM ---------------
  searchForm!: FormGroup;

  async ngOnInit() {
    //console.log(this.parameter30410);
    //console.log(this.parametersUserAuth1002);
    //console.log(this.parametersUserIssued1002);

    // Cargamos la configuración del select
    this.dropdownSettings = {
      singleSelection: true,
      text: "Seleccione un valor",
      selectAllText: "Todos",
      unSelectAllText: "Ninguno",
      enableSearchFilter: true,
      classes: ""
    };

    // Validación para las facturas que son al crédito
    if (this.billing.invoiceType === "FC2" || this.billing.invoiceType === "FC4" || this.billing.invoiceType === "FS2" || this.billing.invoiceType === "FS4") {

      if (this.billing.statusCode === 0) {
        this.identityTransaction = 1;
      } else {
        this.showSelect = true;
      }

    } else {
      this.showSelect = true;
    }

    await this.buildValidate();
    const userName = this.utilService.getSystemUser();

    // Se inisializa el formulario
    //this.searchForm = this.initForm();

    this.messageMinChar = `Solo se permite un mínimo de ${this.minChar} caracteres`;

    // Cargamos los permisos del usuario y los detalles de la factura
    //await this.getInvoiceDetail();
    await this.getControlUserPermissions(userName);
    //console.log(this.controlUserPermissions);

    // Cargamos las sucursales en el select
    if (this.billing.invoiceType === "FC2" || this.billing.invoiceType === "FC4" || this.billing.invoiceType === "FS2" || this.billing.invoiceType === "FS4") {

      if (this.billing.statusCode === 0) {

      } else {
        await this.getBranches();
        let selectDto: SelectMultiModel = {
          id: this.billing.idBranchOffices,
          itemName: `${this.billing.idBranchOffices} - ${this.billing.agency}`
        }


        //this.rowsBrancheOffices.push(dto);
        this.selectedItems.push(selectDto);
        this.paymentCodeHidden = false;
        const paymentCodeControl = this.searchForm.get('paymentCode') as FormControl;

        //paymentCodeControl.setValidators([Validators.required, Validators.maxLength(100)]);

        paymentCodeControl.updateValueAndValidity();

        //this.selectedItems = [...this.dropdownList];
      }

    } else {
      await this.getBranches();
      let selectDto: SelectMultiModel = {
        id: this.billing.idBranchOffices,
        itemName: `${this.billing.idBranchOffices} - ${this.billing.agency}`
      }
      this.selectedItems.push(selectDto);
      this.paymentCodeHidden = false;
      const paymentCodeControl = this.searchForm.get('paymentCode') as FormControl;

      //paymentCodeControl.setValidators([Validators.required, Validators.maxLength(100)]);

      paymentCodeControl.updateValueAndValidity();
      //this.selectedItems = [...this.dropdownList];
    }

    await this.getTypeUser(this.controlUserPermissions.typeUser);



  }

  calculateValue(invoice: InvoiceDetail): number {
    if (this.billing.fiscalProcess === "Facturación por Reclamo de Seguros" && invoice.serieOrBoxNumber != null) {
      return 0.00;
    } else {
      return invoice.unitPrice * invoice.quantity;
    }
  }

  onItemSelect(event) {


    this.billing.idBranchOffices = event.id;

    let array: string[] = event.itemName.split("-");

    this.billing.agency = array[1];

    //console.log(this.billing);
    this.validateBranches = true;

  }

  OnItemDeSelect(event) {

    this.validateBranches = false;


  }

  changeAgency(option: boolean) {

    if (option) {
      this.hiddenAgency = true;
    } else {
      this.hiddenAgency = false;
    }

  }

  changeSelect(event) {

    //console.log(event);

    for (let i = 0; i < this.rowsBrancheOffices.length; i++) {
      if (this.rowsBrancheOffices[i].id == event) {

        this.billing.agency = this.rowsBrancheOffices[i].name;
      }
    }

  }

  buildValidate(): Promise<boolean> {
    return new Promise((resolve, reject) => {


      for (let i = 0; i <= this.parameter.length - 1; i++) {
        //console.log(this.parameter[i]);
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
      typeApproval: [this.typeTransaction],
      description: ["", [Validators.required]],
      paymentCode: ["", []],
    });
  }


  async authorizeInvoice() {

    Swal.fire({
      title: 'Advertencia',
      text: "¿Desea realizar esta acción?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {

        // obtenemos el valor del fomulario
        let controlAuthEmission: ControlAuthEmission = this.searchForm.value;

        // Seteamos los demás valores
        controlAuthEmission.idPrefecture = this.billing.id;
        controlAuthEmission.userCreate = this.utilService.getSystemUser();
        //controlAuthEmission.status = 1;
        controlAuthEmission.typeApproval = 1;



        // Validamos si el tipo de factura
        if (this.billing.invoiceType === "FC2" || this.billing.invoiceType === "FC4" || this.billing.invoiceType === "FS2" || this.billing.invoiceType === "FS4") {

          // Validamos si el usuario existe el la tabla de permisos
          if (this.controlUserPermissions === null) {
            this.utilService.showNotification(1, `No tiene permiso para autorizar la factura`);
          } else {

            let valorExiste = this.parametersUserAuth1002.includes(this.typeUser.typeUser);

            /**
             * Validamos si el tipo de usuario pertenece a los usuario que pueden autorizar
             * 
             */
            if (valorExiste) {

              /**
               * Validamos si tiene el permiso de autorizar la factura
               * 
               */
              if (this.controlUserPermissions.authorizeInvoice === "Y") {

                // Lógica
                const validateControlAuthEmission = await this.postControlAuthEmission(controlAuthEmission, "Autorizar");

                if (validateControlAuthEmission) {
                  this.messageEvent.emit(true);
                  this.closeModal();
                }


              } else {

                this.utilService.showNotification(1, `No tiene permiso para autorizar la factura`);
              }
            } else {
              this.utilService.showNotification(1, `El usuario ${this.controlUserPermissions.userName} es un ${this.typeUser.typeUser} por lo tanto no puede autorizar facturas`);
            }

          }

        }

      }

    })

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

            /**
             * Validamos si va al proceso de Emision
             * 
             */
            if (dto.statusCode === 1 || (dto.invoiceType === 'FS1' || dto.invoiceType === 'FS3' || dto.invoiceType === 'FC1' || dto.invoiceType === 'FC3' || dto.invoiceType === 'SHO' || dto.invoiceType === 'SHP')) {
              dto.buttonEmit = true;
            }

            /**
             * Validamos si va al proceso de Autorización
             * 
             */
            if (dto.statusCode === 0 && (dto.invoiceType === 'FC2' || dto.invoiceType === 'FC4' || dto.invoiceType === 'FS2' || dto.invoiceType === 'FS4')) {
              dto.buttonAuth = true;
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
   * Método que realizar la transacción de emitir la factura
   * 
   */
  async emitInvoice() {

    Swal.fire({
      title: 'Advertencia',
      text: "¿Desea realizar esta acción?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {
        // obtenemos el valor del fomulario
        let controlAuthEmission: ControlAuthEmission = this.searchForm.value;

        // Seteamos los demás valores
        controlAuthEmission.idPrefecture = this.billing.id;
        controlAuthEmission.userCreate = this.utilService.getSystemUser();
        //controlAuthEmission.status = 2;
        controlAuthEmission.typeApproval = 2;
        controlAuthEmission.idBranchOffices = this.billing.idBranchOffices;
        //console.log(controlAuthEmission);

        // Validamos si el usuario existe en la tabla de permisos
        if (this.controlUserPermissions === null) {
          this.utilService.showNotification(1, `No tiene permiso para emitir la factura`);
        } else {

          let valorExiste = this.parametersUserIssued1002.includes(this.typeUser.typeUser);

          /**
           * Validamos si es uno de los usuarios que puede emitir facturas
           * 
           */
          if (valorExiste) {
            // Validamos que el usuario tiene el permiso de emitir sin importar el vendedor que sea
            if (this.controlUserPermissions.issueSellerInvoice === "Y") {

              // Validamos si selecciono la sucursal 
              if (this.validateBranches) {

                // Validamos si hay productos
                if (this.billing.invoiceDetails.length > 0) {


                  Swal.fire({
                    title: 'Emitiendo Factura...',
                    allowOutsideClick: false,
                    onBeforeOpen: () => {
                      Swal.showLoading();
                    }
                  });

                  // Lógica de emisión de factura sin números de series
                  const validateControlAuthEmission = await this.postControlAuthEmission(controlAuthEmission, "Emitir");
                  //const validateControlAuthEmission = true;
                  Swal.close();

                  // Validamos si se consumio sin problemas el servicio de emitir la factura
                  if (validateControlAuthEmission) {

                    await this.sendEmail(); // Ejecutamos la acción sobre el envio de la factura por correo

                    this.closeModal();

                    // Llamar al método getInvoiceById
                    const invoiceFetched = await this.getInvoiceById(this.billing.id);
                    if (invoiceFetched) {
                      // Abrir el modal para imprimir la factura
                      const modalRef = this.modalService.open(PrintInvoiceComponent);
                      modalRef.componentInstance.billing = this.rows[0]; // Pasa la información de la factura al componente

                    } else {
                      this.utilService.showNotification(2, `No se pudo obtener la información de la factura`);
                    }
                  } else {
                    this.utilService.showNotification(2, `No se pudo emitir la factura, Contacte al Administrador del Sistema `);
                  }



                } else {
                  this.utilService.showNotification(1, "No se puede emitir la factura porque no contiene productos");
                }


              } else {
                this.utilService.showNotification(1, "Seleccione una agencia para proceder con la emisión de la factura");
              }

            } else {


              // Validamos si el usuario tiene el permiso para validar
              if (this.controlUserPermissions.generateBill === "Y") {

                // Validamos si el usuario es igual al usuario vendedor, es decir: si es el usuario vendedor de la factura
                if (this.controlUserPermissions.userName === this.billing.seller) {

                  // Validmos si hay seleccionado una agencia o sucursal
                  if (this.validateBranches) {

                    if (this.billing.invoiceDetails.length > 0) {

                      if (this.validateProductFromBilling()) {

                        Swal.fire({
                          title: 'Emitiendo Factura...',
                          allowOutsideClick: false,
                          onBeforeOpen: () => {
                            Swal.showLoading();
                          }
                        });

                        // Lógica de emisión de factura sin números de series
                        const validateControlAuthEmission = await this.postControlAuthEmission(controlAuthEmission, "Emitir");
                        //const validateControlAuthEmission = true;
                        Swal.close();

                        // Validamos si se consumio sin problemas el servicio de emtiri la factura
                        if (validateControlAuthEmission) {

                          await this.sendEmail(); // Ejecutamos la acción sobre el envio de la factura por correo
                          this.messageEvent.emit(true);
                          this.closeModal();

                        } else {
                          this.utilService.showNotification(2, `No se pudo emitir la factura, Contacte al Administrador del Sistema `);
                        }

                      } else {
                        this.utilService.showNotification(1, "No se puede emitir la factura porque los valores monetarios no cuadrán");
                      }

                    } else {
                      this.utilService.showNotification(1, "No se puede emitir la factura porque no contiene productos");
                    }

                  } else {
                    this.utilService.showNotification(1, "Debe de seleccionar la agencia para seguir con el proceso de emisión");
                  }




                } else {
                  this.utilService.showNotification(1, `No tiene permiso para emitir porque no eres el vendedor de esta factura`);
                }

              } else {

                this.utilService.showNotification(1, `No tiene permiso para emitir la factura`);

              }

            }
            //console.log(`El valor '${this.typeUser.typeUser}' existe en el arreglo.`);
          } else {
            this.utilService.showNotification(1, `El usuario ${this.controlUserPermissions.userName} es un ${this.typeUser.typeUser} por lo tanto no puede emitir facturas`);
            //console.log(`El valor '${this.typeUser.typeUser}' no existe en el arreglo.`);
          }



        }

      }

    })

  }


  /**
 * @see validateCorreo()
 * Método que le pregunta al usuario si quiere mandar la factura por
 * correo o no.
 * 
 */
  sendEmail(): Promise<boolean> {

    return new Promise((resolve, reject) => {

      Swal.fire({
        title: 'Advertencia',
        text: `¿Desea enviar por correo la factura?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#002e6e',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Aceptar'
      }).then(async (result: any) => {

        if (result.value) {
          //console.log(result.value)

          const { value: correo } = await Swal.fire({
            title: "Ingrese el correo",
            input: "text",
            confirmButtonText: 'Enviar',
            showCancelButton: true,
            cancelButtonColor: '#d33',
            inputValidator: (value: any) => {
              //console.log(value);
              let VALIDATE_CORREO: string = value.toString();
              if (!value) {
                return "El campo no debe de estar vacio";
              }

              const atIndex = VALIDATE_CORREO.indexOf('@');
              if (atIndex === -1 || VALIDATE_CORREO.lastIndexOf('@') !== atIndex) {
                return "EL correo debe contener un único simbolo de @"; // No contiene un solo @
              }

              // Verificar que el correo no termine con un punto
              if (VALIDATE_CORREO.endsWith('.')) {
                return "El correo no puede terminar con punto"; // Termina con un punto
              }

              // Verificar que hay al menos un caracter antes y después del @
              const parts = VALIDATE_CORREO.split('@');
              if (parts.length !== 2 || parts[0].length === 0 || parts[1].length === 0) {
                return "El correo está incompleto"; // No hay suficientes partes o alguna parte está vacía
              }

              // Verificar que la parte después del @ tenga al menos un punto
              const domainParts = parts[1].split('.');
              if (domainParts.length < 2 || domainParts.every(part => part.length === 0)) {
                return "El correo debe de terminar con un dominio"; // No hay al menos un punto en la parte del dominio
              }

              if (VALIDATE_CORREO.includes(" ")) {
                return "El correo no debe de contener espacios"; // Contiene espacios inapropiados
              }

            }
          });

          /**
           * Condición si el correo cumple para enviar el correo
           * 
           */
          if (correo) {
            const dataMail: CorreoModel = {
              idPrefecture: this.billing.id,
              email: correo,
              cashierName: this.utilService.getSystemUser()

            };

            await this.postSendEmail(dataMail);
            Swal.fire(`Correo enviado con éxito`);
          }

          //this.validateCorreo();
          resolve(true);
        }
        resolve(true);

      });
    });




  }


  //* UTILS
  getTitle() {
    if (this.button === "see") return `Detalles de la Factura #${this.billing.id}`;
    if (this.button === "add") return `Autorizar Factura #${this.billing.id}`;
    if (this.button === "emit") return `Emitir Factura #${this.billing.id}`;
  }

  closeModal() {
    this.activeModal.close();
  }

  /**
   * Promesa que valida si la factura está correcta o no
   * 
   * @returns 
   */
  validateProductFromBilling(): boolean {

    let amountTotal: number = this.billing.subtotal + this.billing.amountTax;
    amountTotal = parseFloat(amountTotal.toFixed(4));
    //console.log(amountTotal);
    //console.log(this.billing.amountTotal);

    if (amountTotal == this.billing.amountTotal) {
      return true;
    }

    return false;


  }

  // METHODS REST


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


  /**
   * Método que consume un servicio para registrar el control de aprobación
   * y emisión de las facturas
   * 
   * @returns 
   */
  postControlAuthEmission(data: ControlAuthEmission, transaction: string): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consumir el servicio
      this.invoiceService.postControlAuthEmission(data, transaction).subscribe((response) => {

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
   * Método que consume un servicio para actualizar el estado de la factura
   * 
   * @returns 
   */
  patchUpdateStatusInvoiceAuth(id: any, status: any, authorizingUser: any, dateAuth: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consumir el servicio
      this.invoiceService.patchUpdateStatusInvoiceAuth(id, status, authorizingUser, dateAuth).subscribe((response) => {

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
   * Método que consume un servicio para actualizar el estado de la factura
   * 
   * @returns 
   */
  patchUpdateStatusInvoiceEmit(id: any, status: any, authorizingUser: any, authorizationDate: any, userIssued: any, dateOfIssue: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consumir el servicio
      this.invoiceService.patchUpdateStatusInvoiceEmit(id, status, authorizingUser, authorizationDate, userIssued, dateOfIssue).subscribe((response) => {

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
   * Método que consume un servicio para actualizar el estado de la factura
   * 
   * @returns 
   */
  putUpdateInvoice(id: any, data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consumir el servicio
      this.invoiceService.putUpdateInvoice(id, data).subscribe((response) => {

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
 * Método encargado de obtener los detalles de la factura
 * 
 */
  /*
    getInvoiceDetail(): Promise<boolean> {
  
      return new Promise((resolve, reject) => {
        // Consumir el servicio
        this.invoiceService.getInvoiceDetailById(this.billing.id).subscribe((response) => {
  
          // Validamos si el response es ok
          if (response.status === 200) {
  
            this.invoiceDetail = [];
  
            let invoiceDetailResponse = response.body as InvoiceDetailResponse;
  
            if (invoiceDetailResponse.code === 1) {
  
              if (invoiceDetailResponse.data.length > 0) {
  
                invoiceDetailResponse.data.map((resourceMap, configError) => {
  
                  let dto: InvoiceDetail = resourceMap;
  
                  this.invoiceDetail.push(dto);
  
                });
  
                this.invoiceDetail = [...this.invoiceDetail];
                resolve(true)
  
              } else {
                this.utilService.showNotification(1, "No se encontraron detalles de la facturas");
                resolve(false);
              }
  
            }
  
          } else {
            resolve(false)
          }
  
        }, (error) => {
          resolve(false)
        })
      })
  
  
  
    }
    */

  /**
   * Método que consume un servicio rest para emitir facturas.
   * 
   * @param data 
   * @returns 
   */
  postAddVoucher(data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consumir el servicio
      this.invoiceService.postAddVoucher(data).subscribe((response) => {

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

  // getAllBrnacheOffices
  /**
   * Consume un servicio para obtener todos los branches
   * 
   * @returns 
   */
  getBranches(): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.getBranches(0, 1000).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {


          // Mapeamos el body del response
          let branchResponse = response.body as BranchResponse;

          // Agregamos los valores a los rows

          branchResponse.data.content.map((resourceMap, configError) => {

            let dto: Branche = resourceMap;
            let selectDto: SelectMultiModel = {
              id: dto.id,
              itemName: `${dto.idPoint} - ${dto.name}`
            }


            //this.rowsBrancheOffices.push(dto);
            this.dropdownList.push(selectDto);
          });


          //this.rowsBrancheOffices = [...this.rowsBrancheOffices];
          this.dropdownList = [...this.dropdownList];

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
* Método que consume un servicio para enviar la factura por el correo
* 
* @param id 
* @returns 
*/
  postSendEmail(data: any): Promise<boolean> {

    return new Promise((resolve, reject) => {

      this.invoiceService.postSendEmail(data).subscribe((response) => {
        //console.log(response);
        //console.log(response.status);
        if (response.status === 200) {

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
   * Método que consume un servicio para traer la info de los canales
   * y validar si se consumen o no los servicios de liberación
   * de BSIM
   * 
   * No está en uso
   * 
   * @param id 
   * @returns 
   */
  getChannelById(id: any): Promise<boolean> {

    return new Promise((resolve, reject) => {

      this.invoiceService.getChannelById(id).subscribe((response) => {
        //console.log(response);
        //console.log(response.status);
        if (response.status === 200) {
          // Mapeamos el body del response
          let channelResponse = response.body as ChannelResponse;
          this.channel = channelResponse.data;

          // Agregamos los valores a los rows

          //this.typeUser = typeUserResponse.data;

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
