import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { InsuranceClaimInquiryModalComponent } from 'src/app/components/insurance-claim-inquiry-modal/insurance-claim-inquiry-modal.component';
import { InsuranceClaimResponse, WarehouseResponse, ConfigParameterResponse, InvoiceTypeResponse, InsuranceClaimResponsePaginated } from 'src/app/entities/response';
import { ConfigParameter, InsuranceClaimModel, InvoiceTypesModel, ItemSelect, WareHouseModel } from 'src/app/models/model';
import { InsuranceClaimInquiryService } from 'src/app/services/insurance-claim-inquiry.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-insurance-claim-inquiry',
  templateUrl: './insurance-claim-inquiry.component.html',
  styleUrls: ['./insurance-claim-inquiry.component.css']
})
export class InsuranceClaimInquiryComponent implements OnInit {

  // Table
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  rows: InsuranceClaimModel[] = [];
  rows2: InsuranceClaimModel[] = [];
  public dropDownListWareHouse: ItemSelect[] = [];
  public dropDownListInventory: ItemSelect[] = [];
  inventoryTypeParam: string = "";
  statusClaimParam: string = "";
  statusPhoneParam: string = "";
  existencesTypeParam: string = "";
  equipmentLineParam: string = "";
  deductibleModelParam: string = "";
  deductibleDescriptionParam: string = "";
  insuranceModelParam: string = "";
  insuranceDescriptionParam: string = "";
  channelParam: string = "";
  cashierNameParam: string = "";
  invoiceTypes: string[] = []; // Tipos de Facturas

  selectedOption: string = 'option1'; // 'option1' para IMEI, 'option2' para Teléfono
  inputText: string = ''; // Para almacenar el valor del input


  // Propiedades de paginación
  currentPage = 0;
  pageSize = 20;
  totalElements = 0;
  totalPages = 0;

  constructor(
    private insuranceClaimService: InsuranceClaimInquiryService,
    private modalService: NgbModal,
    public utilService: UtilService
  ) {

  }



  ngOnInit() {
    this.getInsuranceClaim(this.currentPage, this.pageSize);
    this.configparametersById(1000);
    this.getInvoiceType();

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
    this.getInsuranceClaim();
  }



  // Función para obtener el texto del estado
  getStatusText(status: string): string {
    switch (status) {
      case 'P':
        return 'En Proceso';
      case 'C':
        return 'Confirmado';
      case 'E':
        return 'Eliminado';
      case 'A':
        return 'Anulado';
      default:
        return 'Desconocido'; // O cualquier otro valor por defecto
    }
  }

  // Función para obtener el color del estado
  getStatusColor(status: string): string {
    switch (status) {
      case 'P':
        return 'blue'; // Color azul
      case 'C':
        return 'green'; // Color verde
      case 'E':
        return 'red'; // Color rojo
      case 'A':
        return 'black'; // Color negro
      default:
        return 'black'; // Color por defecto
    }
  }

  getStatusColorClass(statusColor: string | undefined): string {
    switch (statusColor) {
      case 'P':
        return 'blue-text';
      case 'C':
        return 'green-text';
      case 'E':
        return 'red-text';
      case 'A':
        return 'black-text';
      default:
        return 'red-text';
    }
  }


  /**
 * Método para abrir modales según la acción
 * 
 * @param button 
 * @param row 
 */
  openModal(button: string, row: InsuranceClaimModel = null) {

    const modalRef = this.modalService.open(InsuranceClaimInquiryModalComponent, {
      size: "xl",

    });

    modalRef.componentInstance.data = row;
    modalRef.componentInstance.button = button;
    modalRef.componentInstance.warehouseModel = this.dropDownListWareHouse;
    modalRef.componentInstance.inventoryTypeParam = this.inventoryTypeParam;
    modalRef.componentInstance.statusClaimParam = this.statusClaimParam;
    modalRef.componentInstance.statusPhoneParam = this.statusPhoneParam;
    modalRef.componentInstance.existencesTypeParam = this.existencesTypeParam;
    modalRef.componentInstance.equipmentLineParam = this.equipmentLineParam;
    modalRef.componentInstance.deductibleModelParam = this.deductibleModelParam;
    modalRef.componentInstance.deductibleDescriptionParam = this.deductibleDescriptionParam;
    modalRef.componentInstance.insuranceModelParam = this.insuranceModelParam;
    modalRef.componentInstance.insuranceDescriptionParam = this.insuranceDescriptionParam;
    modalRef.componentInstance.channelParam = this.channelParam;
    modalRef.componentInstance.cashierNameParam = this.cashierNameParam;
    modalRef.componentInstance.invoiceTypes = this.invoiceTypes;
    modalRef.componentInstance.messageEvent.subscribe((reload: boolean) => {

      if (reload) {
        this.reloadRows();
      }

    });

  }

  // Methods Services

  // Método para manejar el cambio de página
  async onPageChange(event: any) {
    this.currentPage = event.offset;
    await this.getInsuranceClaim(this.currentPage, this.pageSize);
  }

