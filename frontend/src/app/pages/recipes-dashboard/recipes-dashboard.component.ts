import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router, RouterModule, RoutesRecognized } from '@angular/router';
import { CoffeeRoast } from 'src/dtos';
import { BrewMethod } from 'src/dtos/req/brew-method.enum';
import { CoffeeGrindSetting } from 'src/dtos/req/coffee-grind-setting.enum';
import { CommunityRecipeDto } from 'src/dtos/req/community-recipe.dto';
import { RecipeService } from 'src/services/recipe.service';
import { UserService } from 'src/services/user.service';
import { RedditService } from 'src/services/reddit.service';

@Component({
  selector: 'app-recipes-dashboard',
  templateUrl: './recipes-dashboard.component.html',
  styleUrls: ['./recipes-dashboard.component.css'],
})
export class RecipesDashboardComponent implements OnInit {
  recipes: CommunityRecipeDto[] = [];
  userId: number = this.userService.thisUserId();

  constructor(
    private snackBar: MatSnackBar,
    private recipeService: RecipeService,
    private userService: UserService,
    private redditService: RedditService
  ) {}

  ngOnInit(): void {
    this.recipeService.getAllByUser(this.userId).subscribe({
      next: data => {
        console.log(data);
        this.recipes = data;
      },
      error: error => {
        console.error('Error fetching recipes', error);
        this.snackBar.open(
          'Unable to fetch recipe data, try again later',
          'Close',
          {
            duration: 5000,
          }
        );
      },
    });
  }

  formatRoast(recipe: CommunityRecipeDto): String {
    switch (recipe.coffeeBeanRoast) {
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

  formatGrindSetting(recipe: CommunityRecipeDto): string {
    switch (recipe.extractionGrindSetting) {
      case CoffeeGrindSetting.COARSE: {
        return 'Coarse';
      }
      case CoffeeGrindSetting.MEDIUM_COARSE: {
        return 'Med-Coarse';
      }
      case CoffeeGrindSetting.MEDIUM: {
        return 'Medium';
      }
      case CoffeeGrindSetting.MEDIUM_FINE: {
        return 'Med-Fine';
      }
      case CoffeeGrindSetting.FINE: {
        return 'Fine';
      }
      case CoffeeGrindSetting.EXTRA_FINE: {
        return 'Extra-Fine';
      }
      default: {
        return 'Unknown grind';
      }
    }
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

  deleteRecipe(id: number): void {
    this.recipeService.delete(String(id)).subscribe({
      next: res => {
        this.snackBar.open('Successfully deleted coffee bean', 'Close', {
          duration: 5000,
        });
        this.recipes = this.recipes.filter(obj => {
          return obj.recipeId !== id;
        });
      },
      error: err => {
        this.snackBar.open(err.error.match('\\[.*?\\]'), 'Close', {
          duration: 5000,
        });
      },
    });
  }

  shareOnReddit(recipe: CommunityRecipeDto) {
    this.redditService.postToReddit(recipe);
  }
}
