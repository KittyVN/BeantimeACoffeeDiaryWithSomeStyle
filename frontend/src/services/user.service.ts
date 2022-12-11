import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegisterUserDto, LoginUserDto } from 'src/dtos';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class UserService {
  constructor(private http: HttpClient, private jwtHelper: JwtHelperService) {}

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

  public changeCredentials(user: RegisterUserDto) {
    return this.http.put('auth/change', user, { responseType: 'text' });
  }

  public getByToken(token: string): Observable<RegisterUserDto> {
    return this.http.get<RegisterUserDto>('auth/find/' + token);
  }
}
