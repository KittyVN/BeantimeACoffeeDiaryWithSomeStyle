import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { RoleGuard } from 'src/services/auth/role.guard';

import {
  CoffeeBeanCreateEditMode,
  CoffeeBeanCreateEditComponent,
} from './pages/coffee-bean-create-edit/coffee-bean-create-edit.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { AuthGuard } from '../services/auth/auth.guard';
import { EditAccountDataComponent } from './pages/edit-account-data/edit-account-data.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { ResetPasswordComponent } from './pages/user/reset-password/reset-password.component';
import { UserListComponent } from './pages/user-list/user-list.component';
import { CoffeeBeanDeleteComponent } from './pages/coffee-bean-delete/coffee-bean-delete.component';
import { ProfilePageComponent } from './pages/profile-page/profile-page.component';
import { UserDetailComponent } from './pages/user-list/user-detail/user-detail.component';
import { CoffeeBeanDetailComponent } from './pages/coffee-bean-detail/coffee-bean-detail.component';
import {
  ExtractionCreateEditComponent,
  ExtractionCreateEditMode,
} from './pages/extraction-create-edit/extraction-create-edit.component';
import { CommunityDashboardComponent } from './pages/community-dashboard/community-dashboard.component';
import { RecipeDetailComponent } from './pages/recipe-detail/recipe-detail.component';
import { RecipesDashboardComponent } from './pages/recipes-dashboard/recipes-dashboard.component';
import { RedditAuthCallbackComponent } from './pages/reddit-auth-callback/reddit-auth-callback.component';

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
      { path: '', redirectTo: 'users', pathMatch: 'full' },
      {
        path: 'users',
        children: [
          { path: '', component: UserListComponent },
          { path: ':id/edit', component: UserDetailComponent },
        ],
      },
    ],
  },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'community', component: CommunityDashboardComponent },
  { path: 'community/:id', component: RecipeDetailComponent },
  {
    path: 'my-recipes',
    component: RecipesDashboardComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'socials/reddit/auth',
    canActivate: [AuthGuard],
    component: RedditAuthCallbackComponent,
  },
  {
    path: 'profile',
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        component: ProfilePageComponent,
      },
      {
        path: 'settings',
        component: EditAccountDataComponent,
      },
    ],
  },
  {
    path: 'coffee/:coffeeId/extraction/create',
    canActivate: [AuthGuard],
    component: ExtractionCreateEditComponent,
    data: { mode: ExtractionCreateEditMode.create },
  },
  {
    path: 'coffee/:coffeeId/extraction/:id/edit',
    canActivate: [AuthGuard],
    component: ExtractionCreateEditComponent,
    data: { mode: ExtractionCreateEditMode.edit },
  },
  {
    path: 'coffee/create',
    canActivate: [AuthGuard],
    component: CoffeeBeanCreateEditComponent,
    data: { mode: CoffeeBeanCreateEditMode.create },
  },
  {
    path: 'coffee/:id',
    canActivate: [AuthGuard],
    component: CoffeeBeanDetailComponent,
  },
  {
    path: 'coffee/:id/edit',
    canActivate: [AuthGuard],
    component: CoffeeBeanCreateEditComponent,
    data: { mode: CoffeeBeanCreateEditMode.edit },
  },
  {
    path: 'coffee/:id/delete',
    canActivate: [AuthGuard],
    component: CoffeeBeanDeleteComponent,
  },
  { path: 'resetpassword', component: ResetPasswordComponent },
  { path: '**', redirectTo: 'home' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
