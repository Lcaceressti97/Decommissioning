import { DecimalPipe } from '@angular/common';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {
  BillingServicesModel,
  Branche,
  ChannelModel,
  ControlUserPermissions,
  ExistencesModel,
  InventoryTypeModel,
  InvoiceDetail,
  PriceMasterModel,
  WareHouseModel,
} from 'src/app/models/model';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enum';
import Swal from 'sweetalert2/dist/sweetalert2.js';
import Big from 'big.js';
import { InvoiceService } from 'src/app/services/invoice.service';
import { tap } from 'rxjs/operators';
import {
  ExistencesResponse,
  PriceMasterResponse,
} from 'src/app/entity/response';
import { SerialNumber } from 'src/app/models/reserve-serial';
@Component({
  selector: 'app-add-product-insurance',
  templateUrl: './add-product-insurance.component.html',
  styleUrls: ['./add-product-insurance.component.css'],
})
export class AddProductInsuranceComponent implements OnInit {
  // Props

  //Inputs | Outputs
  @Input() subWareHouse: string;
  @Input() billingServices: BillingServicesModel[] = [];
  @Input() inventoryTypeModel: InventoryTypeModel[] = [];
  @Input() warehouseModel: WareHouseModel[] = [];
  @Input() controlUserPermissions: ControlUserPermissions;
  @Input() taxPorcentage: number = 0;
  @Input() equipmentLineId;

  @Output() invoiceDetalle = new EventEmitter<InvoiceDetail>();
  existencesModel: ExistencesModel[] = [];
  serialNumberList: any[] = [];
  serialReserveTokensList: SerialNumber[] = [];
  priceMasterModel: PriceMasterModel[] = [];
  // Form
  formDetail!: FormGroup;
  messages = messages;
  invoiceDetail: InvoiceDetail[] = [];
  existences: any[] = [];

  // Calculos
  validateTotalDetail: boolean = true;

  // Variables independientes
  model: string = '';
  description: string = '';
  priceUnit: string = '0.00';
  quantity: string = '0';
  subtotal: string = '0.00';
  discount: string = '0.00';
  isv: string = '0.00';
  totalDetail: string = '0.00';

  // Buttons
  readonlyDiscount: boolean = true;

  // Variables para el multiselect
  public selectedWarehouse: any[] = [];
  public dropdownSettings = {
    singleSelection: true,
    text: 'Selecciona una bodega',
    enableSearchFilter: true,
    primaryKey: 'id',
  };

  public dropdownSettingsInventory = {
    singleSelection: true,
    text: 'Selecciona un tipo',
    enableSearchFilter: true,
    primaryKey: 'id',
  };

  constructor(
    public utilService: UtilService,
    private invoiceService: InvoiceService,
    private activeModal: NgbActiveModal,
    private formBuilder: FormBuilder,
    private decimalPipe: DecimalPipe
  ) {}

  ngOnInit(): void {
    this.formDetail = this.initFormDetail();
    console.log(this.existences);
    this.selectedWarehouse = [];
    this.getSerialNumbersQuery();
    this.getSerialNumbersByQuantity();
  }

  closeModal() {
    this.activeModal.close();
  }

  /**
   * Método encargado de obtener el precio
   *
   * @returns
   */

