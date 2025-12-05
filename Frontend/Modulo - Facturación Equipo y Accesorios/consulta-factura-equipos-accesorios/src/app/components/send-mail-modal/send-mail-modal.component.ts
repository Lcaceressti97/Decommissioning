import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { InvoiceEquipmentAccesories } from 'src/app/models/InvoiceEquipmentAccesories';
import { ConsultationInvoiceService } from 'src/app/services/consultation-invoice.service';
//import { EmailService } from 'src/app/services/email.service';
import { UtilService } from 'src/app/services/util.service';
import { Buttons } from 'src/types';

@Component({
  selector: 'app-send-mail-modal',
  templateUrl: './send-mail-modal.component.html',
  styleUrls: ['./send-mail-modal.component.css']
})
export class SendMailModalComponent implements OnInit {

  @Output() messageEvent = new EventEmitter<InvoiceEquipmentAccesories>();
  @Input() invoiceEquipmentAccesories: InvoiceEquipmentAccesories;
  @Input() button: Buttons;

  sendEmailForm: FormGroup;

  emails = [];
  correosApi: any = [];
  loadingNotify=false;
  currentId: string = "1";
  currentCycle: string = "";
  public selectedTipoFacturas = [];
  public typeInvoice=1;
  attachments: any;
  params: any[] = [];

  constructor(private consultationInvoiceService: ConsultationInvoiceService, public utilService: UtilService, private activeModal: NgbActiveModal, private fb: FormBuilder, private activatedRoute: ActivatedRoute) { 
  }

  ngOnInit(): void {
    this.selectedTipoFacturas=[{id:"1",itemName:"Factura Normal"}];
  }

   //* COMPONENTS
   addCorreo(correo:any) {
    if (/^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/.test(correo.value)) {
      let existe = false;
      for (let i = 0; i < this.emails.length; i++) {
        if (this.emails[i][0] === correo.value) {
          existe = true;
        }
      }

      if (!existe) {
        this.emails.push([correo.value, false]);
        correo.value = "";
      } else {
        this.utilService.setMsg('¡Email ya agregado a la lista.!', '', 2000, 'info', 'toast-top-right');
      }

    } else {
      this.utilService.setMsg('¡Error, ingrese un email valido.!', '', 2000, 'danger', 'toast-top-right');
    }
  }

  sendEmailTFC(): void {

    const body: string = 'Estimado Cliente, adjunto encuentra la factura de consumo correspondiente a este ciclo.  Su factura de consumo está en un archivo con extensión pdf, revísela con Acrobat Reader.' + "\n \n" +
    'Cliente:' + ' ' + this.invoiceEquipmentAccesories.customerName + "\n \n" +
    'Ciclo:' + ' ' + this.invoiceEquipmentAccesories.customerName + "\n \n" +
    'Consulta tu consumo disponible en megas y minutos, resuelve tus dudas acerca de tu plan con Mi Tigo App descargarla aquí http://a.tigo.com/[[mitigoapp]] ó ingresa a http://micuenta.tigo.com.hn desde tu navegador.' + "\n \n" +
    'Con Tigo Money puede realizar el pago de su linea postpago fácil y rápido visitando un Agente Tigo Money o desde la App Tigo Money en la opción Pago de Servicios.'
  'Encuentre su agente más cercano enviando sin costo la palabra AGENTE al 555.' + "\n \n" +
    'Para abrir dicho archivo usted debe tener le programa "Adobe Reader".'
  'Si no lo tiene puede descargarlo gratis de Aquí: http://www.adobe.com/[[es][products][acrobat][readstep2.html]]. Escribanos o visítenos a nuestro sitio https://online.celtel.net/[[isis][]]';
  

    const request = {
      from: this.invoiceEquipmentAccesories.customerName,
      cc: '',
      subject: this.invoiceEquipmentAccesories.customerName,
      body,
      sendTo: { send: this.emails },
      attachments: this.attachments
    };

    let response: any = {};
/*     this.emailService.sendMail(request).subscribe((data: any) => {
      response = data;
    }, (error: HttpErrorResponse) => {
      this.utilService.showNotificationError(error.error);
    }, () => {
      try {
        if (response.code == '01') {
            this.utilService.showNotification(0, 'Correo enviado con exito.');;
        } else {
          this.utilService.showNotificationError(400);
        }
      } catch (error) {
        this.utilService.showNotificationError(error);
      }
    }); */
  }
/* 
  sendEmailInvoice(){
    this.loadingNotify=true;
    let json={"typeInvoice":this.typeInvoice,"type_":this.selectedTipoFacturas[0]['id'],"id":this.currentId,"cycle":this.currentCycle,"email":this.emails.map((email:any) => email[0]).join(";")}
    this.emailService.createProcessEmailNotify(json).toPromise().then((result:any)=>{
      this.emails = this.correosApi;
      this.loadingNotify=false;
      this.activeModal.close();
      this.utilService.setMsg('¡Proceso de notificación creado!','',2000,'success','toast-top-right');
    }).catch(console.log)
  } */

  eliminarCorreo(i:any) {
    if (this.emails) {
      this.emails.splice(i, 1)
    }
  }

  //* UTILS
  getTitle() {
    if (this.button === "see") return "Enviar Notificación";
  }

  closeModal() {
    this.activeModal.close();
  }


}
