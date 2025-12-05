import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ParametersComponent } from './views/parameters/parameters.component';

const routes: Routes = [
  { path: 'mov', component: ParametersComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
