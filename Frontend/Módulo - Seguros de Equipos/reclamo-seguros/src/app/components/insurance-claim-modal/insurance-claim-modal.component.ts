import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CustomerInfoResponse, ExistencesResponse, HistoricalDetailResponse, InsuranceClaimResponse, InsuranceControlResponse, PriceMasterResponse, ReasonResponse } from 'src/app/entities/response';
import { CalculateDeductibleRequest, CustomerInfoModel, ExistencesModel, getCalculateOutstandingFeesRequest, HistoricalDetailModel, InsuranceClaimModel, InsuranceControlModel, InventoryTypeModel, PriceMasterModel, ReasonModel, WareHouseModel } from 'src/app/models/model';
import { InsuranceClaimService } from 'src/app/services/insurance-claim.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';
import { Buttons } from 'src/types';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-insurance-claim-modal',
  templateUrl: './insurance-claim-modal.component.html',
  styleUrls: ['./insurance-claim-modal.component.css']
})
export class InsuranceClaimModalComponent implements OnInit {


  // Props

  // Input y Output
  @Input() button: string;
  @Input() data: InsuranceClaimModel;
  @Input() warehouseModel: WareHouseModel[] = [];
  @Input() inventoryTypeParam: string = "";
  @Input() statusClaimParam: string = "";
  @Input() statusPhoneParam: string = "";
  @Input() existencesTypeParam: string = "";
  @Input() equipmentLineParam: string = "";
  @Input() blockDaysParam: string = "";
  @Output() messageEvent = new EventEmitter<boolean>();

  // Form
  dataForm!: FormGroup;
  messages = messages;

  // Readonly
  inputReadOnly: boolean = false;

  // Dates
  creationDate: string = "";

  //Data
  esnInsuranceControl: string;
  modelInsuranceControl: string;
  inventoryTypeInsuranceControl: string;
  reasonsClaim: ReasonModel[] = [];
  existencesModel: ExistencesModel[] = [];
  newModel: string = "";
  newModelDescription: string = "";
  priceMasterModel: PriceMasterModel[] = [];
  warehouseCode: string = "";
  isVisible = false;
  isDisabled: boolean = true;
  public dropdownSettings = {
    singleSelection: true,
    text: "Selecciona una bodega",
    enableSearchFilter: true,
    primaryKey: 'id',

  };
  isTotalLoss: boolean = false;

  constructor(public utilService: UtilService, private insuranceClaimService: InsuranceClaimService, private datePipe: DatePipe, private activeModal: NgbActiveModal, private formBuilder: FormBuilder) { }

  ngOnInit(): void {

    this.getReasonsClaim();

    if (this.button === "see") {
      this.inputReadOnly = true;
    }

    if (this.button === "see" || this.button === "edit") {

      this.dataForm = this.initFormTwo();
      this.loadWarehouseAndModelData();

    } else {
      this.dataForm = this.initForm();
    }

  }


  // Methods Screen

  // Methods for initial Form

