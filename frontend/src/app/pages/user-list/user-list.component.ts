import { Component, OnInit } from '@angular/core';

import { UserDto } from '../../../dtos/req/user.dto';
import { UserService } from '../../../services/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
})
export class UserListComponent implements OnInit {
  searchParameters: UserDto = {};
  users: UserDto[] = [];
  columnsToDisplay = ['id', 'email', 'role'];

  constructor(private service: UserService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers() {
    this.service.search(this.searchParameters).subscribe({
      next: data => {
        this.users = data;
      },
    });
  }
}