  getPriceMaster(): Promise<boolean> {
    return new Promise((resolve) => {
      const model = this.formDetail.get('model').value;
      const inventoryType = this.formDetail.get('inventoryType').value[0]?.code;

      // Verifica si el modelo y el tipo de inventario están seleccionados
      if (!model || !inventoryType) {
        return resolve(false);
      }

      this.invoiceService
        .getPriceMasterByModelAndInventoryType(model, inventoryType)
        .subscribe({
          next: (response) => {
            if (response.status === 200 && response.body) {
              const priceMasterResponse = response.body as PriceMasterResponse;
              this.priceMasterModel = Array.isArray(priceMasterResponse.data)
                ? priceMasterResponse.data
                : [priceMasterResponse.data];

              // Verificar si se han encontrado precios
              if (this.priceMasterModel.length > 0) {
                resolve(true);
              } else {
                const PRICE_CONTRO = this.formDetail.get(
                  'unitPrice'
                ) as FormControl;
                PRICE_CONTRO.setValue('0.00');
                this.utilService.showNotification(
                  1,
                  'No se encontraron precios para el modelo y tipo de inventario seleccionados.'
                );
                resolve(false);
              }
            } else {
              const PRICE_CONTRO = this.formDetail.get(
                'unitPrice'
              ) as FormControl;
              PRICE_CONTRO.setValue('0.00');
              this.utilService.showNotification(
                1,
                'No se encontraron precios para el modelo y tipo de inventario seleccionados.'
              );
              resolve(false);
            }
          },
          error: () => {
            const PRICE_CONTRO = this.formDetail.get(
              'unitPrice'
            ) as FormControl;
            PRICE_CONTRO.setValue('0.00');
            if (!this.priceMasterModel.length) {
              this.utilService.showNotification(
                1,
                'No se encontraron precios para el modelo y tipo de inventario seleccionados.'
              );
            }
            resolve(false);
          },
        });
    });
  }

  /**
   * Método encargado de obtener las existencias
   *
   * @returns
   */
  getExistencesByFilter(): Promise<boolean> {
    return new Promise((resolve, reject) => {
      const warehouseId = this.formDetail.get('warehouse').value[0].id;
      const inventoryTypeId = this.formDetail.get('inventoryType').value[0].id;

      this.invoiceService
        .getExistencesByFilter(warehouseId, inventoryTypeId)
        .subscribe(
          (response) => {
            if (response.status === 200) {
              this.existencesModel = [];

              let existenceResponse = response.body as ExistencesResponse;

              if (existenceResponse.data && existenceResponse.data.length > 0) {
                const filteredExistences = existenceResponse.data.filter(
                  (existence) =>
                    existence.equipmentLineId === this.equipmentLineId
                );

                filteredExistences.map((resourceMap, configError) => {
                  let dto: ExistencesModel = resourceMap;

                  this.existencesModel.push(dto);
                });

                this.existencesModel = [...this.existencesModel];

                if (this.existencesModel.length > 0) {
                  resolve(true);
                } else {
                  this.utilService.showNotification(
                    1,
                    'No se encontraron existencias para la bodega y tipo de inventario seleccionados'
                  );
                  resolve(false);
                }
              } else {
                this.utilService.showNotification(
                  1,
                  'No se encontraron existencias para la bodega y tipo de inventario seleccionados'
                );
                resolve(false);
              }
            } else {
              resolve(false);
            }
          },
          (error) => {
            resolve(false);
          }
        );
    });
  }

  async changeWarehouseAndInventory(event) {
    const warehouseId = this.formDetail.get('warehouse').value;
    const inventoryTypeId = this.formDetail.get('inventoryType').value;

    if (warehouseId && inventoryTypeId) {
      await this.getExistencesByFilter();
      await this.getPriceMaster();
    }
  }

  getSerialNumbersQuery(): void {
    const warehouseId = this.formDetail.get('warehouse').value[0].code;
    const inventoryTypeId = this.formDetail.get('inventoryType').value[0].code;
    const itemCode = this.formDetail.get('model').value;
    const subWarehouseCode = '';

    this.invoiceService
      .getSerialNumbersQuery(
        itemCode,
        warehouseId,
        subWarehouseCode,
        inventoryTypeId
      )
      .subscribe(
        (response) => {
          if (response.status === 200) {
            const responseData = response.body;
            if (
              responseData.code === 1 &&
              responseData.data.result_code === 'INV000'
            ) {
              // Acceder a la lista de números de serie
              const serialNumberList =
                responseData.data.serial_number_list[0].serial_number_list;
              this.serialNumberList = serialNumberList; // Guardar la lista de números de serie
              console.log(this.serialNumberList);
              this.getSerialNumbersByQuantity(); // Llamar a la función para actualizar la cantidad de números de serie
            } else {
              console.error(responseData.description); // Mostrar el mensaje de error
              this.utilService.showNotification(1, responseData.description);
            }
          } else {
            console.error(response); // Manejar errores de respuesta
          }
        },
        (error) => {
          console.error(error); // Manejar errores de la llamada al servicio
        }
      );
  }

