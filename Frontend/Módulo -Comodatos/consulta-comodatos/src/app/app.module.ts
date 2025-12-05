import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AngularMultiSelectModule } from "angular2-multiselect-dropdown";

import { NgbActiveModal, NgbModule, NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { ColorPickerModule } from 'ngx-color-picker';
import { NgxDatatableModule } from "@swimlane/ngx-datatable";
import { HttpClientModule } from "@angular/common/http";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { NgxMaskModule, IConfig } from 'ngx-mask';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ConsultaComodatosSuscriptorComponent } from './components/consulta-comodatos-suscriptor/consulta-comodatos-suscriptor.component';
import { ConsultaComodatosDetailComponent } from './view/consulta-comodatos-detail/consulta-comodatos-detail.component';
import { DatePipe } from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
    ConsultaComodatosSuscriptorComponent,
    ConsultaComodatosDetailComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AngularMultiSelectModule,
    NgbModule,
    NgbNavModule,
    NgxDatatableModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    NgxMaskModule.forRoot(),
    ToastrModule.forRoot(),
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
