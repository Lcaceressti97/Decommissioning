import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PriceMasterModelComponent } from 'src/app/components/price-master-model/price-master-model.component';
import { PriceMasterResponse } from 'src/app/entities/response';
import { PriceMasterModel } from 'src/app/models/model';
import { PriceMasterService } from 'src/app/services/price-master.service';
import { UtilService } from 'src/app/services/util.service';

@Component({
  selector: 'app-price-master',
  templateUrl: './price-master.component.html',
  styleUrls: ['./price-master.component.css']
})
export class PriceMasterComponent implements OnInit {

  // Table
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  rows: PriceMasterModel[] = [];
  rows2: PriceMasterModel[] = [];

  constructor(public utilService: UtilService, private priceMasterService: PriceMasterService, private modalService: NgbModal) { }

  ngOnInit(): void {

    this.getPriceMaster();

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
    this.getPriceMaster();
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

    modalRef.componentInstance.data = row;
    modalRef.componentInstance.button = button;
    modalRef.componentInstance.messageEvent.subscribe((reload: boolean) => {

      if (reload) {
        this.reloadRows();
      }

    });

  }


  // Methods Services
  /**
* Método encargado de obtener los registros de los precios maestros
* 
*/
  getPriceMaster() {

    // Se llama e método del servicio
    this.priceMasterService.getPriceMaster().subscribe((response) => {

      // Validamos si responde con un 200
      if (response.status === 200) {

        // Vaciamos las 
        this.rows = [];
        this.rows2 = [];

        // Mapeamos el body del response
        let channelResponse = response.body as PriceMasterResponse;

        // Agregamos los valores a los rows

        channelResponse.data.map((resourceMap, configError) => {

          let dto: PriceMasterModel = resourceMap;
          //dto.statusStr = dto.status == 1 ? "Activo" : "Inactivo";

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

    })
  }



}
