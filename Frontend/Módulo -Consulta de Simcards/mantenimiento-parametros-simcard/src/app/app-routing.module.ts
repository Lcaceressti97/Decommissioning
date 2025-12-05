import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MantenimientoTarjetasComponent } from './view/mantenimiento-tarjetas/mantenimiento-tarjetas.component';


const routes: Routes = [
  { path: 'mov', component: MantenimientoTarjetasComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
