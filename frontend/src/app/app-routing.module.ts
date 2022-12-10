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

import type { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'home',
    component: TestHomeComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'admin',
    component: TestAdminPageComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: {
      expectedRole: 'ROLE_ADMIN',
    },
  },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
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
  { path: '**', redirectTo: 'home' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
