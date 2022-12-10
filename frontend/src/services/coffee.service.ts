import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({ providedIn: 'root' })
export class CoffeeService {
  constructor(private http: HttpClient) {}

  /**
   * Fetch all coffees tied to the current user 
   * @param userId The ID of the user
   * @returns An observable list of coffee entitys
   */
  public getall(): Observable<CoffeeBean[]> {
    return this.http.get<CoffeeBean[]>('coffee/userCoffees');
  }
}
