import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CoffeeBeanDto } from 'src/dtos';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CoffeeBeanService {
  constructor(private http: HttpClient) {}

  /**
   * Add a new coffee bean
   * @param coffeeBean The bean to add
   */
  public create(coffeeBean: CoffeeBeanDto) {
    return this.http.post('coffee-bean/create', coffeeBean, {
      responseType: 'text',
    });
  }

  /**
   * Edit an existing coffee bean
   * @param coffeeBean The edited bean
   */
  public edit(coffeeBean: CoffeeBeanDto) {
    return this.http.put('coffee-bean/edit', coffeeBean, {
      responseType: 'text',
    });
  }

  /**
   * Get a coffee bean out of the data storage by its id
   *
   * @param id the id of the coffee bean to fetch
   * @returns the coffee bean as an Observable
   */
  public getById(id: string): Observable<CoffeeBeanDto> {
    return this.http.get<CoffeeBeanDto>('coffee-bean/' + id);
  }

  /**
   * Delete a coffee bean out of the data storage by its id
   *
   * @param id the id of the coffee bean to delete
   */
  public delete(id: string) {
    return this.http.delete<CoffeeBeanDto>('coffee-bean/' + id);
  }
}
