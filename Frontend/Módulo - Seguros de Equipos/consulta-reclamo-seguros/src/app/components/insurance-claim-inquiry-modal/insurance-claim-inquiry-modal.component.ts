import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import * as moment from 'moment';
import { InsuranceControlResponse } from 'src/app/entities/response';
import { ExistencesModel, InsuranceClaimModel, InsuranceControlModel, PriceMasterModel, WareHouseModel } from 'src/app/models/model';
import { InsuranceClaimInquiryService } from 'src/app/services/insurance-claim-inquiry.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';
import Swal from "sweetalert2/dist/sweetalert2.js";
import { v4 as uuidv4 } from 'uuid';

@Component({
  selector: 'app-insurance-claim-inquiry-modal',
  templateUrl: './insurance-claim-inquiry-modal.component.html',
  styleUrls: ['./insurance-claim-inquiry-modal.component.css']
})
export class InsuranceClaimInquiryModalComponent implements OnInit {

  // Input y Output
  @Input() button: string;
  @Input() data: InsuranceClaimModel;
  @Input() warehouseModel: WareHouseModel[] = [];
  @Input() inventoryTypeParam: string = "";
  @Input() statusClaimParam: string = "";
  @Input() statusPhoneParam: string = "";
  @Input() existencesTypeParam: string = "";
  @Input() equipmentLineParam: string = "";
  @Input() deductibleModelParam: string = "";
  @Input() deductibleDescriptionParam: string = "";
  @Input() insuranceModelParam: string = "";
  @Input() insuranceDescriptionParam: string = "";
  @Input() channelParam: string = "";
  @Input() cashierNameParam: string = "";
  @Input() invoiceTypes: string[] = [];
  @Output() messageEvent = new EventEmitter<boolean>();

  // Hidden
  hiddeGeneral: boolean = false;
  hiddeDetail: boolean = true;

  // Tab
  active: number = 1;
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
  reasonsClaim: string[] = [];
  existencesModel: ExistencesModel[] = [];
  newModel: string = "";
  priceMasterModel: PriceMasterModel[] = [];
  serialNumberList: any[] = [];
  insuranceControl: InsuranceControlModel[] = [];

  public dropdownSettings = {
    singleSelection: true,
    text: "Selecciona una bodega",
    enableSearchFilter: true,
    primaryKey: 'id'
  };

  constructor(public utilService: UtilService, private insuranceClaimService: InsuranceClaimInquiryService, private activeModal: NgbActiveModal, private formBuilder: FormBuilder) { }

  ngOnInit(): void {

    this.getInsuranceControlByEsn(this.data.actualEsn);
    if (this.button === "see") {

    }

    if (this.button === "see" || this.button === "add") {
      //this.getSerialNumbersQuery();
      this.dataForm = this.initFormTwo();

    } else {
      this.getSerialNumbersQuery();
      this.dataForm = this.initFormTwo();

    }

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
    } else if (option === 2) {
      this.hiddeGeneral = true;
      this.hiddeDetail = false;

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
      newEsn: ['', [Validators.required]],
      newModel: ['', [Validators.required]],
      newInvType: ['', []],
      userCreate: [this.utilService.getSystemUser(), []],
      dateCreate: ['', []],
      userResolution: ['', []],
      dateResolution: ['', []],
      invoiceType: ['', [Validators.required]],
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
      newInvType: [this.data.newInvType, []],
      reasonClaim: [this.data.reasonClaim, []],
      userCreate: [this.data.userCreate, []],
      dateCreate: [this.data.dateCreate, []],
      userResolution: [this.data.userResolution, []],
      dateResolution: [this.data.dateResolution, []],
      invoiceType: [this.data.invoiceType, [Validators.required]],
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

  }

  closeModal() {
    this.activeModal.close();
  }





