import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { RegisterUserDto, LoginUserDto } from 'src/dtos';
import { Observable } from 'rxjs';

import { UserDto } from '../dtos/req/user.dto';
import { environment } from '../environment/environment';

const baseUri = environment.apiBase + '/users';

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

  /**
   * Get a list of users with the given search parameters.
   * @param searchParameters: parameters that can be combined as required
   * @return observable list of found users.
   */
  public search(searchParameters: UserDto): Observable<UserDto[]> {
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

    return this.http.get<UserDto[]>(baseUri, { params });
  }
}
