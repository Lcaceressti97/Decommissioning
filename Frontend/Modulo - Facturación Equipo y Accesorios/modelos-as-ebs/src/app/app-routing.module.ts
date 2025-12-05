import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ModelsAsEbsComponent } from './views/models-as-ebs/models-as-ebs.component';


const routes: Routes = [
  { path: 'mov', component: ModelsAsEbsComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },

]
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
