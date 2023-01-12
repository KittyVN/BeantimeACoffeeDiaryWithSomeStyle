import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ExtractionService } from 'src/services/extraction.service';
import { ChartData, ChartOptions, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { ExtractionSearchDto } from 'src/dtos/req/extraction-search-dto';
import { debounce, interval, scan, Subject } from 'rxjs';

import { CoffeeBeanDto, ExtractionDetailDto } from '../../../dtos';
import { Roast } from '../../../dtos/req/roast-type.enum';
import { CoffeeBeanService } from '../../../services/coffee-bean.service';
import { CoffeeBeanAvgExtractionRating } from '../../../dtos/req/coffee-bean-avg-extraction-rating';
import { CoffeeBeanDetailDto } from '../../../dtos/req/coffee-bean-detail.dto';

@Component({
  selector: 'app-coffee-bean-detail',
  templateUrl: './coffee-bean-detail.component.html',
  styleUrls: ['./coffee-bean-detail.component.css'],
})
export class CoffeeBeanDetailComponent implements OnInit {
  coffee: CoffeeBeanDto = {
    id: 0,
    name: '',
    price: undefined,
    origin: undefined,
    height: undefined,
    coffeeRoast: Roast.LIGHT,
    description: undefined,
    userId: 0,
  };

  searchParams: ExtractionSearchDto = {};
  extractions: ExtractionDetailDto[] = [];

  avgExtractionResults: CoffeeBeanAvgExtractionRating =
    new CoffeeBeanAvgExtractionRating({
      acidity: 0,
      aftertaste: 0,
      aromatics: 0,
      body: 0,
      id: 0,
      sweetness: 0,
    });

  radarChartLabels: string[] = [
    'Body',
    'Acidity',
    'Aromatics',
    'Sweetness',
    'Aftertaste',
  ];

  radarChartOptions: ChartOptions<'radar'> = {
    scales: {
      r: {
        suggestedMin: 0,
        suggestedMax: 5,
        pointLabels: {
          font: {
            family: 'Roboto, sans-serif',
            size: 14,
          },
        },
      },
    },
    plugins: {
      legend: {
        display: false,
      },
      tooltip: {
        enabled: false,
      },
    },
    events: [],
  };

  radarChartType: ChartType = 'radar';

  radarChartData: ChartData<'radar'> = {
    labels: this.radarChartLabels,
    datasets: [],
  };

  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;

  constructor(
    private coffeeService: CoffeeBeanService,
    private extractionService: ExtractionService,
    private router: Router,
    private route: ActivatedRoute,
    public keyUp: Subject<KeyboardEvent | Event>,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(({ id }) => {
      this.coffee.id = id;
      this.avgExtractionResults.id = id;

      this.coffeeService.getById(id).subscribe({
        next: data => {
          this.coffee = data.coffeeBean;
          this.avgExtractionResults = new CoffeeBeanAvgExtractionRating(
            data.avgExtractionRating
          );

          this.radarChartData.datasets = [
            {
              data: this.avgExtractionResults.getChartData(),
            },
          ];

          this.chart?.update();

          this.extractionService.search(this.searchParams, id).subscribe({
            next: data => {
              this.extractions = data;
            },
            error: error => {
              if (error.status == 404) {
                this.snackBar.open(
                  `Unable to load extractions, please try again later`,
                  'Close'
                );
              }
            },
          });
        },
        error: error => {
          if (error.status == 404) {
            this.router.navigate(['/home']);
            this.snackBar.open(
              `Coffee Bean with ID ${this.coffee.id} not found.`,
              'OK'
            );
          }
        },
      });
    });
    this.keyUp
      .pipe(
        scan(i => ++i, 1),
        debounce(i => interval(60 * i))
      )
      .subscribe(() => {
        let id = this.coffee.id ? this.coffee.id : -1;
        if (id === -1) {
          this.router.navigate(['/home']);
          this.snackBar.open(
            `Unable to get coffee id, returning to dashboard`,
            'Close',
            {
              duration: 5000,
            }
          );
        }
        this.extractionService
          .search(this.searchParams, this.coffee.id ? this.coffee.id : -1)
          .subscribe({
            next: data => {
              this.extractions = data;
            },
            error: error => {
              console.error('Error fetching extractions', error);
              this.snackBar.open(
                'Unable to fetch extraction data, try again later',
                'Close',
                {
                  duration: 5000,
                }
              );
            },
          });
      });
  }
}
