import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CoffeeBeanDashboardDto, CoffeeBeanDto } from 'src/dtos';
import { Observable } from 'rxjs';

import { CoffeeBeanDetailDto } from '../dtos/req/coffee-bean-detail.dto';

@Injectable({ providedIn: 'root' })
export class CoffeeBeanService {
  constructor(private http: HttpClient) {}

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
   * Fetch all coffees

   * @returns An observable list of coffee entitys
   */
  public getall(): Observable<CoffeeBeanDashboardDto[]> {
    return this.http.get<CoffeeBeanDashboardDto[]>('coffee-beans');
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
