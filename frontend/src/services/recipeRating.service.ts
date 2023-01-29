import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { RecipeRatingCreateDto } from '../dtos/req/recipeRatingCreate.dto';
import { RecipeRatingListDto } from '../dtos/req/recipeRatingList.dto';

@Injectable({ providedIn: 'root' })
export class RecipeRatingService {
  constructor(private http: HttpClient) {}

  /**
   * Add a new rating
   *
   * @param rating create dto to be added to the system
   * @return Observable of the created rating
   */
  public create(
    rating: RecipeRatingCreateDto
  ): Observable<RecipeRatingListDto> {
    return this.http
      .post<RecipeRatingListDto>(`recipes/${rating.recipeId}/ratings`, rating)
      .pipe();
  }

  /**
   * Get ratings by recipe id
   *
   * @param recipeId of the recipe
   * @return an array of Observables containing the found recipes
   */
  public getByRecipeId(recipeId: number): Observable<RecipeRatingListDto[]> {
    return this.http
      .get<RecipeRatingListDto[]>(`recipes/${recipeId}/ratings`)
      .pipe();
  }

  /**
   * Delete a rating by its id
   *
   * @param id of the rating to delete
   * @param recipeId of the recipe
   */
  public delete(id: number, recipeId: number) {
    return this.http.delete(`recipes/${recipeId}/ratings/${id}`).pipe();
  }
}
