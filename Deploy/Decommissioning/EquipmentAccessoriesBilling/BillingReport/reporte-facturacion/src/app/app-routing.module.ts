import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BillingReportComponent } from './views/billing-report/billing-report.component';

const routes: Routes = [
  { path: 'mov', component: BillingReportComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
