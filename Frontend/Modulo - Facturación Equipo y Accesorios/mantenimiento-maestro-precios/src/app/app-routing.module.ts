import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PriceMasterMaintenanceComponent } from './view/price-master-maintenance/price-master-maintenance.component';

const routes: Routes = [
  { path: 'mov', component: PriceMasterMaintenanceComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },

]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
