import {
  Component,
  OnInit,
  Input,
  ViewChild,
  EventEmitter,
  Output,
} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router, ActivatedRoute } from '@angular/router';
import { ChartConfiguration, ChartData } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { Observable } from 'rxjs/internal/Observable';
import { BrewMethod } from 'src/dtos/req/brew-method.enum';
import { CoffeeGrindSetting } from 'src/dtos/req/coffee-grind-setting.enum';
import { ExtractionDetailDto } from 'src/dtos/req/extraction-detail.dto';
import { RecipeDto } from 'src/dtos/req/recipe.dto';
import { RecipeService } from 'src/services/recipe.service';

import { DeleteDialogExtractionComponent } from '../dialog/delete-dialog-extraction/delete-dialog-extraction.component';

@Component({
  selector: 'app-extraction-card',
  templateUrl: './extraction-card.component.html',
  styleUrls: ['./extraction-card.component.css'],
})
export class ExtractionCardComponent implements OnInit {
  @Input() extraction?: ExtractionDetailDto;
  @Input() coffeeBeanId?: number;
  @Input() cardActions?: boolean;
  @Output() deleteItemEvent = new EventEmitter<number>();
  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;

  constructor(
    private recipeService: RecipeService,
    private router: Router,
    private snackBar: MatSnackBar,
    private dialog: MatDialog
  ) {}

  public doughnutChartData: ChartData<'doughnut'> = {
    datasets: [
      {
        data: this.getOverallRating(),
        backgroundColor: ['#4caf50', '#f68f83'],
      },
    ],
  };
  public doughnutChartType: ChartConfiguration<'doughnut'>['type'] = 'doughnut';
  public doughnutChartOptions: ChartConfiguration<'doughnut'>['options'] = {
    responsive: true,
    cutout: '70%',
  };

  rated(): boolean {
    if (this.extraction) {
      return !(
        this.extraction.overallRating === 0 &&
        this.extraction.body === 0 &&
        this.extraction.acidity === 0 &&
        this.extraction.aromatics === 0 &&
        this.extraction.sweetness === 0 &&
        this.extraction.aftertaste === 0
      );
    }
    return false;
  }

  exists(): boolean {
    if (this.extraction) {
      return true;
    }
    return false;
  }

  ngOnInit(): void {
    this.doughnutChartData.datasets = [
      {
        data: this.getOverallRating(),
        backgroundColor: ['#4caf50', '#f68f83'],
      },
    ];
    console.log(this.getOverallRating());
    this.chart?.update();
  }

  getOverallRating(): number[] {
    if (this.extraction?.overallRating) {
      return [
        this.extraction.overallRating,
        25 - this.extraction.overallRating,
      ];
    } else {
      return [1, 2];
    }
  }

  realizeBody(): number {
    if (this.extraction?.body) {
      return this.extraction.body;
    }
    return -1;
  }

  realizeAcidity(): number {
    if (this.extraction?.acidity) {
      return this.extraction.acidity;
    }
    return -1;
  }

  realizeAromatics(): number {
    if (this.extraction?.aromatics) {
      return this.extraction.aromatics;
    }
    return -1;
  }

  realizeSweetness(): number {
    if (this.extraction?.sweetness) {
      return this.extraction.sweetness;
    }
    return -1;
  }

  realizeAftertaste(): number {
    if (this.extraction?.aftertaste) {
      return this.extraction.aftertaste;
    }
    return -1;
  }

  realizeOverallRating(): number {
    if (this.extraction?.overallRating) {
      return this.extraction.overallRating;
    }
    return -1;
  }

  ratingArray(): Array<number> {
    return [].constructor(5);
  }

  formatBrewTime(): string {
    if (this.extraction) {
      let brewtime = this.extraction.brewTime;
      let milliseconds = Math.floor((brewtime % 1000) / 100),
        seconds = Math.floor((brewtime / 1000) % 60),
        minutes = Math.floor((brewtime / (1000 * 60)) % 60),
        hours = Math.floor((brewtime / (1000 * 60 * 60)) % 24);

      return (
        (hours < 10 ? '0' + hours : hours) +
        ':' +
        (minutes < 10 ? '0' + minutes : minutes) +
        ':' +
        (seconds < 10 ? '0' + seconds : seconds)
      );
    }
    return 'undefined';
  }

  formatBrewMethod(): string {
    if (this.extraction) {
      switch (this.extraction.brewMethod) {
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
          return 'Espresso';
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
      }
    }
    return 'undefined';
  }

  formatGrindSetting(): string {
    if (this.extraction) {
      switch (this.extraction.grindSetting) {
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
      }
    }
    return 'undefined';
  }

  onSubmitRecipe(extractionId: number) {
    if (
      this.rated() &&
      this.extraction?.brewMethod &&
      this.extraction.brewTime &&
      this.extraction.dose &&
      this.extraction.waterTemperature &&
      this.extraction.grindSetting &&
      this.extraction.waterAmount
    ) {
      let observable: Observable<string>;
      let recipeDto: RecipeDto = {
        id: 0,
        shared: false,
        extractionId: extractionId,
      };

      observable = this.recipeService.create(recipeDto);

      observable.subscribe({
        next: data => {
          this.snackBar.open('Recipe was successfully shared', 'Close', {
            duration: 5000,
          });
          this.router.navigate(['/coffee/' + this.extraction?.beanId]);
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
            this.router.navigate(['/coffee/' + this.extraction?.beanId]);
          }
        },
      });
    } else {
      this.snackBar.open(
        'An extraction needs to be complete and rated before it can be shared',
        'Close',
        { duration: 5000 }
      );
    }
  }

  deleteDialog(id: number): void {
    const dialogRef = this.dialog.open(DeleteDialogExtractionComponent, {
      width: '300px',
      hasBackdrop: true,
      data: {
        dataKey: id,
      },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result == 'closed') {
        this.deleteItemEvent.emit(this.extraction?.id);
      }
    });
  }
}
