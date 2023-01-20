import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router, ActivatedRoute } from '@angular/router';
import { CommunityRecipeDto } from 'src/dtos/req/community-recipe.dto';
import { RecipeService } from 'src/services/recipe.service';

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
    private snackBar: MatSnackBar
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
}
