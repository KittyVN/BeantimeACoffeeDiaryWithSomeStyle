import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { DeleteDialogComponent } from 'src/app/components/dialog/delete-dialog/delete-dialog.component';
import { UserService } from 'src/services/user.service';
import { RedditAuthService } from 'src/services/reddit-auth.service';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css'],
})
export class ProfilePageComponent {
  constructor(
    public dialog: MatDialog,
    private router: Router,
    private userService: UserService,
    private redditAuthService: RedditAuthService
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

  goToReddit() {
    console.log('test');
    this.redditAuthService.redirectToAuth();
  }
}
