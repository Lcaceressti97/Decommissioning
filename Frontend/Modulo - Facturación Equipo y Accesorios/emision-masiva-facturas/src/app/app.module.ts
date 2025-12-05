import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgxDatatableModule } from "@swimlane/ngx-datatable";
import { HttpClientModule } from "@angular/common/http";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { NgxMaskModule } from 'ngx-mask'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AngularMultiSelectModule } from "angular2-multiselect-dropdown";

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ColorPickerModule } from 'ngx-color-picker';
import { DatePipe } from '@angular/common';
import { BulkEmissionComponent } from './views/bulk-emission/bulk-emission.component';
import { DetailInvoiceModalComponent } from './components/detail-invoice-modal/detail-invoice-modal.component';
import { EmissionModalComponent } from './components/emission-modal/emission-modal.component';
import { BulkPrintInvoicesComponent } from './components/bulk-print-invoices/bulk-print-invoices.component';

@NgModule({
  declarations: [
    AppComponent,
    BulkEmissionComponent,
    DetailInvoiceModalComponent,
    EmissionModalComponent,
    BulkPrintInvoicesComponent
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
    ColorPickerModule
  ],
  providers: [NgbActiveModal, DatePipe],
  bootstrap: [AppComponent],
})
export class AppModule { }
