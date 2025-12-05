import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PriceMasterModelComponent } from 'src/app/components/price-master-model/price-master-model.component';
import { PriceMasterPagesResponse, PriceMasterResponse } from 'src/app/entity/response';
import { PriceMasterModel } from 'src/app/model/model';
import { EquipmentAccesoriesService } from 'src/app/services/equipment-accesories.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-price-master-maintenance',
  templateUrl: './price-master-maintenance.component.html',
  styleUrls: ['./price-master-maintenance.component.css']
})
export class PriceMasterMaintenanceComponent implements OnInit {

  // Props

  // Table
  messages = messages;
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  rows: PriceMasterModel[] = [];
  rows2: PriceMasterModel[] = [];

  // Propiedades de paginación
  currentPage = 0;
  pageSize = 20;
  totalElements = 0;
  totalPages = 0;

  consultForm!: FormGroup;

  //* STYLES
  labelClasses = "col-form-label text-dark col-sm-2 col-md-2 col-lg-2";
  inputClasses = "my-auto col-sm-2 col-md-4 col-lg-8";
  inputClassesTwo = "my-auto col-sm-12 col-md-2 col-lg-2";
  inputClassesConsult = "my-auto";

  constructor(public utilService: UtilService, private equipmentAccesoriesService: EquipmentAccesoriesService, private modalService: NgbModal, private readonly fb: FormBuilder) { }

  ngOnInit(): void {
    this.consultForm = this.initForm();
    this.getPricesMaster();
  }


  // Methods

  initForm(): FormGroup {
    return this.fb.group({
      model: ['', [Validators.required]],
    })
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

  reloadRows() {
    //this.getModelsAsEbs();
    this.refreshTable();
  }

  /**
  * Método para abrir modales según la acción
  * 
  * @param button 
  * @param row 
  */
  openModal(button: string, row: PriceMasterModel = null) {

    const modalRef = this.modalService.open(PriceMasterModelComponent, {
      size: "lg"
    });

    modalRef.componentInstance.button = button;
    modalRef.componentInstance.data = row;
    modalRef.componentInstance.messageEvent.subscribe((reload: boolean) => {

      if (reload) {
        this.reloadRows();
      }

    });

  }

  // Methods Rest

  async refreshTable() {
    // Limpiar el valor del formulario
    this.consultForm.get('model')?.setValue('');

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
    await this.getPricesMaster();

    // Cerrar el loading
    Swal.close();
  }

  getPricesMasterByModel(model: any) {
    this.equipmentAccesoriesService.getPricesMasterModelByModel(model).subscribe((response) => {

      if (response.status === 200) {

        this.rows = [];
        this.rows2 = [];

        const priceMasterResponse = response.body as PriceMasterResponse;

        this.currentPage = priceMasterResponse.data.length;
        this.pageSize = priceMasterResponse.data.length;
        this.totalElements = priceMasterResponse.data.length;
        this.totalPages = priceMasterResponse.data.length;
        priceMasterResponse.data.map((dataOk) => {

          let dto: PriceMasterModel = dataOk;

          this.rows.push(dto);

        });

        this.rows = [...this.rows];
        this.rows2 = [...this.rows];
        this.loadingIndicator = false;

        if (this.rows.length > 0) {
          this.utilService.showNotification(0, "Datos cargados");
        } else {
          this.utilService.showNotification(1, "No se encontraron datos para el modelo ingresado.!!");
          this.rows = [];
          this.rows2 = [];
        }

      } else {
        this.utilService.showNotification(1, "No se encontraron datos para el modelo ingresado.!!");
        this.rows = [];
        this.rows2 = [];
      }

    }, (error) => {
      this.utilService.showNotification(1, "No se encontraron datos para el modelo ingresado.!!");
      this.rows = [];
      this.rows2 = [];
      this.currentPage = 0;
      this.pageSize = 0;
      this.totalElements = 0;
      this.totalPages = 0;
    });
  }


  // Método para manejar el cambio de página
  async onPageChange(event: any) {
    this.currentPage = event.offset;
    await this.getPricesMaster();
  }

  /**
   * Método que consume un servicio para obtener los datos de la 
   * tabla MEA_PRICE_MASTER
   * 
   */
  getPricesMaster() {

    this.equipmentAccesoriesService.getPricesMaster(this.currentPage, this.pageSize).subscribe((response) => {

      if (response.status === 200) {

        this.rows = [];
        this.rows2 = [];

        let priceMasterResponse = response.body as PriceMasterPagesResponse;

        // Actualizar la información de paginación
        this.totalElements = priceMasterResponse.data.totalElements;
        this.totalPages = priceMasterResponse.data.totalPages;
        this.currentPage = priceMasterResponse.data.number;

        priceMasterResponse.data.content.map((resourceMap, configError) => {

          let dto: PriceMasterModel = resourceMap;

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
