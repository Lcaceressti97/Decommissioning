import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ControlOrderModalComponent } from 'src/app/components/control-order-modal/control-order-modal.component';
import { CreateOrderModalComponent } from 'src/app/components/create-order-modal/create-order-modal.component';
import { FileDetailModalComponent } from 'src/app/components/file-detail-modal/file-detail-modal.component';
import { OrderControlModalComponent } from 'src/app/components/order-control-modal/order-control-modal.component';
import { SeeOrderModalComponent } from 'src/app/components/see-order-modal/see-order-modal.component';
import { SuppliersModalComponent } from 'src/app/components/suppliers-modal/suppliers-modal.component';
import { ControlOrderEntity } from 'src/app/entities/ControlOrderEntity';
import { SimcardControlEntity } from 'src/app/entities/SimcardControlEntity';
import { ControlOrder } from 'src/app/models/ControlOrder';
import { SimcardControl } from 'src/app/models/SimcardControl';
import { SimcardControlFileService } from 'src/app/services/simcard-control-file.service';
import { UtilService } from 'src/app/services/util.service';
import { Buttons } from 'src/types';

@Component({
  selector: 'app-simcard-control-file',
  templateUrl: './simcard-control-file.component.html',
  styleUrls: ['./simcard-control-file.component.css']
})
export class SimcardControlFileComponent implements OnInit {

  simcardControl:SimcardControl[] = [];
  rowsSimcardControl:SimcardControl[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  typeValue: string = "";

  constructor(private simcardControlFileService: SimcardControlFileService, private modalService: NgbModal,
    public utilService: UtilService) { }

  ngOnInit(): void {
    this.getSimcardControl();
  }

  getSimcardControl() {
    this.simcardControlFileService.getSimcardControl().subscribe((res) => {
      if (res.status === 200) {
        this.loadingIndicator = false;
        this.rowsSimcardControl = [];
        this.simcardControl = [];
        let response = res.body.data as SimcardControlEntity[];

        response.map((v) => {
          let dto = {} as SimcardControl;
          dto.id = v?.id;
          dto.nameFile = v?.nameFile;
          switch (v?.status) {
            case 0:
              dto.status = 'Pendiente';
              dto.statusColor = 'blue';
              break;
            case 1:
              dto.status = 'Finalizado';
              dto.statusColor = 'green';
              break;
            case -1:
              dto.status = 'Error al procesar el archivo';
              dto.statusColor = 'red';
              break;
            default:
              dto.status = 'Desconocido';
              dto.statusColor = 'black';
          }
          dto.created = this.formatDate(v?.created);
          dto.idSimcardPadre = v?.idSimcardPadre;
          dto.suppliersId = v?.suppliersId;
          dto.idSimcardOrder = v?.idSimcardOrder;
          this.rowsSimcardControl.push(dto);
        });
        this.loadingIndicator = false;
        this.rowsSimcardControl = [...this.rowsSimcardControl];
        this.simcardControl = [...this.simcardControl];
      } else {
        this.utilService.showNotificationError(res.status);
      }
    });
  }

  onFileSelected(event: any) {
    const selectedFile = event.target.files[0];

    if (selectedFile) {
      this.simcardControlFileService.processSimcardFile(selectedFile).subscribe(
        (response) => {
          this.utilService.handleApiResponse(response);
          if (response.code === 200) {
            this.ngOnInit();
          }
        },
        (error) => {
          this.utilService.handleApiResponse(error.error);
        }
      );
    } else {
      this.utilService.showNotificationError(500);
    }
  }

  // MODALS
  openDetailModal(button: Buttons, rowsSimcardControl: SimcardControl = null) {

    const modalRef = this.modalService.open(FileDetailModalComponent, { size: 'xl', scrollable: false });
    modalRef.componentInstance.rowsSimcardControl = rowsSimcardControl;
    modalRef.componentInstance.button = button;
  }

  openModalSuppliers(event: any, row: SimcardControl = null) {
    event.stopPropagation();

    const modalRef = this.modalService.open(SuppliersModalComponent, { size: 'xl', backdrop: 'static' });

    if (row && row.idSimcardPadre !== undefined) {
      modalRef.componentInstance.idSimcardPadre = row.idSimcardPadre;
      modalRef.componentInstance.idSimcardControl = row.id;
    } else {
      console.error('Error: idSimcardPadre no está definido en el objeto row.');
      modalRef.close();
      return;
    }
    modalRef.result.then(
      (result) => {
        this.getSimcardControl();
      },
    );
    modalRef.componentInstance.messageEvent.subscribe((simcardControl: SimcardControl) => {

    });
  }

  
  // En Uso
  openModalOrderControl(data: SimcardControl){

      // Se abre la modal
      const modalRef = this.modalService.open(ControlOrderModalComponent, {
        size: "xl"
      });
  
      // Se pasa el input
      modalRef.componentInstance.idPadre = data.idSimcardPadre;
      modalRef.componentInstance.idSimcardControl = data.id;

  }

  // No está en uso
  openModalCreateOrder(event: any, row: SimcardControl = null) {
    event.stopPropagation();
    const modalRef = this.modalService.open(CreateOrderModalComponent, { size: 'xl', backdrop: 'static' });
    if (row && row.idSimcardPadre !== undefined) {
      modalRef.componentInstance.idSimcardPadre = row.idSimcardPadre;
      modalRef.componentInstance.idSimcardControl = row.id;
    } else {
      console.error('Error: idSimcardPadre no está definido en el objeto row.');
      modalRef.close();
      return;
    }
    modalRef.result.then(
      (result) => {
        this.getSimcardControl();
      },
    );

  }

  openModalSeeOrder(event: any, row: SimcardControl = null) {
    event.stopPropagation();
    const modalRef = this.modalService.open(SeeOrderModalComponent, { size: 'xl', backdrop: 'static' });
    if (row && row.idSimcardPadre !== undefined) {
      modalRef.componentInstance.idSimcardPadre = row.idSimcardPadre;
    } else {
      console.error('Error: idSimcardPadre no está definido en el objeto row.');
      modalRef.close();
      return;
    }

  }
  openModalControlOrder(button: Buttons, row: ControlOrder = null) {

    const modalRef = this.modalService.open(OrderControlModalComponent, { size: 'xl', backdrop: 'static' });
    modalRef.componentInstance.rowsOrderControl = row;
    modalRef.componentInstance.button = button;
    modalRef.componentInstance.messageEvent.subscribe((controlOrder: ControlOrder) => {
    });
  }


  //* UTILS
  searchSimcardControl() {
    this.rowsSimcardControl = this.rowsSimcardControl.filter((rowsSimcardControl) => {
      return JSON.stringify(rowsSimcardControl)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  getTotalText() {
    return this.rowsSimcardControl.length == 1 ? "Registro" : "Registros";
  }

  reloadRows() {
    this.getSimcardControl();
  }

  refrescar() {
    this.ngOnInit();
  }

  formatDate(fecha: string | null): string {
    if (!fecha) {
      return '';
    }

    const fechaFormateada = new Date(fecha);
    const dia = fechaFormateada.getDate().toString().padStart(2, '0');
    const mes = (fechaFormateada.getMonth() + 1).toString().padStart(2, '0');
    const año = fechaFormateada.getFullYear();
    return `${dia}/${mes}/${año}`;
  }

  getStatusColorClass(statusColor: string | undefined): string {
    switch (statusColor) {
      case 'blue':
        return 'blue-text';
      case 'yellow':
        return 'yellow-text';
      case 'green':
        return 'green-text';
      case 'red':
        return 'red-text';
      default:
        return '';
    }
  }

}
