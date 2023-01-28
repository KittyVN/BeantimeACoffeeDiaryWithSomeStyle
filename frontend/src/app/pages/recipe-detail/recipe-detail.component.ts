import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router, ActivatedRoute } from '@angular/router';
import { CommunityRecipeDto } from 'src/dtos/req/community-recipe.dto';
import { RecipeService } from 'src/services/recipe.service';
import { Observable } from 'rxjs';

import { BrewMethod } from '../../../dtos/req/brew-method.enum';
import { RecipeDto } from '../../../dtos/req/recipe.dto';
import { RedditService } from '../../../services/reddit.service';

@Component({
  selector: 'app-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.css'],
})
export class RecipeDetailComponent implements OnInit {
  recipe: CommunityRecipeDto = {
    recipeId: 0,
    recipeShared: false,
    extractionId: 0,
    extractionAcidity: 0,
    extractionAftertaste: 0,
    extractionAromatics: 0,
    extractionBody: 0,
    extractionBrewMethod: '',
    extractionBrewTime: 0,
    extractionDate: new Date(),
    extractionDose: 0,
    extractionGrindSetting: '',
    extractionRatingNotes: '',
    extractionRecipeSteps: '',
    extractionSweetness: 0,
    extractionWaterAmount: 0,
    extractionWaterTemperature: 0,
    coffeeBeanBlend: '',
    coffeeBeanDescription: '',
    coffeeBeanHeight: 0,
    coffeeBeanId: 0,
    coffeeBeanName: '',
    coffeeBeanOrigin: '',
    coffeeBeanPrice: 0,
    coffeeBeanRoast: '',
    coffeeBeanStrength: '',
    coffeeBeanUrl: '',
  };

  constructor(
    private recipeService: RecipeService,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar,
    private redditService: RedditService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(({ id }) => {
      this.recipe.recipeId = id;
      if (this.recipe.recipeId !== undefined) {
        this.recipeService.getById(String(this.recipe.recipeId)).subscribe({
          next: data => {
            this.recipe = data;
            console.log(this.recipe);
          },
          error: error => {
            if (error.status == 404) {
              this.router.navigate(['/community']);
              this.snackBar.open(
                `Recipe with ID ${this.recipe.recipeId} not found.`,
                'OK'
              );
            }
          },
        });
      }
    });
  }

  formatBrewMethod(recipe: CommunityRecipeDto): string {
    switch (recipe.extractionBrewMethod) {
      case BrewMethod.DRIP: {
        return 'Drip';
      }
      case BrewMethod.BOILING: {
        return 'Boiling';
      }
      case BrewMethod.STEEPING: {
        return 'Steeping';
      }
      case BrewMethod.PRESSURE: {
        return 'Pressure';
      }
      case BrewMethod.TURKISH: {
        return 'Turkish';
      }
      case BrewMethod.FRENCH_PRESS: {
        return 'French press';
      }
      case BrewMethod.COLD_BREW: {
        return 'Cold Brew';
      }
      case BrewMethod.INSTANT: {
        return 'Instant';
      }
      case BrewMethod.POUR_OVER: {
        return 'Pour over';
      }
      case BrewMethod.V60: {
        return 'v60';
      }
      case BrewMethod.ESPRESSO_MACHINE: {
        return 'Espresso machine';
      }
      case BrewMethod.MOKA: {
        return 'Moka';
      }
      case BrewMethod.AEROPRESS: {
        return 'Aeropress';
      }
      case BrewMethod.POD_MACHINE: {
        return 'Pod';
      }
      case BrewMethod.OTHER: {
        return 'Other';
      }
      default: {
        return 'Unknown brewing method';
      }
    }
  }

  deleteRecipe(): void {
    this.recipeService.delete(this.recipe.recipeId.toString()).subscribe({
      next: res => {
        this.snackBar.open('Successfully deleted recipe', 'Close', {
          duration: 5000,
        });
      },
      error: err => {
        this.snackBar.open(err.error.match('\\[.*?\\]'), 'Close', {
          duration: 5000,
        });
      },
    });
  }

  shareOnReddit() {
    this.redditService.postToReddit(this.recipe);
  }

  editRecipe() {
    let observable: Observable<string>;
    let recipeDto: RecipeDto = {
      id: this.recipe.recipeId,
      shared: this.recipe.recipeShared,
      extractionId: this.recipe.extractionId,
    };

    if (this.recipe.recipeShared) {
      recipeDto.shared = false;
    } else {
      recipeDto.shared = true;
    }
    observable = this.recipeService.edit(recipeDto);

    observable.subscribe({
      next: data => {
        this.recipe.recipeShared = recipeDto.shared;
      },
      error: err => {
        if (err.status == 404) {
          this.snackBar.open('There is no recipe by that id', 'Close', {
            duration: 5000,
          });
        }
      },
    });
  }
}
