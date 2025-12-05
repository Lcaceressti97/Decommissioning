import { Component, OnInit } from '@angular/core';
import { WareHousesResponse } from 'src/app/entity/response';
import { WareHouseModel } from 'src/app/model/model';
import { JsonService } from 'src/app/services/json.service';
import { UtilService } from 'src/app/services/util.service';

@Component({
  selector: 'app-warehouse-configuration',
  templateUrl: './warehouse-configuration.component.html',
  styleUrls: ['./warehouse-configuration.component.css']
})
export class WarehouseConfigurationComponent implements OnInit {

  // Props

  // Table
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  rows: WareHouseModel[] = [];
  rows2: WareHouseModel[] = [];
  size: number = 2000;

  constructor(public utilService: UtilService, private json:JsonService) { }

  ngOnInit(): void {

    this.getWareHouses();

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
 * Método encargado de obtener los registros de las facturas
 * 
 */
  getWareHouses() {

    // Se llama e método del servicio
    this.json.getWareHouses().subscribe((response) => {

      // Validamos si responde con un 200
      if (response.status === 200) {

        // Vaciamos las 
        this.rows = [];
        this.rows2 = [];

        // Mapeamos el body del response
        let billingResponse = response.body as WareHousesResponse;

        // Agregamos los valores a los rows

        billingResponse.data.map((resourceMap, configError) => {

          let dto: WareHouseModel = resourceMap;

          this.rows.push(dto);

        });

        this.loadingIndicator = false;
        this.rows = [...this.rows];
        this.rows2 = [...this.rows];

        if(this.rows.length>0){
          this.utilService.showNotification(0, "Datos cargados");
        }



      }

    }, (error) => {

    })
  }


}
