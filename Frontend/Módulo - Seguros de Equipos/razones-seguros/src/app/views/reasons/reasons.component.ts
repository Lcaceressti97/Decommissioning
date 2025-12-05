import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ReasonsModalComponent } from 'src/app/components/reasons-modal/reasons-modal.component';
import { ReasonResponse } from 'src/app/entities/response';
import { ReasonModel } from 'src/app/models/model';
import { ReasonsService } from 'src/app/services/reasons.service';
import { UtilService } from 'src/app/services/util.service';

@Component({
  selector: 'app-reasons',
  templateUrl: './reasons.component.html',
  styleUrls: ['./reasons.component.css']
})
export class ReasonsComponent implements OnInit {

  // Table
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  rows: ReasonModel[] = [];
  rows2: ReasonModel[] = [];

  constructor(public utilService: UtilService, private reasonService: ReasonsService, private modalService: NgbModal) { }

  ngOnInit(): void {

    this.getReasons();

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
    this.getReasons();
  }

  // Función para obtener el texto del estado
  getStatusText(status: number): string {
    switch (status) {
      case 1:
        return 'Activo';
      case 0:
        return 'Inactivo';
      default:
        return 'Desconocido'; // O cualquier otro valor por defecto
    }
  }

  // Función para obtener el color del estado
  getStatusColor(status: number): string {
    switch (status) {
      case 1:
        return 'green'; // Color verde
      case 0:
        return 'red'; // Color rojo
      default:
        return 'black'; // Color por defecto
    }
  }

  getStatusColorClass(statusColor: number | undefined): string {
    switch (statusColor) {
      case 1:
        return 'green-text';
      case 0:
        return 'red-text';
      default:
        return 'red-text';
    }
  }

  /**
 * Método para abrir modales según la acción
 * 
 * @param button 
 * @param row 
 */
  openModal(button: string, row: ReasonModel = null) {

    const modalRef = this.modalService.open(ReasonsModalComponent, {
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
* Método encargado de obtener los registros de la tabla de razones
* 
*/
getReasons() {

  // Se llama e método del servicio
  this.reasonService.getReasons().subscribe((response) => {

    // Validamos si responde con un 200
    if (response.status === 200) {

      // Vaciamos las 
      this.rows = [];
      this.rows2 = [];

      // Mapeamos el body del response
      let reasonResponse = response.body as ReasonResponse;

      // Agregamos los valores a los rows

      reasonResponse.data.map((resourceMap, configError) => {

        let dto: ReasonModel = resourceMap;

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
