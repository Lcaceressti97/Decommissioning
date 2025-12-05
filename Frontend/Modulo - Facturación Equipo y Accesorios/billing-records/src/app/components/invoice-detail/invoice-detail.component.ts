import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BillingResponse } from 'src/app/entities/response';
import { Billing, ConfigParameter, ControlInvoice } from 'src/app/models/model';
import { InvoiceService } from 'src/app/services/invoice.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-invoice-detail',
  templateUrl: './invoice-detail.component.html',
  styleUrls: ['./invoice-detail.component.css']
})
export class InvoiceDetailComponent implements OnInit {

  // Input
  @Input() controlInvoice: ControlInvoice;
  @Input() parameters: ConfigParameter[];
  billing: Billing = {};

  constructor(private activeModal: NgbActiveModal, public utilService: UtilService, private invoiceService: InvoiceService) { }

  async ngOnInit() {

    Swal.fire({
      title: 'Cargando Factura...',
      allowOutsideClick: false,
      onBeforeOpen: () => {
        Swal.showLoading();
      }
    });
    
    const validate = await this.getbillingById();

    if (validate) {

      this.utilService.showNotification(0, `Datos de la factura ${this.controlInvoice.idPrefecture} cargados`);

    }
    Swal.close();


  }

  closeModal() {
    this.activeModal.close();
  }

  getbillingById(): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.getbillingById(this.controlInvoice.idPrefecture).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {



          // Mapeamos el body del response
          let billingResponse = response.body as BillingResponse;

          // Agregamos los valores a los rows
          this.billing = billingResponse.data;
          this.billing.status = this.billing.status === this.parameters[0].stateCode ? this.parameters[0].parameterValue : this.billing.status === this.parameters[1].stateCode ? this.parameters[1].parameterValue : this.billing.status === this.parameters[2].stateCode ? this.parameters[2].parameterValue : this.billing.status === this.parameters[3].stateCode ? this.parameters[3].parameterValue : this.billing.status === this.parameters[4].stateCode ? this.parameters[4].parameterValue : this.parameters[5].stateCode === this.billing.status ? this.parameters[5].parameterValue : this.parameters[6].parameterValue;

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })
    })


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
