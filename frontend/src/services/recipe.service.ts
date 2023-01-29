import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { RecipeDetailDto } from 'src/dtos/req/recipeDetail.dto';
import { RecipeListDto } from 'src/dtos/req/recipeList.dto';

@Injectable({
  providedIn: 'root',
})
export class RecipeService {
  constructor(private http: HttpClient, private router: Router) {}

  /**
   * Add a new recipe
   * @param recipe The recipe to add
   */
  public create(recipe: RecipeListDto) {
    return this.http.post('recipes', recipe, {
      responseType: 'text',
    });
  }

  /**
   * Edit an existing recipe
   * @param recipe The edited recipe
   */
  public edit(recipe: RecipeListDto) {
    return this.http.put('recipes/extraction/' + recipe.extractionId, recipe, {
      responseType: 'text',
    });
  }

  /**
   * Get an recipe out of the data storage by its corresponding extraction id
   *
   * @param id the id of the extraction to fetch the recipe by
   * @returns the recipe as an Observable
   */
  public getByExtractionId(id: string): Observable<RecipeListDto> {
    return this.http.get<RecipeListDto>('recipes/extraction/' + id);
  }

  /**
   * Get all recipe out of the data storage
   *
   * @returns the recipes as an Observable List
   */
  public getAll(): Observable<RecipeDetailDto[]> {
    return this.http.get<RecipeDetailDto[]>('recipes');
  }

  /**
   * Get a recipe out of the data storage by its id
   *
   * @param id the id of the recipe to fetch
   * @returns the recipe as an Observable
   */
  public getById(id: string): Observable<RecipeDetailDto> {
    return this.http.get<RecipeDetailDto>('recipes/' + id);
  }

  /**
   * Get all recipes from the user out of the data storage
   *
   * @returns the recipes as an Observable List
   */
  public getAllByUser(id: number): Observable<RecipeDetailDto[]> {
    const idAsString = String(id);
    return this.http.get<RecipeDetailDto[]>('recipes/user/' + idAsString);
  }

  /**
   * Delete a recipe out of the data storage by its id
   *
   * @param id the id of the recipe to delete
   */
  public delete(id: string) {
    return this.http.delete('recipes/' + id);
  }
}
