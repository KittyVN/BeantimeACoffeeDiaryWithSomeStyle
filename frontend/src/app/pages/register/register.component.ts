import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import Cookies from 'js-cookie';
import { RegisterService } from 'src/services/register.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  constructor(
    private registerService: RegisterService,
    private router: Router
  ) {}

  registerForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
  });

  onSubmit() {
    console.log(this.registerForm.value);

    if (this.registerForm.valid) {
      const { email, password } = this.registerForm.value;
      if (email && password) {
        this.registerService.register({ email, password }).subscribe({
          next: res => {
            Cookies.set('token', res);
            this.router.navigate(['/']);
          },
          error: err => console.log(err),
        });
      }
    }
  }

  getErrorMessage() {
    if (this.registerForm.hasError('required')) {
      return 'You must enter a value';
    }

    return this.registerForm.controls.email.hasError('email')
      ? 'Not a valid email'
      : '';
  }
}
