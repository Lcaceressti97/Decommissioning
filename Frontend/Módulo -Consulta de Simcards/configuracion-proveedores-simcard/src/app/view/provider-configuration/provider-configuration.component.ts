import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FileModalComponent } from 'src/app/components/file-modal/file-modal.component';
import { ProviderCrudComponent } from 'src/app/components/provider-crud/provider-crud.component';
import { ProviderResponse } from 'src/app/entity/response';
import { ProviderModel } from 'src/app/model/model';
import { SimCardService } from 'src/app/service/sim-card.service';
import { UtilService } from 'src/app/service/util.service';

@Component({
  selector: 'app-provider-configuration',
  templateUrl: './provider-configuration.component.html',
  styleUrls: ['./provider-configuration.component.css']
})
export class ProviderConfigurationComponent implements OnInit {

  // Props

  // Table
  rows: ProviderModel[] = [];
  rows2: ProviderModel[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  typeValue: string = "";

  constructor(public utilService: UtilService, private modalService: NgbModal, private simcardService: SimCardService) { }

  async ngOnInit() {
    await this.getProvider();
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
  openModalCrud(data:ProviderModel, action: string) {

    // Se abre la modal
    const modalRef = this.modalService.open(ProviderCrudComponent, {
      size: "lg"
    });

    // Se pasa el input
    modalRef.componentInstance.data = data;
    modalRef.componentInstance.action = action;
    modalRef.componentInstance.messageEvent.subscribe((status: boolean) => {

      if (status) {
        this.getProvider();
      }

    });

  }


  // Rest

  /**
   * Método que consume un servicio para obtener los proveedores
   * 
   */
  getProvider(): Promise<boolean>{
    return new Promise((resolve,reject)=>{
      this.simcardService.getProvider().subscribe((response)=>{
       // console.log(response);
        if(response.status===200){
  
          this.rows = [];
          this.rows2 = [];
  
          const providerResponse = response.body as ProviderResponse;
          //console.log(providerResponse);
          providerResponse.data.map((dataOk)=>{
  
            let dto: ProviderModel = dataOk;
            dto.statusCode = dto.status === 1 ? 'Activo' : 'Inactivo';
  
            this.rows.push(dto);
  
          });
  
          this.rows = [...this.rows];
          this.rows2 = [...this.rows];
          this.loadingIndicator = false;
  
          if(this.rows.length>0){
            this.utilService.showNotification(0, "Datos cargados");
          }
          resolve(true);
  
        }
  
      }, (error)=>{
        resolve(false);
      });
    });

  }

  
}
