import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router, RoutesRecognized } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { filter, pairwise } from 'rxjs';
import { Observable } from 'rxjs/internal/Observable';
import { ExtractionDetailDto } from 'src/dtos';
import { RecipeDto } from 'src/dtos/req/recipe.dto';
import { RecipeService } from 'src/services/recipe.service';

export enum RecipeCreateMode {
  create,
  edit,
}

@Component({
  selector: 'app-recipe-create',
  templateUrl: './recipe-create.component.html',
  styleUrls: ['./recipe-create.component.css'],
})
export class RecipeCreateComponent implements OnInit {
  constructor(
    private recipeService: RecipeService,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) {}

  id: string | null = null;
  previousUrl: string = 'Not an url';
  coffeeId: string | null = null;
  recipeDto: RecipeDto = {
    id: 0,
    description: '',
    extractionId: 0,
  };
  extraction?: ExtractionDetailDto;
  mode: RecipeCreateMode = RecipeCreateMode.create;

  public get submitButtonText(): string {
    switch (this.mode) {
      case RecipeCreateMode.create:
        return 'Create';
      case RecipeCreateMode.edit:
        return 'Edit';
      default:
        return '?';
    }
  }

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      this.mode = data['mode'];
    });
    this.router.events
      .pipe(
        filter((evt: any) => evt instanceof RoutesRecognized),
        pairwise()
      )
      .subscribe((events: RoutesRecognized[]) => {
        this.previousUrl = events[0].urlAfterRedirects;
        console.log(this.previousUrl);
      });
    this.route.paramMap.subscribe(paramMap => {
      this.id = paramMap.get('id');
      this.coffeeId = paramMap.get('coffeeId');
    });
    if (this.id != null) {
      this.recipeDto.extractionId = Number(this.id);
      this.recipeService.getByExtractionId(this.id).subscribe({
        next: data => {
          this.recipeDto = data;
          this.mode = RecipeCreateMode.edit;
        },
        error: err => {
          this.mode = RecipeCreateMode.create;
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
    if (this.createRecipeForm.valid) {
      let observable: Observable<string>;
      switch (this.mode) {
        case RecipeCreateMode.create:
          observable = this.recipeService.create(this.recipeDto);
          break;
        case RecipeCreateMode.edit:
          observable = this.recipeService.edit(this.recipeDto);
          break;
        default:
          console.error('Unknown Mode', this.mode);
          return;
      }
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