  // Methods Asyncronos
  getSerialNumbersQuery(): void {
    const warehouseId = this.data.warehouse;
    const inventoryTypeId = this.inventoryTypeParam;
    const itemCode = this.data.newModel;
    const subWarehouseCode = "";

    this.insuranceClaimService.getSerialNumbersQuery(itemCode, warehouseId, subWarehouseCode, inventoryTypeId).subscribe((response) => {
      if (response.status === 200) {
        const responseData = response.body;
        if (responseData.code === 1 && responseData.data.result_code === "INV000") {
          // Acceder a la lista de números de serie
          const serialNumberList = responseData.data.serial_number_list[0].serial_number_list;
          this.serialNumberList = serialNumberList; // Guardar la lista de números de serie
          this.getSerialNumbersByQuantity(); // Llamar a la función para actualizar la cantidad de números de serie
        } else {
          console.error(responseData.description); // Mostrar el mensaje de error
          this.utilService.showNotification(1, responseData.description);
          response
        }
      } else {
        console.error(response);

      }
    }, (error) => {

      this.utilService.showNotification(1, error.error.errors[0].userMessage);
    });
  }

  getSerialNumbersByQuantity(): void {
    const quantity = 1;

    if (this.serialNumberList && this.serialNumberList.length > 0 && quantity > 0) {
      // Limitar la cantidad de números de serie a la cantidad ingresada
      const limitedSerialNumbers = this.serialNumberList.slice(0, quantity).map(item => item.serialNumber);
      this.dataForm.get('newEsn').setValue(limitedSerialNumbers.join(', ')); // Unirlos como una cadena
    } else {
      this.dataForm.get('newEsn').setValue('');
    }
  }

  private cleanBillingAccount(account: string): string {
    if (account.startsWith('1-F') || account.startsWith('1-M')) {
      return account.substring(3);
    }
    return account;
  }

