import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TransactionHistoryComponent } from './views/transaction-history/transaction-history.component';

const routes: Routes = [
  { path: 'mov/:param/:type', component: TransactionHistoryComponent },
  { path: 'mov', redirectTo: 'mov/0/0', pathMatch: 'full' },
  { path: '', redirectTo: 'mov/0/0', pathMatch: 'full' },
  { path: '**', component: TransactionHistoryComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
