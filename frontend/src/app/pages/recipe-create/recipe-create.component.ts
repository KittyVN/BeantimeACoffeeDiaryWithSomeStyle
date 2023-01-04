import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs/internal/Observable';
import { RecipeService } from 'src/app/services/recipe.service';
import { ExtractionDetailDto } from 'src/dtos';
import { RecipeDto } from 'src/dtos/req/recipe.dto';
import { ExtractionService } from 'src/services/extraction.service';

@Component({
  selector: 'app-recipe-create',
  templateUrl: './recipe-create.component.html',
  styleUrls: ['./recipe-create.component.css'],
})
export class RecipeCreateComponent implements OnInit {
  constructor(
    private recipeService: RecipeService,
    private extractionService: ExtractionService,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar,
    private jwtHelper: JwtHelperService
  ) {}

  id: string | null = null;
  coffeeId: string | null = null;
  recipeDto: RecipeDto = {
    id: 0,
    description: '',
    extractionId: 0,
  };
  extraction?: ExtractionDetailDto;

  ngOnInit(): void {
    this.route.paramMap.subscribe(paramMap => {
      this.id = paramMap.get('id');
      this.coffeeId = paramMap.get('coffeeId');
    });
    if (this.id != null) {
      this.recipeDto.extractionId = Number(this.id);
      this.extractionService.getById(this.id).subscribe({
        next: data => {
          this.extraction = data;
        },
        error: err => {
          this.snackBar.open(err.error, 'Close', {
            duration: 5000,
          });
          this.router.navigate(['/home']);
        },
      });
    } else {
      this.snackBar.open(
        'The extraction you tried to show does not exist',
        'Close',
        {
          duration: 5000,
        }
      );
    }
  }

  createRecipeForm = new FormGroup({
    description: new FormControl('', Validators.maxLength(5000)),
  });

  onSubmit() {
    console.log(this.recipeDto);
    if (this.createRecipeForm.valid) {
      let observable: Observable<string>;
      observable = this.recipeService.create(this.recipeDto);
      observable.subscribe({
        next: data => {
          this.snackBar.open('Recipe was successfully shared', 'Close', {
            duration: 5000,
          });
          this.router.navigate(['/coffee/' + this.coffeeId]);
        },
        error: err => {
          if (err.status == 409) {
            this.snackBar.open(
              'There already exists a recipe for this extraction',
              'Close',
              {
                duration: 5000,
              }
            );
            this.router.navigate(['/coffee/' + this.coffeeId]);
          }
        },
      });
    }
  }
}
