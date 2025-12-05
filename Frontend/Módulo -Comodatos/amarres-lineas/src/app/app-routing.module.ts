import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LineMooringComponent } from './view/line-mooring/line-mooring.component';


const routes: Routes = [
  { path: 'mov/:param/:type', component: LineMooringComponent },
  { path: 'mov', redirectTo: 'mov/0/0', pathMatch: 'full' },
  { path: '', redirectTo: 'mov/0/0', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov/0/0', pathMatch: 'full' },

]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
