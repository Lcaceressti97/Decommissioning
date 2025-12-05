import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InsuranceConsultationComponent } from './views/insurance-consultation/insurance-consultation.component';

const routes: Routes = [
  { path: 'mov', component: InsuranceConsultationComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
