import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SucursalesComponent } from './view/sucursales/sucursales.component';


const routes: Routes = [
  { path: 'mov', component: SucursalesComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },

]
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
