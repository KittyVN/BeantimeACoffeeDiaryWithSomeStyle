import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { UserService } from 'src/services/user.service';
import { RedditAuthService } from 'src/services/auth/reddit-auth.service';

import { UserProfileDto } from '../../../dtos/req/userProfile.dto';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css'],
})
export class ProfilePageComponent implements OnInit {
  constructor(
    public dialog: MatDialog,
    private router: Router,
    private userService: UserService,
    private redditAuthService: RedditAuthService
  ) {}

  userId: number = this.userService.thisUserId();
  profile: UserProfileDto = {
    email: 'Loading ...',
    extractionMatrix: undefined,
  };
  extractionMatrixMonthLabels: Map<number, string> = new Map<number, string>();
  Math = Math;

  redditLoggedIn: boolean = false;

  ngOnInit() {
    this.redditLoggedIn = this.redditAuthService.isAuthenticated();

    let today = new Date();
    let startDate = new Date();
    startDate.setDate(today.getDate() - ((today.getDay() + 6) % 7) - 52 * 7);

    let i: number = 2;
    let currentMonth: number = -1;
    let date = new Date(startDate.valueOf());
    while (date < today) {
      if (date.getMonth() !== currentMonth) {
        this.extractionMatrixMonthLabels.set(
          i,
          date.toLocaleDateString('en', { month: 'short' })
        );
        currentMonth = date.getMonth();
      }

      date.setDate(date.getDate() + 7);
      i++;
    }

    this.userService.getProfileById(this.userId).subscribe({
      next: profile => {
        this.profile = profile;
      },
    });
  }

  goToReddit() {
    this.redditAuthService.redirectToAuth();
  }
}