  /*   getSerialNumbersQuery(): void {
      const warehouseId = this.formDetail.get('warehouse').value[0].code;
      const inventoryTypeId = this.formDetail.get('inventoryType').value[0].code;
      const itemCode = this.formDetail.get('model').value;
      const subWarehouseCode = "";

      // Simulamos la respuesta del servidor
      const response = {
        "code": 1,
        "description": "Success",
        "data": {
          "result_message": "Transacción exitosa",
          "result_code": "INV000",
          "count": 1,
          "serial_number_list": [
            {
              "sub_warehouse_code": "POS",
              "count": 15,
              "serial_number_list": [
                {
                  "serialNumber": "354061761362270"
                },
                {
                  "serialNumber": "354061761362437"
                },
                {
                  "serialNumber": "354061761362726"
                },
                {
                  "serialNumber": "354061761362809"
                },
                {
                  "serialNumber": "354061761363005"
                },
                {
                  "serialNumber": "354061761363120"
                },
                {
                  "serialNumber": "354061761364565"
                },
                {
                  "serialNumber": "354061761364797"
                },
                {
                  "serialNumber": "354061761370166"
                },
                {
                  "serialNumber": "354061761376213"
                },
                {
                  "serialNumber": "354061761377971"
                },
                {
                  "serialNumber": "354061761378128"
                },
                {
                  "serialNumber": "354061761378136"
                },
                {
                  "serialNumber": "354061761378227"
                }
              ]
            }
          ]
        },
        "errors": []
      };

      // Procesamos la respuesta
      if (response.code === 1 && response.data.result_code === "INV000") {
        const serialNumberList = response.data.serial_number_list[0].serial_number_list;
        this.serialNumberList = serialNumberList;
        console.log(this.serialNumberList);
        this.getSerialNumbersByQuantity();
      } else {
        console.error(response.description);
        this.utilService.showNotification(1, response.description);
      }
    }
   */

  getSerialNumbersByQuantity(): void {
    const quantity = this.formDetail.get('quantity').value; // Obtener la cantidad ingresada
    if (
      this.serialNumberList &&
      this.serialNumberList.length > 0 &&
      quantity > 0
    ) {
      // Limitar la cantidad de números de serie a la cantidad ingresada
      const limitedSerialNumbers = this.serialNumberList
        .slice(0, quantity)
        .map((item) => item.serialNumber);
      this.formDetail
        .get('serieOrBoxNumber')
        .setValue(limitedSerialNumbers.join(', ')); // Unirlos como una cadena
    } else {
      this.formDetail.get('serieOrBoxNumber').setValue('');
    }
  }

  /**
   * Método que solo permite números con un máximo de 4 digitos decimal
   *
   * @param control
   * @returns
   */
  validarNumeroDecimal(control) {
    const numeroDecimalRegExp = /^[0-9]+(\.[0-9]{1,4})?$/;
    if (control.value && !numeroDecimalRegExp.test(control.value)) {
      return { numeroDecimalInvalido: true };
    }
    return null;
  }

  initFormDetail(): FormGroup {
    return this.formBuilder.group({
      idFind: ['', []],
      model: ['', [Validators.required]],
      description: ['', []],
      quantity: [1, [Validators.required]],
      unitPrice: ['0.00', [Validators.required, this.validarNumeroDecimal]],
      subtotal: ['0.00', [Validators.required]],
      discountPercentage: ['0', []],
      discount: ['0.00', [Validators.required, this.validarNumeroDecimal]],
      selectTax: [0, []],
      taxPercentage: ['0', []],
      tax: ['0.00', [Validators.required]],
      amountTotal: ['0.00', []],
      serieOrBoxNumber: ['', []],
      inventoryType: ['', []],
      warehouse: ['', []],
      selectedWarehouse: ['', []],
    });
  }

