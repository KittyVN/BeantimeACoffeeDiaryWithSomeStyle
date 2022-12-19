import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ChartData, ChartOptions, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';

import { CoffeeBeanDto } from '../../../dtos';
import { Roast } from '../../../dtos/req/roast-type.enum';
import { CoffeeBeanService } from '../../../services/coffee-bean.service';
import { CoffeeBeanAvgExtractionRating } from '../../../dtos/req/coffee-bean-avg-extraction-rating';

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
    custom: false,
    userId: 0,
  };

  avgExtractionResults: CoffeeBeanAvgExtractionRating =
    new CoffeeBeanAvgExtractionRating(0, 0, 0, 0, 0, 0);

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
    private service: CoffeeBeanService,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(({ id }) => {
      this.coffee.id = id;
      this.avgExtractionResults.id = id;

      this.service.getById(id).subscribe({
        next: data => {
          this.coffee = data;
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

      this.service.getAvgExtractionResultsById(id).subscribe({
        next: data => {
          this.avgExtractionResults = new CoffeeBeanAvgExtractionRating(
            data.id,
            data.body,
            data.acidity,
            data.aromatics,
            data.sweetness,
            data.aftertaste
          );

          console.log(this.avgExtractionResults);
          this.radarChartData.datasets = [
            {
              data: this.avgExtractionResults.getChartData(),
            },
          ];

          this.chart?.update();
        },
      });
    });
  }
}
