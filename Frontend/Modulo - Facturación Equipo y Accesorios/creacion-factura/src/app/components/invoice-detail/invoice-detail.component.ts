import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Billing } from 'src/app/models/model';
import { InvoiceService } from 'src/app/services/invoice.service';
import { UtilService } from 'src/app/services/util.service';


@Component({
  selector: 'app-invoice-detail',
  templateUrl: './invoice-detail.component.html',
  styleUrls: ['./invoice-detail.component.css']
})
export class InvoiceDetailComponent implements OnInit {

  // Props

  @Input() billing: Billing;

  constructor(private activeModal: NgbActiveModal, private invoiceService:InvoiceService, public utilsService: UtilService) { }

  ngOnInit(): void {
  }

  closeModal() {
    this.activeModal.close();
  }

}
