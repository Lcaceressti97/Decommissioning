import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgxDatatableModule } from "@swimlane/ngx-datatable";
import { HttpClientModule } from "@angular/common/http";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { NgxMaskModule, IConfig } from 'ngx-mask'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AngularMultiSelectModule } from "angular2-multiselect-dropdown";

import { NgbActiveModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ColorPickerModule } from 'ngx-color-picker';
import { DatePipe } from '@angular/common';
import { InsuranceClaimComponent } from './views/insurance-claim/insurance-claim.component';
import { InsuranceClaimModalComponent } from './components/insurance-claim-modal/insurance-claim-modal.component';

@NgModule({
  declarations: [
    AppComponent,
    InsuranceClaimComponent,
    InsuranceClaimModalComponent
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
  entryComponents: []
})
export class AppModule { }
