import { MatDialog } from '@angular/material/dialog';
import { Component, OnInit, ViewChild } from '@angular/core';
import { debounce, interval, scan, Subject } from 'rxjs';
import { CoffeeBeanService } from 'src/services/coffee-bean.service';
import { ChartData, ChartConfiguration } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { CoffeeBeanDashboardDto, CoffeeRoast } from 'src/dtos';
import { Router, RouterModule } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { coffeeBeanSearchDto } from 'src/dtos/req/coffee-bean-search.dto';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { DeleteDialogCoffeeComponent } from 'src/app/components/dialog/delete-dialog-coffee/delete-dialog-coffee.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  sortingDirective: string = '-id';
  searchParams: coffeeBeanSearchDto = {};
  coffees: CoffeeBeanDashboardDto[] = [];

  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;

  constructor(
    private router: Router,
    private route: RouterModule,
    private snackBar: MatSnackBar,
    private dialog: MatDialog,
    public keyUp: Subject<KeyboardEvent | Event>,
    private coffeeBeanService: CoffeeBeanService
  ) {}

  public doughnutChartType: ChartConfiguration<'doughnut'>['type'] = 'doughnut';
  public doughnutChartOptions: ChartConfiguration<'doughnut'>['options'] = {
    responsive: true,
    cutout: '60%',
    animation: { animateRotate: false },
  };

  searchCoffeeForm = new FormGroup({
    name: new FormControl('', Validators.maxLength(150)),
    description: new FormControl('', Validators.maxLength(150)),
    roast: new FormControl(''),
    beanBlend: new FormControl('', Validators.maxLength(150)),
  });

  public doughnutChartData(
    coffee: CoffeeBeanDashboardDto
  ): ChartData<'doughnut'> {
    return {
      datasets: [
        {
          data: [coffee.overallAverageRating, 25 - coffee.overallAverageRating],
          backgroundColor: ['#4caf50', '#f68f83'],
        },
      ],
    };
  }

  ngOnInit(): void {
    this.coffeeBeanService.search(this.searchParams).subscribe({
      next: data => {
        this.coffees = data;
        this.sortCoffeesByDirective();
        console.log(this.coffees);
      },
      error: error => {
        console.error('Error fetching coffee data', error);
        this.snackBar.open(
          'Unable to fetch coffe data, try again later',
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
        this.coffeeBeanService.search(this.searchParams).subscribe({
          next: data => {
            this.coffees = data;
            this.sortCoffeesByDirective();
          },
          error: error => {
            console.error('Error fetching coffee data', error);
            this.snackBar.open(
              'Unable to fetch coffe data, try again later',
              'Close',
              {
                duration: 5000,
              }
            );
          },
        });
      });
  }

  deleteDialog(id: number): void {
    const dialogRef = this.dialog.open(DeleteDialogCoffeeComponent, {
      width: '300px',
      hasBackdrop: true,
      data: {
        dataKey: id,
      },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result == 'closed') {
        this.coffees = this.coffees.filter(obj => {
          return obj.id !== id;
        });
      }
    });
  }

  formatRoast(coffee: CoffeeBeanDashboardDto): String {
    switch (coffee.coffeeRoast) {
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

  sortCoffeesByDirective() {
    switch (this.sortingDirective) {
      case 'id': {
        this.coffees.sort((a, b) => {
          if (a.id > b.id) {
            return 1;
          }
          if (a.id < b.id) {
            return -1;
          }
          return 0;
        });
        break;
      }
      case '-id': {
        this.coffees.sort((a, b) => {
          if (a.id < b.id) {
            return 1;
          }
          if (a.id > b.id) {
            return -1;
          }
          return 0;
        });
        break;
      }
      case 'name': {
        this.coffees.sort((a, b) => {
          if (a.name.toLowerCase() > b.name.toLowerCase()) {
            return 1;
          }
          if (a.name.toLowerCase() < b.name.toLowerCase()) {
            return -1;
          }
          return 0;
        });
        break;
      }
      case '-name': {
        this.coffees.sort((a, b) => {
          if (a.name.toLowerCase() < b.name.toLowerCase()) {
            return 1;
          }
          if (a.name.toLowerCase() > b.name.toLowerCase()) {
            return -1;
          }
          return 0;
        });
        break;
      }
      case 'score': {
        this.coffees.sort((a, b) => {
          if (a.overallAverageRating > b.overallAverageRating) {
            return 1;
          }
          if (a.overallAverageRating < b.overallAverageRating) {
            return -1;
          }
          return 0;
        });
        break;
      }
      case '-score': {
        this.coffees.sort((a, b) => {
          if (a.overallAverageRating < b.overallAverageRating) {
            return 1;
          }
          if (a.overallAverageRating > b.overallAverageRating) {
            return -1;
          }
          return 0;
        });
        break;
      }
    }
  }
}
