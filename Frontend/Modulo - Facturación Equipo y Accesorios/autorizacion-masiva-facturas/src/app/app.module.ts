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
import { BulkAuthorizeComponent } from './views/bulk-authorize/bulk-authorize.component';
import { DetailInvoiceModalComponent } from './components/detail-invoice-modal/detail-invoice-modal.component';
import { AuthorizeModalComponent } from './components/authorize-modal/authorize-modal.component';

@NgModule({
  declarations: [
    AppComponent,
    BulkAuthorizeComponent,
    DetailInvoiceModalComponent,
    AuthorizeModalComponent
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
