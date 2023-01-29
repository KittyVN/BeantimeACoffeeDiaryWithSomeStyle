import { Component, OnInit } from '@angular/core';
import { debounce, interval, scan, Subject } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router, RouterModule } from '@angular/router';
import { BrewMethod } from 'src/dtos/req/brew-method.enum';
import { CoffeeGrindSetting } from 'src/dtos/req/coffee-grind-setting';
import { CoffeeRoast } from 'src/dtos/req/coffee-roast';
import { CommunityRecipeDto } from 'src/dtos/req/community-recipe.dto';
import { RecipeService } from 'src/services/recipe.service';
import { RedditService } from 'src/services/reddit.service';
import { RedditAuthService } from 'src/services/auth/reddit-auth.service';
import { RecipeCommunitySearchDto } from 'src/dtos/req/recipe-community-search.dto';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-community-dashboard',
  templateUrl: './community-dashboard.component.html',
  styleUrls: ['./community-dashboard.component.css'],
})
export class CommunityDashboardComponent implements OnInit {
  searchParams: RecipeCommunitySearchDto = {};
  recipes: CommunityRecipeDto[] = [];

  constructor(
    private router: Router,
    private route: RouterModule,
    private snackBar: MatSnackBar,
    private recipeService: RecipeService,
    public keyUp: Subject<KeyboardEvent | Event>,
    private redditAuthService: RedditAuthService
  ) {}

  searchRecipeForm = new FormGroup({
    name: new FormControl('', Validators.maxLength(50)),
    blend: new FormControl('', Validators.maxLength(50)),
    brewMethod: new FormControl(''),
    roast: new FormControl(''),
  });

  redditLoggedIn: boolean = false;

  ngOnInit(): void {
    this.redditLoggedIn = this.redditAuthService.isAuthenticated();
    this.recipeService.search(this.searchParams).subscribe({
      next: data => {
        this.recipes = data;
        console.log(this.recipes);
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
    this.keyUp
      .pipe(
        scan(i => ++i, 1),
        debounce(i => interval(60 * i))
      )
      .subscribe(() => {
        this.recipeService.search(this.searchParams).subscribe({
          next: data => {
            this.recipes = data;
          },
          error: error => {
            console.error('Error fetching recipe data', error);
            this.snackBar.open(
              'Unable to fetch recipe data, try again later',
              'Close',
              {
                duration: 5000,
              }
            );
          },
        });
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
}
