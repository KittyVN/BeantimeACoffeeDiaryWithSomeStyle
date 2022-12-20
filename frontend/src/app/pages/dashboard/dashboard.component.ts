import { Component, OnInit } from '@angular/core';
import { CoffeeBeanService } from 'src/services/coffee-bean.service';
import { CoffeeBeanDashboardDto, CoffeeRoast } from 'src/dtos';
import { Router, RouterModule } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  coffees: CoffeeBeanDashboardDto[] = [];

  constructor(
    private router: Router,
    private route: RouterModule,
    private jwtHelper: JwtHelperService,
    private snackBar: MatSnackBar,
    private coffeeBeanService: CoffeeBeanService
  ) {}

  ngOnInit(): void {
    this.coffeeBeanService.getall().subscribe({
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
