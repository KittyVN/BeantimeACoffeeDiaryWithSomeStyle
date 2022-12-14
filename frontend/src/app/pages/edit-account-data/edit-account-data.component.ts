import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/services/user.service';
import { AuthService } from 'src/services/auth/auth.service';
import { UpdateUserDto } from 'src/dtos';
import { JwtHelperService } from '@auth0/angular-jwt';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-edit-account-data',
  templateUrl: './edit-account-data.component.html',
  styleUrls: ['./edit-account-data.component.css'],
})
export class EditAccountDataComponent implements OnInit {
  constructor(
    private userService: UserService,
    private router: Router,
    private authService: AuthService,
    private jwtHelper: JwtHelperService,
    private snackBar: MatSnackBar
  ) {}

  user: UpdateUserDto = { id: -999, email: '', password: '' };

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (token !== null) {
      const payload = this.jwtHelper.decodeToken(token);
      this.user.id = payload.jti;
      this.user.email = payload.sub;
    } else {
      this.snackBar.open('You are not logged in.', 'Close', {
        duration: 5000,
      });
    }
  }

  changeCredentialsForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
  });

  onSubmit() {
    const token = localStorage.getItem('token');
    if (token != null) {
      this.userService.update(this.user, this.user.id).subscribe({
        next: res => {
          localStorage.removeItem('token');
          localStorage.setItem('token', res);
          this.snackBar.open('Successfully changed account data.', 'Close', {
            duration: 5000,
          });
          this.router.navigate(['/home']);
        },
        error: err => {
          this.snackBar.open(err.error.match('\\[.*?\\]'), 'Close', {
            duration: 5000,
          });
        },
      });
    } else {
      this.snackBar.open('Not logged in.', 'Close', {
        duration: 5000,
      });
    }
  }

  getErrorMessage() {
    if (this.changeCredentialsForm.hasError('required')) {
      return 'You must enter a value';
    }

    return this.changeCredentialsForm.controls.email.hasError('email')
      ? 'Not a valid email'
      : '';
  }
}
