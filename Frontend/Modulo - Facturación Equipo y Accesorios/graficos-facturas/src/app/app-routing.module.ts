import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GraficosPrincipalesComponent } from './view/graficos-principales/graficos-principales.component';


const routes: Routes = [
  { path: 'mov', component: GraficosPrincipalesComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },

]
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
