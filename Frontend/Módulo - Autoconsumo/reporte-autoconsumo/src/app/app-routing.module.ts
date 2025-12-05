import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SelfControlReportComponent } from './views/self-control-report/self-control-report.component';


const routes: Routes = [
  { path: 'mov', component: SelfControlReportComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
