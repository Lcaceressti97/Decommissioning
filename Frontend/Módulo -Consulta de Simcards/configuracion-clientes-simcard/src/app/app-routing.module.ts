import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ClientConfigurationComponent } from './view/client-configuration/client-configuration.component';


const routes: Routes = [
  { path: 'mov', component: ClientConfigurationComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
