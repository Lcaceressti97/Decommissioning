import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ReportGenerationInvComponent } from './views/report-generation-inv/report-generation-inv.component';

const routes: Routes = [
  { path: 'mov', component: ReportGenerationInvComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
