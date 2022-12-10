import { Component, OnInit } from '@angular/core';
import { debounce, interval, scan, Subject } from 'rxjs';

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
  columnsToDisplay = ['id', 'email', 'role', 'isActive'];

  constructor(
    private service: UserService,
    public keyUp: Subject<KeyboardEvent | Event>
  ) {}

  ngOnInit(): void {
    this.loadUsers();
    this.keyUp
      .pipe(
        scan(i => ++i, 1),
        debounce(i => interval(40 * i))
      )
      .subscribe(() => {
        console.log(this.searchParameters);
        this.loadUsers();
      });
  }

  loadUsers() {
    this.service.search(this.searchParameters).subscribe({
      next: data => {
        this.users = data;
      },
    });
  }
}
