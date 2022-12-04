import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RegisterComponent } from './pages/register/register.component';

import type { Routes } from '@angular/router';

const routes: Routes = [{ path: 'register', component: RegisterComponent }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
