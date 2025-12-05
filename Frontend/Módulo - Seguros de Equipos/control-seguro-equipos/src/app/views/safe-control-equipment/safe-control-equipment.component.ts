import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SafeControlEquipmentDetailComponent } from 'src/app/components/safe-control-equipment-detail/safe-control-equipment-detail.component';
import { SafeControlEquipmentModalComponent } from 'src/app/components/safe-control-equipment-modal/safe-control-equipment-modal.component';
import { EquipmentInsuranceControlResponse, EquipmentInsuranceResponse } from 'src/app/entities/response';
import { EquipmentInsuranceModel } from 'src/app/models/EquipmentInsuranceModel';
import { SafeControlEquipmentService } from 'src/app/services/safe-control-equipment.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-safe-control-equipment',
  templateUrl: './safe-control-equipment.component.html',
  styleUrls: ['./safe-control-equipment.component.css']
})
export class SafeControlEquipmentComponent implements OnInit {
  // Props

  // Table
  rows: EquipmentInsuranceModel[] = [];
  rows2: EquipmentInsuranceModel[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  consultForm!: FormGroup;

  // Propiedades de paginación
  currentPage = 0;
  pageSize = 20;
  totalElements = 0;
  totalPages = 0;

  //* STYLES
  labelClasses = "col-form-label text-dark col-sm-2 col-md-2 col-lg-2 font-size-large";
  inputClasses = "my-auto";

  constructor(private safeControlEquipmentService: SafeControlEquipmentService,
    private modalService: NgbModal,
    public utilService: UtilService,
    private activatedRoute: ActivatedRoute,
    private router: Router, private readonly fb: FormBuilder) { }

  ngOnInit(): void {
    this.consultForm = this.initForm();
    this.getEquipmentInsurance(this.currentPage, this.pageSize);
  }

  initForm(): FormGroup {
    return this.fb.group({
      esn: ['', [Validators.required]],
    })
  }

  // Methods
  search() {
    this.rows = this.rows2.filter((rowsSimcardControl) => {
      return JSON.stringify(rowsSimcardControl)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  getTotalText() {
    return this.rows.length == 1 ? "Registro" : "Registros";
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

  // Modal
  openModalDetail(data: EquipmentInsuranceModel) {

    // Se abre la modal
    const modalRef = this.modalService.open(SafeControlEquipmentDetailComponent, {
      size: "xl"
    });

    // Se pasa el input
    modalRef.componentInstance.rowsSafeControlEquipment = data;
    //modalRef.componentInstance.action = action;
    modalRef.componentInstance.messageEvent.subscribe((status: boolean) => {

      if (status) {
        this.getEquipmentInsurance();
      }

    });

  }

  openModalCrud(data: EquipmentInsuranceModel, action: string) {

    // Se abre la modal
    const modalRef = this.modalService.open(SafeControlEquipmentModalComponent, {
      size: "xl"
    });

    // Se pasa el input
    modalRef.componentInstance.data = data;
    modalRef.componentInstance.action = action;
    modalRef.componentInstance.messageEvent.subscribe((status: boolean) => {

      if (status) {
        this.getEquipmentInsurance();
      }

    });

  }

  // Rest

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
    await this.getEquipmentInsurance(this.currentPage, this.pageSize);

    // Cerrar el loading
    Swal.close();
  }


  getEquipmentInsuranceByEsn(esn: any) {
    this.safeControlEquipmentService.getEquipmentInsuranceByEsn(esn).subscribe((response) => {

      if (response.status === 200) {

        this.rows = [];
        this.rows2 = [];

        const insuranceControlResponse = response.body as EquipmentInsuranceControlResponse;

        this.currentPage = insuranceControlResponse.data.length;
        this.pageSize = insuranceControlResponse.data.length;
        this.totalElements = insuranceControlResponse.data.length;
        this.totalPages = insuranceControlResponse.data.length;
        insuranceControlResponse.data.map((dataOk) => {

          let dto: EquipmentInsuranceModel = dataOk;
          dto.insuranceStatus = dto.insuranceStatus === 1 ? 'Activo' : 'Inactivo';

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
    await this.getEquipmentInsurance(this.currentPage, this.pageSize);
  }


  /**
   * Método que consume un servicio para obtener los proveedores
   * 
   */
  getEquipmentInsurance(page?: any, size?: any) {
    this.safeControlEquipmentService.getEquipmentInsurance(page, size).subscribe((response) => {

      if (response.status === 200) {

        const equipmentInsuranceResponse = response.body as EquipmentInsuranceResponse;

        // Actualizar la información de paginación
        this.totalElements = equipmentInsuranceResponse.data.totalElements;
        this.totalPages = equipmentInsuranceResponse.data.totalPages;
        this.currentPage = equipmentInsuranceResponse.data.number;

        this.rows = equipmentInsuranceResponse.data.content.map((resourceMap) => {

          let dto: EquipmentInsuranceModel = resourceMap;
          dto.insuranceStatus = dto.insuranceStatus === 1 ? 'Activo' : 'Inactivo';


          return dto;

        });

        this.rows = [...this.rows];
        this.rows2 = [...this.rows];
        this.loadingIndicator = false;

        if (this.rows.length > 0) {
          this.utilService.showNotification(0, "Datos cargados");
        }

      }

    });
  }


}
