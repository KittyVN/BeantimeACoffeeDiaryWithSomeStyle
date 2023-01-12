import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/services/user.service';

import { UserDetailDto } from '../../../dtos';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css'],
})
export class ProfilePageComponent implements OnInit {
  constructor(private router: Router, private userService: UserService) {}

  userId: number = this.userService.thisUserId();
  user: UserDetailDto = {
    id: 0,
    email: 'email@example.com',
    role: 'USER',
    active: true,
  };

  ngOnInit(): void {
    this.userService.getSelf(this.userId).subscribe({
      next: user => {
        this.user = user;
      },
    });
  }
}
