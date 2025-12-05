import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { AngularMultiSelectModule } from "angular2-multiselect-dropdown";

import { NgbActiveModal, NgbModule, NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxDatatableModule } from "@swimlane/ngx-datatable";
import { HttpClientModule } from "@angular/common/http";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { GraficosPrincipalesComponent } from './view/graficos-principales/graficos-principales.component';
import { GraficosCircularesComponent } from './components/graficos-circulares/graficos-circulares.component';
import { GraficosBarraComponent } from './components/graficos-barra/graficos-barra.component';
import { GraficoCircularTipoComponent } from './components/grafico-circular-tipo/grafico-circular-tipo.component';
import { GraficoBarraTipoEstadoComponent } from './components/grafico-barra-tipo-estado/grafico-barra-tipo-estado.component';
import { GraficoBarraSucursalComponent } from './components/grafico-barra-sucursal/grafico-barra-sucursal.component';




@NgModule({
  declarations: [
    AppComponent,
    GraficosPrincipalesComponent,
    GraficosCircularesComponent,
    GraficosBarraComponent,
    GraficoCircularTipoComponent,
    GraficoBarraTipoEstadoComponent,
    GraficoBarraSucursalComponent
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
    ToastrModule.forRoot(),
    NgbNavModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
