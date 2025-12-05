import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { BankConfigurationModalComponent } from 'src/app/components/bank-configuration-modal/bank-configuration-modal.component';
import { BankNavegaResponse } from 'src/app/entities/response';
import { BankModel } from 'src/app/models/BankModel';
import { BankConfigurationService } from 'src/app/services/bank-configuration.service';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-bank-configuration',
  templateUrl: './bank-configuration.component.html',
  styleUrls: ['./bank-configuration.component.css']
})
export class BankConfigurationComponent implements OnInit {

  // Props

  // Table
  rows: BankModel[] = [];
  rows2: BankModel[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  typeValue: string = "";

  constructor(public utilService: UtilsService, private modalService: NgbModal, private bankConfigurationService: BankConfigurationService) { }

  ngOnInit(): void {
    this.getBanks();
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

  // Modal
  openModalCrud(data: BankModel, action: string) {

    // Se abre la modal
    const modalRef = this.modalService.open(BankConfigurationModalComponent, {
      size: "lg"
    });

    // Se pasa el input
    modalRef.componentInstance.data = data;
    modalRef.componentInstance.action = action;
    modalRef.componentInstance.messageEvent.subscribe((status: boolean) => {

      if (status) {
        this.getBanks();
      }

    });

  }

  // Rest

  /**
   * Método que consume un servicio para obtener los proveedores
   * 
   */
  getBanks() {
    this.bankConfigurationService.getBank().subscribe((response) => {

      if (response.status === 200) {

        this.rows = [];
        this.rows2 = [];

        const providerResponse = response.body as BankNavegaResponse;

        providerResponse.data.map((dataOk) => {

          let dto: BankModel = dataOk;
          dto.status = dto.status === 1 ? 'Activo' : 'Inactivo';
          dto.createdDate = this.formatDate(dto?.createdDate);
          this.rows.push(dto);

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
