import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from 'src/services/user.service';

@Component({
  selector: 'app-delete-dialog',
  templateUrl: './delete-dialog.component.html',
  styleUrls: ['./delete-dialog.component.css'],
})
export class DeleteDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<DeleteDialogComponent>,
    @Inject(MAT_DIALOG_DATA)
    public data: { id: number },
    private userService: UserService,
    private snackBar: MatSnackBar
  ) {}

  onNoClick(): void {
    this.dialogRef.close('open');
  }

  deleteAccount(id: number) {
    this.userService.delete(id).subscribe({
      next: data => {
        console.log(data);
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
}
