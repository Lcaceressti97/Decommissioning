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

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PriceMasterComponent } from './views/price-master/price-master.component';
import { PriceMasterModelComponent } from './components/price-master-model/price-master-model.component';
import { DatePipe } from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
    PriceMasterComponent,
    PriceMasterModelComponent
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
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
