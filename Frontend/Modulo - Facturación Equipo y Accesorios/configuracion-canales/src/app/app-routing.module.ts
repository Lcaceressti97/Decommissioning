import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ChannelSettingsComponent } from './view/channel-settings/channel-settings.component';


const routes: Routes = [
  { path: 'mov', component: ChannelSettingsComponent },
  { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
  { path: '', redirectTo: 'mov', pathMatch: 'full' },
  { path: '**', redirectTo: 'mov', pathMatch: 'full' },

]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
