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
import { MatDividerModule } from '@angular/material/divider';
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
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { NgChartsModule } from 'ng2-charts';
import { MatStepperModule } from '@angular/material/stepper';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatListModule } from '@angular/material/list';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from './pages/register/register.component';
import { ContainerComponent } from './components/ui/container/container.component';
import { NavigationComponent } from './components/navigation/navigation.component';
import { LoginComponent } from './pages/login/login.component';
import { EditAccountDataComponent } from './pages/edit-account-data/edit-account-data.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { CoffeeBeanCreateEditComponent } from './pages/coffee-bean-create-edit/coffee-bean-create-edit.component';
import { ResetPasswordComponent } from './pages/user/reset-password/reset-password.component';
import { UserListComponent } from './pages/user-list/user-list.component';
import { CoffeeBeanDeleteComponent } from './pages/coffee-bean-delete/coffee-bean-delete.component';
import { ProfilePageComponent } from './pages/profile-page/profile-page.component';
import { UserDetailComponent } from './pages/user-list/user-detail/user-detail.component';
import { CoffeeBeanDetailComponent } from './pages/coffee-bean-detail/coffee-bean-detail.component';
import { ExtractionCardComponent } from './components/extraction-card/extraction-card.component';
import { ExtractionCreateEditComponent } from './pages/extraction-create-edit/extraction-create-edit.component';
import { ExtractionTimerComponent } from './pages/extraction-create-edit/extraction-timer/extraction-timer.component';
import { StarRatingComponent } from './pages/extraction-create-edit/star-rating/star-rating.component';
import { CommunityDashboardComponent } from './pages/community-dashboard/community-dashboard.component';
import { MillisecondsToSecondsPipe } from './pages/extraction-create-edit/milliseconds-to-seconds.pipe';
import { RecipeDetailComponent } from './pages/recipe-detail/recipe-detail.component';
import { RecipesDashboardComponent } from './pages/recipes-dashboard/recipes-dashboard.component';
import { RedditAuthCallbackComponent } from './pages/reddit-auth-callback/reddit-auth-callback.component';
import { DeleteDialogExtractionComponent } from './components/dialog/delete-dialog-extraction/delete-dialog-extraction.component';
import { DeleteDialogUserComponent } from './components/dialog/delete-dialog-user/delete-dialog-user.component';
import { DeleteDialogCoffeeComponent } from './components/dialog/delete-dialog-coffee/delete-dialog-coffee.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    ContainerComponent,
    NavigationComponent,
    LoginComponent,
    EditAccountDataComponent,
    DashboardComponent,
    CoffeeBeanCreateEditComponent,
    ResetPasswordComponent,
    UserListComponent,
    CoffeeBeanDeleteComponent,
    ProfilePageComponent,
    UserDetailComponent,
    CoffeeBeanDetailComponent,
    ExtractionCardComponent,
    ExtractionCreateEditComponent,
    ExtractionTimerComponent,
    StarRatingComponent,
    CommunityDashboardComponent,
    MillisecondsToSecondsPipe,
    RecipeDetailComponent,
    RecipesDashboardComponent,
    RedditAuthCallbackComponent,
    DeleteDialogExtractionComponent,
    DeleteDialogUserComponent,
    DeleteDialogCoffeeComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatIconModule,
    MatFormFieldModule,
    MatToolbarModule,
    MatSidenavModule,
    MatDividerModule,
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
    MatSlideToggleModule,
    MatStepperModule,
    NgChartsModule,
    MatTooltipModule,
    MatListModule,
    MatProgressSpinnerModule,
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
