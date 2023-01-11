import { Component, OnInit, Input } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ExtractionService } from 'src/services/extraction.service';
import { CoffeeBeanDto } from 'src/dtos';
import { Roast } from 'src/dtos/req/roast-type.enum';
import { CoffeeBeanService } from 'src/services/coffee-bean.service';
import { ExtractionCreateDto } from 'src/dtos/req/extraction-create.dto';
import { BrewMethod } from 'src/dtos/req/brew-method-enum';
import { Observable } from 'rxjs';

export enum ExtractionCreateEditMode {
  create,
  edit,
}

@Component({
  selector: 'app-extraction-create-edit',
  templateUrl: './extraction-create-edit.component.html',
  styleUrls: ['./extraction-create-edit.component.css'],
})
export class ExtractionCreateEditComponent implements OnInit {
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar,
    private extractionService: ExtractionService,
    private coffeeBeanService: CoffeeBeanService
  ) {}

  currentStep: number = 0;
  id: string | null = null;
  coffeeId: string | null = null;
  coffee: CoffeeBeanDto = {
    name: 'Coffee',
    coffeeRoast: Roast.LIGHT,
  };
  extraction: ExtractionCreateDto = {
    brewMethod: BrewMethod.DRIP,
    brewTime: 300,
    body: 3,
    acidity: 3,
    aromatics: 3,
    aftertaste: 3,
    sweetness: 3,
  };

  mode: ExtractionCreateEditMode = ExtractionCreateEditMode.create;
  parameterForm = new FormGroup({
    brewMethod: new FormControl('', Validators.required),
    grindSetting: new FormControl(''),
    waterTemperature: new FormControl('', [
      Validators.min(0),
      Validators.max(100),
    ]),
    dose: new FormControl('', [Validators.min(0)]),
    waterAmount: new FormControl('', [Validators.min(0), Validators.max(3000)]),
  });
  ratingForm = new FormGroup({
    acidity: new FormControl('', [Validators.min(0)]),
    aromatics: new FormControl('', [Validators.min(0)]),
    sweetness: new FormControl('', [Validators.min(0)]),
    aftertaste: new FormControl('', [Validators.min(0)]),
    ratingNotes: new FormControl('', Validators.maxLength(255)),
  });

  get modeIsCreate(): boolean {
    return this.mode === ExtractionCreateEditMode.create;
  }

  displayTime(): number {
    console.log('a');
    if (this.extraction.brewTime) return this.extraction.brewTime * 100;
    else return 0;
  }
  onSubmit() {
    let observable: Observable<string>;
    switch (this.mode) {
      case ExtractionCreateEditMode.create:
        observable = this.extractionService.create(this.extraction);
        break;
      case ExtractionCreateEditMode.edit:
        observable = this.extractionService.edit(this.extraction);
        break;
      default:
        console.error('Unknown CoffeeBeanCreateEditMode', this.mode);
        return;
    }
    observable.subscribe({
      next: data => {
        this.router.navigate(['/home']);
      },
      error: err => {
        this.snackBar.open(err.error.match('\\[.*?\\]'), 'Close', {
          duration: 5000,
        });
      },
    });
  }

  resetRatings() {
    this.extraction.body = 0;
    this.extraction.acidity = 0;
    this.extraction.aromatics = 0;
    this.extraction.aftertaste = 0;
    this.extraction.sweetness = 0;
  }

  setDefaultRatings(extraction: ExtractionCreateDto) {
    extraction.body = 3;
    extraction.acidity = 3;
    extraction.aromatics = 3;
    extraction.aftertaste = 3;
    extraction.sweetness = 3;
  }

  submitNoRating() {
    this.resetRatings();
    let observable: Observable<string>;
    switch (this.mode) {
      case ExtractionCreateEditMode.create:
        observable = this.extractionService.create(this.extraction);
        break;
      case ExtractionCreateEditMode.edit:
        observable = this.extractionService.edit(this.extraction);
        break;
      default:
        console.error('Unknown CoffeeBeanCreateEditMode', this.mode);
        return;
    }
    observable.subscribe({
      next: data => {
        this.router.navigate(['/home']);
      },
      error: err => {
        this.snackBar.open(err.error.match('\\[.*?\\]'), 'Close', {
          duration: 5000,
        });
      },
    });
  }

  changeBody(value: number) {
    this.extraction.body = value;
    console.log(this.extraction);
  }

  changeAftertaste(value: number) {
    this.extraction.aftertaste = value;
  }

  changeAcidity(value: number) {
    this.extraction.acidity = value;
  }

  changeSweetness(value: number) {
    this.extraction.sweetness = value;
  }

  changeAromatics(value: number) {
    this.extraction.aromatics = value;
  }

  rated(extraction: ExtractionCreateDto): boolean {
    return !(
      extraction.body === 0 &&
      extraction.acidity === 0 &&
      extraction.aromatics === 0 &&
      extraction.sweetness === 0 &&
      extraction.aftertaste === 0
    );
    return false;
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(paramMap => {
      this.coffeeId = paramMap.get('coffeeId');
    });
    if (this.coffeeId != null) this.extraction.beanId = parseInt(this.coffeeId);
    this.route.data.subscribe(data => {
      this.mode = data['mode'];
    });
    if (this.coffeeId != null) {
      this.coffeeBeanService.getById(this.coffeeId.toString()).subscribe({
        next: data => {
          this.coffee = data.coffeeBean;
        },
        error: err => {
          this.snackBar.open(
            'You cannot start a extraction without a coffee',
            'Close',
            {
              duration: 5000,
            }
          );
        },
      });
      if (this.mode === ExtractionCreateEditMode.edit) {
        this.currentStep = 2;
        this.route.paramMap.subscribe(paramMap => {
          this.id = paramMap.get('id');
        });
        if (this.id != null) {
          this.extractionService.getById(this.id).subscribe({
            next: data => {
              console.log(data);
              console.log(this.rated(data));
              console.log('before changes');
              console.log(data);
              if (!this.rated(data)) this.setDefaultRatings(data);
              console.log('after changes');
              console.log(data);
              this.extraction = data;
            },
            error: err => {
              this.snackBar.open(err.error, 'Close', {
                duration: 5000,
              });
            },
          });
        } else {
          this.snackBar.open(
            'The extraction you tried to edit does not exist',
            'Close',
            {
              duration: 5000,
            }
          );
        }
      }
    } else {
      this.snackBar.open(
        'The coffee you tried extract with does not exist',
        'Close',
        {
          duration: 5000,
        }
      );
    }
  }
}
