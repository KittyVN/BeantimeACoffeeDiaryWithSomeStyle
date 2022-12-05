import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';

import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class RoleGuard implements CanActivate {
  constructor(
    private auth: AuthService,
    private router: Router,
    private jwtHelper: JwtHelperService
  ) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRole = route.data['expectedRole'];

    const token = localStorage.getItem('token');
    if (!token) {
      this.router.navigate(['/register']);
      return false;
    }

    const tokenPayload = this.jwtHelper.decodeToken(token);

    if (!this.auth.isAuthenticated() || tokenPayload.rol[0] !== expectedRole) {
      this.router.navigate(['/register']);
      return false;
    }
    return true;
  }
}
