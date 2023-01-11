import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
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
  }

  changeCredentialsForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
  });

  onSubmit() {
    const token = localStorage.getItem('token');
    this.userService.update(this.user, this.user.id).subscribe({
      next: res => {
        localStorage.removeItem('token');
        this.snackBar.open('Successfully changed account data.', 'Close', {
          duration: 5000,
        });
        this.router.navigate(['/login']);
      },
      error: err => {
        this.snackBar.open(err.error.match('\\[.*?\\]'), 'Close', {
          duration: 5000,
        });
      },
    });
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
}
