import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BalanceInquiryAmsysComponent } from './views/balance-inquiry-amsys/balance-inquiry-amsys.component';

const routes: Routes = [
  { path: 'mov', component: BalanceInquiryAmsysComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
