import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { RegisterUserDto, LoginUserDto } from 'src/dtos';
import { Observable } from 'rxjs';

import { UserSearchDto } from '../dtos/req/userSearch.dto';
import { UserDetailDto } from '../dtos/req/userDetail.dto';
import { UserAdminEditDto } from '../dtos/req/userAdminEdit.dto';

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

    if (searchParameters.active != null) {
      params = params.set('active', searchParameters.active);
    }

    return this.http.get<UserDetailDto[]>('users', { params }).pipe();
  }

  /**
   * Get the Observable of the user with the specified ID.
   *
   * @param id of the user to get
   * @return the Observable of the user with the specified ID.
   */
  public getById(id: number): Observable<UserDetailDto> {
    return this.http.get<UserDetailDto>(`users/${id}`).pipe();
  }

  /**
   * Update the role and active status for the user with the specified id.
   *
   * @param id of the user to update
   * @param userDto contains the attributes to update
   * @return the Observable for the updated user.
   */
  public updateByAdmin(
    id: number,
    userDto: UserAdminEditDto
  ): Observable<UserDetailDto> {
    return this.http.put<UserDetailDto>(`users/${id}`, userDto).pipe();
  }
}
