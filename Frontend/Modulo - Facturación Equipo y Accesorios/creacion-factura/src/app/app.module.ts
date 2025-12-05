import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AngularMultiSelectModule } from "angular2-multiselect-dropdown";

import { ColorPickerModule } from 'ngx-color-picker';
import { NgxDatatableModule } from "@swimlane/ngx-datatable";
import { HttpClientModule } from "@angular/common/http";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { NgxMaskModule, IConfig } from 'ngx-mask';
import { NgSelectModule } from '@ng-select/ng-select';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { InvoiceCreationComponent } from './view/invoice-creation/invoice-creation.component';
import { InvoiceCreationModalComponent } from './components/invoice-creation-modal/invoice-creation-modal.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AddProductModalComponent } from './components/add-product-modal/add-product-modal.component';
import { InvoiceDetailComponent } from './components/invoice-detail/invoice-detail.component';
import { DecimalPipe } from '@angular/common';
import { DetailBlockedLinesComponent } from './components/detail-blocked-lines/detail-blocked-lines.component';
import { AddProductInsuranceComponent } from './components/add-product-insurance/add-product-insurance.component';
import { AddProductServicesComponent } from './components/add-product-services/add-product-services.component';

@NgModule({
  declarations: [
    AppComponent,
    InvoiceCreationComponent,
    InvoiceCreationModalComponent,
    AddProductModalComponent,
    InvoiceDetailComponent,
    DetailBlockedLinesComponent,
    AddProductInsuranceComponent,
    AddProductServicesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    AngularMultiSelectModule,
    NgxDatatableModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    NgxMaskModule.forRoot(),
    ToastrModule.forRoot(),
    ColorPickerModule,
    NgSelectModule
  ],
  providers: [DecimalPipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
