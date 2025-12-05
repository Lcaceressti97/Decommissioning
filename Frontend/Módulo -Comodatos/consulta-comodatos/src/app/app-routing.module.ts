import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ConsultaComodatosSuscriptorComponent } from './components/consulta-comodatos-suscriptor/consulta-comodatos-suscriptor.component';


const routes: Routes = [
  { path: 'mov/:param/:type', component: ConsultaComodatosSuscriptorComponent },
  { path: 'mov', redirectTo: 'mov/0/0', pathMatch: 'full' },
  { path: '', redirectTo: 'mov/0/0', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov/0/0', pathMatch: 'full' },

]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