  /**
* Método encargado de obtener los registros de los reclamos de seguro
* 
*/
getInsuranceClaim(page?: any, size?: any) {

  // Se llama e método del servicio
  this.insuranceClaimService.getInsuranceClaim(page, size).subscribe((response) => {

    // Validamos si responde con un 200
    if (response.status === 200) {

      // Mapeamos el body del response
      let insuranceClaimResponse = response.body as InsuranceClaimResponsePaginated;

      // Actualizar la información de paginación
      this.totalElements = insuranceClaimResponse.data.totalElements;
      this.totalPages = insuranceClaimResponse.data.totalPages;
      this.currentPage = insuranceClaimResponse.data.number;

      // Agregamos los valores a los rows
      this.rows = insuranceClaimResponse.data.content.map((resourceMap) => {

        let dto: InsuranceClaimModel = resourceMap;

        return dto;

      });

      this.loadingIndicator = false;
      this.rows = [...this.rows];
      this.rows2 = [...this.rows];
      if (this.rows.length > 0) {
        this.utilService.showNotification(0, "Datos cargados");
      }

    }

  }, (error) => {

  })
}
  findByEsnOrPhone(): void {

    if (this.selectedOption === 'option1') {
      this.insuranceClaimService.getInsuranceClaimByEsn(this.inputText).subscribe(
        response => {
          if (response.status === 200) {
            const insuranceClaimResponse = response.body as InsuranceClaimResponse;

            if (insuranceClaimResponse.data && insuranceClaimResponse.data.length > 0) {
              this.utilService.showNotification(0, "Datos cargados");
              this.rows = insuranceClaimResponse.data; // Asignar datos a la tabla
            } else {
              this.utilService.showNotification(1, `No se encontraron datos para la IMEI: ${this.inputText}`);
              this.rows = []; // Limpiar tabla
            }
          }
        },
        error => {
          this.utilService.showNotification(1, `No se encontraron datos para la IMEI: ${this.inputText}`);
          this.rows = []; // Limpiar tabla en caso de error
        }
      );
    } else if (this.selectedOption === 'option2') {
      this.insuranceClaimService.getInsuranceClaimByPhone(this.inputText).subscribe(
        response => {
          if (response.status === 200) {
            const insuranceClaimResponse = response.body as InsuranceClaimResponse;

            if (insuranceClaimResponse.data && insuranceClaimResponse.data.length > 0) {
              this.utilService.showNotification(0, "Datos cargados");
              this.rows = insuranceClaimResponse.data; // Asignar datos a la tabla
            } else {
              this.utilService.showNotification(1, `No se encontraron reclamos por Teléfono: ${this.inputText}`);
              this.rows = []; // Limpiar tabla
            }
          }
        },
        error => {
          this.utilService.showNotification(1, `No se encontraron reclamos por Teléfono: ${this.inputText}`);
          this.rows = []; // Limpiar tabla en caso de error
        }
      );
    }
  }

  deleteInsuranceClaim(row: any) {
    let action: string = "Anular";

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
        // Llamar al servicio para eliminar el reclamo
        this.insuranceClaimService.deleteInsuranceClaim(row.id).subscribe(
          response => {
            if (response.status === 200) {
              this.utilService.showNotification(0, "El reclamo se anulo exitosamente");
              window.location.reload();

            }
          },
          error => {
            console.error('Error al eliminar el reclamo:', error);
            this.utilService.showNotification(1, "No se pudo eliminar el reclamo");
          }
        );
      }
    });
  }


  /**
  * Método encargado de obtener los parámetros de la pantalla
  * 
  */
  configparametersById(id: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.insuranceClaimService.configparametersById(id).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {

          // Mapeamos el body del response
          let configParameterResponse = response.body as ConfigParameterResponse;
          //console.log(configParameterResponse);

          // Agregamos los valores a los rows

          configParameterResponse.data.map((resourceMap, configError) => {

            let dto: ConfigParameter = resourceMap;

            if (dto.parameterType == "INVENTORY_TYPE") {
              this.inventoryTypeParam = String(dto.parameterValue);
            }

            if (dto.parameterType == "STATUS_COLGANTE") {
              this.statusClaimParam = String(dto.parameterValue);
            }


            if (dto.parameterType == "STATUS_PHONE") {
              this.statusPhoneParam = String(dto.parameterValue);
            }


            if (dto.parameterType == "EXISTENCES_TYPE") {
              this.existencesTypeParam = String(dto.parameterValue);
            }


            if (dto.parameterType == "EXISTENCES_EQUIPMENT_LINE") {
              this.equipmentLineParam = String(dto.parameterValue);
            }

            if (dto.parameterType == "DEDUCTIBLE_MODEL") {
              this.deductibleModelParam = String(dto.parameterValue);
            }

            if (dto.parameterType == "DEDUCTIBLE_DESCRIPTION") {
              this.deductibleDescriptionParam = String(dto.parameterValue);
            }

            if (dto.parameterType == "INSURANCE_PREMIUM_MODEL") {
              this.insuranceModelParam = String(dto.parameterValue);
            }

            if (dto.parameterType == "INSURANCE_PREMIUM_DESCRIPTION") {
              this.insuranceDescriptionParam = String(dto.parameterValue);
            }

            if (dto.parameterType == "CHANNEL") {
              this.channelParam = String(dto.parameterValue);
            }

            if (dto.parameterType == "CASHIER_NAME") {
              this.cashierNameParam = String(dto.parameterValue);
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
  * Método encargado de obtener los parámetros de los tipos de facturas
  * 
  */
  getInvoiceType(): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.insuranceClaimService.getInvoiceType().subscribe((response) => {

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

}

