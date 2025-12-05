import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { InvoiceDetailResponse } from 'src/app/entities/invoice.entity';
import { InvoiceEquipmentAccesories } from 'src/app/models/InvoiceEquipmentAccesories';
import { Billing } from 'src/app/models/billing';
import { InvoiceDetail } from 'src/app/models/billing';
import { InvoiceService } from 'src/app/services/invoice.service';
import { UtilService } from 'src/app/services/util.service';
import { Buttons } from 'src/types';

@Component({
  selector: 'app-detail-invoice-modal',
  templateUrl: './detail-invoice-modal.component.html',
  styleUrls: ['./detail-invoice-modal.component.css']
})
export class DetailInvoiceModalComponent implements OnInit {

  @Output() messageEvent = new EventEmitter<Billing>();
  @Input() billing: Billing;
  @Input() button: Buttons;

  // Invoice
  //invoiceDetail:InvoiceDetail[] = [];

  constructor(private activeModal: NgbActiveModal, private invoiceService:InvoiceService, public utilsService: UtilService) { }

  ngOnInit(): void {

    //this.getInvoiceDetail();
  }

  calculateValue(invoice: InvoiceDetail): number {
    if (this.billing.fiscalProcess === "Facturación por Reclamo de Seguros" && invoice.serieOrBoxNumber != null) {
        return invoice.amountTotal;
    } else {
        return invoice.unitPrice * invoice.quantity;
    }
}

  //* UTILS
  getTitle() {
    if (this.button === "see") return "Detalle Factura:";
    if (this.button === "add") return "Detalle Factura:";
  }

  closeModal() {
    this.activeModal.close();
  }

  /**
   * Método encargado de obtener los detalles de la factura
   * 
   */
  /*
  getInvoiceDetail(){

    // Consumir el servicio
    this.invoiceService.getInvoiceDetailById(this.billing.id).subscribe((response)=>{

      // Validamos si el response es ok
      if(response.status===200){

        this.invoiceDetail = [];

        let invoiceDetailResponse = response.body as InvoiceDetailResponse;

        if(invoiceDetailResponse.code===1){

          if(invoiceDetailResponse.data.length>0){

            invoiceDetailResponse.data.map((resourceMap, configError) => {

              let dto: InvoiceDetail = resourceMap;
  
              this.invoiceDetail.push(dto);
  
            });

            this.invoiceDetail = [...this.invoiceDetail];

          }else{
            this.utilsService.showNotification(1,"No se encontraron detalles de la facturas");
          }

        }

      }else{

      }

    }, (error)=>{

    })

  }
  */
}
