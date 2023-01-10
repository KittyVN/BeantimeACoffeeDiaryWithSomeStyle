import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { CommunityRecipeDto } from 'src/dtos/req/community-recipe.dto';
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

  public getAll(): Observable<CommunityRecipeDto[]> {
    return this.http.get<CommunityRecipeDto[]>('recipes');
  }
}