  confirm() {

    Swal.fire({
      title: 'Advertencia',
      text: `¿Desea Confirmar el Reclamo?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {
      if (result.value) {
        // Verifica si el formulario es válido
        if (this.dataForm.invalid) {
          this.utilService.showNotification(1, "Por favor, completa todos los campos requeridos.");
          return;
        }

        const priceAdjustment = parseFloat(this.dataForm.get('priceAdjustment').value) || 0;
        const adjustmentPremiums = parseFloat(this.dataForm.get('adjustmentPremiums').value) || 0;

        // Crea el payload a enviar para la factura
        const payload = {
          invoiceNo: "0",
          invoiceType: this.dataForm.get('invoiceType').value,
          acctCode: this.cleanBillingAccount(this.dataForm.get('billingAccount').value),
          primaryIdentity: this.dataForm.get('phone').value,
          customer: this.dataForm.get('customerName').value,
          seller: this.utilService.getSystemUser(),
          cashierName: this.cashierNameParam,
          customerId: this.dataForm.get('customerAccount').value,
          inventoryType: this.dataForm.get('newInvType').value,
          warehouse: this.dataForm.get('warehouse').value,
          subWarehouse: "POS",
          channel: this.channelParam,
          exemptAmount:  parseFloat(this.dataForm.get('deductible').value) + priceAdjustment,
          includesInsurance: "S",
          fiscalProcess: "Facturación por Reclamo de Seguros",
          uti: uuidv4(),
          invoiceDetails: [
            {
              model: this.dataForm.get('newModel').value,
              description: this.data.newModelDescription,
              quantity: 1,
              unitPrice: parseFloat(this.dataForm.get('actualPrice').value),
              serieOrBoxNumber: this.dataForm.get('newEsn').value
            },
            {
              model: this.deductibleModelParam,
              description: this.deductibleDescriptionParam,
              quantity: 1,
              unitPrice: parseFloat(this.dataForm.get('deductible').value) + priceAdjustment
            },
            {
              model: this.insuranceModelParam,
              description: this.insuranceDescriptionParam,
              quantity: 1,
              unitPrice: parseFloat(this.dataForm.get('insurancePremium').value) + adjustmentPremiums
            }
          ]
        };

        // Llama al servicio para crear la factura
        this.insuranceClaimService.postAddBilling(payload).subscribe({
          next: async (response) => {
            if (response.status === 200) {
              this.utilService.showNotification(0, "Factura creada exitosamente.");

              // Obtén el ID de la factura creada desde la respuesta
              // const invoiceId = response.data.id;
              // Aquí obtén el id del reclamo que deseas actualizar
              const data: InsuranceClaimModel = this.dataForm.value;

              // Crea el payload para la actualización del reclamo

              data.invoiceNumber = response.body.data.id;
              data.newEsn = this.dataForm.get('newEsn').value;
              data.userResolution = this.utilService.getSystemUser();
              data.invoiceType = this.dataForm.get('invoiceType').value;
              data.statusClaim = 'C'
              data.actualPrice = parseFloat(data.actualPrice.toString());
              data.deductible = parseFloat(data.deductible.toString());
              data.insurancePremium = parseFloat(data.insurancePremium.toString());
              data.newModelDescription = this.data.newModelDescription;
              const updateSuccess = await this.putInsuranceClaim(this.data.id, data);


              if (updateSuccess) {
                this.utilService.showNotification(0, "Reclamo actualizado exitosamente.");
              } else {
                this.utilService.showNotification(1, "Error al actualizar el reclamo. Intente nuevamente.");
              }

              const dataControl: InsuranceControlModel = this.insuranceControl[0];

              dataControl.insuranceStatus = 0;
              dataControl.transactionCode = 'SEG63'
              const updateControlSuccess = await this.putEquipmentInsurance(this.insuranceControl[0].id, dataControl);
              if (updateControlSuccess) {
                this.utilService.showNotification(0, "Control de Seguro actualizado exitosamente.");
              } else {
                this.utilService.showNotification(1, "Error al actualizar el registro de control. Intente nuevamente.");
              }

              this.messageEvent.emit(true); // Notifica que se ha creado la factura
              this.closeModal(); // Cierra el modal
            } else {
              this.utilService.showNotification(1, "Error al crear la factura. Intente nuevamente.");
            }
          },
          error: (error) => {
            console.error("Error al crear la factura:", error);
            this.utilService.showNotification(1, "Error al crear la factura. Intente nuevamente.");
          }
        });
      }
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

  /**
 * Método que consume un servicio para obtener la información
 * del seguro por el esn
 * 
 * @param esn 
 * @returns 
 */
  getInsuranceControlByEsn(esn: any): Promise<string | boolean> {
    return new Promise((resolve, reject) => {

      this.insuranceClaimService.getInsuranceControlByEsn(esn).subscribe((response) => {

        if (response.status === 200) {

          let insuranceControlResponse = response.body as InsuranceControlResponse;
          let data: InsuranceControlModel[] = insuranceControlResponse.data;

          if (!data || data.length === 0) {
            this.utilService.showNotification(1, "No se encontraron seguros de equipo para la Imei.");
            resolve(null);
            return;
          }

          // Buscamos los códigos de transacción
          const seg61Data = data.find(item => item.transactionCode === "SEG61");
          const seg62Data = data.find(item => item.transactionCode === "SEG62");
          const seg63Data = data.find(item => item.transactionCode === "SEG63");

          // Si encontramos SEG63, usamos sus datos; de lo contrario, usamos SEG61
          const dto = seg63Data || seg61Data || seg62Data;

          if (dto) {
            const currentDate = new Date();
            const endDate = new Date(dto.endDate);

            if (currentDate > endDate) {
              this.utilService.showNotification(1, "El seguro para la IMEI consultada ha vencido.");
              this.resetFormGeneral();
              resolve(null);
              return;
            }


            this.insuranceControl.push(dto);

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

  putEquipmentInsurance(id: any, data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consumir el servicio
      this.insuranceClaimService.putEquipmentInsurance(id, data).subscribe((response) => {

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

  formatDate(date: string | null): string {
    if (!date) {
      return '';
    }

    const formattedDate = new Date(date);
    const day = formattedDate.getDate().toString().padStart(2, '0');
    const month = (formattedDate.getMonth() + 1).toString().padStart(2, '0');
    const year = formattedDate.getFullYear();
    let hours = formattedDate.getHours();
    const ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12;
    hours = hours ? hours : 12;
    const minutes = formattedDate.getMinutes().toString().padStart(2, '0');
    const seconds = formattedDate.getSeconds().toString().padStart(2, '0');

    return `${day}/${month}/${year} ${hours}:${minutes}:${seconds} ${ampm}`;
  }
}


