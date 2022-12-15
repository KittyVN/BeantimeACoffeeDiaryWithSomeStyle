import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/services/user.service';
import { AuthService } from 'src/services/auth/auth.service';
import { UpdateUserDto } from 'src/dtos';
import { JwtHelperService } from '@auth0/angular-jwt';

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
    private jwtHelper: JwtHelperService
  ) {}

  user: UpdateUserDto = { id: -999, email: '', password: '' };

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (token !== null) {
      const payload = this.jwtHelper.decodeToken(token);
      this.user.id = payload.jti;
      this.user.email = payload.sub;
    }
    console.log(this.user);
  }

  changeCredentialsForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
  });

  onSubmit() {
    const token = localStorage.getItem('token');
    if (token != null) {
      this.userService.changeCredentials(this.user, this.user.id).subscribe({
        next: res => {
          localStorage.removeItem('token');
          localStorage.setItem('token', res);
        },
        error: err => {
          console.log('error occured');
        },
      });
    } else {
      console.log('Not logged in');
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
