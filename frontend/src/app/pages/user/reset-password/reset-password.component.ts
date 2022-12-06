import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/services/user.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css'],
})
export class ResetPasswordComponent {
  constructor(
    private userService: UserService,
    private router: Router,
    private notification: ToastrService
  ) {}

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
            if (res !== 'This email doesnt exist') {
              this.notification.success(`password was successfully changed }.`);
              this.router.navigate(['/home']);
            }
            console.log(res);
            this.notification.info(`this email doesnt exist.`);
          },
          error: err => {
            console.log(err);
            this.notification.error(`${err}`);
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
