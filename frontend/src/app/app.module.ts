import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatIconModule } from '@angular/material/icon';
import {
  MatFormFieldModule,
  MAT_FORM_FIELD_DEFAULT_OPTIONS,
} from '@angular/material/form-field';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatInputModule } from '@angular/material/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { APIInterceptor } from 'src/util';
import { MatToolbarModule } from '@angular/material/toolbar';
import { JwtModule } from '@auth0/angular-jwt';
import { MatMenuModule } from '@angular/material/menu';
import { LayoutModule } from '@angular/cdk/layout';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialogModule } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { Subject } from 'rxjs';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from './pages/register/register.component';
import { ContainerComponent } from './components/ui/container/container.component';
import { NavigationComponent } from './components/navigation/navigation.component';
import { TestHomeComponent } from './pages/test-home/test-home.component';
import { TestAdminPageComponent } from './pages/test-admin-page/test-admin-page.component';
import { LoginComponent } from './pages/login/login.component';
import { EditAccountDataComponent } from './pages/edit-account-data/edit-account-data.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { CoffeeBeanCreateEditComponent } from './pages/coffee-bean-create-edit/coffee-bean-create-edit.component';
import { ResetPasswordComponent } from './pages/user/reset-password/reset-password.component';
import { UserListComponent } from './pages/user-list/user-list.component';
import { CoffeeBeanDeleteComponent } from './pages/coffee-bean-delete/coffee-bean-delete.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    ContainerComponent,
    NavigationComponent,
    TestHomeComponent,
    TestAdminPageComponent,
    LoginComponent,
    EditAccountDataComponent,
    DashboardComponent,
    CoffeeBeanCreateEditComponent,
    ResetPasswordComponent,
    UserListComponent,
    CoffeeBeanDeleteComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatIconModule,
    MatFormFieldModule,
    MatToolbarModule,
    MatSidenavModule,
    MatSnackBarModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatDialogModule,
    FormsModule,
    ReactiveFormsModule,
    MatGridListModule,
    HttpClientModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: () => localStorage.getItem('token'),
      },
    }),
    MatMenuModule,
    LayoutModule,
    MatTableModule,
  ],
  providers: [
    Subject,
    {
      provide: MAT_FORM_FIELD_DEFAULT_OPTIONS,
      useValue: { appearance: 'outline' },
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: APIInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
