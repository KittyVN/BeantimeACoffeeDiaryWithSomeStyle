import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { DeleteDialogComponent } from 'src/app/components/dialog/delete-dialog/delete-dialog.component';
import { UserService } from 'src/services/user.service';

@Component({
  templateUrl: './test-home.component.html',
  styleUrls: ['./test-home.component.css'],
})
export class TestHomeComponent {
  constructor(
    public dialog: MatDialog,
    private router: Router,
    private userService: UserService
  ) {}
  openDialog(): void {
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