  /**
   * Método que resetea o limpia el formulario
   * en el apartado de detalles
   *
   */
  resetFormDetail() {
    this.formDetail.controls['model'].reset();
    this.formDetail.controls['description'].reset();
    this.formDetail.controls['serieOrBoxNumber'].reset();

    const QUANTITY_CONTRO = this.formDetail.get('quantity') as FormControl;
    QUANTITY_CONTRO.setValue(0);

    const PRICE_CONTRO = this.formDetail.get('unitPrice') as FormControl;
    PRICE_CONTRO.setValue('0.00');
    const SUBTOTAL_CONTRO = this.formDetail.get('subtotal') as FormControl;
    SUBTOTAL_CONTRO.setValue('0.00');
    const DISCOUNT_CONTRO = this.formDetail.get('discount') as FormControl;
    DISCOUNT_CONTRO.setValue('0.00');
    const ISV_CONTRO = this.formDetail.get('tax') as FormControl;
    ISV_CONTRO.setValue('0.00');
    const SELECT_CONTRO = this.formDetail.get('selectTax') as FormControl;
    SELECT_CONTRO.setValue(0);
    const TOTAL_CONTRO = this.formDetail.get('amountTotal') as FormControl;
    TOTAL_CONTRO.setValue('0.00');

    // Variables globales del calculo y disabled
    this.validateTotalDetail = true;
    this.model = '';
    this.description = '';
    this.priceUnit = '0.00';
    this.subtotal = '0.00';
    this.discount = '0.00';
    this.isv = '0.00';
    this.totalDetail = '0.00';
    this.readonlyDiscount = true;
  }

  /**
   * Se usa para validar si se calcula o no el
   * impuesto
   *
   * @param event
   */
  changeCalISV(event) {
    //console.log(event.target.value);

    const DISCOUNT_CONTRO = this.formDetail.get('discount') as FormControl;
    const SELECT_ISV_CONTRO = this.formDetail.get('selectTax') as FormControl;

    if (event.target.value == 1) {
      this.readonlyDiscount = false;
      SELECT_ISV_CONTRO.setValue('1');
    } else {
      this.readonlyDiscount = true;
      this.isv = '0.00';
      SELECT_ISV_CONTRO.setValue(0);
      DISCOUNT_CONTRO.setValue('0.00');
    }
  }

  /**
   * Método para mostrar la data selecciona en
   * el select de Código de Servicio
   *
   */
  changeCodeService(event) {
    this.validateTotalDetail = false;
    const SERVICE: string = event.target.value;
    const selectedObject = this.existencesModel.find(
      (existence) => existence.code === SERVICE
    );

    // Llama a getPriceMaster para obtener el precio
    this.getPriceMaster().then((success) => {
      if (success) {
        // Aquí se asume que priceMasterModel tiene el objeto que necesitas
        const priceControl = this.formDetail.get('unitPrice') as FormControl;

        // Asumiendo que priceMasterModel tiene un solo objeto después de la llamada
        if (this.priceMasterModel.length > 0) {
          const selectedPriceMaster = this.priceMasterModel[0]; // Obtener el primer objeto
          priceControl.setValue(selectedPriceMaster.price); // Asignar el precio al control
        }
      }
    });

    if (selectedObject) {
      this.model = selectedObject.code;
      this.description = selectedObject.model;
    }
    this.getSerialNumbersQuery();

    const SUBTOTAL_CONTRO = this.formDetail.get('subtotal') as FormControl;
    SUBTOTAL_CONTRO.setValue('0.00');
    const DISCOUNT_CONTRO = this.formDetail.get('discount') as FormControl;
    DISCOUNT_CONTRO.setValue('0.00');
    const ISV_CONTRO = this.formDetail.get('tax') as FormControl;
    ISV_CONTRO.setValue('0.00');
    const SELECT_CONTRO = this.formDetail.get('selectTax') as FormControl;
    SELECT_CONTRO.setValue(0);
    const TOTAL_CONTRO = this.formDetail.get('amountTotal') as FormControl;
    TOTAL_CONTRO.setValue('0.00');

    //this.priceUnit = PRICE_UNIT_CONST;
    this.subtotal = '0.00';
    this.discount = '0.00';
    this.isv = '0.00';
    this.totalDetail = '0.00';
    this.readonlyDiscount = true;
  }

