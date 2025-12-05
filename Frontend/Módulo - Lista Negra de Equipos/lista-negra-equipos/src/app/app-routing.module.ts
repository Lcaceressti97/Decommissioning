import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BlacklistPageComponent } from './views/blacklist-page/blacklist-page.component';


const routes: Routes = [
  { path: 'mov/:param/:type', component: BlacklistPageComponent },
  { path: 'mov', redirectTo: 'mov/0/0', pathMatch: 'full' },
  { path: '', redirectTo: 'mov/0/0', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov/0/0', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
