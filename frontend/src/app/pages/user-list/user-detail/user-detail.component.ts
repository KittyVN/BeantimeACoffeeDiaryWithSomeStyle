import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

import { UserService } from '../../../../services/user.service';
import { UserDetailDto } from '../../../../dtos/req/userDetail.dto';
import { UserAdminEditDto } from '../../../../dtos/req/userAdminEdit.dto';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css'],
})
export class UserDetailComponent implements OnInit {
  user: UserDetailDto = {
    id: 0,
    email: '',
    username: '',
    role: '',
    active: false,
  };

  constructor(
    private service: UserService,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(({ id }) => {
      this.service.getById(id).subscribe({
        next: data => {
          this.user = data;
        },
        error: error => {
          if (error.status == 404) {
            this.router.navigate(['/admin/users']);
            this.snackBar.open(`User with ID ${this.user.id} not found.`, 'OK');
          }
        },
      });
    });
  }

  onSubmit(): void {
    let userDto: UserAdminEditDto = {
      role: this.user.role,
      active: this.user.active,
    };
    this.service.updateByAdmin(this.user.id, userDto).subscribe({
      next: data => {
        this.user = data;
        this.snackBar.open(
          `User ${this.user.email} successfully updated!`,
          'OK'
        );
      },
    });
  }
}
