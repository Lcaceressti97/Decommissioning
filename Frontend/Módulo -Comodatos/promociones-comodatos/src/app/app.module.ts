import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AngularMultiSelectModule } from "angular2-multiselect-dropdown";

import { NgbActiveModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ColorPickerModule } from 'ngx-color-picker';
import { NgxDatatableModule } from "@swimlane/ngx-datatable";
import { HttpClientModule } from "@angular/common/http";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { NgxMaskModule, IConfig } from 'ngx-mask';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PromotionComodatosComponent } from './view/promotion-comodatos/promotion-comodatos.component';
import { PromotionModalComponent } from './components/promotion-modal/promotion-modal.component';
import { DatePipe } from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
    PromotionComodatosComponent,
    PromotionModalComponent
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
    ColorPickerModule
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
