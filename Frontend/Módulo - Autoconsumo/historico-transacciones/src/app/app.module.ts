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

import { NgbActiveModal, NgbModule, NgbNav, NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { ColorPickerModule } from 'ngx-color-picker';
import { DatePipe } from '@angular/common';
import { TransactionHistoryComponent } from './views/transaction-history/transaction-history.component';
import { TransactionHistoryDetailComponent } from './components/transaction-history-detail/transaction-history-detail.component';
import { StrModalComponent } from './components/str-modal/str-modal.component';

@NgModule({
  declarations: [
    AppComponent,
    TransactionHistoryComponent,
    TransactionHistoryDetailComponent,
    StrModalComponent
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
    NgbNavModule 
  ],
  providers: [NgbActiveModal, DatePipe,NgbNav],
  bootstrap: [AppComponent]
})
export class AppModule { }
