import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegisterUserDto, LoginUserDto } from 'src/dtos';

@Injectable({ providedIn: 'root' })
export class UserService {
  constructor(private http: HttpClient) {}

  /**
   * Register a new user
   * @param newUser The user to register
   * @returns The user's token
   */
  public register(newUser: RegisterUserDto) {
    return this.http.post('auth/register', newUser, { responseType: 'text' });
  }

  /**
   * Login an existing user
   * @param user The user to login
   * @returns The user's token
   */
  public login(user: LoginUserDto) {
    return this.http.post('auth/login', user, { responseType: 'text' });
  }
}
