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
import { LineMooringComponent } from './view/line-mooring/line-mooring.component';
import { LineMooriningDetailComponent } from './components/line-moorining-detail/line-moorining-detail.component';
import { ComodatosMooringComponent } from './components/comodatos-mooring/comodatos-mooring.component';
import { ComodatosDetailComponent } from './components/comodatos-detail/comodatos-detail.component';
import { DatePipe } from '@angular/common';
import { ComodatosCancelationComponent } from './components/comodatos-cancelation/comodatos-cancelation.component';

@NgModule({
  declarations: [
    AppComponent,
    LineMooringComponent,
    LineMooriningDetailComponent,
    ComodatosMooringComponent,
    ComodatosDetailComponent,
    ComodatosCancelationComponent
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
    ColorPickerModule
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
