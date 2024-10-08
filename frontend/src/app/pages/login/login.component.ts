import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from 'src/services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
  });

  ngOnInit() {
    if (this.authService.isAuthenticated() === false) {
      localStorage.removeItem('token');
    }
  }

  onSubmit() {
    console.log(this.loginForm.value);

    if (this.loginForm.valid) {
      const { email, password } = this.loginForm.value;
      if (email && password) {
        this.userService.login({ email, password }).subscribe({
          next: res => {
            localStorage.setItem('token', res);
            this.router.navigate(['/home']);
          },
          error: err => {
            this.snackBar.open(
              'The credentials did not match a user in the system',
              'Close',
              {
                duration: 5000,
              }
            );
          },
        });
      }
    }
  }

  getErrorMessage() {
    return this.loginForm.hasError('required') ? 'You must enter a value' : '';
  }
}
