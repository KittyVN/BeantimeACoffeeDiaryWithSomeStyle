import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ExtractionService } from 'src/services/extraction.service';
import { ChartData, ChartOptions, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { ExtractionSearchDto } from 'src/dtos/req/extraction-search-dto';
import { debounce, interval, Observable, scan, Subject } from 'rxjs';
import { Workbook } from 'exceljs';
import { saveAs } from 'file-saver';
import { RecipeService } from 'src/services/recipe.service';

import { CoffeeBeanDto, ExtractionDetailDto } from '../../../dtos';
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
    private recipeService: RecipeService,
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

  excelExport() {
    let workbook = new Workbook();
    let datamap = workbook.addWorksheet('Extractions');

    datamap.columns = [
      { header: 'Created at', key: 'created', width: 24 },
      { header: 'Grind setting', key: 'grind', width: 14 },
      { header: 'Brew method', key: 'brew', width: 20 },
      { header: 'Water temperature', key: 'temp', width: 20 },
      { header: 'Water amount', key: 'water', width: 16 },
      { header: 'Dose', key: 'dose', width: 8 },
      { header: 'Brew time', key: 'time', width: 12 },
      { header: ' ', key: 'gap', width: 4 },
      { header: 'Overall Rating', key: 'rating', width: 16 },
      { header: 'Body', key: 'body', width: 6 },
      { header: 'Acidity', key: 'acidity', width: 8 },
      { header: 'Aromatics', key: 'aromatics', width: 12 },
      { header: 'Sweetness', key: 'sweetness', width: 12 },
      { header: 'Aftertaste', key: 'aftertaste', width: 10 },
      { header: ' ', key: 'gap2', width: 4 },
      { header: 'Rating notes', key: 'notes', width: 60 },
    ];

    datamap.getRow(1).font = { bold: true };
    datamap.getRow(1).alignment = { horizontal: 'center' };

    if (this.extractions.length > 0) {
      this.extractions.forEach(ex => {
        datamap.addRow({
          created: ex.dateTime,
          grind: ex.grindSetting,
          brew: ex.brewMethod,
          temp: ex.waterTemperature,
          water: ex.waterAmount,
          dose: ex.dose,
          time: this.formatBrewTime(ex),
          gap: '',
          rating: ex.overallRating,
          body: ex.body,
          acidity: ex.acidity,
          aromatics: ex.aromatics,
          sweetness: ex.sweetness,
          aftertaste: ex.aftertaste,
          gap2: '',
          notes: ex.ratingNotes,
        });
      });

      workbook.xlsx.writeBuffer().then(extractions => {
        let blob = new Blob([extractions], {
          type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
        });
        saveAs(blob, 'Extractions_' + this.coffee.name + '.xlsx');
      });
    }
  }

  formatBrewTime(ex: ExtractionDetailDto): string {
    if (ex) {
      let brewtime = ex.brewTime;
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
    return '???';
  }
}
