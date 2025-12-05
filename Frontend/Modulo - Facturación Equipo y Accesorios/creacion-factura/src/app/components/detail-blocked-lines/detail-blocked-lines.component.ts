import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { HistoricalDetailResponse } from 'src/app/entity/response';
import { HistoricalDetail } from 'src/app/models/model';
import { InvoiceService } from 'src/app/services/invoice.service';
import { UtilService } from 'src/app/services/util.service';
import { resolve } from 'url';

@Component({
  selector: 'app-detail-blocked-lines',
  templateUrl: './detail-blocked-lines.component.html',
  styleUrls: ['./detail-blocked-lines.component.css']
})
export class DetailBlockedLinesComponent implements OnInit {
  @Input() historical: HistoricalDetail;
  @Output() selectInsuranceClaim = new EventEmitter<number>();

  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";

  constructor(private invoiceService: InvoiceService, public utilService: UtilService, private activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

  // Método para emitir el idInsuranceClaim cuando se selecciona un registro
  selectClaim(idInsuranceClaim: number) {
    this.invoiceService.getBillingByInsuranceClaim(idInsuranceClaim).subscribe(
      (response) => {
        // Manejo de la respuesta exitosa
        if (response.status === 200) {
          this.selectInsuranceClaim.emit(idInsuranceClaim);
          console.log(idInsuranceClaim);
          this.closeModal();
        }
      },
      (error) => {
        // Manejo de errores
        if (error.status === 400) {
          this.utilService.showNotification(1, "La linea seleccionada ya tiene un reclamo de seguro pendiente");
        } else {
          this.utilService.showNotification(1, "Ocurrió un error al seleccionar la linea");
        }
      }
    );
  }


  closeModal() {
    this.activeModal.close();
  }

  getTitle() {
    return "Detalle Lineas Bloqueadas:";
  }

  reloadRows() {
  }

}