  /**
   * Se usa solo para la creación
   * 
   * @returns 
   */
  initForm(): FormGroup {
    return this.formBuilder.group({
      customerAccount: ['', []],
      serviceAccount: ['', []],
      billingAccount: ['', []],
      phone: ['', []],
      phoneStatus: ['', []],
      clientType: ['', []],
      actualPrice: [0.00.toFixed(2), []],
      actualEsn: ['', [Validators.required]],
      actualModel: ['', []],
      actualInvType: ['', []],
      reasonClaim: ['', []],
      newEsn: ['', []],
      newModel: ['', [Validators.required]],
      newModelDescription: ['', []],
      newInvType: ['', []],
      userCreate: [this.utilService.getSystemUser(), []],
      dateCreate: ['', []],
      userResolution: ['', []],
      dateResolution: ['', []],
      invoiceType: ['', []],
      invoiceLetter: ['', []],
      invoiceNumber: ['', []],
      branchAnnex: ['', []],
      statusClaim: ['', []],
      observations: ['', []],
      insurancePremium: [0.00.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      deductible: [0.00.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      bandit: ['', []],
      customerName: ['', []],
      warehouse: ['', [Validators.required]],
      workshopOrderNumber: ['', []],
      priceAdjustment: [0.00.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      adjustmentPremiums: [0.00.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      insuredSum: [0.00.toFixed(2), []],

    });
  }

  /**
   * Se usa solo para visualizar o modificar
   * 
   * @returns 
   */
  initFormTwo(): FormGroup {
    return this.formBuilder.group({
      customerAccount: [this.data.customerAccount, [Validators.required, Validators.maxLength(50)]],
      serviceAccount: [this.data.serviceAccount, [Validators.required, Validators.maxLength(50)]],
      billingAccount: [this.data.billingAccount, [Validators.required, Validators.maxLength(15)]],
      phone: [this.data.phone, [Validators.required, Validators.maxLength(15)]],
      phoneStatus: [this.data.phoneStatus, [Validators.required]],
      clientType: [this.data.clientType, []],
      actualPrice: [this.data.actualPrice.toFixed(2), []],
      actualEsn: [this.data.actualEsn, []],
      actualModel: [this.data.actualModel, []],
      actualInvType: [this.data.actualInvType, []],
      newEsn: [this.data.newEsn, []],
      newModel: [this.data.newModel, []],
      newModelDescription: [this.data.newModelDescription, []],
      newInvType: [this.data.newInvType, []],
      reasonClaim: [this.data.reasonClaim, []],
      userCreate: [this.data.userCreate, []],
      dateCreate: [this.data.dateCreate, []],
      userResolution: [this.data.userResolution, []],
      dateResolution: [this.data.dateResolution, []],
      invoiceType: [this.data.invoiceType, []],
      invoiceLetter: [this.data.invoiceLetter, []],
      invoiceNumber: [this.data.invoiceNumber, []],
      branchAnnex: [this.data.branchAnnex, []],
      statusClaim: [this.data.statusClaim, []],
      observations: [this.data.observations, []],
      insurancePremium: [this.data.insurancePremium.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      deductible: [this.data.deductible.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      bandit: [this.data.bandit, []],
      customerName: [this.data.customerName, []],
      warehouse: [this.data.warehouse, []],
      workshopOrderNumber: [this.data.workshopOrderNumber, []],
      priceAdjustment: [this.data.priceAdjustment.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      adjustmentPremiums: [this.data.adjustmentPremiums.toFixed(2), [Validators.required, this.validarNumeroDecimal]],
      insuredSum: [this.data.insuredSum.toFixed(2), []],

    });
  }

  validarNumeroDecimal(control) {
    const numeroDecimalRegExp = /^[0-9]+(\.[0-9]{1,2})?$/;
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
    this.dataForm.controls['phone'].reset();
    this.dataForm.controls['phoneStatus'].reset();
    this.dataForm.controls['billingAccount'].reset();
    this.dataForm.controls['customerAccount'].reset();
    this.dataForm.controls['serviceAccount'].reset();
    this.dataForm.controls['bandit'].reset();
    this.dataForm.controls['statusClaim'].reset();
    this.dataForm.controls['actualModel'].reset();
    this.dataForm.controls['actualInvType'].reset();
    this.dataForm.controls['customerName'].reset();
    this.dataForm.controls['insuredSum'].reset();
  }

  resetFormPrice() {
    this.dataForm.controls['deductible'].reset();
    this.dataForm.controls['insurancePremium'].reset();
    this.dataForm.controls['actualPrice'].reset();
    this.dataForm.controls['newModel'].reset();

  }

  closeModal() {
    this.activeModal.close();
  }


  private async loadWarehouseAndModelData() {
    if (!this.dataForm) {
      console.error("dataForm no está definido.");
      return;
    }

    // Cargar la bodega
    const warehouseIndex: number = this.warehouseModel.findIndex(item => item.code === this.data.warehouse);
    if (warehouseIndex !== -1) {
      this.dataForm.get('warehouse').setValue([this.warehouseModel[warehouseIndex]]);
      // Establecer el código de bodega inicial
      this.warehouseCode = this.data.warehouse;

      // Después de cargar la bodega, obtenemos las existencias
      await this.getExistencesViewByFilter();

      // Una vez que tenemos las existencias, establecemos el modelo
      if (this.data.newModel && this.existencesModel.length > 0) {
        // Buscar el modelo en las existencias
        const selectedModel = this.existencesModel.find(
          existence => existence.modelCode === this.data.newModel
        );

        if (selectedModel) {
          // Establecer el modelo y su descripción
          this.dataForm.patchValue({
            newModel: selectedModel.modelCode,
            newModelDescription: selectedModel.description
          });

          // Obtener el precio y calcular deducible y prima
          // await this.getPriceMaster();
          //await this.calculateDeductible();
          //await this.getCalculateOutstandingFees();
        }
      }
    }
  }

  onReasonChange() {
    console.log('onReasonChange called'); // Debugging

    const selectedReasonId = Number(this.dataForm.get('reasonClaim')?.value);
    const selectedReason = this.reasonsClaim.find(reason => reason.id === selectedReasonId);

    if (selectedReason) {
      this.isTotalLoss = selectedReason.reason === 'PERDIDA TOTAL';
      console.log('isTotalLoss:', this.isTotalLoss); // Debugging

      if (this.isTotalLoss) {
        this.dataForm.get('workshopOrderNumber').setValidators([Validators.required]);

      } else {

        this.dataForm.get('workshopOrderNumber').clearValidators();
      }

      // Actualizar el estado del control
      this.dataForm.get('workshopOrderNumber').updateValueAndValidity();

      this.resetFormGeneral();
      this.resetFormPrice();
    }
  }

  /**
 * Método encargado de obtener la información del seguro 
 * consumiendo un servicio por el esn
 * 
 */
  async findByEsn() {
    const insuranceClaimControl: InsuranceClaimModel = this.dataForm.value;
    const selectedReasonId = Number(insuranceClaimControl.reasonClaim);
    const selectedReason = this.reasonsClaim.find(reason => reason.id === selectedReasonId);

    // Primero, obtenemos el control de seguro por ESN
    await this.getInsuranceControlByEsn(insuranceClaimControl.actualEsn);

    // Ahora obtenemos el número de teléfono del formulario
    const phone = this.dataForm.get('phone')?.value;

    // Verificar cuántos reclamos existen para el número de teléfono
    const existingClaimsCount = await this.getExistingClaimsCountByPhone(phone);

    if (existingClaimsCount >= 3) {
      this.utilService.showNotification(1, "No puede procesar un reclamo porque ya se hicieron 3 reclamos");
      this.resetFormGeneral();

      return;
    }

    // Validación Mora
    const billingAccount = this.dataForm.get('billingAccount')?.value;
    const sellProduct = await this.fetchSellProductByBillingAccount(billingAccount);

    if (sellProduct && sellProduct.data && sellProduct.data.assetStatus == "EN MORA2") {
      this.utilService.showNotification(1, "No puede procesar el reclamo por mora");
      this.resetFormGeneral();
      return;
    }

    // Continuar con la lógica independientemente del motivo
    if (selectedReason.reason === 'PERDIDA TOTAL') {
      const customerAccountValue = this.dataForm.get('customerAccount')?.value;
      await this.getCustomerInfo(customerAccountValue);
      return;
    }

    const historicalResult = await this.getHistoricalDetailByEsn(insuranceClaimControl.actualEsn);

    if (historicalResult) {
      await this.getInsuranceControlByEsn(insuranceClaimControl.actualEsn);
      const customerAccountValue = this.dataForm.get('customerAccount')?.value;
      await this.getCustomerInfo(customerAccountValue);
    }
  }

  // Methods Asyncronos

  /**
   * Método que consume un servicio para obtener la información
   * del seguro por el esn
   * 
   * @param esn 
   * @returns 
   */
  getInsuranceControlByEsn(esn: any): Promise<string | boolean> {
    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.insuranceClaimService.getInsuranceControlByEsn(esn).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {
          // Mapeamos el body del response
          let insuranceControlResponse = response.body as InsuranceControlResponse;
          let data: InsuranceControlModel[] = insuranceControlResponse.data; // Asegúrate de que esto es un arreglo

          // Verificamos si no hay datos
          if (!data || data.length === 0) {
            this.utilService.showNotification(1, "No se encontraron seguros de equipo para la Imei.");
            //this.resetFormGeneral();
            resolve(null);
            return;
          }

          // Buscamos los códigos de transacción
          const seg61Data = data.find(item => item.transactionCode === "SEG61");
          const seg62Data = data.find(item => item.transactionCode === "SEG62");
          const seg63Data = data.find(item => item.transactionCode === "SEG63");

          // Si encontramos SEG62, devolvemos el mensaje correspondiente
          if (seg62Data) {
            this.utilService.showNotification(1, "No tiene seguro activo");
            this.resetFormGeneral();
            resolve(null);
            return;
          }

          // Si encontramos SEG63, usamos sus datos; de lo contrario, usamos SEG61
          const dto = seg63Data || seg61Data;

          if (dto) {
            const currentDate = new Date();
            const endDate = new Date(dto.endDate);

            if (currentDate > endDate) {
              this.utilService.showNotification(1, "El seguro para la IMEI consultada ha vencido.");
              this.resetFormGeneral();
              resolve(null);
              return;
            }

            if (dto.insuranceStatus === 0) {
              this.utilService.showNotification(1, "El seguro para la IMEI consultada esta inactivo.");
              this.resetFormGeneral();
              resolve(null);
              return;
            }

            // Asignamos los valores a los controles del formulario
            this.assignFormValues(dto);

            this.utilService.showNotification(0, "Datos encontrados con éxito");
            resolve(dto.esn);
          } else {
            // Si no hay datos de SEG61 o SEG63
            this.utilService.showNotification(1, "No se encontraron seguros de equipo para la Imei.");
            resolve(null);
          }
        } else {
          resolve(null);
        }

      }, (error) => {
        this.resetFormGeneral();

        if (error.error) {
          if (error.error.code) {
            this.utilService.showNotification(1, "No se encontraron seguros de equipo para la Imei.");
          } else {
            this.utilService.showNotification(3, "Fallo al realizar la consulta, Contacte al administrador del sistema.");
          }
        }

        resolve(null);
      });
    });
  }

  /**
   * Método para asignar los valores a los controles del formulario
   * @param dto 
   */
  private async assignFormValues(dto: InsuranceControlModel) {
    const PHONE_CONTROL = this.dataForm.get('phone');
    if (PHONE_CONTROL) {
      PHONE_CONTROL.setValue(dto.phoneNumber);
    } else {
      console.error("Control 'phone' no encontrado.");
    }

    const PHONE_STATUS_CONTROL = this.dataForm.get('phoneStatus');
    if (PHONE_STATUS_CONTROL) {
      PHONE_STATUS_CONTROL.setValue(this.statusPhoneParam);
    } else {
      console.error("Control 'phoneStatus' no encontrado.");
    }

    const STATUS_CLAIM_CONTROL = this.dataForm.get('statusClaim');
    if (STATUS_CLAIM_CONTROL) {
      STATUS_CLAIM_CONTROL.setValue(this.statusClaimParam);
    } else {
      console.error("Control 'statusClaim' no encontrado.");
    }

    const INVENTORY_TYPE_CONTROL = this.dataForm.get('newInvType');
    if (INVENTORY_TYPE_CONTROL) {
      INVENTORY_TYPE_CONTROL.setValue(this.inventoryTypeParam);
    } else {
      console.error("Control 'newInvType' no encontrado.");
    }

    if (dto.billingAccount) {
      const BILLING_ACCOUNT_CONTROL = this.dataForm.get('billingAccount');
      if (BILLING_ACCOUNT_CONTROL) {
        BILLING_ACCOUNT_CONTROL.setValue(dto.billingAccount);
      } else {
        console.error("Control 'billingAccount' no encontrado.");
      }
    }

    if (dto.customerAccount) {
      const CUSTOMER_ACCOUNT_CONTROL = this.dataForm.get('customerAccount');
      if (CUSTOMER_ACCOUNT_CONTROL) {
        CUSTOMER_ACCOUNT_CONTROL.setValue(dto.customerAccount);
      } else {
        console.error("Control 'customerAccount' no encontrado.");
      }
    }

    if (dto.serviceAccount) {
      const SERVICE_ACCOUNT_CONTROL = this.dataForm.get('serviceAccount');
      if (SERVICE_ACCOUNT_CONTROL) {
        SERVICE_ACCOUNT_CONTROL.setValue(dto.serviceAccount);
      } else {
        console.error("Control 'serviceAccount' no encontrado.");
      }
    }

    if (dto.equipmentModel) {
      const ACTUAL_MODEL_CONTROL = this.dataForm.get('actualModel');
      if (ACTUAL_MODEL_CONTROL) {
        ACTUAL_MODEL_CONTROL.setValue(dto.equipmentModel);
      } else {
        console.error("Control 'actualModel' no encontrado.");
      }
    }

    if (dto.inventoryTypeAs) {
      const ACTUAL_INV_TYPE_CONTROL = this.dataForm.get('actualInvType');
      if (ACTUAL_INV_TYPE_CONTROL) {
        ACTUAL_INV_TYPE_CONTROL.setValue(dto.inventoryTypeAs);
      } else {
        console.error("Control 'actualInvType' no encontrado.");
      }
    }

    const model = dto.equipmentModel; 
    const inventoryType = this.inventoryTypeParam;

    const priceSuccess = await this.getPriceMaster(inventoryType, model);
    if (priceSuccess && this.priceMasterModel.length > 0) {
      const selectedPriceMaster = this.priceMasterModel[0];
      this.dataForm.get('insuredSum')?.setValue(selectedPriceMaster.price.toFixed(2)); 
  }
    
  }

  /**
  * Método que consume un servicio para obtener la información
  * del cliente por medio de la cuenta de facturación
  * 
  * @param acctCode 
  * @returns 
  */
  getCustomerInfo(serviceAccount: any): Promise<string | boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.insuranceClaimService.getCustomerInfo(serviceAccount).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {

          // Mapeamos el body del response
          let customerInfoResponse = response.body as CustomerInfoResponse;

          let dto: CustomerInfoModel = customerInfoResponse.data;

          if (dto.customerName) {
            const CUSTOMER_CONTROL = this.dataForm.get('customerName') as FormControl;
            CUSTOMER_CONTROL.setValue(dto.customerName);
          }

          //this.utilService.showNotification(0, "Datos encontrados con exito");
          resolve(dto.customerName);

        } else {
          resolve(null);
        }

      }, (error) => {
        //console.log(error);
        //console.log(error.error);
        this.resetFormGeneral();

        if (error.error) {

          if (error.error.code >= 400 && error.error.code <= 499) {

            this.utilService.showNotification(1, "No se ha encontrado el nombre del cliente para la cuenta de cliente.");

          } else {
            this.utilService.showNotification(3, "Fallo al realizar la consulta, Contacte al administrador del sistema.");
          }

        }

        resolve(null);

      })
    });


  }

  /**
   * Método que consume un servicio para obtener la información
   * del seguro por el esn
   * 
   * @param esn 
   * @returns 
   */
  getHistoricalDetailByEsn(esn: any): Promise<string | boolean> {
    return new Promise((resolve, reject) => {
      // Se llama al método del servicio
      this.insuranceClaimService.getHistoricalDetailsByEsn(esn).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {
          // Mapeamos el body del response
          let historicalResponse = response.body as HistoricalDetailResponse;
          let data: HistoricalDetailModel[] = historicalResponse.data;

          // Verificamos si no hay datos o si el tipobloqueo no es "ROBO"
          if (!data || data.length === 0 || !this.isTipobloqueoValid(data[0].tipobloqueo)) {
            this.utilService.showNotification(1, "No se puede procesar el seguro del equipo porque no tiene un reporte por robo.");
            this.resetFormGeneral();
            resolve(null);
            return;
          }


          // Validar el campo fechaBloqueo
          const dateBlock = new Date(data[0].fechaBloqueo);
          const currentDate = new Date();
          const differenceInDays = Math.floor((currentDate.getTime() - dateBlock.getTime()) / (1000 * 3600 * 24));


          const blockDays = parseInt(this.blockDaysParam, 10);


          if (differenceInDays > blockDays) {
            this.utilService.showNotification(1, "No se puede procesar el reclamo porque el bloqueo es mayor a " + blockDays + " días.");
            this.resetFormGeneral();
            resolve(null);
            return;
          }

          // Si hay datos y el tipobloqueo es válido, asignamos el valor
          if (data[0].tipobloqueo) {
            const BANDIT_CONTROL = this.dataForm.get('bandit') as FormControl;
            BANDIT_CONTROL.setValue(data[0].tipobloqueo);
          }

          //this.utilService.showNotification(0, "Datos encontrados con éxito");
          resolve(data[0].esnImei);
        } else {
          resolve(null);
        }

      }, (error) => {
        this.resetFormGeneral();

        if (error.error) {
          if (error.error.code) {
            this.utilService.showNotification(1, "No se puede procesar el seguro del equipo porque no tiene un reporte por robo.");
          } else {
            this.utilService.showNotification(3, "Fallo al realizar la consulta, Contacte al administrador del sistema.");
          }
        }

        resolve(null);
      });
    });
  }

  /**
   * Método para validar el campo tipobloqueo
   * @param tipobloqueo 
   * @returns boolean
   */
  private isTipobloqueoValid(tipobloqueo: string): boolean {
    return tipobloqueo && (tipobloqueo.toUpperCase() === "ROBO");
  }

  /**
* Método encargado de obtener los reclamos por telefono
* 
*/
  async getExistingClaimsCountByPhone(phone: string): Promise<number> {
    return new Promise((resolve) => {
      this.insuranceClaimService.getInsuranceClaimByPhone(phone).subscribe((response) => {
        if (response.status === 200) {
          const claimsResponse = response.body as InsuranceClaimResponse;
          const currentYear = new Date().getFullYear();

          // Filtrar reclamos que sean del año actual
          const claimsThisYear = claimsResponse.data.filter(claim => {
            const claimDate = new Date(claim.dateCreate);
            return claimDate.getFullYear() === currentYear && (claim.statusClaim === 'P' || claim.statusClaim === 'C');
          });

          resolve(claimsThisYear.length);
        } else {
          resolve(0);
        }
      }, () => {
        resolve(0);
      });
    });
  }

  /**
  * Método encargado de obtener los motivos
  * 
  */
  getReasonsClaim(): Promise<boolean> {
    return new Promise((resolve, reject) => {
      // Se llama al método del servicio
      this.insuranceClaimService.getReason().subscribe((response) => {
  
        // Validamos si responde con un 200
        if (response.status === 200) {
  
          // Vaciamos las razones anteriores
          this.reasonsClaim = [];
  
          // Mapeamos el body del response
          let reasonResponse = response.body as ReasonResponse;
  
          // Filtramos los datos cuyo status sea igual a 1 y los agregamos a reasonsClaim
          this.reasonsClaim = reasonResponse.data
            .filter((resource) => resource.status === 1)
            .map((resourceMap) => {
              let dto: ReasonModel = resourceMap;
              return dto;
            });
  
          // Actualizamos el array para reflejar los cambios en la vista
          this.reasonsClaim = [...this.reasonsClaim];
          resolve(true);
  
        } else {
          resolve(false);
        }
  
      }, (error) => {
        resolve(false);
      });
    });
  }


  async changeWarehouseAndInventory(event) {
    const warehouseValue = this.dataForm.get('warehouse').value;

    // Si no hay valor seleccionado y estamos en modo edición, mantener la bodega original
    if (!warehouseValue && this.button === 'edit') {
      this.warehouseCode = this.data.warehouse;
    } else {
      // Si hay una nueva selección, usar el nuevo código
      this.warehouseCode = warehouseValue[0]?.code || this.data.warehouse;
    }

    if (this.warehouseCode) {
      await this.getExistencesViewByFilter();
    }
  }


  /**
  * Método encargado de obtener el precio 
  * 
  * @returns 
  */

  getPriceMaster(inventoryType: any, model: any): Promise<boolean> {
    return new Promise((resolve) => {

      // Verifica si el modelo y el tipo de inventario están seleccionados
      if (!model || !inventoryType) {
        return resolve(false);
      }

      this.insuranceClaimService.getPriceMasterByModelAndInventoryType(inventoryType, model).subscribe({
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
              const PRICE_CONTRO = this.dataForm.get('actualPrice') as FormControl;
              PRICE_CONTRO.setValue('0.00');
              const INSURED_SUM = this.dataForm.get('insuredSum') as FormControl;
              INSURED_SUM.setValue('0.00');
              this.utilService.showNotification(1, "No se encontraron precios para el modelo y tipo de inventario seleccionados.");
              resolve(false);
            }
          } else {
            const PRICE_CONTRO = this.dataForm.get('actualPrice') as FormControl;
            PRICE_CONTRO.setValue('0.00');
            const INSURED_SUM = this.dataForm.get('insuredSum') as FormControl;
            INSURED_SUM.setValue('0.00');
            this.utilService.showNotification(1, "No se encontraron precios para el modelo y tipo de inventario seleccionados.");
            resolve(false);
          }
        },
        error: () => {
          const PRICE_CONTRO = this.dataForm.get('actualPrice') as FormControl;
          PRICE_CONTRO.setValue('0.00');
          const INSURED_SUM = this.dataForm.get('insuredSum') as FormControl;
          INSURED_SUM.setValue('0.00');
          if (!this.priceMasterModel.length) {
            this.utilService.showNotification(1, "No se encontraron precios para el modelo y tipo de inventario seleccionados.");
          }
          resolve(false);
        }
      });
    });
  }


  /**
  * Método encargado de obtener las existencias
  * 
  * @returns 
  */
  getExistencesViewByFilter(): Promise<boolean> {
    return new Promise((resolve, reject) => {
      const warehouseId = this.dataForm.get('warehouse').value[0].id;

      this.insuranceClaimService.getExistencesViewByFilter(warehouseId, this.existencesTypeParam, this.equipmentLineParam).subscribe(
        (response) => {
          if (response.status === 200) {
            const existenceResponse = response.body as ExistencesResponse;

            if (existenceResponse.data && existenceResponse.data.length > 0) {
              // Filtrar los recursos por el tipo de inventario
              this.existencesModel = existenceResponse.data
                .filter((resourceMap) => resourceMap.inventoryType === this.inventoryTypeParam);

              // Verificar si hay datos después del filtrado
              if (this.existencesModel.length > 0) {
                this.utilService.showNotification(0, "Datos encontrados con éxito");
                resolve(true);
              } else {
                this.utilService.showNotification(1, "No se encontraron existencias que cumplan con el tipo de inventario especificado.");
                this.resetFormPrice();
                resolve(false);
              }
            } else {
              this.utilService.showNotification(1, "No se encontraron existencias para la bodega.");
              this.existencesModel = [];
              this.resetFormPrice();
              resolve(false);
            }
          } else {
            console.error("Error en la respuesta del servidor:", response);
            resolve(false);
          }
        },
        (error) => {
          console.error("Error al consultar existencias:", error);
          resolve(false);
        }
      );
    });
  }
  async calculateDeductible() {
    const insuranceClaimControl: InsuranceClaimModel = this.dataForm.value;

    // Obtener la ESN actual
    const actualEsn = insuranceClaimControl.actualEsn;

    // Lógica para determinar el valor de quantity
    const existingClaimsCount = await this.getExistingClaimsCount(actualEsn);

    let quantity = 1; // Valor por defecto
    if (existingClaimsCount === 1) {
      quantity = 2;
    } else if (existingClaimsCount === 2) {
      quantity = 3;
    } else if (existingClaimsCount > 3) {
      this.utilService.showNotification(1, "Ya se realizaron todos los reclamos para esa IMEI");
      return; // Salimos del método si hay más de 3 reclamos
    }

    // Crear el objeto de solicitud para el deducible
    const requestData: CalculateDeductibleRequest = {
      model: insuranceClaimControl.newModel,
      reasonId: Number(this.dataForm.get('reasonClaim')?.value),
      price: this.dataForm.get('insuredSum').value,
      quantity: quantity
    };

    // Llamar al servicio para calcular el deducible
    const result = await this.getCalculateDeductible(requestData);
    if (result && result.code === 1) { // Verifica que la respuesta sea exitosa
      this.utilService.showNotification(0, "Deducible calculado exitosamente");

      // Asigna el valor calculado al control 'deductible'
      this.dataForm.get('deductible')?.setValue(result.data.totalDeductible);
    } else {
      this.utilService.showNotification(1, "Error al calcular el deducible: No se encontraron registros para el modelo seleccionado");
      this.dataForm.get('deductible')?.setValue('0.00');
    }
  }

  /**
   * Método para obtener el número de reclamos existentes para una ESN
   * @param esn 
   * @returns 
   */
  async getExistingClaimsCount(esn: string): Promise<number> {
    return new Promise((resolve) => {
      this.insuranceClaimService.getInsuranceClaimByEsn(esn).subscribe((response) => {
        if (response.status === 200) {
          const claimsResponse = response.body as InsuranceClaimResponse;
          // Filtrar reclamos que no tengan statusClaim "E"
          const validClaims = claimsResponse.data.filter(claim => claim.statusClaim !== 'E');
          resolve(validClaims.length);
        } else {
          resolve(0);
        }
      }, () => {
        resolve(0);
      });
    });
  }

  /**
  * Método para obtener el producto vendido por cuenta de facturación
  * @returns Promise<any>
  */
  async fetchSellProductByBillingAccount(billingAccount: any): Promise<any> {

    // Validar y modificar el billingAccount si comienza con "1-F"
    let modifiedBillingAccount = billingAccount;
    if (modifiedBillingAccount?.startsWith("1-F")) {
      modifiedBillingAccount = modifiedBillingAccount.substring(3);
    }

    try {
      const response = await this.insuranceClaimService.getSellProductByBillingAccount(modifiedBillingAccount).toPromise();
      if (response.status === 200) {
        return response.body;
      } else {
        this.utilService.showNotification(1, "No se encontraron productos vendidos para la cuenta de facturación proporcionada.");
        return null;
      }
    } catch (error) {
      console.error("Error al obtener el producto vendido:", error);
      this.utilService.showNotification(1, "Error al consultar el producto vendido. Intente nuevamente.");
      return null;
    }
  }

  /**
   * Método que consume un servicio para obtener el deducible
   * 
   * @param data 
   * @returns 
   */
  getCalculateDeductible(data: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.insuranceClaimService.getCalculateDeductible(data).subscribe((response) => {
        if (response.status === 200) {
          resolve(response.body);
        } else {
          resolve(null);
        }
      }, (error) => {
        resolve(null);
      });
    });
  }

  /**
   * Método que consume un servicio para obtener la prima de seguro
   * 
   * @param data 
   * @returns 
   */
  getCalculateOutstandingFees(): Promise<any> {
    return new Promise((resolve, reject) => {
      const insuranceClaimControl: InsuranceClaimModel = this.dataForm.value;

      // Obtener la ESN actual
      const actualEsn = insuranceClaimControl.actualEsn;

      // Obtener la fecha actual
      const currentDate = new Date();

      // Formatear la fecha actual en el formato ISO 8601
      const formattedDate = currentDate.toISOString();

      // Crear el objeto de solicitud para el deducible
      const requestData: getCalculateOutstandingFeesRequest = {
        equipmentModel: insuranceClaimControl.actualModel,
        esn: actualEsn,
        startDate: formattedDate
      };

      this.insuranceClaimService.getCalculateOutstandingFees(requestData).subscribe((response) => {

        if (response.status === 200) {
          // Accede a response.body para obtener el código y los datos
          if (response.body.code === 1) { // Verifica que la respuesta sea exitosa
            this.utilService.showNotification(0, "Prima de Seguro calculada exitosamente");

            // Asigna el valor calculado al control 'insurancePremium'
            const formattedPremium = parseFloat(response.body.data.totalPeriods).toFixed(2);
            this.dataForm.get('insurancePremium')?.setValue(formattedPremium);

          } else {

            this.utilService.showNotification(1, "Error al calcular la prima de seguro: No se encontraron registros para el modelo seleccionado");
            this.dataForm.get('insurancePremium')?.setValue('0.00');
          }
          resolve(response.body);
        } else {
          this.dataForm.get('insurancePremium')?.setValue('0.00');
          resolve(null);
        }
      }, (error) => {
        //this.utilService.showNotification(1, "Error en la solicitud al servidor");
        this.dataForm.get('insurancePremium')?.setValue('0.00');
        resolve(null);
      });
    });
  }
  /**
  * Método para mostrar la data selecciona en
  * el select de Código de Servicio
  * 
  */
  changeCodeService(event) {
    const selectedModelCode: string = event.target.value;
    const selectedObject = this.existencesModel.find((existence) => existence.modelCode === selectedModelCode);

    const model = this.dataForm.get('newModel').value;
    const inventoryType = this.inventoryTypeParam;

    // Llama a getPriceMaster y espera su resultado
    this.getPriceMaster(inventoryType, model).then(success => {
      if (success) {
        const priceControl = this.dataForm.get('actualPrice') as FormControl;

        if (this.priceMasterModel.length > 0) {
          const selectedPriceMaster = this.priceMasterModel[0];
          priceControl.setValue(selectedPriceMaster.price.toFixed(2));
        }
      }

      // Si se encontró el objeto seleccionado, actualiza los controles del formulario
      if (selectedObject) {
        this.dataForm.patchValue({
          newModel: selectedObject.modelCode,
          newModelDescription: selectedObject.description
        });
      }

      // Llama a las funciones después de que se haya procesado el precio
      this.calculateDeductible();
      this.getCalculateOutstandingFees();
    });
  }

  crudInsuranceClaim() {
    let action: string = this.button === 'add' ? "crear" : "modificar";

    Swal.fire({
      title: 'Advertencia',
      text: `¿Desea ${action} el reclamo?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {

        const data: InsuranceClaimModel = this.dataForm.value;
        data.warehouse = this.warehouseCode || this.data.warehouse;
        data.actualPrice = parseFloat(data.actualPrice.toString());
        data.deductible = parseFloat(data.deductible.toString());
        data.insurancePremium = parseFloat(data.insurancePremium.toString());
        data.insuredSum = parseFloat(data.insuredSum.toString());

        if (this.button === 'add') {

          const VALIDATE_CREATION = await this.postInsuranceClaim(data);

          if (VALIDATE_CREATION) {
            this.utilService.showNotification(0, "El reclamo se creo exitosamente");
            this.messageEvent.emit(true);
            this.closeModal();
          }

        } else {



          const VALIDATE_UPDATION = await this.putInsuranceClaim(this.data.id, data);

          if (VALIDATE_UPDATION) {
            this.utilService.showNotification(0, "El reclamo se modifico exitosamente");

            this.messageEvent.emit(true);
            this.closeModal();

          }


        }


      }

    })
  }

  // Methods Services

  /**
  * Método que consume un servicio para crear un reclamo de seguros
  * 
  * @param data 
  * @returns 
  */
  postInsuranceClaim(data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.insuranceClaimService.postInsuranceClaim(data).subscribe((response) => {

        if (response.status === 200) {
          resolve(true);
        } else {
          resolve(false);
        }

      }, (erro) => {
        resolve(false);
      });

    });

  }

  /**
   * Método que consume un servicio para actualizar un reclamo de seguros
   * 
   * @param id 
   * @param data 
   * @returns 
   */
  putInsuranceClaim(id: any, data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.insuranceClaimService.putInsuranceClaim(id, data).subscribe((response) => {

        if (response.status === 200) {
          resolve(true);
        } else {
          resolve(false);
        }

      }, (erro) => {
        resolve(false);
      });

    });

  }


}
