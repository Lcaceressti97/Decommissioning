import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ConsultSimcardComponent } from './view/consult-simcard/consult-simcard.component';


const routes: Routes = [
  { path: 'mov/:param/:type', component: ConsultSimcardComponent },
  { path: 'mov', redirectTo: 'mov/0/0', pathMatch: 'full' },
  { path: '', redirectTo: 'mov/0/0', pathMatch: 'full' },
  { path: '**', component: ConsultSimcardComponent }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
