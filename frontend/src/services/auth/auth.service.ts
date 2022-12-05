import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import Cookies from 'js-cookie';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(public jwtHelper: JwtHelperService) {}

  isAuthenticated(): boolean {
    const token = localStorage.getItem('token');

    if (token) {
      return !this.jwtHelper.isTokenExpired(token);
    } else {
      return false;
    }
  }
}
