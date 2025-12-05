import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DeductibleRatesComponent } from './views/deductible-rates/deductible-rates.component';

const routes: Routes = [
  { path: 'mov', component: DeductibleRatesComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },

]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
