import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BankConfigurationComponent } from './views/bank-configuration/bank-configuration.component';

const routes: Routes = [
  { path: 'mov', component: BankConfigurationComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
