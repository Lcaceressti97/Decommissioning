import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgxDatatableModule } from "@swimlane/ngx-datatable";
import { HttpClientModule } from "@angular/common/http";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { NgxMaskModule, IConfig } from 'ngx-mask';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AngularMultiSelectModule } from "angular2-multiselect-dropdown";

import { NgbActiveModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ColorPickerModule } from 'ngx-color-picker';
import { DatePipe } from '@angular/common';
import { ConsultationInvoiceComponent } from './views/consultation-invoice/consultation-invoice.component';
import { DetailInvoiceModalComponent } from './components/detail-invoice-modal/detail-invoice-modal.component';
import { SendMailModalComponent } from './components/send-mail-modal/send-mail-modal.component';
import { PrintInvoiceComponent } from './components/print-invoice/print-invoice.component';
import { LoadOceComponent } from './components/load-oce/load-oce.component';
import { LoadOceCorporativeComponent } from './components/load-oce-corporative/load-oce-corporative.component';
import { CorreoModalComponent } from './components/correo-modal/correo-modal.component';

@NgModule({
  declarations: [
    AppComponent,
    ConsultationInvoiceComponent,
    DetailInvoiceModalComponent,
    SendMailModalComponent,
    PrintInvoiceComponent,
    LoadOceComponent,
    LoadOceCorporativeComponent,
    CorreoModalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AngularMultiSelectModule,
    NgxDatatableModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    NgxMaskModule.forRoot(),
    ToastrModule.forRoot(),
    ColorPickerModule,
    
  ],
  providers: [NgbActiveModal, DatePipe],
  bootstrap: [AppComponent],
  entryComponents: []
})
export class AppModule { }
