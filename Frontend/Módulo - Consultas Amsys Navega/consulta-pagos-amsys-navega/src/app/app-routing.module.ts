import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PaymentConsultationAmsysComponent } from './views/payment-consultation-amsys/payment-consultation-amsys.component';

const routes: Routes = [
  { path: 'mov', component: PaymentConsultationAmsysComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
