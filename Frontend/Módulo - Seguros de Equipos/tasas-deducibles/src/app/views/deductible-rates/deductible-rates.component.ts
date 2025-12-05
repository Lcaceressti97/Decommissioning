import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DeductibleRatesModalComponent } from 'src/app/components/deductible-rates-modal/deductible-rates-modal.component';
import { DeductibleRateResponse } from 'src/app/entities/response';
import { DeductibleRateModel } from 'src/app/models/model';
import { DeductibleRatesService } from 'src/app/services/deductible-rates.service';
import { UtilService } from 'src/app/services/util.service';

@Component({
  selector: 'app-deductible-rates',
  templateUrl: './deductible-rates.component.html',
  styleUrls: ['./deductible-rates.component.css']
})
export class DeductibleRatesComponent implements OnInit {

  // Table
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  rows: DeductibleRateModel[] = [];
  rows2: DeductibleRateModel[] = [];

  constructor(public utilService: UtilService, private deductibleRateService: DeductibleRatesService, private modalService: NgbModal) { }

  ngOnInit(): void {

    this.getDeductibleRates();

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
    this.getDeductibleRates();
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
  openModal(button: string, row: DeductibleRateModel = null) {

    const modalRef = this.modalService.open(DeductibleRatesModalComponent, {
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
* Método encargado de obtener los registros de la tabla de deducibles
* 
*/
  getDeductibleRates() {

    // Se llama e método del servicio
    this.deductibleRateService.getDeductibleRates().subscribe((response) => {

      // Validamos si responde con un 200
      if (response.status === 200) {

        // Vaciamos las 
        this.rows = [];
        this.rows2 = [];

        // Mapeamos el body del response
        let deductibleRateResponse = response.body as DeductibleRateResponse;

        // Agregamos los valores a los rows

        deductibleRateResponse.data.map((resourceMap, configError) => {

          let dto: DeductibleRateModel = resourceMap;

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
