import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CoffeeBeanDashboardDto } from 'src/dtos';

@Injectable({ providedIn: 'root' })
export class CoffeeService {
  constructor(private http: HttpClient) {}

  /**
   * Fetch all coffees 

   * @returns An observable list of coffee entitys
   */
  public getall(): Observable<CoffeeBeanDashboardDto[]> {
    return this.http.get<CoffeeBeanDashboardDto[]>('coffee-bean');
  }
}
