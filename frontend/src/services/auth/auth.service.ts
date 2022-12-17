import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private jwtHelper: JwtHelperService) {}

  /**
   * Check if the user is authenticated
   * @returns True if a valid token is present
   */
  isAuthenticated(): boolean {
    const token = localStorage.getItem('token');

    if (token) {
      return !this.jwtHelper.isTokenExpired(token);
    } else {
      return false;
    }
  }

  /**
   * Check if the user has ROLE_ADMIN
   * @returns true if user has the role admin
   */
  isAdmin(): boolean {
    const token = localStorage.getItem('token');

    if (token) {
      return this.jwtHelper.decodeToken(token).rol.includes('ROLE_ADMIN');
    } else {
      return false;
    }
  }
}
