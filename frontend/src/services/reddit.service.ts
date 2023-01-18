import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CoffeeRoast } from 'src/dtos';
import { BrewMethod } from 'src/dtos/req/brew-method.enum';
import { CoffeeGrindSetting } from 'src/dtos/req/coffee-grind-setting.enum';
import { CommunityRecipeDto } from 'src/dtos/req/community-recipe.dto';

import { RedditAuthService } from './auth/reddit-auth.service';

@Injectable({
  providedIn: 'root',
})
export class RedditService {
  constructor(
    private http: HttpClient,
    private snackBar: MatSnackBar,
    private redditAuthService: RedditAuthService
  ) {}

  buildPostingString(recipe: CommunityRecipeDto) {
    let posting = '';
    posting += 'I used ' + recipe.coffeeBeanName + ' for this extraction.\n\n';
    if (recipe.coffeeBeanBlend) {
      posting +=
        'This coffee is a blend of ' +
        recipe.coffeeBeanBlend +
        ' and a is a ' +
        this.formatRoast(recipe) +
        '\n\n';
    } else {
      posting += 'The coffee is a ' + this.formatRoast(recipe) + '\n\n';
    }
    if (recipe.coffeeBeanOrigin) {
      posting += 'Origin: ' + recipe.coffeeBeanOrigin + '\n\n';
    }
    if (recipe.coffeeBeanHeight) {
      posting += 'Height: ' + recipe.coffeeBeanHeight + '\n\n';
    }
    if (recipe.coffeeBeanDescription) {
      posting += 'Description: ' + recipe.coffeeBeanDescription + '\n\n';
    }
    if (recipe.coffeeBeanUrl) {
      posting +=
        'If you want to try this coffee for yourself, you can purchase it here: ' +
        recipe.coffeeBeanUrl +
        '\n\n';
    }
    if (recipe.extractionBrewMethod) {
      posting +=
        'Extraction method used: ' + this.formatBrewMethod(recipe) + '\n\n';
    }
    if (recipe.extractionGrindSetting) {
      posting +=
        'Grind setting used: ' + this.formatGrindSetting(recipe) + '\n\n';
    }
    if (recipe.extractionWaterAmount) {
      posting += 'Amount of water: ' + recipe.extractionWaterAmount + ' ml\n\n';
    }

    if (recipe.extractionWaterTemperature) {
      posting +=
        'Temperature of water used: ' +
        recipe.extractionWaterTemperature +
        ' °C\n\n';
    }

    if (recipe.extractionDose) {
      posting += 'Dose: ' + recipe.extractionDose + ' g\n\n';
    }
    if (recipe.extractionBrewTime) {
      posting +=
        'Time of extraction : ' +
        this.formatTime(recipe.extractionBrewTime) +
        ' seconds\n\n';
    }
    if (recipe.extractionRatingNotes) {
      posting += recipe.extractionRatingNotes;
    }
    return posting;
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

  formatTime(milliseconds: number): number {
    return milliseconds / 1000;
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

  postToReddit(recipe: CommunityRecipeDto) {
    let access_token = this.redditAuthService.getStoredAccessToken();
    if (access_token) {
      let httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/x-www-form-urlencoded',
          Authorization: 'Bearer ' + access_token,
        }),
      };
      let text = this.buildPostingString(recipe);
      let data = new HttpParams()
        .set('text', text)
        .set('title', 'Made this coffee recently:')
        .set('kind', 'self')
        .set('sr', 'privateApiTestingBean');
      this.http
        .post('https://oauth.reddit.com/api/submit', data, httpOptions)
        .subscribe({
          next: data => {},
          error: err => {
            console.log(err);
          },
        });
    } else {
      console.log('Not logged in with Reddit');
      this.snackBar.open(
        'You need to be connected to a Reddit Account in order to post!',
        'Close',
        {
          duration: 5000,
        }
      );
    }
  }
}
