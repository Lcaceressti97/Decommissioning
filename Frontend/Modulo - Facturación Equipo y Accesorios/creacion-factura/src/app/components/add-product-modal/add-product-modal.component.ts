import { DecimalPipe } from '@angular/common';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import {
  BillingServicesModel,
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
import {
  ExistencesResponse,
  PriceMasterResponse,
} from 'src/app/entity/response';
import { InvoiceService } from 'src/app/services/invoice.service';
import { ReserveSerialApiResponse, SerialNumber } from 'src/app/models/reserve-serial';

@Component({
  selector: 'app-add-product-modal',
  templateUrl: './add-product-modal.component.html',
  styleUrls: ['./add-product-modal.component.css'],
})
export class AddProductModalComponent implements OnInit {
  // Props

  //Inputs | Outputs
  @Input() subWareHouse: string;
  @Input() billingServices: BillingServicesModel[] = [];
  @Input() inventoryTypeModel: InventoryTypeModel[] = [];
  @Input() warehouseModel: WareHouseModel[] = [];
  @Input() controlUserPermissions: ControlUserPermissions;
  @Input() taxPorcentage: number = 0;
  @Output() invoiceDetalle = new EventEmitter<InvoiceDetail[]>();

  existencesModel: ExistencesModel[] = [];
  serialNumberList: any[] = [];
  serialReserveTokensList: SerialNumber[] = [];

  // Form
  formDetail!: FormGroup;
  messages = messages;
  invoiceDetail: InvoiceDetail[] = [];
  existences: any[] = [];
  priceMasterModel: PriceMasterModel[] = [];

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
    this.selectedWarehouse = [];

    this.formDetail.get('quantity')?.valueChanges.subscribe(() => {
      if (this.serialNumberList.length > 0) {
        this.getSerialNumbersByQuantity();
      }

      this.refreshAutomaticDiscountAmount();
      this.resetTaxAndTotal();
    });

    this.formDetail.get('unitPrice')?.valueChanges.subscribe(() => {
      this.refreshAutomaticDiscountAmount();
      this.resetTaxAndTotal();
    });

    this.getExistencesByFilter().then(() => {});
    this.getSerialNumbersQuery();
  }

  closeModal() {
    this.activeModal.close();
  }

  /**
   * True cuando el usuario seleccionó "Sí" en Agregar descuento
   */
  private isManualDiscountEnabled(): boolean {
    return Number(this.formDetail.get('selectTax')?.value) === 1;
  }

  /**
   * Obtiene el porcentaje automático desde factorCode
   */
  private getAutomaticDiscountPercent(): number {
    return Number(this.formDetail.get('discountPercentage')?.value || 0);
  }

  /**
   * Obtiene el monto manual ingresado por el usuario
   */
  private getManualDiscountAmount(): number {
    return Number(this.formDetail.get('discount')?.value || 0);
  }

  /**
   * Obtiene subtotal actual usando cantidad * precio
   */
  private getCurrentSubtotal(): number {
    const quantity = Number(this.formDetail.get('quantity')?.value || 0);
    const unitPrice = Number(this.formDetail.get('unitPrice')?.value || 0);
    return Number(this.roundToTwoDecimals(quantity * unitPrice));
  }

  /**
   * Calcula el descuento automático en monto
   * usando subtotal * porcentaje / 100
   */
  private calculateAutomaticDiscountAmount(subtotal: number): number {
    const percent = this.getAutomaticDiscountPercent();

    if (!subtotal || subtotal <= 0 || !percent || percent <= 0) {
      return 0;
    }

    return Number(this.roundToTwoDecimals((subtotal * percent) / 100));
  }

  /**
   * Devuelve el descuento real para cálculos:
   * - Si Agregar descuento = Sí -> monto manual
   * - Si Agregar descuento = No -> monto automático
   */
  private getEffectiveDiscount(subtotal?: number): number {
    const currentSubtotal =
      subtotal != null ? Number(subtotal) : this.getCurrentSubtotal();

    if (this.isManualDiscountEnabled()) {
      return this.getManualDiscountAmount();
    }

    return this.calculateAutomaticDiscountAmount(currentSubtotal);
  }

  /**
   * Limpia descuento automático cuando no hay price master
   */
  private clearAutomaticDiscount(): void {
    const discountControl = this.formDetail.get('discount') as FormControl;
    const discountPercentageControl = this.formDetail.get('discountPercentage') as FormControl;

    discountPercentageControl.setValue('0.00');
    discountControl.setValue('0.00');
  }

  /**
   * Refresca el monto de descuento automático
   */
  private refreshAutomaticDiscountAmount(): void {
    if (this.isManualDiscountEnabled()) {
      return;
    }

    const discountControl = this.formDetail.get('discount') as FormControl;
    const subtotal = this.getCurrentSubtotal();
    const automaticDiscount = this.calculateAutomaticDiscountAmount(subtotal);

    discountControl.setValue(this.roundToTwoDecimals(automaticDiscount));
  }

  /**
   * Refresca el valor visual del campo descuento según la opción seleccionada
   */
  private syncDiscountFieldByMode(): void {
    const discountControl = this.formDetail.get('discount') as FormControl;

    if (this.isManualDiscountEnabled()) {
      this.readonlyDiscount = false;
      discountControl.setValue('0.00');
      return;
    }

    this.readonlyDiscount = true;

    const subtotal = this.getCurrentSubtotal();
    const automaticDiscount = this.calculateAutomaticDiscountAmount(subtotal);

    discountControl.setValue(this.roundToTwoDecimals(automaticDiscount));
  }

  /**
   * Limpia impuesto y total para forzar recálculo
   */
  private resetTaxAndTotal(): void {
    const TAX_CONTROL = this.formDetail.get('tax') as FormControl;
    const TOTAL_CONTROL = this.formDetail.get('amountTotal') as FormControl;

    TAX_CONTROL.setValue('0.00');
    TOTAL_CONTROL.setValue('0.00');
    this.isv = '0.00';
    this.totalDetail = '0.00';
  }

  /**
   * Método encargado de obtener el precio
   *
   * @returns
   */
  getPriceMaster(): Promise<boolean> {
    return new Promise((resolve) => {
      const model = this.formDetail.get('model')?.value;
      const inventoryType = this.formDetail.get('inventoryType')?.value[0]?.code;

      const PRICE_CONTROL = this.formDetail.get('unitPrice') as FormControl;
      const DISCOUNT_CONTROL = this.formDetail.get('discount') as FormControl;
      const DISCOUNT_PERC_CONTROL = this.formDetail.get('discountPercentage') as FormControl;

      if (!model || !inventoryType) {
        PRICE_CONTROL.setValue('0.00');
        this.clearAutomaticDiscount();
        resolve(false);
        return;
      }

      this.invoiceService
        .getPriceMasterByModelAndInventoryType(model, inventoryType)
        .subscribe({
          next: (response) => {
            if (response.status === 200 && response.body) {
              const priceMasterResponse = response.body as PriceMasterResponse;
              this.priceMasterModel = Array.isArray(priceMasterResponse.data)
                ? priceMasterResponse.data
                : priceMasterResponse.data
                  ? [priceMasterResponse.data]
                  : [];

              if (this.priceMasterModel.length > 0) {
                const selectedPriceMaster = this.priceMasterModel[0];

                const price = Number(selectedPriceMaster.price || 0);
                const factorCode = Number(selectedPriceMaster.factorCode || 0);

                PRICE_CONTROL.setValue(this.roundToTwoDecimals(price));
                DISCOUNT_PERC_CONTROL.setValue(this.roundToTwoDecimals(factorCode));

                if (!this.isManualDiscountEnabled()) {
                  const subtotal = this.getCurrentSubtotal();
                  const automaticDiscount = this.calculateAutomaticDiscountAmount(subtotal);
                  DISCOUNT_CONTROL.setValue(this.roundToTwoDecimals(automaticDiscount));
                }

                this.resetTaxAndTotal();
                resolve(true);
              } else {
                PRICE_CONTROL.setValue('0.00');
                this.clearAutomaticDiscount();
                this.resetTaxAndTotal();
                resolve(false);
              }
            } else {
              PRICE_CONTROL.setValue('0.00');
              this.clearAutomaticDiscount();
              this.resetTaxAndTotal();
              resolve(false);
            }
          },
          error: () => {
            PRICE_CONTROL.setValue('0.00');
            this.clearAutomaticDiscount();
            this.resetTaxAndTotal();
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
    return new Promise((resolve) => {
      const warehouseValue = this.formDetail.get('warehouse')?.value;
      const inventoryTypeValue = this.formDetail.get('inventoryType')?.value;

      if (!warehouseValue?.length || !inventoryTypeValue?.length) {
        resolve(false);
        return;
      }

      const warehouseId = warehouseValue[0].id;
      const inventoryTypeId = inventoryTypeValue[0].id;

      this.invoiceService
        .getExistencesByFilter(warehouseId, inventoryTypeId)
        .subscribe(
          (response) => {
            if (response.status === 200) {
              this.existencesModel = [];

              const existenceResponse = response.body as ExistencesResponse;

              if (existenceResponse.data && existenceResponse.data.length > 0) {
                existenceResponse.data.forEach((resourceMap) => {
                  const dto: ExistencesModel = resourceMap;
                  this.existencesModel.push(dto);
                });

                this.existencesModel = [...this.existencesModel];
                resolve(true);
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
          () => {
            resolve(false);
          }
        );
    });
  }

  async changeWarehouseAndInventory(event: any) {
    const warehouseId = this.formDetail.get('warehouse')?.value;
    const inventoryTypeId = this.formDetail.get('inventoryType')?.value;

    if (warehouseId && inventoryTypeId) {
      await this.getExistencesByFilter();
      await this.getPriceMaster();
    }
  }

  getSerialNumbersQuery(): void {
    const warehouse = this.formDetail.get('warehouse')?.value;
    const inventoryType = this.formDetail.get('inventoryType')?.value;
    const itemCode = this.formDetail.get('model')?.value;

    if (!warehouse?.length || !inventoryType?.length || !itemCode) {
      this.serialNumberList = [];
      this.formDetail.get('serieOrBoxNumber')?.setValue('');
      return;
    }

    const warehouseId = warehouse[0].code;
    const inventoryTypeId = inventoryType[0].code;
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
              const serialNumberList =
                responseData.data.serial_number_list[0].serial_number_list;
              this.serialNumberList = serialNumberList;
              this.getSerialNumbersByQuantity();
            } else {
              this.utilService.showNotification(1, responseData.description);
            }
          }
        },
        (error) => {
          console.error(error);
        }
      );
  }

  getSerialNumbersByQuantity(): void {
    const quantity = this.formDetail.get('quantity')?.value;

    if (
      this.serialNumberList &&
      this.serialNumberList.length > 0 &&
      quantity > 0
    ) {
      if (quantity > this.serialNumberList.length) {
        this.utilService.showNotification(
          1,
          `La cantidad solicitada (${quantity}) excede el números de serie disponibles (${this.serialNumberList.length}).`
        );

        this.formDetail.get('serieOrBoxNumber')?.setValue('');
      } else {
        const limitedSerialNumbers = this.serialNumberList
          .slice(0, quantity)
          .map((item) => item.serialNumber);

        this.formDetail
          .get('serieOrBoxNumber')
          ?.setValue(limitedSerialNumbers.join(', '));
      }
    } else {
      this.formDetail.get('serieOrBoxNumber')?.setValue('');
    }
  }

  /**
   * Método que solo permite números con un máximo de 4 digitos decimal
   *
   * @param control
   * @returns
   */
  validarNumeroDecimal(control: FormControl) {
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
      quantity: [0, [Validators.required]],
      unitPrice: ['0.00', [Validators.required, this.validarNumeroDecimal]],
      subtotal: ['0.00', [Validators.required]],
      discountPercentage: ['0.00', []],
      discount: ['0.00', [Validators.required, this.validarNumeroDecimal]],
      selectTax: [0, []], // 0 = No, 1 = Sí
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

    (this.formDetail.get('quantity') as FormControl).setValue(0);
    (this.formDetail.get('unitPrice') as FormControl).setValue('0.00');
    (this.formDetail.get('subtotal') as FormControl).setValue('0.00');
    (this.formDetail.get('discountPercentage') as FormControl).setValue('0.00');
    (this.formDetail.get('discount') as FormControl).setValue('0.00');
    (this.formDetail.get('tax') as FormControl).setValue('0.00');
    (this.formDetail.get('selectTax') as FormControl).setValue(0);
    (this.formDetail.get('amountTotal') as FormControl).setValue('0.00');

    this.validateTotalDetail = true;
    this.model = '';
    this.description = '';
    this.priceUnit = '0.00';
    this.quantity = '0';
    this.subtotal = '0.00';
    this.discount = '0.00';
    this.isv = '0.00';
    this.totalDetail = '0.00';
    this.readonlyDiscount = true;
  }

  /**
   * Maneja el cambio de "Agregar descuento"
   */
  changeCalISV(event: any) {
    const SELECT_CONTROL = this.formDetail.get('selectTax') as FormControl;
    SELECT_CONTROL.setValue(Number(event.target.value));

    this.syncDiscountFieldByMode();
    this.resetTaxAndTotal();
  }

  /**
   * Método para mostrar la data selecciona en
   * el select de Código de Servicio
   *
   */
  changeCodeService(event: any) {
    this.validateTotalDetail = false;
    const SERVICE: string = event.target.value;

    const selectedObject = this.existencesModel.find(
      (existence) => existence.code === SERVICE
    );

    if (selectedObject) {
      this.model = selectedObject.code;
      this.description = selectedObject.description;
    }

    this.getPriceMaster().then(() => {});
    this.getSerialNumbersQuery();

    (this.formDetail.get('subtotal') as FormControl).setValue('0.00');
    (this.formDetail.get('tax') as FormControl).setValue('0.00');
    (this.formDetail.get('amountTotal') as FormControl).setValue('0.00');

    this.subtotal = '0.00';
    this.isv = '0.00';
    this.totalDetail = '0.00';

    this.syncDiscountFieldByMode();
  }

  roundToTwoDecimals(numero: number | string): string {
    Big.DP = 10;
    Big.RM = Big.roundHalfUp;
    const bigNumero = new Big(numero || 0);
    return bigNumero.round(2).toFixed(2);
  }

  /**
   * Método que se utiliza para calcular el total de los productos
   *
   */
  calculoSubtotalDetail() {
    const subtotalControl = this.formDetail.get('subtotal') as FormControl;
    const producto = this.formDetail.value as InvoiceDetail;

    if (producto.quantity == null || Number(producto.quantity) <= 0) {
      this.utilService.showNotification(
        1,
        'Ingrese una cantidad mayor a cero para calcular el subtotal'
      );
      return;
    }

    this.subtotal = this.roundToTwoDecimals(
      Number(producto.quantity) * Number(producto.unitPrice)
    );

    // Guardar valores normalizados para comparaciones posteriores
    this.priceUnit = this.normalizeMoney(producto.unitPrice);
    this.quantity = String(Number(producto.quantity));

    subtotalControl.setValue(this.subtotal);
    this.refreshAutomaticDiscountAmount();
    this.resetTaxAndTotal();
  }

  /**
   * Método que calcula el impuesto
   *
   */
  calISV() {
    const producto = this.formDetail.value as InvoiceDetail;
    const SUBTOTAL: number = Number(producto.subtotal);
    const DISCOUNT: number = this.getEffectiveDiscount(SUBTOTAL);

    if (Number(producto.quantity) <= 0) {
      this.utilService.showNotification(1, 'Ingrese una cantidad mayor a cero');
      return;
    }

    if (SUBTOTAL <= 0 || DISCOUNT > SUBTOTAL) {
      this.utilService.showNotification(1, 'Vuelva a calcular el subtotal');
      return;
    }

    if (this.hasUnitPriceChanged(producto.unitPrice)) {
      this.utilService.showNotification(
        1,
        'EL precio unitario se modifico, vuelva a calcular el subtotal'
      );
      return;
    }

    const TAX_PORCENTAGE: number =
      this.taxPorcentage !== 0 ? this.taxPorcentage / 100 : 0;

    this.discount = this.roundToTwoDecimals(DISCOUNT);

    const SUB_DISCOUNT: string = this.roundToTwoDecimals(
      SUBTOTAL - DISCOUNT
    );

    const TAX: string = this.roundToTwoDecimals(
      Number(SUB_DISCOUNT) * TAX_PORCENTAGE
    );

    this.isv = TAX;
    const TAX_AMOUNT_CONTROL = this.formDetail.get('tax') as FormControl;
    TAX_AMOUNT_CONTROL.setValue(this.isv);
  }

  /**
   * Método que calcula el total de los valores
   *
   */
  calTotal() {
    const producto = this.formDetail.value as InvoiceDetail;
    const SUBTOTAL: number = Number(producto.subtotal);
    const DISCOUNT: number = this.getEffectiveDiscount(SUBTOTAL);
    const TAX_PRODUCT: number = Number(producto.tax);

    if (DISCOUNT > SUBTOTAL) {
      this.utilService.showNotification(
        1,
        'El descuento no debe de ser mayor al subtotal'
      );
      return;
    }

    if (Number(producto.quantity) <= 0) {
      this.utilService.showNotification(
        1,
        'La cantidad debe de ser mayor a cero'
      );
      return;
    }

    if (SUBTOTAL <= 0) {
      this.utilService.showNotification(
        1,
        'El subtotal no puede ser menor o igual a cero'
      );
      return;
    }

    if (this.hasUnitPriceChanged(producto.unitPrice)) {
      this.utilService.showNotification(
        1,
        'El precio unitario fue modificado, vuelva a realizar los cálculos'
      );
      return;
    }

    if (this.normalizeMoney(producto.tax) !== this.normalizeMoney(this.isv)) {
      this.utilService.showNotification(
        1,
        'El impuesto no cuadra, vuelva a calcular el impuesto'
      );
      return;
    }

    const TOTAL: string = this.roundToTwoDecimals(
      SUBTOTAL - DISCOUNT + TAX_PRODUCT
    );

    this.totalDetail = TOTAL;
    const TOTAL_AMOUNT_CONTROL = this.formDetail.get('amountTotal') as FormControl;
    TOTAL_AMOUNT_CONTROL.setValue(TOTAL);
  }

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

        const SUBTOTAL: number = Number(producto.subtotal);
        const DISCOUNT: number = this.getEffectiveDiscount(SUBTOTAL);
        const TAX_PERCENTAGE: number =
          this.taxPorcentage !== 0 ? this.taxPorcentage / 100 : 0;

        const SUB: string = this.roundToTwoDecimals(
          Number(producto.quantity) * Number(producto.unitPrice)
        );

        const SUB_DIS: string = this.roundToTwoDecimals(
          Number(SUB) - DISCOUNT
        );

        const ISV: string = this.roundToTwoDecimals(
          Number(SUB_DIS) * TAX_PERCENTAGE
        );

        const TOTAL: string = this.roundToTwoDecimals(
          Number(SUB_DIS) + Number(ISV)
        );

        const productsAdd: InvoiceDetail[] = [];

        if (DISCOUNT > SUBTOTAL) {
          this.utilService.showNotification(
            1,
            'El descuento no debe de ser mayor al subtotal'
          );
          return;
        }

        if (Number(producto.quantity) <= 0) {
          this.utilService.showNotification(
            1,
            'La cantidad no debe de ser menor a uno'
          );
          return;
        }

        if (
          !this.hasUnitPriceChanged(producto.unitPrice) &&
          !this.hasQuantityChanged(producto.quantity)
        ) {
          const reserveSerials = await this.getSerialNumbersReserveQuery();

          if (reserveSerials) {
            const quantity = this.formDetail.get('quantity')?.value;
            const serialNumbers = this.serialNumberList.slice(0, quantity);

            serialNumbers.forEach((serialNumber) => {
              const productAdd: InvoiceDetail = {};

              productAdd.serieOrBoxNumber = serialNumber.serialNumber;
              productAdd.idFind = this.generateUUID();
              productAdd.model = this.model;
              productAdd.description = this.description;
              productAdd.unitPrice = Number(producto.unitPrice);
              productAdd.quantity = 1;
              productAdd.subtotal = Number(SUB) / quantity;
              productAdd.discount = Number(DISCOUNT) / quantity;
              productAdd.taxPercentage = this.taxPorcentage;
              productAdd.tax = Number(ISV) / quantity;
              productAdd.amountTotal = Number(TOTAL) / quantity;

              this.serialReserveTokensList.forEach((item) => {
                if (serialNumber.serialNumber === item.serial_number) {
                  productAdd.reserveKey = item.reservation_result;
                }
              });

              productsAdd.push(productAdd);
            });

            this.invoiceDetalle.emit(productsAdd);
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
            'Los valores no cuadran, verifique los campos volviendo a calcular subtotal, impuesto y total'
          );
        }
      }
    });
  }

  getSerialNumbersReserveQuery(): Promise<boolean> {
    const warehouseId = this.formDetail.get('warehouse')?.value[0]?.code;
    const inventoryTypeId = this.formDetail.get('inventoryType')?.value[0]?.code;
    const itemCode = this.formDetail.get('model')?.value;
    const subWarehouseCode = this.subWareHouse;
    const quantity = Number(this.formDetail.get('quantity')?.value);

    return new Promise<boolean>((resolve) => {
      this.serialReserveTokensList = [];

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
              const responseData = response.body as ReserveSerialApiResponse;
              if (
                responseData.code === 1 &&
                responseData.data.result_code === 'INV000'
              ) {
                responseData.data.serial_number_list.forEach((item) => {
                  item.serial_number_list.forEach((item2) => {
                    this.serialReserveTokensList.push(item2);
                  });
                });
                resolve(true);
              } else {
                this.utilService.showNotification(1, responseData.description);
                resolve(false);
              }
            } else {
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

  private normalizeMoney(value: any): string {
    return this.roundToTwoDecimals(Number(value || 0));
  }

  private hasUnitPriceChanged(currentValue: any): boolean {
    return this.normalizeMoney(currentValue) !== this.normalizeMoney(this.priceUnit);
  }

  private hasQuantityChanged(currentValue: any): boolean {
    return Number(currentValue || 0) !== Number(this.quantity || 0);
  }
}