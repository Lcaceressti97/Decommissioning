import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ChannelSettingsModalComponent } from 'src/app/components/channel-settings-modal/channel-settings-modal.component';
import { ChannelResponse } from 'src/app/entity/response';
import { ChannelModel } from 'src/app/model/model';
import { InvoiceService } from 'src/app/services/invoice.service';
import { UtilService } from 'src/app/services/util.service';

@Component({
  selector: 'app-channel-settings',
  templateUrl: './channel-settings.component.html',
  styleUrls: ['./channel-settings.component.css']
})
export class ChannelSettingsComponent implements OnInit {

  // Props

  // Table
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  rows: ChannelModel[] = [];
  rows2: ChannelModel[] = [];

  constructor(public utilService: UtilService, private invoiceService: InvoiceService, private modalService: NgbModal) { }

  ngOnInit(): void {

    this.getChannels();
  
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
    this.getChannels();
  }

  /**
 * Método para abrir modales según la acción
 * 
 * @param button 
 * @param row 
 */
  openModal(button: string, row: ChannelModel = null) {

    const modalRef = this.modalService.open(ChannelSettingsModalComponent, {
      size: "md"
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
* Método encargado de obtener los registros de las facturas
* 
*/
  getChannels() {

    // Se llama e método del servicio
    this.invoiceService.getChannels(0, 2000).subscribe((response) => {

      // Validamos si responde con un 200
      if (response.status === 200) {

        // Vaciamos las 
        this.rows = [];
        this.rows2 = [];

        // Mapeamos el body del response
        let channelResponse = response.body as ChannelResponse;

        // Agregamos los valores a los rows

        channelResponse.data.map((resourceMap, configError) => {

          let dto: ChannelModel = resourceMap;
          dto.statusStr = dto.status == 1 ? "Activo" : "Inactivo";

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
