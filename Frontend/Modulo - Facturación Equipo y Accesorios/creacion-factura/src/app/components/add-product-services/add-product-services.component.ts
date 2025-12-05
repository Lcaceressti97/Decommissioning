import { InvoiceService } from 'src/app/services/invoice.service';
import { DecimalPipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {
  FormGroup,
  FormBuilder,
  Validators,
  FormControl,
} from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import {
  BillingServicesModel,
  ControlUserPermissions,
  InvoiceDetail,
} from 'src/app/models/model';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enum';
import Swal from 'sweetalert2/dist/sweetalert2.js';
import Big from 'big.js';

@Component({
  selector: 'app-add-product-services',
  templateUrl: './add-product-services.component.html',
  styleUrls: ['./add-product-services.component.css'],
})
export class AddProductServicesComponent implements OnInit {
  // Props

  //Inputs | Outputs
  @Input() billingServices: BillingServicesModel[] = [];
  @Input() controlUserPermissions: ControlUserPermissions;
  @Input() taxPorcentage: number = 0;
  @Output() invoiceDetalle = new EventEmitter<InvoiceDetail>();

  // Form
  formDetail!: FormGroup;
  messages = messages;
  invoiceDetail: InvoiceDetail[] = [];

  // Calculos
  validateTotalDetail: boolean = true;

  // Variables independientes
  model: string = '';
  priceUnit: string = '0.00';
  quantity: string = '0';
  subtotal: string = '0.00';
  discount: string = '0.00';
  isv: string = '0.00';
  totalDetail: string = '0.00';

  // Buttons
  readonlyDiscount: boolean = true;

  constructor(
    public utilService: UtilService,
    private activeModal: NgbActiveModal,
    private formBuilder: FormBuilder,
    private decimalPipe: DecimalPipe,
    private invoiceService: InvoiceService
  ) {}

  ngOnInit(): void {
    this.formDetail = this.initFormDetail();
  }

  closeModal() {
    this.activeModal.close();
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
      quantity: [0, [Validators.required]],
      unitPrice: ['0.00', [Validators.required, this.validarNumeroDecimal]],
      subtotal: ['0.00', [Validators.required]],
      discountPercentage: ['0', []],
      discount: ['0.00', [Validators.required, this.validarNumeroDecimal]],
      selectTax: [0, []],
      taxPercentage: ['0', []],
      tax: ['0.00', [Validators.required]],
      amountTotal: ['0.00', []],
      serieOrBoxNumber: ['', []],
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
    const ARRAY_SERVICE = SERVICE.split('||');
    //console.log(SERVICE);
    //console.log(ARRAY_SERVICE);

    this.model = ARRAY_SERVICE[0];

    const descriptionControl = this.formDetail.get(
      'description'
    ) as FormControl;
    descriptionControl.setValue(ARRAY_SERVICE[1]);

    const priceControl = this.formDetail.get('unitPrice') as FormControl;
    const PRICE_UNIT_CONST: string = Number(ARRAY_SERVICE[2]).toFixed(2);
    priceControl.setValue(PRICE_UNIT_CONST);

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

    this.priceUnit = PRICE_UNIT_CONST;
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
                productAdd.serieOrBoxNumber = producto.serieOrBoxNumber;

                productAdd.idFind = this.generateUUID();
                productAdd.model = this.model;
                productAdd.description = producto.description;

                productAdd.unitPrice = Number(producto.unitPrice);
                productAdd.quantity = producto.quantity;
                productAdd.subtotal = Number(producto.subtotal);
                productAdd.discount = Number(producto.discount);
                productAdd.taxPercentage = this.taxPorcentage;
                productAdd.tax = Number(producto.tax);
                productAdd.amountTotal = Number(producto.amountTotal);

                //console.log(productAdd);

                this.invoiceDetalle.emit(productAdd);
                this.closeModal();

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
