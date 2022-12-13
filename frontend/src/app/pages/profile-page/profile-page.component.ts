import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css'],
})
export class ProfilePageComponent implements OnInit {
  constructor(private snackBar: MatSnackBar, private router: Router) {}
  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (!token) {
      this.snackBar.open('You are not logged in.', 'Close', {
        duration: 5000,
      });
      this.router.navigate(['/login']);
    }
  }
}
