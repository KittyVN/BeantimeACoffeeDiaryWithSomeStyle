import { Injectable } from '@angular/core';
import Cookies from 'js-cookie';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor() {}

  IsLoggedIn(): boolean {
    return !!Cookies.get('token');
  }
}