  roundToTwoDecimalsTest(numero: number): number {
    return Math.round((numero + Number.EPSILON) * 100) / 100;
  }

  roundToTwoDecimalsTes(numero: number): number {
    return parseFloat(numero.toFixed(2));
  }

  roundToTwoDecimalsT(numero: number): number {
    return Math.round(numero * 100) / 100;
  }

  roundToTwoDecimalsNo(numero: number): string {
    return this.decimalPipe.transform(numero, '1.2-2');
  }

  roundToTwoDecimalsTest2(numero: number): string {
    return parseFloat(numero.toFixed(2)).toString();
  }

  roundToTwoDecimals(numero: number | string): string {
    //console.log(numero);
    Big.DP = 10;
    Big.RM = Big.roundHalfUp;
    const bigNumero = new Big(numero);
    return bigNumero.round(4).toFixed(4);
  }

  /**
   * Método que se utiliza para calcular el total de los productos
   *
   */
  calculoSubtotalDetail() {
    const descriptionControl = this.formDetail.get('subtotal') as FormControl;

    const producto = this.formDetail.value as InvoiceDetail;
    //console.log(producto);
    //console.log(this.model);
    const price: number = Number(producto.unitPrice);

    //console.log(price);

    if (producto.quantity == null) {
      this.utilService.showNotification(
        1,
        'Ingrese una cantidad para calcular el total'
      );
    } else {
      //this.subtotal = (Math.round((producto.quantity * price))).toFixed(2);
      this.subtotal = this.roundToTwoDecimals(
        producto.quantity * producto.unitPrice
      );
      this.priceUnit = producto.unitPrice.toString();
      this.quantity = producto.quantity.toString();
      //console.log(this.totalDetail);
      descriptionControl.setValue(this.subtotal);
    }
  }

  /**
   * Método que calcula el impuesto
   *
   */
  calISV() {
    const producto = this.formDetail.value as InvoiceDetail;
    //console.log(producto);
    const SUBTOTAL: number = Number(producto.subtotal);
    const DISCOUNT: number = Number(producto.discount);

    if (producto.quantity > 0) {
      if (SUBTOTAL >= DISCOUNT && SUBTOTAL > 0) {
        if (producto.unitPrice.toString() == this.priceUnit) {
          const TAX_PORCENTAGE: number =
            this.taxPorcentage != 0 ? this.taxPorcentage / 100 : 0;

          this.discount = this.roundToTwoDecimals(DISCOUNT);
          //const SUB_DISCOUNT: string = (Math.round(SUBTOTAL-DISCOUNT)).toFixed(2);
          const SUB_DISCOUNT: string = this.roundToTwoDecimals(
            SUBTOTAL - DISCOUNT
          );

          const SUB_DISCOUNT_NUMBER: number = Number(SUB_DISCOUNT);

          //const TAX: number = Math.round(SUB_DISCOUNT_NUMBER * TAX_PORCENTAGE);
          const TAX: string = this.roundToTwoDecimals(
            SUB_DISCOUNT_NUMBER * TAX_PORCENTAGE
          );

          this.isv = TAX;
          //console.log(this.isv);
          const TAX_AMOUNT_CONTROL = this.formDetail.get('tax') as FormControl;
          TAX_AMOUNT_CONTROL.setValue(this.isv);
        } else {
          this.utilService.showNotification(
            1,
            'EL precio unitario se modifico, vuelva a calcular el subtotal'
          );
        }
      } else {
        this.utilService.showNotification(1, 'Vuelva a calcular el subtotal');
      }
    } else {
      this.utilService.showNotification(1, 'Ingrese una cantidad mayor a cero');
    }
  }

