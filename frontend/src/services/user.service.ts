import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { RegisterUserDto, LoginUserDto, EmailDto } from 'src/dtos';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';

import { UserSearchDto } from '../dtos/req/userSearch.dto';
import { UserDetailDto } from '../dtos/req/userDetail.dto';

@Injectable({ providedIn: 'root' })
export class UserService {
  constructor(
    private http: HttpClient,
    private router: Router,
    private jwtHelper: JwtHelperService
  ) {}

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

  public changeCredentials(user: RegisterUserDto, token: string) {
    return this.http.put('auth/change/' + token, user, {
      responseType: 'text',
    });
  }

  public getByToken(token: string): Observable<RegisterUserDto> {
    return this.http.get<RegisterUserDto>('auth/find/' + token);
  }
  /**
   * Logout the current user and redirect to the login page
   * @returns void
   */
  public logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  /**
   * Reset the password of an existing user
   * @param email The email to the password to reset
   * @returns the email
   */
  public resetPassword(email: EmailDto) {
    return this.http.put('auth/resetpassword', email, { responseType: 'text' });
  }

  /**
   * Checks if email links to an existing user
   * @param email The email to check for
   * @returns the email
   */
  public checkEmail(email: EmailDto) {
    const params = new HttpParams().set('email', email.email);
    return this.http.get('auth/checkemail', { params });
  }

  /**
   * Deletes an existing user in the system.
   *
   * @param id the id of the account that should be deleted
   * @return a string giving information
   */
  delete(id: number): Observable<string> {
    return this.http.delete<string>('auth/' + id);
  }

  /**
   * Get a list of users with the given search parameters.
   * @param searchParameters: parameters that can be combined as required
   * @return observable list of found users.
   */
  public search(searchParameters: UserSearchDto): Observable<UserDetailDto[]> {
    let params = new HttpParams();

    if (searchParameters.id != null) {
      params = params.set('id', searchParameters.id);
    }

    if (searchParameters.email != null) {
      params = params.set('email', searchParameters.email);
    }

    if (searchParameters.role != null) {
      params = params.set('role', searchParameters.role);
    }

    return this.http.get<UserDetailDto[]>('users', { params });
  }
}
