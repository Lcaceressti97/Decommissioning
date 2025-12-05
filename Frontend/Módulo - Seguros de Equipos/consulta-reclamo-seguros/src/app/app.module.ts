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
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DecimalPipe } from '@angular/common';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { InsuranceClaimInquiryComponent } from './views/insurance-claim-inquiry/insurance-claim-inquiry.component';
import { InsuranceClaimInquiryModalComponent } from './components/insurance-claim-inquiry-modal/insurance-claim-inquiry-modal.component';

@NgModule({
  declarations: [
    AppComponent,
    InsuranceClaimInquiryComponent,
    InsuranceClaimInquiryModalComponent
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
