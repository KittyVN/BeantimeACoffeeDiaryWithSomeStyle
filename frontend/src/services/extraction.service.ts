import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ExtractionDetailDto } from 'src/dtos/req/extraction-detail.dto';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ExtractionService {
  constructor(private http: HttpClient) {}

  public getAllByCoffeeId(id: number): Observable<ExtractionDetailDto[]> {
    return this.http.get<ExtractionDetailDto[]>('extractions/bean/' + id);
  }
}
