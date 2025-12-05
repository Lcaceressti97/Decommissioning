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
import { MantenimientoTarjetasComponent } from './view/mantenimiento-tarjetas/mantenimiento-tarjetas.component';
import { LoteTarjetasModalComponent } from './components/lote-tarjetas-modal/lote-tarjetas-modal.component';
import { DetallesLoteTarjetasModalComponent } from './components/detalles-lote-tarjetas-modal/detalles-lote-tarjetas-modal.component';

@NgModule({
  declarations: [
    AppComponent,
    MantenimientoTarjetasComponent,
    LoteTarjetasModalComponent,
    DetallesLoteTarjetasModalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AngularMultiSelectModule,
    NgbModule,
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
  bootstrap: [AppComponent]
})
export class AppModule { }
