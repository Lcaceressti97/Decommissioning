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
import { ConfigurationParametersComponent } from './view/configuration-parameters/configuration-parameters.component';
import { ConfigurationParameterDetailComponent } from './components/configuration-parameter-detail/configuration-parameter-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    ConfigurationParametersComponent,
    ConfigurationParameterDetailComponent
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
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
