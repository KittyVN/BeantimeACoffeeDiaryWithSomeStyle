import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  constructor(private userService: UserService, private router: Router) {}

  registerForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
  });

  onSubmit() {
    console.log(this.registerForm.value);

    if (this.registerForm.valid) {
      const { email, password } = this.registerForm.value;
      if (email && password) {
        this.userService.register({ email, password }).subscribe({
          next: res => {
            localStorage.setItem('token', res);
            this.router.navigate(['/home']);
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
