import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RegisterComponent } from './pages/register/register.component';
import { TestHomeComponent } from './pages/test-home/test-home.component';
import { AuthGuard } from '../services/auth/auth.guard';

import type { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'home',
    component: TestHomeComponent,
    canActivate: [AuthGuard],
  },
  { path: 'register', component: RegisterComponent },
  { path: '**', redirectTo: 'register' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
