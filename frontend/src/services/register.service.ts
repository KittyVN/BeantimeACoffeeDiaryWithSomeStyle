import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegisterUserDto } from 'src/dtos';

@Injectable({ providedIn: 'root' })
export class RegisterService {
  constructor(private http: HttpClient) {}

  /**
   * Register a new user
   * @param newUser The user to register
   * @returns The user's token
   */
  public register(newUser: RegisterUserDto) {
    return this.http.post('auth/register', newUser, { responseType: 'text' });
  }
}
