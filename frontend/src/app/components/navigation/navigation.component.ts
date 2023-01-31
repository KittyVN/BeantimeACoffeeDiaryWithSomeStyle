import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { environment } from 'src/environment/environment';
import { AuthService } from 'src/services/auth/auth.service';
import { UserService } from 'src/services/user.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css'],
})
export class NavigationComponent {
  appName = environment.appName;
  isAuthenticated = false;
  isAdmin = false;
  username = 'User';

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private router: Router
  ) {
    this.router.events.subscribe(event => {
      // check if the event is of type NavigationEnd
      if (event instanceof NavigationEnd) {
        this.isAdmin = this.authService.isAdmin();
        this.isAuthenticated = this.authService.isAuthenticated();
        this.userService.getSelf(this.userService.thisUserId()).subscribe({
          next: user => {
            this.username = user.username;
          },
        });
      }
    });
  }

  public logout() {
    this.userService.logout();
  }
}
