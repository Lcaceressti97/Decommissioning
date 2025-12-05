import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Billing, BillingServicesModel, Branche, ChannelModel, ControlUserPermissions, CustomerInfoModel, HistoricalDetail, InventoryTypeModel, InvoiceDetail, SubWarehouseModel, WareHouseModel } from 'src/app/models/model';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enum';
import Swal from "sweetalert2/dist/sweetalert2.js";
import { AddProductModalComponent } from '../add-product-modal/add-product-modal.component';
import { InvoiceService } from 'src/app/services/invoice.service';
import { BillingResponse, CustomerInfoResponse, HistoricalDetailResponse, SubWarehouseNameResponse } from 'src/app/entity/response';
import Big from 'big.js';
import { DetailBlockedLinesComponent } from '../detail-blocked-lines/detail-blocked-lines.component';
import { AddProductInsuranceComponent } from '../add-product-insurance/add-product-insurance.component';
import { AddProductServicesComponent } from '../add-product-services/add-product-services.component';

@Component({
  selector: 'app-invoice-creation-modal',
  templateUrl: './invoice-creation-modal.component.html',
  styleUrls: ['./invoice-creation-modal.component.css']
})
export class InvoiceCreationModalComponent implements OnInit {

  // Props

  //Inputs | Outputs
  @Input() billingServices: BillingServicesModel[] = [];
  @Input() invoiceTypes: string[] = [];
  @Input() branchOffices: Branche[] = [];
  @Input() inventoryTypeModel: InventoryTypeModel[] = [];
  @Input() warehouseModel: WareHouseModel[] = [];
  @Input() subWarehouseModel: SubWarehouseModel[] = [];
  @Input() controlUserPermissions: ControlUserPermissions;
  @Input() taxPorcentage: number = 0;
  taxPorcentageStr: string = "0%";
  @Input() exchangeRate: number = 0;
  @Input() equipmentLineId: number = 0;
  @Input() nameFinalConsumer: string = "";
  @Input() rtnFinalConsumer: string = "";
  @Output() messageEvent = new EventEmitter<Billing>();

  fiscalProcessData = [
    { id: 1, name: 'Facturación Equipos y Accesorios' },
    { id: 2, name: 'Facturación por Reclamo de Seguros' },
    { id: 3, name: 'Facturación de Servicios' }
  ];


  customerTypeData = [
    { id: 1, name: 'Pospago' },
    { id: 2, name: 'Factura con Nombre y RTN' },
    { id: 3, name: 'Consumidor Final' }
  ];

  // Billing
  billing: Billing = null;

