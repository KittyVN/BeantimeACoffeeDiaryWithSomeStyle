import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { CoffeeBeanDashboardDto, CoffeeBeanDto } from 'src/dtos';
import { Observable } from 'rxjs';
import { coffeeBeanSearchDto } from 'src/dtos/req/coffee-bean-search.dto';
import { JwtHelperService } from '@auth0/angular-jwt';

import { CoffeeBeanDetailDto } from '../dtos/req/coffee-bean-detail.dto';

@Injectable({ providedIn: 'root' })
export class CoffeeBeanService {
  constructor(private http: HttpClient, private jwtHelper: JwtHelperService) {}

  /**
   * Add a new coffee bean
   * @param coffeeBean The bean to add
   */
  public create(coffeeBean: CoffeeBeanDto) {
    return this.http.post('coffee-beans', coffeeBean, {
      responseType: 'text',
    });
  }

  /**
   * Fetch all coffees belonging to the
   * user currently logged in matching the given search params.
   * If no params are given, fetches all coffees belonging to the
   * user currently logged in.
   *
   * @param searchParams the parameters to search coffee beans by,
   * including (partial) name, (partial) description and roast method
   * @returns An observable list of coffee entitys
   */
  public search(
    searchParams: coffeeBeanSearchDto
  ): Observable<CoffeeBeanDashboardDto[]> {
    const token = localStorage.getItem('token');
    let tokenPayload;
    if (token) {
      tokenPayload = this.jwtHelper.decodeToken(token);
    }
    let params = new HttpParams();

    if (searchParams.name != null && searchParams.name !== '') {
      params = params.set('name', searchParams.name);
    }

    if (searchParams.roast != null) {
      params = params.set('coffeeRoast', searchParams.roast);
    }

    if (searchParams.description != null && searchParams.description !== '') {
      params = params.set('description', searchParams.description);
    }

    if (searchParams.beanBlend != null && searchParams.beanBlend !== '') {
      params = params.set('beanBlend', searchParams.beanBlend);
    }

    return this.http.get<CoffeeBeanDashboardDto[]>(
      'coffee-beans/user/' + tokenPayload.jti,
      {
        params,
      }
    );
  }

  /**
   * Edit an existing coffee bean
   * @param coffeeBean The edited bean
   */
  public edit(coffeeBean: CoffeeBeanDto) {
    return this.http.put('coffee-beans/' + coffeeBean.id, coffeeBean, {
      responseType: 'text',
    });
  }

  /**
   * Get a coffee bean out of the data storage by its id
   *
   * @param id the id of the coffee bean to fetch
   * @returns the coffee bean as an Observable
   */
  public getById(id: string): Observable<CoffeeBeanDetailDto> {
    return this.http.get<CoffeeBeanDetailDto>('coffee-beans/' + id);
  }

  /**
   * Delete a coffee bean out of the data storage by its id
   *
   * @param id the id of the coffee bean to delete
   */
  public delete(id: string) {
    console.log(id);
    return this.http.delete('coffee-beans/' + id);
  }
}
