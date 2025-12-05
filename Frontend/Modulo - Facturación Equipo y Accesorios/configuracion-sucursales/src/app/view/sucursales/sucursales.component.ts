import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AssignBranchComponent } from 'src/app/components/assign-branch/assign-branch.component';
import { BranchDetailComponent } from 'src/app/components/branch-detail/branch-detail.component';
import { BranchResponse } from 'src/app/entity/response';
import { Branche } from 'src/app/model/model';
import { InvoiceService } from 'src/app/services/invoice.service';
import { UtilService } from 'src/app/services/util.service';

@Component({
  selector: 'app-sucursales',
  templateUrl: './sucursales.component.html',
  styleUrls: ['./sucursales.component.css']
})
export class SucursalesComponent implements OnInit {

  // Props

  // Props of table
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  rows: Branche[] = [];
  rows2: Branche[] = [];

  // Propiedades de paginación
  currentPage = 0;
  pageSize = 20;
  totalElements = 0;
  totalPages = 0;

  constructor(public utilService: UtilService, private invoiceService: InvoiceService, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.getBranches();
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
    this.getBranches();
  }

  /**
   * Método para abrir modales según la acción
   * 
   * @param button 
   * @param row 
   */
  openModal(button: string, row: Branche = null) {

    const modalRef = this.modalService.open(BranchDetailComponent, {
      size: "xl"
    });

    modalRef.componentInstance.branche = row;
    modalRef.componentInstance.button = button;
    modalRef.componentInstance.messageEvent.subscribe((reload: boolean) => {

      if (reload) {
        this.reloadRows();
      }

    });

  }


  /**
   * Método que abré la modal para la asignación de una sucursal al usuario
   * 
   */
  openModalAssing(row: Branche) {

    const modalRef = this.modalService.open(AssignBranchComponent, {
      size: "lg"
    });

    modalRef.componentInstance.branche = row;
    modalRef.componentInstance.messageEvent.subscribe((reload: boolean) => {

      if (reload) {
        this.reloadRows();
      }

    });

  }

  // Método para manejar el cambio de página
  async onPageChange(event: any) {
    this.currentPage = event.offset;
    await this.getBranches();
  }

  /**
* Método encargado de obtener los registros de las facturas
* 
*/
  getBranches() {

    // Se llama e método del servicio
    this.invoiceService.getBranches(this.currentPage, this.pageSize).subscribe((response) => {

      // Validamos si responde con un 200
      if (response.status === 200) {

        // Vaciamos las 
        this.rows = [];
        this.rows2 = [];

        // Mapeamos el body del response
        let branchResponse = response.body as BranchResponse;

        // Actualizar la información de paginación
        this.totalElements = branchResponse.data.totalElements;
        this.totalPages = branchResponse.data.totalPages;
        this.currentPage = branchResponse.data.number;

        // Agregamos los valores a los rows

        branchResponse.data.content.map((resourceMap, configError) => {

          let dto: Branche = resourceMap;

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
