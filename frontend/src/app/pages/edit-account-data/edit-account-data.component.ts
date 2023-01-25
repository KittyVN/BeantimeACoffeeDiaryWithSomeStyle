import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { UserService } from 'src/services/user.service';
import { UpdateUserDto } from 'src/dtos';
import { JwtHelperService } from '@auth0/angular-jwt';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';

import { DeleteDialogComponent } from '../../components/dialog/delete-dialog/delete-dialog.component';

@Component({
  selector: 'app-edit-account-data',
  templateUrl: './edit-account-data.component.html',
  styleUrls: ['./edit-account-data.component.css'],
})
export class EditAccountDataComponent implements OnInit {
  constructor(
    private dialog: MatDialog,
    private userService: UserService,
    private jwtHelper: JwtHelperService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {}

  user: UpdateUserDto = { id: -999, email: '', password: '' };

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (token !== null) {
      const payload = this.jwtHelper.decodeToken(token);
      this.user.id = payload.jti;
      this.userService.getSelf(this.user.id).subscribe({
        next: data => {
          this.user.email = data.email;
        },
        error: err => {
          this.snackBar.open(err.error, 'Close', {
            duration: 5000,
          });
        },
      });
    }

    this.form.get('newPassword')?.valueChanges.subscribe(val => {
      if (val) {
        this.form
          .get('newPasswordRepeat')
          ?.setValidators([
            Validators.required,
            this.newPasswordRepeatValidator,
          ]);
      } else {
        this.form
          .get('newPasswordRepeat')
          ?.setValidators([this.newPasswordRepeatValidator]);

        this.form.get('newPasswordRepeat')?.setErrors({});
      }
    });
  }

  newPasswordRepeatValidator(formControl: AbstractControl) {
    if (!formControl.parent) {
      return null;
    }
    if (formControl.parent.get('newPassword')?.value) {
      return formControl.value === formControl.parent.get('newPassword')?.value
        ? null
        : { equalsNewPassword: true };
    }
    return null;
  }

  form = new FormGroup(
    {
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required]),
      newPassword: new FormControl(''),
      newPasswordRepeat: new FormControl('', [this.newPasswordRepeatValidator]),
    },
    { updateOn: 'change' }
  );

  onSubmit() {
    if (this.form.valid) {
      this.userService.update(this.user, this.user.id).subscribe({
        next: res => {
          localStorage.removeItem('token');
          this.snackBar.open('Successfully changed account data.', 'Close', {
            duration: 5000,
          });
          this.router.navigate(['/login']);
        },
        error: error => {
          if (error.status === 422) {
            this.form.get('password')?.setErrors({ wrongPassword: true });
          }
        },
      });
    }
  }

  getErrorMessage() {
    if (this.form.hasError('required')) {
      return 'You must enter a value';
    }

    return this.form.controls.email.hasError('email')
      ? 'Not a valid email'
      : '';
  }

  deleteDialog(): void {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      width: '300px',
      hasBackdrop: true,
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result == 'closed') {
        this.router.navigate(['/login']);
      }
    });
  }

  get passwordControl() {
    return this.form.get('password');
  }

  get newPasswordControl() {
    return this.form.get('newPasswordRepeat');
  }
}
