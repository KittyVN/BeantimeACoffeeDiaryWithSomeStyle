import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/services/user.service';

import { UserProfileDto } from '../../../dtos/req/userProfile.dto';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css'],
})
export class ProfilePageComponent implements OnInit {
  constructor(private router: Router, private userService: UserService) {}

  userId: number = this.userService.thisUserId();
  profile: UserProfileDto = {
    email: 'Loading ...',
    extractionMatrix: undefined,
  };

  ngOnInit(): void {
    this.userService.getProfileById(this.userId).subscribe({
      next: profile => {
        this.profile = profile;
      },
    });
  }
}
