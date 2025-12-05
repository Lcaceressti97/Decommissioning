import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SafeControlEquipment } from 'src/app/models/SafeControlEquipment';
import { PdfService } from 'src/app/services/pdf.service';
import { UtilService } from 'src/app/services/util.service';
import { Buttons } from 'src/types';

@Component({
  selector: 'app-insurance-consultation-detail',
  templateUrl: './insurance-consultation-detail.component.html',
  styleUrls: ['./insurance-consultation-detail.component.css']
})
export class InsuranceConsultationDetailComponent implements OnInit {

  @Output() messageEvent = new EventEmitter<SafeControlEquipment>();
  @Input() rowsSafeControlEquipment: SafeControlEquipment;
  @Input() button: Buttons;

  constructor(private activeModal: NgbActiveModal, private modalService: NgbModal, public utilService: UtilService, private pdfService: PdfService) { }

  ngOnInit(): void {
  }

  generatePdf() {
    this.pdfService.generatePdf(this.rowsSafeControlEquipment);
  }

  getTitle() {
    if (this.button === "see") return "Detalle de Seguros:";
  }


  closeModal() {
    this.activeModal.close();
  }

}
