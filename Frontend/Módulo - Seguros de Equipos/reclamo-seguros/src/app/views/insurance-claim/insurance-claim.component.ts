import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { InsuranceClaimModalComponent } from 'src/app/components/insurance-claim-modal/insurance-claim-modal.component';
import { ConfigParameterResponse, InsuranceClaimResponse, InsuranceClaimResponsePaginated, InventoryTypeResponse, WarehouseResponse } from 'src/app/entities/response';
import { ConfigParameter, InsuranceClaimModel, InventoryTypeModel, ItemSelect, WareHouseModel } from 'src/app/models/model';
import { InsuranceClaimService } from 'src/app/services/insurance-claim.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-insurance-claim',
  templateUrl: './insurance-claim.component.html',
  styleUrls: ['./insurance-claim.component.css']
})
export class InsuranceClaimComponent implements OnInit {

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
  blockDaysParam: string = "";
  consultForm!: FormGroup;

  // Propiedades de paginación
  currentPage = 0;
  pageSize = 20;
  totalElements = 0;
  totalPages = 0;

  inputClasses = "my-auto";

  constructor(
    private insuranceClaimService: InsuranceClaimService,
    private modalService: NgbModal,
    public utilService: UtilService, private readonly fb: FormBuilder
  ) {

  }

  ngOnInit() {
    this.getInsuranceClaim(this.currentPage, this.pageSize);
    this.getWareHouses();
    this.configparametersById(1000);
    this.consultForm = this.initForm();

  }

  initForm(): FormGroup {
    return this.fb.group({
      esn: ['', [Validators.required]],
    })
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

  /**
 * Método para abrir modales según la acción
 * 
 * @param button 
 * @param row 
 */
  openModal(button: string, row: InsuranceClaimModel = null) {

    const modalRef = this.modalService.open(InsuranceClaimModalComponent, {
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
    modalRef.componentInstance.blockDaysParam = this.blockDaysParam;
    modalRef.componentInstance.messageEvent.subscribe((reload: boolean) => {

      if (reload) {
        this.reloadRows();
      }

    });

  }

  async refreshTable() {
    // Limpiar el valor del formulario
    this.consultForm.get('esn')?.setValue('');

    this.pageSize = 20;
    this.currentPage = 0;

    // Mostrar el loading
    Swal.fire({
      title: 'Cargando ...',
      allowOutsideClick: false,
      onBeforeOpen: () => {
        Swal.showLoading();
      }
    });

    // Llamar a la función para obtener todos los registros
    await this.getInsuranceClaim(this.currentPage, this.pageSize);

    // Cerrar el loading
    Swal.close();
  }



  // Methods Services

  /**
* Método encargado de obtener los registros por imei
* 
*/
  getInsuranceClaimByEsn(esn: any) {
    this.insuranceClaimService.getInsuranceClaimByEsn(esn).subscribe((response) => {

      if (response.status === 200) {

        this.rows = [];
        this.rows2 = [];

        const insuranceControlResponse = response.body as InsuranceClaimResponse;

        this.currentPage = insuranceControlResponse.data.length;
        this.pageSize = insuranceControlResponse.data.length;
        this.totalElements = insuranceControlResponse.data.length;
        this.totalPages = insuranceControlResponse.data.length;
        insuranceControlResponse.data.map((dataOk) => {

          let dto: InsuranceClaimModel = dataOk;

          this.rows.push(dto);

        });

        this.rows = [...this.rows];
        this.rows2 = [...this.rows];
        this.loadingIndicator = false;

        if (this.rows.length > 0) {
          this.utilService.showNotification(0, "Datos cargados");
        } else {
          this.utilService.showNotification(1, "No se encontraron datos para la IMEI ingresada.!!");
          this.rows = [];
          this.rows2 = [];
        }

      } else {
        this.utilService.showNotification(0, "No se encontraron datos para la IMEI ingresada.!!");
        this.rows = [];
        this.rows2 = [];
      }

    }, (error) => {
      this.utilService.showNotification(0, "No se encontraron datos para el IMEI ingresado.!!");
      this.rows = [];
      this.rows2 = [];
    });
  }

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


  /**
* Método encargado de obtener las bodegas
* 
* @returns 
*/
  getWareHouses(): Promise<boolean> {

    return new Promise((resolve, reject) => {

      this.insuranceClaimService.getWareHouses().subscribe((response) => {

        if (response.status === 200) {

          let warehouseResponse = response.body as WarehouseResponse;

          warehouseResponse.data.map((resourceMap, configError) => {

            let dto: WareHouseModel = resourceMap;
            let item: ItemSelect = {
              id: dto.id,
              code: dto.code,
              itemName: `${dto.code} - ${dto.name}`
            }

            this.dropDownListWareHouse.push(item);

          });

          this.dropDownListWareHouse = [...this.dropDownListWareHouse];

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

            if (dto.parameterType == "BLOCK_DAYS") {
              this.blockDaysParam = String(dto.parameterValue);
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

}
