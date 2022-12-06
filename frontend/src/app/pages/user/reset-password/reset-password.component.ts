import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/services/user.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css'],
})
export class ResetPasswordComponent {
  constructor(private userService: UserService, private router: Router) {}

  resetForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
  });

  onSubmit() {
    console.log(this.resetForm.value);

    if (this.resetForm.valid) {
      const { email } = this.resetForm.value;
      if (email) {
        this.userService.resetPassword({ email }).subscribe({
          next: res => {
            console.log(res);
            this.router.navigate(['/home']);
          },
          error: err => console.log(err),
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
