import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InsuranceClaimComponent } from './views/insurance-claim/insurance-claim.component';

const routes: Routes = [
  { path: 'mov', component: InsuranceClaimComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
