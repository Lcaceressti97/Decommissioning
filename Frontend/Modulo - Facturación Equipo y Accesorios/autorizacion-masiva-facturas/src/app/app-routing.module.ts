import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BulkAuthorizeComponent } from './views/bulk-authorize/bulk-authorize.component';


const routes: Routes = [
  { path: 'mov', component: BulkAuthorizeComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
