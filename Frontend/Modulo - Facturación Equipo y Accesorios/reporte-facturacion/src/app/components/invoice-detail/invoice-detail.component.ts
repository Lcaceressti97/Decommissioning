import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Billing } from 'src/app/models/InvoiceEquipmentAccesories';
import { InvoiceService } from 'src/app/services/invoice.service';
import { UtilService } from 'src/app/services/util.service';

@Component({
  selector: 'app-invoice-detail',
  templateUrl: './invoice-detail.component.html',
  styleUrls: ['./invoice-detail.component.css']
})
export class InvoiceDetailComponent implements OnInit {

  // Input y Output
  @Input() billing: Billing;

  constructor(private activeModal: NgbActiveModal, public utilService: UtilService, private invoiceService: InvoiceService) { }

  async ngOnInit() {
  }

  closeModal(){
    this.activeModal.close();
  }

    /**
 * Método encargado de obtener los detalles de la factura
 * 
 */
/*
    getInvoiceDetail(): Promise<boolean> {

      return new Promise((resolve, reject) => {
        // Consumir el servicio
        this.invoiceService.getInvoiceDetailById(this.billing.id).subscribe((response) => {
  
          // Validamos si el response es ok
          if (response.status === 200) {
  
            this.invoiceDetail = [];
  
            let invoiceDetailResponse = response.body as InvoiceDetailResponse;
  
            if (invoiceDetailResponse.code === 1) {
  
              if (invoiceDetailResponse.data.length > 0) {
  
                invoiceDetailResponse.data.map((resourceMap, configError) => {
  
                  let dto: InvoiceDetail = resourceMap;
  
                  this.invoiceDetail.push(dto);
  
                });
  
                this.invoiceDetail = [...this.invoiceDetail];
                resolve(true);
  
              } else {
                this.utilService.showNotification(1, "No se encontraron detalles de la facturas");
                resolve(false);
              }
  
            }
  
          } else {
            resolve(false);
          }
  
        }, (error) => {
          resolve(false);
        })
      })
  
  
    }
    */
}
