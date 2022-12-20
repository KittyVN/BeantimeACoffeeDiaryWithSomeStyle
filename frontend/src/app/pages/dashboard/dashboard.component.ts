import { Component, OnInit } from '@angular/core';
import { debounce, interval, scan, Subject } from 'rxjs';
import { CoffeeBeanService } from 'src/services/coffee-bean.service';
import { CoffeeBeanDashboardDto, CoffeeRoast } from 'src/dtos';
import { Router, RouterModule } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { coffeeBeanSearchDto } from 'src/dtos/req/coffee-bean-search.dto';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  searchParams: coffeeBeanSearchDto = {};
  coffees: CoffeeBeanDashboardDto[] = [];

  constructor(
    private router: Router,
    private route: RouterModule,
    private snackBar: MatSnackBar,
    public keyUp: Subject<KeyboardEvent | Event>,
    private coffeeBeanService: CoffeeBeanService
  ) {}

  ngOnInit(): void {
    this.coffeeBeanService.search(this.searchParams).subscribe({
      next: data => {
        this.coffees = data;
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
          },
          error: error => {
            console.error('Error fetching coffee data', error);
          },
        });
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
      default: {
        return 'Unknown Roast';
      }
    }
  }
}
