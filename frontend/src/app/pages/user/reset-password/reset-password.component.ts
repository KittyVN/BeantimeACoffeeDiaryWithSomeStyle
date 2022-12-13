import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/services/user.service';
import { MatSnackBar, MatSnackBarRef } from '@angular/material/snack-bar';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css'],
})
export class ResetPasswordComponent {
  constructor(
    private userService: UserService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  resetForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
  });

  onSubmit() {
    if (this.resetForm.valid) {
      const { email } = this.resetForm.value;
      if (email) {
        this.userService.checkEmail({ email }).subscribe({
          next: res => {
            console.log('Email password can be reset');
            this.snackBar.open(
              'You should receive an email with your new password shortly.',
              'Close',
              {
                duration: 5000,
              }
            );
            this.router.navigate(['/home']);
            this.userService.resetPassword({ email }).subscribe({
              next: res => {
                console.log(res);
              },
              error: err => {
                console.log(err);
                this.snackBar.open(
                  'Something went wrong while resetting your password. Please try again later',
                  'Close',
                  {
                    duration: 5000,
                  }
                );
              },
            });
          },
          error: err => {
            console.log(err.error);
            this.snackBar.open(err.error, 'Close', {
              duration: 5000,
            });
          },
        });
      }
    }
  }

  getErrorMessage() {
    if (this.resetForm.hasError('required')) {
      return 'You must enter a value';
    }

    return this.resetForm.controls.email.hasError('email')
      ? 'Not a valid email'
      : '';
  }
}
