import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Router } from '@angular/router';
import { UserService } from 'src/services/user.service';

@Component({
  selector: 'app-delete-dialog-user',
  templateUrl: './delete-dialog-user.component.html',
  styleUrls: ['./delete-dialog-user.component.css'],
})
export class DeleteDialogUserComponent {
  id: number | undefined;
  constructor(
    public dialogRef: MatDialogRef<DeleteDialogUserComponent>,
    private userService: UserService,
    private snackBar: MatSnackBar,
    private router: Router,
    private jwtHelper: JwtHelperService
  ) {}

  onNoClick(): void {
    this.dialogRef.close('open');
  }

  deleteAccount() {
    const token = localStorage.getItem('token');
    if (token !== null) {
      const payload = this.jwtHelper.decodeToken(token);
      this.id = payload.jti;
      console.log(this.id);
      if (this.id !== undefined) {
        this.userService.delete(this.id).subscribe({
          next: data => {
            console.log(data);
            localStorage.removeItem('token');
            this.snackBar.open(
              'Your account has been permanentely deleted',
              'Close',
              {
                duration: 5000,
              }
            );
            this.dialogRef.close('closed');
          },
          error: error => {
            console.log(error);
            this.snackBar.open(
              'Something went wrong during the deletion',
              'Close',
              {
                duration: 5000,
              }
            );
          },
        });
      }
    } else {
      this.router.navigate(['/login']);
    }
  }
}
