import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router, ActivatedRoute } from '@angular/router';
import { RecipeDetailDto } from 'src/dtos/req/recipeDetail.dto';
import { RecipeService } from 'src/services/recipe.service';
import { Observable } from 'rxjs';

import { BrewMethod } from '../../../dtos/req/brew-method.enum';
import { RecipeListDto } from '../../../dtos/req/recipeList.dto';
import { RedditService } from '../../../services/reddit.service';
import { CoffeeRoast } from '../../../dtos';
import { RecipeRatingListDto } from '../../../dtos/req/recipeRatingList.dto';
import { RecipeRatingService } from '../../../services/recipeRating.service';
import { AuthService } from '../../../services/auth/auth.service';
import { UserService } from '../../../services/user.service';
import { RecipeRatingCreateDto } from '../../../dtos/req/recipeRatingCreate.dto';

@Component({
  selector: 'app-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.css'],
})
export class RecipeDetailComponent implements OnInit {
  recipe: RecipeDetailDto = {
    id: 0,
    shared: false,
    extractionId: 0,
    extractionBrewMethod: '',
    extractionGrindSettings: '',
    extractionBody: 0,
    extractionAcidity: 0,
    extractionAromatics: 0,
    extractionSweetness: 0,
    extractionAftertaste: 0,
    extractionRecipeSteps: '',
    extractionRatingNotes: '',
    extractionWaterAmount: 0,
    extractionWaterTemperature: 0,
    extractionBrewTime: 0,
    extractionDose: 0,
    coffeeBeanId: 0,
    coffeeBeanName: '',
    coffeeBeanBlend: '',
    coffeeBeanRoast: '',
    coffeeBeanUrl: '',
    coffeeBeanStrength: '',
    coffeeBeanOrigin: '',
    coffeeBeanHeight: '',
    coffeeBeanDescription: '',
    coffeeBeanUserId: 0,
    coffeeBeanUserUsername: '',
  };
  ratings: RecipeRatingListDto[] = [];
  isAdmin: boolean = this.authService.isAdmin();
  userId: number = this.userService.thisUserId();
  newRating: RecipeRatingCreateDto = {
    recipeId: 0,
    authorId: this.userId,
    rating: 1,
    text: undefined,
  };

  constructor(
    private recipeService: RecipeService,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar,
    private redditService: RedditService,
    private recipeRatingService: RecipeRatingService,
    private authService: AuthService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(({ id }) => {
      this.recipe.id = id;
      if (this.recipe.id !== undefined) {
        this.recipeService.getById(String(this.recipe.id)).subscribe({
          next: data => {
            this.recipe = data;
            this.newRating.recipeId = this.recipe.id;
          },
          error: error => {
            if (error.status == 404) {
              this.router.navigate(['/community']);
              this.snackBar.open(
                `Recipe with ID ${this.recipe.id} not found.`,
                'OK'
              );
            }
          },
        });

        this.recipeRatingService.getByRecipeId(this.recipe.id).subscribe({
          next: data => {
            this.ratings = data;
          },
        });
      }
    });
  }

  formatBrewMethod(recipe: RecipeDetailDto): string {
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

  formatRoast(): String {
    switch (this.recipe.coffeeBeanRoast) {
      case CoffeeRoast.light: {
        return 'Light Roast';
      }
      case CoffeeRoast.medium: {
        return 'Medium Roast';
      }
      case CoffeeRoast.dark: {
        return 'Dark Roast';
      }
      case CoffeeRoast.double: {
        return 'Double Roast';
      }
      case CoffeeRoast.espresso: {
        return 'Espresso Roast';
      }
      case CoffeeRoast.spanish: {
        return 'Spanish Roast';
      }
      default: {
        return 'Unknown Roast';
      }
    }
  }

  deleteRecipe(): void {
    this.recipeService.delete(this.recipe.id.toString()).subscribe({
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
    let recipeDto: RecipeListDto = {
      id: this.recipe.id,
      shared: !this.recipe.shared,
      extractionId: this.recipe.extractionId,
    };

    this.recipeService.edit(recipeDto).subscribe({
      next: data => {
        this.recipe.shared = data.shared;
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

  deleteRating(rating: RecipeRatingListDto) {
    this.recipeRatingService.delete(rating.id, rating.recipeId).subscribe({
      next: data => {
        this.ratings.splice(this.ratings.indexOf(rating), 1);
        this.snackBar.open('Deleted rating successfully', 'Close', {
          duration: 5000,
        });
      },
    });
  }

  createRating() {
    this.recipeRatingService.create(this.newRating).subscribe({
      next: data => {
        this.ratings.unshift(data);
        this.snackBar.open(`Rating created successfully.`, 'OK');
        this.newRating.rating = 1;
        this.newRating.text = undefined;
      },
    });
  }
}
