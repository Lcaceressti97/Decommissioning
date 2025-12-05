import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PriceMasterComponent } from './views/price-master/price-master.component';

const routes: Routes = [
  { path: 'mov', component: PriceMasterComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },

]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
