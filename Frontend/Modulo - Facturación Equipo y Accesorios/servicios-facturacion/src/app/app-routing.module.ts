import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BillingServicesComponent } from './view/billing-services/billing-services.component';


const routes: Routes = [
  { path: 'mov', component: BillingServicesComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },

]
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
