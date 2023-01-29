import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { JwtHelperService } from '@auth0/angular-jwt';

import { RecipeRatingCreateDto } from '../dtos/req/recipeRatingCreate.dto';
import { RecipeRatingUpdateDto } from '../dtos/req/recipeRatingUpdate.dto';

@Injectable({ providedIn: 'root' })
export class RecipeRatingService {
  constructor(private http: HttpClient, private jwtHelper: JwtHelperService) {}

  /**
   * Add a new rating
   *
   * @param rating create dto to be added to the system
   */
  public create(rating: RecipeRatingCreateDto) {}

  /**
   * Get ratings by recipe id
   *
   * @param recipeId of the recipe
   */
  public getByRecipeId(recipeId: number) {}

  /**
   * Update a rating with the given dto
   *
   * @param rating the rating to update
   */
  public update(rating: RecipeRatingUpdateDto) {}

  /**
   * Delete a rating by its id
   *
   * @param id of the rating to delete
   */
  public delete(id: number) {}
}