  // Detalles
  invoiceDetail: InvoiceDetail[] = [];
  rows2: InvoiceDetail[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 3;
  searchedValue: string = "";
  inventoryType: string = "";
  selectedCustomerType: boolean = false;
  customerTypeId;
  // Form
  formGeneral!: FormGroup;
  formValidation!: FormGroup;
  messages = messages;

  // Hidden
  hiddeGeneral: boolean = false;
  hiddeDetail: boolean = true;
  hiddeValidation: boolean = true;

  // Tab
  active: number = 1;

  // Select Sucursal
  idBranchOffice: number = 0; // Id de la sucursal
  agency: string = ""; // Nombre de la sucursal
  warehouseCode: string = ""; // Código de la bodega al que pertenece la sucursal
  acctCodeBranchOffice: string = "";

  // Acumuladores
  acumuladorSubtotal: number = 0.00;
  acumuladorDiscount: number = 0.00;
  acumuladorTax: number = 0.00;
  acumuladorTotal: number = 0.00;
  idInsuranceClaim: number;

  public dropdownSettingsInventory = {
    singleSelection: true,
    text: "Selecciona un tipo",
    enableSearchFilter: true,
    primaryKey: 'id'
  };

  constructor(public utilService: UtilService, private activeModal: NgbActiveModal, private formBuilder: FormBuilder, private modalService: NgbModal, private invoiceService: InvoiceService) { }

  ngOnInit(): void {
    this.taxPorcentageStr = `${this.taxPorcentage}%`;
    this.formGeneral = this.initFormGeneral();
    this.formValidation = this.initFormValidation();
     this.formGeneral.get('customer')?.disable();
    this.formGeneral.get('primaryIdentity')?.disable();
    this.formGeneral.get('customerRtnId')?.disable();
  }

  // Methods

  /**
* Buscardor de la tabla Activos
*
*/
  search(): void {
    this.invoiceDetail = this.rows2.filter((row) => {
      return JSON.stringify(row)
        .toLowerCase()
        .includes(this.searchedValue.toString()
          .toLowerCase());
    });
  }

  getTotalTextActive() {
    return this.invoiceDetail.length == 1 ? "Registro" : "Registros";
  }

  closeModal() {
    this.activeModal.close();
  }

  /**
* Método para abrir modales según la acción
*
* @param button
* @param row
*/
  openModalAddProduct() {
    const selectedFiscalProcessId = this.formGeneral.get('fiscalProcess')?.value;
    let modalRef;

    if (selectedFiscalProcessId == 1) {
      modalRef = this.modalService.open(AddProductModalComponent, {
        size: "lg"
      });
    } else if (selectedFiscalProcessId == 2) {
      modalRef = this.modalService.open(AddProductInsuranceComponent, {
        size: "lg"
      });
    } else {
      modalRef = this.modalService.open(AddProductServicesComponent, {
        size: "lg"
      });
    }

    modalRef.componentInstance.subWareHouse = this.formGeneral.get('subWarehouse').value;
    modalRef.componentInstance.billingServices = this.billingServices;
    modalRef.componentInstance.taxPorcentage = this.taxPorcentage;
    modalRef.componentInstance.equipmentLineId = this.equipmentLineId;
    modalRef.componentInstance.inventoryTypeModel = this.inventoryTypeModel;
    modalRef.componentInstance.warehouseModel = this.warehouseModel;
    modalRef.componentInstance.controlUserPermissions = this.controlUserPermissions;
    modalRef.componentInstance.invoiceDetalle.subscribe((invoices: InvoiceDetail | InvoiceDetail[]) => {
      const invoiceDetails = Array.isArray(invoices) ? invoices : [invoices];
      invoiceDetails.forEach((invoice: InvoiceDetail) => {
        this.invoiceDetail.push(invoice);
        this.invoiceDetail = [...this.invoiceDetail];
        this.rows2 = [...this.invoiceDetail];
        this.loadingIndicator = true;

        // Agregar acumulación
        this.acumuladorSubtotal += invoice.subtotal;
        this.acumuladorDiscount += invoice.discount;
        this.acumuladorTax += invoice.tax;
        this.acumuladorTotal += invoice.amountTotal;

        // Refrescar los inputs
        const SUBTOTAL_CONTROL = this.formValidation.get('subtotal') as FormControl;
        SUBTOTAL_CONTROL.setValue(this.acumuladorSubtotal.toFixed(2));

        const DISCOUNT_CONTROL = this.formValidation.get('discount') as FormControl;
        DISCOUNT_CONTROL.setValue(this.acumuladorDiscount.toFixed(2));

        const TAX_CONTROL = this.formValidation.get('amountTax') as FormControl;
        TAX_CONTROL.setValue(this.acumuladorTax.toFixed(2));

        const TOTAL_CONTROL = this.formValidation.get('amountTotal') as FormControl;
        TOTAL_CONTROL.setValue(this.acumuladorTotal.toFixed(2));

        let totalLps: string = (this.exchangeRate * this.acumuladorTotal).toFixed(2);
        const TOTAL_LPS_CONTROL = this.formValidation.get('totalLps') as FormControl;
        TOTAL_LPS_CONTROL.setValue(totalLps);

        this.utilService.showNotification(0, "Producto agregado con éxito");
      });
    });

  }

  /**
   * Se utiliza para inicializar los valores del formulario
   * en este caso son los valores generales de la factura
   *
   */
  initFormGeneral(): FormGroup {
    return this.formBuilder.group({
      fiscalProcess: ['', [Validators.required]],
      invoiceType: ['', [Validators.required]],
      customerType: ['', []],
      acctCode: ['', []],
      customerId: ['', []],
      customer: ['', []],
      primaryIdentity: ['', []],
      customerRtnId: ['', []],
      customerAddress: ['', []],
      seller: [this.utilService.getSystemUser(), [Validators.required]],
      idBranchOffices: ['', [Validators.required]],
      agency: ['', []],
      channel: [3, []],
      inventoryType: ['', [Validators.required, Validators.maxLength(50)]],
      warehouse: ['', []],
      subWarehouse: ['', [Validators.required]]
    });
  }

  /**
   * Se utiliza para inicializar el formulario que
   * representa los valores monetarios
   *
   * @returns
   */
  initFormValidation(): FormGroup {
    return this.formBuilder.group({
      subtotal: [this.acumuladorSubtotal.toFixed(2), [Validators.required]],
      discountPercentage: ['0', []],
      discount: [this.acumuladorDiscount.toFixed(2), [Validators.required]],
      taxPercentage: [this.taxPorcentageStr, []],
      amountTax: [this.acumuladorTax.toFixed(2), [Validators.required]],
      amountTotal: [this.acumuladorTotal.toFixed(2), [Validators.required]],
      exchangeRate: [this.exchangeRate.toFixed(4), [Validators.required]],
      totalLps: ['0.00', []]
    });
  }



  /**
   * Método que solo permite números
   *
   * @param control
   * @returns
   */
  validateNumber(control) {
    const numeroDecimalRegExp = /^[0-9]+$/;
    if (control.value && !numeroDecimalRegExp.test(control.value)) {
      return { 'numeroDecimalInvalido': true };
    }
    return null;
  }

  /**
   * Método que solo permite números con un máximo de 2 digitos decimal
   *
   * @param control
   * @returns
   */
  validarNumeroDecimal(control) {
    const numeroDecimalRegExp = /^[0-9]+(\.[0-9]{1,2})?$/;
    if (control.value && !numeroDecimalRegExp.test(control.value)) {
      return { 'numeroDecimalInvalido': true };
    }
    return null;
  }

  /**
   * Método que solo permite números con un máximo de 4 digitos decimal
   *
   * @param control
   * @returns
   */
  validarNumeroDecimalFour(control) {
    const numeroDecimalRegExp = /^[0-9]+(\.[0-9]{1,4})?$/;
    if (control.value && !numeroDecimalRegExp.test(control.value)) {
      return { 'numeroDecimalInvalido': true };
    }
    return null;
  }


  /**
   * Método que resetea los valores de la información del cliente
   *
   */
  resetFormGeneral() {
    this.formGeneral.controls['customer'].reset();
    this.formGeneral.controls['customerId'].reset();
    this.formGeneral.controls['customerRtnId'].reset();
    this.formGeneral.controls['customerAddress'].reset();
    this.formGeneral.controls['primaryIdentity'].reset();
  }

  // Methods Screen

  /**
* Método que controla la visualización de las tablas
*
* @param option
*/
  changeTab(option: number) {
    //this.resetFormDetail();

    if (option === 1) {
      this.hiddeGeneral = false;
      this.hiddeDetail = true;
      this.hiddeValidation = true;
    } else if (option === 2) {
      this.hiddeGeneral = true;
      this.hiddeDetail = false;
      this.hiddeValidation = true;

    } else {
      this.hiddeGeneral = true;
      this.hiddeValidation = false;
      this.hiddeDetail = true;
    }

  }

  onCustomerTypeChange(event: any) {
    this.customerTypeId = event.target.value;

    if (this.formGeneral.get('fiscalProcess')?.value == 1) {
      // Limpiamos los valores de los campos antes de habilitarlos
      if (this.customerTypeId != 3 || this.customerTypeId != 2) { // Si no es "Consumidor Final"
        this.formGeneral.get('customer')?.setValue('');
        this.formGeneral.get('primaryIdentity')?.setValue('');
        this.formGeneral.get('customerRtnId')?.setValue('');
        this.formGeneral.get('acctCode')?.setValue('');
        this.formGeneral.get('customerId')?.setValue('');
      }

      // Habilitamos o deshabilitamos los campos según el tipo de cliente
      const isCustomerTypeTwo = this.customerTypeId == 2; // 2 es el id para "Factura con Nombre y RTN"
      const isCustomerTypeThree = this.customerTypeId == 3; // 3 es el id para "Consumidor Final"

      if (isCustomerTypeTwo) {
        this.formGeneral.get('customer')?.enable();
        this.formGeneral.get('primaryIdentity')?.enable();
        this.formGeneral.get('customerRtnId')?.enable();
        this.formGeneral.get('acctCode')?.disable();
      } else {
        this.formGeneral.get('customer')?.disable();
        this.formGeneral.get('primaryIdentity')?.disable();
        this.formGeneral.get('customerRtnId')?.disable();
        this.formGeneral.get('acctCode')?.enable();

      }

      // Si es "Consumidor Final", establecemos los valores y deshabilitamos los campos
      if (isCustomerTypeThree) {
        this.formGeneral.get('customer')?.setValue(this.nameFinalConsumer);
        this.formGeneral.get('customerRtnId')?.setValue(this.rtnFinalConsumer);
        this.formGeneral.get('customer')?.disable();
        this.formGeneral.get('customerRtnId')?.disable();
        this.formGeneral.get('acctCode')?.disable();
      }
    }

  }


  /**
   * Se utiliza cuando el usuario cambia la sucursal para mostrar
   * los datos del código de bodega y el nombre de la agencia "Sucursal"
   *
   * @param event
   */
  async changeSucursal(event) {

    const DATA_BRANCH: string = event.target.value;

    const ARRAY_DATA_BRANCH: string[] = DATA_BRANCH.split("||");

    // Seteo de los valores
    this.idBranchOffice = ARRAY_DATA_BRANCH[0] != 'null' ? Number(ARRAY_DATA_BRANCH[0]) : null;
    this.agency = ARRAY_DATA_BRANCH[1] != 'null' ? ARRAY_DATA_BRANCH[1] : null;
    this.warehouseCode = ARRAY_DATA_BRANCH[2] != 'null' ? ARRAY_DATA_BRANCH[2] : "0";
    this.acctCodeBranchOffice = ARRAY_DATA_BRANCH[3] != 'null' ? ARRAY_DATA_BRANCH[3] : "0";

    // Setear el valor de la cuenta de facturacion  al input

    if (this.customerTypeId != 1 && this.formGeneral.get('fiscalProcess')?.value == 1) {
      const ACCTCODE_CONTROL = this.formGeneral.get('acctCode') as FormControl;
      ACCTCODE_CONTROL.setValue(this.acctCodeBranchOffice);
    }


    // Setear el valor del código de bodega al input
    const WARECODE_CONTROL = this.formGeneral.get('warehouse') as FormControl;
    const nameWarehouse = await this.getSubWareHouseByCode(this.warehouseCode);

    if (nameWarehouse != null) {
      WARECODE_CONTROL.setValue(nameWarehouse);

    } else {
      WARECODE_CONTROL.setValue(this.warehouseCode);

    }



  }

  onChangeInventoryType(event) {
    this.inventoryType = this.formGeneral.get('inventoryType').value[0].code;

    console.log(this.inventoryType);
  }

  /**
   * Se utiliza para remover un elemento de la tabla productos
   * también, se reduce los valores monetarios que este
   * contenia a los valores acumulados
   *
   * @param row
   */
  removeProduct(row: InvoiceDetail) {
    Swal.fire({
      title: 'Advertencia',
      text: `¿Desea remover este registro de la tabla?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {
        const index: number = this.invoiceDetail.findIndex(item => item.idFind === row.idFind);
        //console.log(index);

        if (index !== -1) {
          //console.log("Se encontro");

          const invoice = this.invoiceDetail[index];
          this.acumuladorSubtotal -= invoice.subtotal;
          this.acumuladorDiscount -= invoice.discount;
          this.acumuladorTax -= invoice.tax;
          this.acumuladorTotal -= invoice.amountTotal;

          // Refrescar los inputs
          const SUBTOTAL_CONTROL = this.formValidation.get('subtotal') as FormControl;
          SUBTOTAL_CONTROL.setValue(this.acumuladorSubtotal.toFixed(2));

          const DISCOUNT_CONTROL = this.formValidation.get('discount') as FormControl;
          DISCOUNT_CONTROL.setValue(this.acumuladorDiscount.toFixed(2));

          const TAX_CONTROL = this.formValidation.get('amountTax') as FormControl;
          TAX_CONTROL.setValue(this.acumuladorTax.toFixed(2));

          const TOTAL_CONTROL = this.formValidation.get('amountTotal') as FormControl;
          TOTAL_CONTROL.setValue(this.acumuladorTotal.toFixed(2));

          let totalLps: string = (this.exchangeRate * this.acumuladorTotal).toFixed(2);
          const TOTAL_LPS_CONTROL = this.formValidation.get('totalLps') as FormControl;
          TOTAL_LPS_CONTROL.setValue(totalLps);

          this.invoiceDetail.splice(index, 1);
          this.invoiceDetail = [...this.invoiceDetail];

        }
      }

    });
  }

  roundToTwoDecimals(numero: number | string): string {
    //console.log(numero);
    Big.DP = 10;
    Big.RM = Big.roundHalfUp;
    const bigNumero = new Big(numero);
    return bigNumero.round(2).toFixed(2);
  }


  async openModalDetailBlockedLines(phone: any) {
    const historicalDetail = await this.getHistoricalDetailsByPhone(phone);

    if (historicalDetail) {
      const modalRef = this.modalService.open(DetailBlockedLinesComponent, { size: 'xl', backdrop: 'static' });
      modalRef.componentInstance.historical = historicalDetail.data;

      modalRef.componentInstance.selectInsuranceClaim.subscribe((idInsuranceClaim: number) => {
        // Asigna el idInsuranceClaim al formulario de la factura
        this.formGeneral.patchValue({ idInsuranceClaim: idInsuranceClaim });
        console.log("ID:" + idInsuranceClaim);
        this.idInsuranceClaim = idInsuranceClaim;
      });
    } else {
      console.log('No se pudo obtener el detalle histórico.');
    }
  }

  /**
   * Método que tiene la logica para la creación de la factura
   *
   */
  async createInvoice() {
    Swal.fire({
      title: 'Advertencia',
      text: `¿Desea crear la factura?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {

        Swal.fire({
          title: 'Creando Factura...',
          allowOutsideClick: false,
          onBeforeOpen: () => {
            Swal.showLoading();
          }
        });

        const invoiceFormGeneral: Billing = this.formGeneral.value;
        const invoiceFormValidation: Billing = this.formValidation.value;

        let porcentajeDescuento: any = this.roundToTwoDecimals((invoiceFormValidation.discount / invoiceFormValidation.subtotal) * 100);

        // Busca el nombre del fiscalProcess basado en el id
        // Asegúrate de que invoiceFormGeneral.fiscalProcess sea un número
        const fiscalProcessName = this.fiscalProcessData.find(
          process => process.id === Number(invoiceFormGeneral.fiscalProcess)  // Convertir a número
        )?.name || '';

        const customerTypeName = this.customerTypeData.find(
          process => process.id === Number(invoiceFormGeneral.customerType)  // Convertir a número
        )?.name || '';

        console.log('Estado de los campos:', {
          primaryIdentity: this.formGeneral.get('primaryIdentity').value,
          customer: this.formGeneral.get('customer').value,
          acctCode: invoiceFormGeneral.primaryIdentity,
        });

        let billing: Billing = {
          invoiceType: invoiceFormGeneral.invoiceType,
          invoiceNo: "0",
          acctCode: this.formGeneral.get('acctCode').value,
          primaryIdentity: this.formGeneral.get('primaryIdentity').value,
          customer: this.formGeneral.get('customer').value,
          customerId: invoiceFormGeneral.customerId,
          customerRtnId: this.formGeneral.get('customerRtnId').value,
          customerAddress: invoiceFormGeneral.customerAddress,
          idBranchOffices: this.idBranchOffice,
          agency: this.agency,
          channel: 3,
          inventoryType: this.inventoryType,
          warehouse: this.warehouseCode,
          subWarehouse: invoiceFormGeneral.subWarehouse,
          seller: invoiceFormGeneral.seller,
          cashierName: this.utilService.getSystemUser(),
          subtotal: Number(invoiceFormValidation.subtotal),
          exchangeRate: invoiceFormValidation.exchangeRate,
          discount: Number(invoiceFormValidation.discount),
          discountPercentage: porcentajeDescuento,
          taxPercentage: this.taxPorcentage,
          amountTax: Number(invoiceFormValidation.amountTax),
          amountTotal: Number(invoiceFormValidation.amountTotal),
          fiscalProcess: fiscalProcessName,
          customerType: customerTypeName,
          idInsuranceClaim: this.idInsuranceClaim || null,
          invoiceDetails: this.invoiceDetail,

        }

        //console.log(invoiceFormGeneral);
        //console.log(invoiceFormValidation);
        //console.log(billing);

        if (this.invoiceDetail.length > 0) {
          console.log(billing)
          const VALIDATE_ADD_BILLING = await this.postAddBilling(billing);
          //const VALIDATE_ADD_BILLING = true;
          Swal.close();
          if (VALIDATE_ADD_BILLING) {
            this.messageEvent.emit(this.billing);
            this.closeModal();
          } else {
            this.utilService.showNotification(3, "Fallo al crear la factura, Contacte al administrador del sistema.");
          }


        } else {
          Swal.close();
          this.utilService.showNotification(1, "Debe de haber un producto como mínimo para crear la factura");
        }

      }

    })
  }




  /**
   * Método encargado de obtener la información del cliente
   * consumiendo un servicio por la cuenta de facturación
   *
   */
  async findByAcctCode() {
    const billing: Billing = this.formGeneral.value;

    const primaryIdentity = await this.getCustomerInfo(billing.acctCode);
    const selectedFiscalProcessId = this.formGeneral.get('fiscalProcess')?.value;

    console.log(selectedFiscalProcessId);
    if (selectedFiscalProcessId == 2) {
      this.openModalDetailBlockedLines(primaryIdentity);
    }
  }




  // Methods Asyncronos

  /**
   * Método que consume un servicio para obtener la información
   * del cliente por medio de la cuenta de facturación
   *
   * @param acctCode
   * @returns
   */
  getCustomerInfo(acctCode: any): Promise<string | boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.getCustomerInfo(acctCode).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {



          // Mapeamos el body del response
          let customerInfoResponse = response.body as CustomerInfoResponse;

          let dto: CustomerInfoModel = customerInfoResponse.data;

          if (dto.customerName) {
            const CUSTOMER_CONTROL = this.formGeneral.get('customer') as FormControl;
            CUSTOMER_CONTROL.setValue(dto.customerName);
          }

          if (dto.customerAddress) {
            const CUSTOMER_ADDRESS_CONTROL = this.formGeneral.get('customerAddress') as FormControl;
            CUSTOMER_ADDRESS_CONTROL.setValue(dto.customerAddress);
          }

          if (dto.customerId) {
            const CUSTOMER_ID_CONTROL = this.formGeneral.get('customerId') as FormControl;
            CUSTOMER_ID_CONTROL.setValue(dto.customerId);
          }

          if (dto.customerRtn) {
            const CUSTOMER_RTN_CONTROL = this.formGeneral.get('customerRtnId') as FormControl;
            CUSTOMER_RTN_CONTROL.setValue(dto.customerRtn);
          }

          if (dto.primaryIdentity) {
            const PRIMARY_IDENTITY_CONTROL = this.formGeneral.get('primaryIdentity') as FormControl;
            PRIMARY_IDENTITY_CONTROL.setValue(dto.primaryIdentity);
          }

          this.utilService.showNotification(0, "Datos encontrados con exito");
          resolve(dto.primaryIdentity);

        } else {
          resolve(null);
        }

      }, (error) => {
        //console.log(error);
        //console.log(error.error);
        this.resetFormGeneral();

        if (error.error) {

          if (error.error.code >= 400 && error.error.code <= 499) {

            this.utilService.showNotification(1, "No se han encontrado registros para la cuenta de facturación.");

          } else {
            this.utilService.showNotification(3, "Fallo al realizar la consulta, Contacte al administrador del sistema.");
          }

        }

        resolve(null);

      })
    });


  }


  /**
   * Método que consume un servicio para la creación
   * de una factura
   *
   */
  postAddBilling(payload: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.postAddBilling(payload).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {

          // Mapeamos el body del response
          let billingResponse = response.body as BillingResponse;

          this.billing = billingResponse.data;
          this.billing.statusCode = "Pendiente";


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
   * Método que obtiene el nombre de la bodega
   *
   */
  getSubWareHouseByCode(code: any): Promise<string> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.getSubWareHouseByCode(code).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {

          // Mapeamos el body del response
          let nameWarehouse = response.body as SubWarehouseNameResponse;




          resolve(nameWarehouse.data);

        } else {
          resolve(null);
        }

      }, (error) => {

        resolve(null);
      })
    });


  }

  /**
  * Método que obtiene el detalle de las líneas bloqueadas por teléfono
  */
  async getHistoricalDetailsByPhone(phone: any): Promise<HistoricalDetailResponse | null> {
    return new Promise((resolve, reject) => {
      this.invoiceService.getHistoricalDetailsByPhone(phone).subscribe(
        (response) => {
          if (response.status === 200) {
            // Mapeamos el body del response
            let historitalDetail = response.body as HistoricalDetailResponse;
            resolve(historitalDetail);
          } else {
            resolve(null);
          }
        },
        (error) => {
          resolve(null);
        }
      );
    });
  }


}
