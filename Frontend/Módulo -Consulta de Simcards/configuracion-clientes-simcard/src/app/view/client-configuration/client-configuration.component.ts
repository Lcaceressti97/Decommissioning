import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ClientModalComponent } from 'src/app/components/client-modal/client-modal.component';
import { CustomerResponse } from 'src/app/entity/response';
import { CustomerModel } from 'src/app/models/model';
import { SimCardService } from 'src/app/services/sim-card.service';
import { UtilService } from 'src/app/services/util.service';

@Component({
  selector: 'app-client-configuration',
  templateUrl: './client-configuration.component.html',
  styleUrls: ['./client-configuration.component.css']
})
export class ClientConfigurationComponent implements OnInit {

  // Props

  // Table
  rows: CustomerModel[] = [];
  rows2: CustomerModel[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  typeValue: string = "";

  constructor(public utilService: UtilService, private modalService: NgbModal, private simcardService: SimCardService) { }

  ngOnInit(): void {
    this.getCustomer();
  }

  // Methods

  // Generales
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
  openModalCrud(data: CustomerModel, action: string) {

    // Se abre la modal
    const modalRef = this.modalService.open(ClientModalComponent, {
      size: "md"
    });

    // Se pasa el input
    modalRef.componentInstance.data = data;
    modalRef.componentInstance.action = action;
    modalRef.componentInstance.messageEvent.subscribe((status: boolean) => {

      if (status) {
        this.getCustomer();
      }

    });

  }


  /**
 * Método que consume un servicio para obtener los proveedores
 * 
 */
  getCustomer() {
    this.simcardService.getCustomer().subscribe((response) => {

      if (response.status === 200) {

        this.rows = [];
        this.rows2 = [];

        const providerResponse = response.body as CustomerResponse;

        providerResponse.data.map((dataOk) => {

          let dto: CustomerModel = dataOk;
          dto.statusCode = dto.status === 1 ? 'Activo' : 'Inactivo';

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

}