  /**
   * Método que calcula el total de los valores
   *
   */
  calTotal() {
    const producto = this.formDetail.value as InvoiceDetail;
    const SUBTOTAL: number = Number(producto.subtotal);
    const DISCOUNT: number = Number(producto.discount);
    const TAX_PRODUCT: number = Number(producto.tax);

    /**
     * Validamos que el descuento no sea mayor al subtotal
     *
     */
    if (DISCOUNT > SUBTOTAL) {
      this.utilService.showNotification(
        1,
        'El descuento no debe de ser mayor al subtotal'
      );
    } else {
      /***
       * Validamos si la cantidad es mayor a cero
       *
       */
      if (producto.quantity <= 0) {
        this.utilService.showNotification(
          1,
          'La cantidad debe de ser mayor a cero'
        );
      } else {
        /**
         * Validamos que el subtotal no sea cero
         *
         */
        if (SUBTOTAL <= 0) {
          this.utilService.showNotification(
            1,
            'El subtotal no puede menor a cero'
          );
        } else {
          /**
           * Validamos si se cambio el precio
           *
           */
          if (producto.unitPrice.toString() == this.priceUnit) {
            if (producto.tax.toString() == this.isv) {
              const TOTAL: string = this.roundToTwoDecimals(
                SUBTOTAL - DISCOUNT + TAX_PRODUCT
              );
              this.totalDetail = TOTAL;
              const TOTAL_AMOUNT_CONTROL = this.formDetail.get(
                'amountTotal'
              ) as FormControl;
              TOTAL_AMOUNT_CONTROL.setValue(TOTAL);
            } else {
              this.utilService.showNotification(
                1,
                'El impuesto no cuadra, vuelva a calcular el impuesto'
              );
            }
          } else {
            this.utilService.showNotification(
              1,
              'El precio unitario fue modificado, vuelva a realizar los calculos'
            );
          }
        }
      }
    }
  }

