import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../../services/user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {UserDetailDto} from "../../../../dtos/req/userDetail.dto";
import {UserAdminEditDto} from "../../../../dtos/req/userAdminEdit.dto";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {

  user: UserDetailDto = {
    id: 0,
    email: '',
    role: '',
    active: false,
  };

  constructor(
    private service: UserService,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(({id}) => {
      this.service.getById(id).subscribe({
        next: data => {
          this.user = data;
        }
      })
    });
  }

  onSubmit(): void {
    let userDto: UserAdminEditDto = {
      role: this.user.role,
      active: this.user.active
    }
    this.service.updateByAdmin(this.user.id, userDto).subscribe({
      next: data => {
        this.user = data;
        this.snackBar.open(`User ${this.user.email} successfully updated!`, 'OK');
      }
    });
  }
}
