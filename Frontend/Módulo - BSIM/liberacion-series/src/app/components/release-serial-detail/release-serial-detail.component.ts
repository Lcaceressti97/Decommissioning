import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ReleaseSeriesLogModel } from 'src/app/models/model';
import { MessageModalComponent } from '../message-modal/message-modal.component';

@Component({
  selector: 'app-release-serial-detail',
  templateUrl: './release-serial-detail.component.html',
  styleUrls: ['./release-serial-detail.component.css']
})
export class ReleaseSerialDetailComponent implements OnInit {

  @Output() messageEvent = new EventEmitter<ReleaseSeriesLogModel>();
  @Input() dataReleaseSeriesLog: ReleaseSeriesLogModel;

  resultsPerPage: number = 10;
  loadingIndicator: boolean = true;

  constructor(private activeModal: NgbActiveModal, private modalService: NgbModal) { }

  ngOnInit(): void {
    // You can perform any initialization here if needed
  }

  closeModal() {
    this.activeModal.close();
  }

  showMessage(message: string) {
    const modalRef = this.modalService.open(MessageModalComponent);
    modalRef.componentInstance.message = message;
  }

  // Función para obtener el texto del estado
  getStatusText(status: any): string {
    switch (status) {
      case "C":
        return 'Completado';
      case "E":
        return 'Error';
      default:
        return 'Desconocido';
    }
  }

  // Función para obtener el color del estado
  getStatusColor(status: any): string {
    switch (status) {
      case "C":
        return 'green';
      case "E":
        return 'red';
      default:
        return 'black';
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

}