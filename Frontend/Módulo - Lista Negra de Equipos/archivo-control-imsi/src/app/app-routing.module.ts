import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ImeiControlFileComponent } from './views/imei-control-file/imei-control-file.component';

const routes: Routes = [
  { path: 'mov/:param/:type', component: ImeiControlFileComponent },
  { path: 'mov', redirectTo: 'mov/0/0', pathMatch: 'full' },
  { path: '', redirectTo: 'mov/0/0', pathMatch: 'full' },
  { path: '**', component: ImeiControlFileComponent }
];

@NgModule({ 
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
