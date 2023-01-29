import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { CommunityRecipeDto } from 'src/dtos/req/community-recipe.dto';
import { RecipeCommunitySearchDto } from 'src/dtos/req/recipe-community-search.dto';
import { RecipeDto } from 'src/dtos/req/recipe.dto';

@Injectable({
  providedIn: 'root',
})
export class RecipeService {
  constructor(private http: HttpClient, private router: Router) {}

  /**
   * Add a new recipe
   * @param recipe The recipe to add
   */
  public create(recipe: RecipeDto) {
    return this.http.post('recipes', recipe, {
      responseType: 'text',
    });
  }

  /**
   * Edit an existing recipe
   * @param recipe The edited recipe
   */
  public edit(recipe: RecipeDto) {
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
  public getByExtractionId(id: string): Observable<RecipeDto> {
    return this.http.get<RecipeDto>('recipes/extraction/' + id);
  }

  /**
   * Get all recipes out of the data storage
   *
   * @returns the recipes as an Observable List
   */
  public getAll(): Observable<CommunityRecipeDto[]> {
    return this.http.get<CommunityRecipeDto[]>('recipes');
  }

  /**
   * Get all recipes out of the data storage that match
   * the given search params. If no params match, an empty
   * list is returned. If no params are given, fetches all recipes
   * instead
   *
   * @returns the recipes as an Observable List
   */
  public search(
    searchParams: RecipeCommunitySearchDto
  ): Observable<CommunityRecipeDto[]> {
    let params = new HttpParams();

    if (searchParams.name != null && searchParams.name) {
      params = params.set('name', searchParams.name.toString());
    }

    if (searchParams.blend != null && searchParams.blend) {
      params = params.set('blend', searchParams.blend.toString());
    }

    if (searchParams.brewMethod != null) {
      params = params.set('brewMethod', searchParams.brewMethod);
    }

    if (searchParams.roast != null) {
      params = params.set('roast', searchParams.roast);
    }
    return this.http.get<CommunityRecipeDto[]>('recipes/search', { params });
  }

  /**
   * Get a recipe out of the data storage by its id
   *
   * @param id the id of the recipe to fetch
   * @returns the recipe as an Observable
   */
  public getById(id: string): Observable<CommunityRecipeDto> {
    return this.http.get<CommunityRecipeDto>('recipes/' + id);
  }

  /**
   * Get all recipes from the user out of the data storage
   *
   * @returns the recipes as an Observable List
   */
  public getAllByUser(id: number): Observable<CommunityRecipeDto[]> {
    const idAsString = String(id);
    return this.http.get<CommunityRecipeDto[]>('recipes/user/' + idAsString);
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
