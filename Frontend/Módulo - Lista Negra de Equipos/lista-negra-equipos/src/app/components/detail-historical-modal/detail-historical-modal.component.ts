import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Buttons } from 'src/types';
import pdfMake from 'pdfmake/build/pdfmake';
import pdfFonts from 'pdfmake/build/vfs_fonts';

import { UtilService } from 'src/app/services/util.service';
import { PdfService } from 'src/app/services/pdf.service';
import { HistoricalDetail } from 'src/app/models/HistoricalDetail';
import { HistoricalDetailService } from 'src/app/services/historical-detail.service';

pdfMake.vfs = pdfFonts.pdfMake.vfs;

@Component({
  selector: 'app-detail-historical-modal',
  templateUrl: './detail-historical-modal.component.html',
  styleUrls: ['./detail-historical-modal.component.css']
})
export class DetailHistoricalModalComponent implements OnInit {


  @Output() messageEvent = new EventEmitter<HistoricalDetail>();
  @Input() rowsHistoricalDetail: HistoricalDetail;
  @Input() button: Buttons;

  detailHistorical: HistoricalDetail;
  rowsHistoricalPdf: HistoricalDetail;
  loadingIndicator: boolean = true;

  constructor(private historicalDetailService: HistoricalDetailService, private activeModal: NgbActiveModal, private modalService: NgbModal, public utilService: UtilService, private pdfService: PdfService) { }

  ngOnInit(): void {
  }

  generatePdf() {
    this.pdfService.generatePdf(this.rowsHistoricalDetail);
  }

  getTitlePDF() {
    return 'Título del PDF';
  }

  getTitle() {
    if (this.button === "see") return "Detalle Histórico:";
  }

  closeModal() {
    this.activeModal.close();
  }
}
