import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BulkEmissionComponent } from './views/bulk-emission/bulk-emission.component';


const routes: Routes = [
  { path: 'mov', component: BulkEmissionComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },
];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