  /**
   * Método para agregar un producto
   *
   */
  addProduct() {
    Swal.fire({
      title: 'Advertencia',
      text: `¿Desea agregar este servicio a la tabla?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar',
    }).then(async (result: any) => {
      if (result.value) {
        const producto = this.formDetail.value as InvoiceDetail;

        //console.log(producto);

        // Constante de valores finales
        const SUBTOTAL: number = Number(producto.subtotal);
        const DISCOUNT: number = Number(producto.discount);
        const TAX_PORCENTAGE: number =
          this.taxPorcentage != 0 ? this.taxPorcentage / 100 : 0;
        //const TAX: number = Number(producto.tax);
        //const TOTAL: number = Number(producto.amountTotal);

        const SUB: string = this.roundToTwoDecimals(
          producto.quantity * producto.unitPrice
        );

        const SUB_DIS: string = this.roundToTwoDecimals(
          Number(SUB) - producto.discount
        );
        let ISV: string = '0.00';

        ISV = this.roundToTwoDecimals(Number(SUB_DIS) * TAX_PORCENTAGE);

        const TOTAL: string = this.roundToTwoDecimals(
          Number(SUB_DIS) + Number(ISV)
        );

        /*
        console.log(this.subtotal);
        console.log(this.discount);
        console.log(this.isv);
        console.log(this.totalDetail);
        console.log("------------------------------");
        console.log(SUB);
        console.log(DISCOUNT);
        console.log(ISV);
        console.log(TOTAL);
        */

        // Variable que se va a retornar al componente padre
        let productAdd: InvoiceDetail = {};

        /**
         * Validamos que el descuento no sea mayor al subtotal
         *
         */
        if (DISCOUNT > SUBTOTAL) {
          this.utilService.showNotification(
            1,
            'El descuento no debe de ser mayor al subtotal'
          );
        } else {
          /**
           * Validamos que la cantidad sea mayor a cero
           *
           */
          if (producto.quantity <= 0) {
            this.utilService.showNotification(
              1,
              'La cantidad no debe de ser menor a uno'
            );
          } else {
            /**
             * Validamos que el precio unitario sea el mismo que desde un princio se realizo el calculo
             *
             */
            if (
              producto.unitPrice.toString() == this.priceUnit &&
              producto.quantity.toString() == this.quantity
            ) {
              if (
                ISV == this.isv &&
                TOTAL == this.totalDetail &&
                SUB == this.subtotal
              ) {
                const reserveSerials =
                  await this.getSerialNumbersReserveQuery();

                if (reserveSerials) {
                  productAdd.serieOrBoxNumber = producto.serieOrBoxNumber;

                  productAdd.idFind = this.generateUUID();
                  productAdd.model = this.model;
                  productAdd.description = this.description;

                  productAdd.unitPrice = Number(producto.unitPrice);
                  productAdd.quantity = producto.quantity;
                  productAdd.subtotal = Number(producto.subtotal);
                  productAdd.discount = Number(producto.discount);
                  productAdd.taxPercentage = this.taxPorcentage;
                  productAdd.tax = Number(producto.tax);
                  productAdd.amountTotal = Number(producto.amountTotal);

                  this.serialReserveTokensList.forEach(item => {
                    if(producto.serieOrBoxNumber === item.serial_number) productAdd.reserveKey = item.reservation_result;
                  })

                  //console.log(productAdd);

                  this.invoiceDetalle.emit(productAdd);
                  this.closeModal();

                } else {
                  this.utilService.showNotification(
                    1,
                    'No se pudieron reservar las series'
                  );
                }
              } else {
                this.utilService.showNotification(
                  1,
                  'Los valores no cuadrán, verifique los campos volviendo a calcular el subtotal, impuesto y total'
                );
              }
            } else {
              this.utilService.showNotification(
                1,
                'Los valores no cuadran, verifique los campos volviendo a calcular el subtotal, impuesto y total'
              );
            }
          }
        }
      }
    });
  }

  getSerialNumbersReserveQuery(): Promise<boolean> {
    const warehouseId = this.formDetail.get('warehouse').value[0].code;
    const inventoryTypeId = this.formDetail.get('inventoryType').value[0].code;
    const itemCode = this.formDetail.get('model').value;
    const subWarehouseCode = this.subWareHouse;
    const quantity = Number(this.formDetail.get('quantity').value);

    return new Promise<boolean>((resolve, reject) => {
      this.invoiceService
        .getSerialNumbersReserveQuery(
          itemCode,
          warehouseId,
          subWarehouseCode,
          inventoryTypeId,
          quantity
        )
        .subscribe(
          (response) => {
            if (response.status === 200) {
              const responseData = response.body;
              if (
                responseData.code === 1 &&
                responseData.data.result_code === 'INV000'
              ) {
                responseData.data.serial_number_list.forEach(item => {
                  item.serial_number_list.forEach(item2 => {
                    this.serialReserveTokensList.push(item2);
                  })
                });
                console.log(this.serialReserveTokensList);
                resolve(true);
              } else {
                console.error(responseData.description);
                this.utilService.showNotification(1, responseData.description);
                resolve(false);
              }
            } else {
              console.error(response);
              resolve(false);
            }
          },
          (error) => {
            console.error(error);
            resolve(false);
          }
        );
    });
  }

  generateUUID(): string {
    let uuid = '';
    const characters =
      'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';

    for (let i = 0; i < 36; i++) {
      if (i === 8 || i === 13 || i === 18 || i === 23) {
        uuid += '-';
      } else if (i === 14) {
        uuid += '4';
      } else if (i === 19) {
        uuid += characters.charAt(
          Math.floor(Math.random() * characters.length)
        );
      } else {
        uuid += characters.charAt(
          Math.floor(Math.random() * characters.length)
        );
      }
    }

    return uuid;
  }
}
