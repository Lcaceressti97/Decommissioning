import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfigurationParameterDetailComponent } from 'src/app/components/configuration-parameter-detail/configuration-parameter-detail.component';
import { ParametersResponse } from 'src/app/entity/response';
import { ParametersModel } from 'src/app/model/model';
import { ComodatosService } from 'src/app/services/comodatos.service';
import { UtilService } from 'src/app/services/util.service';

import { messages } from 'src/app/utils/emun';

@Component({
  selector: 'app-configuration-parameters',
  templateUrl: './configuration-parameters.component.html',
  styleUrls: ['./configuration-parameters.component.css']
})
export class ConfigurationParametersComponent implements OnInit {

  // Props

  // Table
  messages = messages;
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  rows: ParametersModel[] = [];
  rows2: ParametersModel[] = [];

  //* STYLES
  labelClasses = "col-form-label text-dark col-sm-2 col-md-2 col-lg-2";
  inputClasses = "my-auto col-sm-2 col-md-4 col-lg-8";

  constructor(public utilService: UtilService, private comodatosService: ComodatosService, private modalService: NgbModal) { }

  async ngOnInit() {

    await this.configparametersById(1000);

  }


  // Métodos de la tabla 

  search() {
    this.rows = this.rows2.filter((approvalControl) => {
      return JSON.stringify(approvalControl)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  getTotalText() {
    return this.rows.length == 1 ? "Registro" : "Registros";
  }

  async reloadRows(){
    await this.configparametersById(1000);
  }

  // Modal

  openModal(data: any) {

    const modalRef = this.modalService.open(ConfigurationParameterDetailComponent, {
      size: "lg", backdrop: "static"
    })

    modalRef.componentInstance.parameter = data;
    modalRef.componentInstance.messageEvent.subscribe((reload: boolean) => {

      if(reload){
        this.reloadRows();
      }

    });
   
  }

  // Métodos asyncronos

  /**
* Método encargado de obtener los parámetros
* 
*/
  configparametersById(id: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.comodatosService.configparametersById(id).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {

          // Vaciamos las 
          this.rows = [];
          this.rows2 = [];

          // Mapeamos el body del response
          let parametersResponse = response.body as ParametersResponse;

          // Agregamos los valores a los rows

          parametersResponse.data.map((resourceMap, configError) => {

            let dto: ParametersModel = resourceMap;

            this.rows.push(dto);

          });

          this.rows = [...this.rows];
          this.rows2 = [...this.rows];


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
