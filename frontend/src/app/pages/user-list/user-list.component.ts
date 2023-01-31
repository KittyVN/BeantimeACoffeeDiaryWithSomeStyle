import { Component, OnInit } from '@angular/core';
import { debounce, interval, scan, Subject } from 'rxjs';

import { UserSearchDto } from '../../../dtos/req/userSearch.dto';
import { UserService } from '../../../services/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
})
export class UserListComponent implements OnInit {
  searchParameters: UserSearchDto = {};
  users: UserSearchDto[] = [];
  columnsToDisplay = ['username', 'email', 'role', 'isActive', 'buttons'];

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
