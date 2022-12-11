import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/services/user.service';
import { AuthService } from 'src/services/auth/auth.service';
import { RegisterUserDto } from 'src/dtos';

@Component({
  selector: 'app-edit-account-data',
  templateUrl: './edit-account-data.component.html',
  styleUrls: ['./edit-account-data.component.css'],
})
export class EditAccountDataComponent implements OnInit {
  constructor(
    private userService: UserService,
    private router: Router,
    private authService: AuthService
  ) {}

  user: RegisterUserDto = { email: '', password: '' };

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    console.log(token);
    if (token !== null) {
      this.userService.getByToken(token).subscribe({
        next: data => {
          this.user = data;
          console.log(data);
        },
        error: err => {
          console.log('Error fetching User: ', err.getErrorMessage);
        },
      });
    } else {
      console.log('Not logged in');
    }
    console.log(this.user);
  }

  changeCredentialsForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
  });

  onSubmit() {}

  getErrorMessage() {
    if (this.changeCredentialsForm.hasError('required')) {
      return 'You must enter a value';
    }

    return this.changeCredentialsForm.controls.email.hasError('email')
      ? 'Not a valid email'
      : '';
  }
}
