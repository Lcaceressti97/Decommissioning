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

import { NgbActiveModal, NgbModule, NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { ColorPickerModule } from 'ngx-color-picker';
import { ConsultSimcardComponent } from './view/consult-simcard/consult-simcard.component';
import { DatePipe } from '@angular/common';
import { SelectComponentComponent } from './plugin/select-component/select-component.component';
import { OrderControlComponent } from './components/order-control/order-control.component';
import { OrderDetailComponent } from './components/order-detail/order-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    ConsultSimcardComponent,
    SelectComponentComponent,
    OrderControlComponent,
    OrderDetailComponent
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
    NgbNavModule
  ],
  providers: [NgbActiveModal, DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
