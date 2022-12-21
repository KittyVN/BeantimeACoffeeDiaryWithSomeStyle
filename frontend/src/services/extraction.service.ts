import { Injectable } from '@angular/core';
import { ExtractionDetailDto } from 'src/dtos/req/extraction-detail.dto';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { ExtractionCreateDto } from 'src/dtos/req/extraction-create.dto';

@Injectable({ providedIn: 'root' })
export class ExtractionService {
  constructor(private http: HttpClient, private router: Router) {}

  /**
   * Add a new extraction
   * @param extraction The extraction to add
   */
  public create(extraction: ExtractionCreateDto) {
    return this.http.post('extractions', extraction, {
      responseType: 'text',
    });
  }

  /**
   * Edit an existing extraction
   * @param extraction The extraction to edit
   */
  public edit(extraction: ExtractionCreateDto) {
    return this.http.put('extractions', extraction, {
      responseType: 'text',
    });
  }

  public getAllByCoffeeId(id: number): Observable<ExtractionDetailDto[]> {
    return this.http.get<ExtractionDetailDto[]>('extractions/bean/' + id);
  }
}
