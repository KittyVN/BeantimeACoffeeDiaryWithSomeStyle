import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ExtractionCreateDto } from 'src/dtos/req/extraction-create.dto';
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
}
