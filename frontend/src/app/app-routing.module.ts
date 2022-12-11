import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { RoleGuard } from 'src/services/auth/role.guard';

import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { TestHomeComponent } from './pages/test-home/test-home.component';
import { AuthGuard } from '../services/auth/auth.guard';
import { TestAdminPageComponent } from './pages/test-admin-page/test-admin-page.component';
import { ResetPasswordComponent } from './pages/user/reset-password/reset-password.component';
import { UserListComponent } from './pages/user-list/user-list.component';

import type { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'home',
    component: TestHomeComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'admin',
    canActivate: [AuthGuard, RoleGuard],
    data: {
      expectedRole: 'ROLE_ADMIN',
    },
    children: [
      { path: '', component: TestAdminPageComponent },
      { path: 'users', children: [{ path: '', component: UserListComponent }] },
    ],
  },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'resetpassword', component: ResetPasswordComponent },
  { path: '**', redirectTo: 'home' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
