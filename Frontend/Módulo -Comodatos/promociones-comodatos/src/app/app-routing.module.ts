import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PromotionComodatosComponent } from './view/promotion-comodatos/promotion-comodatos.component';


const routes: Routes = [
  { path: 'mov', component: PromotionComodatosComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },

]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
