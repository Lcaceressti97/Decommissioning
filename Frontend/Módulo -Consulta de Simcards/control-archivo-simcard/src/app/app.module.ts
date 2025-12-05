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
import { DatePipe } from '@angular/common';
import { SimcardControlFileComponent } from './views/simcard-control-file/simcard-control-file.component';
import { SuppliersModalComponent } from './components/suppliers-modal/suppliers-modal.component';
import { CreateOrderModalComponent } from './components/create-order-modal/create-order-modal.component';
import { OrderControlModalComponent } from './components/order-control-modal/order-control-modal.component';
import { FileDetailModalComponent } from './components/file-detail-modal/file-detail-modal.component';
import { SeeOrderModalComponent } from './components/see-order-modal/see-order-modal.component';
import { ControlOrderModalComponent } from './components/control-order-modal/control-order-modal.component';
import { SimcardOrderControlComponent } from './views/simcard-order-control/simcard-order-control.component';
import { LoadFileComponent } from './components/load-file/load-file.component';
import { ReadingFileComponent } from './components/reading-file/reading-file.component';

@NgModule({
  declarations: [
    AppComponent,
    SimcardControlFileComponent,
    SuppliersModalComponent,
    CreateOrderModalComponent,
    OrderControlModalComponent,
    FileDetailModalComponent,
    SeeOrderModalComponent,
    ControlOrderModalComponent,
    SimcardOrderControlComponent,
    LoadFileComponent,
    ReadingFileComponent
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
  bootstrap: [AppComponent],
  entryComponents: []
})
export class AppModule { }
