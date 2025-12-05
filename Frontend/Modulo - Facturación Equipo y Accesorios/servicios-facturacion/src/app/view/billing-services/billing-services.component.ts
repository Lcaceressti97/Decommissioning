import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { BillingServicesCrudComponent } from 'src/app/components/billing-services-crud/billing-services-crud.component';
import { BillingServicesPagesResponse, BillingServicesResponse } from 'src/app/entity/response';
import { BillingServicesModel } from 'src/app/model/model';
import { JsonService } from 'src/app/services/json.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';

@Component({
  selector: 'app-billing-services',
  templateUrl: './billing-services.component.html',
  styleUrls: ['./billing-services.component.css']
})
export class BillingServicesComponent implements OnInit {

  // Props

  // Table
  messages = messages;
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  rows: BillingServicesModel[] = [];
  rows2: BillingServicesModel[] = [];

  // Propiedades de paginación
  currentPage = 0;
  pageSize = 20;
  totalElements = 0;
  totalPages = 0;

  //* STYLES
  labelClasses = "col-form-label text-dark col-sm-2 col-md-2 col-lg-2";
  inputClasses = "my-auto col-sm-2 col-md-4 col-lg-8";
  inputClassesTwo = "my-auto col-sm-12 col-md-2 col-lg-2";

  constructor(public utilService: UtilService, private billingService: JsonService, private modalService: NgbModal) { }

  ngOnInit(): void {

    this.getBillingServiceByPagination();

  }

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

  /**
 * Método para abrir modales según la acción
 * 
 * @param button 
 * @param row 
 */
  openModal(button: string, row: BillingServicesModel = null) {

    const modalRef = this.modalService.open(BillingServicesCrudComponent, {
      size: "md"
    });

    modalRef.componentInstance.button = button;
    modalRef.componentInstance.data = row;
    modalRef.componentInstance.messageEvent.subscribe((reload: boolean) => {

      if (reload) {
        this.getBillingServiceByPagination();
      }

    });

  }

  // Métodos Asyncronos

  // Método para manejar el cambio de página
  async onPageChange(event: any) {
    this.currentPage = event.offset;
    await this.getBillingServiceByPagination();
  }

  /**
   * Método que consume un servicio para obtener todos los servicios de
   * facturación
   * 
   * @param page 
   * @param size 
   */
  getBillingServiceByPagination() {

    this.billingService.getBillingServiceByPagination(this.currentPage, this.pageSize).subscribe((response) => {

      if (response.status === 200) {

        this.rows = [];
        this.rows2 = [];

        let billingServiceResponse = response.body as BillingServicesPagesResponse;

        // Actualizar la información de paginación
        this.totalElements = billingServiceResponse.data.totalElements;
        this.totalPages = billingServiceResponse.data.totalPages;
        this.currentPage = billingServiceResponse.data.number;


        billingServiceResponse.data.content.map((resourceMap, configError) => {

          let dto: BillingServicesModel = resourceMap;
          dto.statusCode = dto.status;
          dto.status = dto.status === 0 ? 'Inactivo' : "Activo";

          this.rows.push(dto);


        });

        this.loadingIndicator = false;
        this.rows = [...this.rows];
        this.rows2 = [...this.rows];

        if (this.rows.length > 0) {
          this.utilService.showNotification(0, "Datos cargados");
        }

      }

    }, (error) => {

    });

  }


}
