import { Injectable } from '@angular/core';
import { ExtractionDetailDto } from 'src/dtos/req/extraction-detail.dto';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { ExtractionCreateDto } from 'src/dtos/req/extraction-create.dto';
import { ExtractionSearchDto } from 'src/dtos/req/extraction-search-dto';

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

  /**
   * Fetch all extractions belonging to the
   * coffee bean defined by the given Id matching the given search params.
   * If no params are given, fetches all extractions belonging to the
   * coffee bean defined by the given Id.
   *
   * @param searchParams the parameters to search extractions by,
   * including date of creation, rating, brew and grind method
   * @returns An observable list of extraction entitys
   */
  public search(
    searchParams: ExtractionSearchDto,
    id: number
  ): Observable<ExtractionDetailDto[]> {
    let params = new HttpParams();

    if (searchParams.created != null && searchParams.created) {
      params = params.set('created', searchParams.created.toString());
    }

    if (searchParams.reverseCreated != null) {
      params = params.set('reverseDate', searchParams.reverseCreated);
    }

    if (searchParams.overallRating != null) {
      params = params.set('overallRating', searchParams.overallRating);
    }

    if (searchParams.reverseOverallRating != null) {
      params = params.set(
        'reverseOverallRating',
        searchParams.reverseOverallRating
      );
    }

    if (searchParams.grindSetting != null) {
      params = params.set('grindSetting', searchParams.grindSetting);
    }

    if (searchParams.brewMethod != null) {
      params = params.set('brewMethod', searchParams.brewMethod);
    }

    return this.http.get<ExtractionDetailDto[]>(
      'extractions/bean/search/' + id,
      {
        params,
      }
    );
  }

  /**
   * Get an extraction out of the data storage by its id
   *
   * @param id the id of the extraction to fetch
   * @returns the extraction as an Observable
   */
  public getById(id: string): Observable<ExtractionDetailDto> {
    return this.http.get<ExtractionDetailDto>('extractions/' + id);
  }

  /**
   * Deletes an existing extraction in the system.
   *
   * @param id the id of the extraction that should be deleted
   * @return nothing
   */
  public delete(id: number) {
    return this.http.delete('extractions/' + id);
  }
}
