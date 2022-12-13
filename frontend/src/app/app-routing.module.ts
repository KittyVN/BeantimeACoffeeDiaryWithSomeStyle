import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { RoleGuard } from 'src/services/auth/role.guard';

import {
  CoffeeBeanCreateEditMode,
  CoffeeBeanCreateEditComponent,
} from './pages/coffee-bean-create-edit/coffee-bean-create-edit.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { TestHomeComponent } from './pages/test-home/test-home.component';
import { AuthGuard } from '../services/auth/auth.guard';
import { TestAdminPageComponent } from './pages/test-admin-page/test-admin-page.component';
import { EditAccountDataComponent } from './pages/edit-account-data/edit-account-data.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { ResetPasswordComponent } from './pages/user/reset-password/reset-password.component';
import { UserListComponent } from './pages/user-list/user-list.component';
import { CoffeeBeanDeleteComponent } from './pages/coffee-bean-delete/coffee-bean-delete.component';

import type { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'home',
    component: DashboardComponent,
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
  { path: 'change', component: EditAccountDataComponent },
  {
    path: 'create-coffee-bean',
    component: CoffeeBeanCreateEditComponent,
    data: { mode: CoffeeBeanCreateEditMode.create },
  },
  {
    path: ':id/edit-coffee-bean',
    component: CoffeeBeanCreateEditComponent,
    data: { mode: CoffeeBeanCreateEditMode.edit },
  },
  { path: ':id/delete-coffee-bean', component: CoffeeBeanDeleteComponent },
  { path: 'resetpassword', component: ResetPasswordComponent },
  { path: '**', redirectTo: 'home' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
