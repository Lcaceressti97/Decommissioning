import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserControlComponent } from './view/user-control/user-control.component';


const routes: Routes = [
  { path: 'mov', component: UserControlComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },

]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
