import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SimcardControlFileComponent } from './views/simcard-control-file/simcard-control-file.component';
import { SimcardOrderControlComponent } from './views/simcard-order-control/simcard-order-control.component';

const routes: Routes = [
  { path: 'mov', component: SimcardOrderControlComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
