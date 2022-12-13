import { Component, OnInit } from '@angular/core';
import { map } from 'rxjs/operators';
import { Breakpoints, BreakpointObserver } from '@angular/cdk/layout';
import { CoffeeBeanService } from 'src/services/coffee-bean.service';
import { CoffeeBeanDashboardDto } from 'src/dtos';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  coffees: CoffeeBeanDashboardDto[] = [];

  constructor(
    private breakpointObserver: BreakpointObserver,
    private coffeeBeanService: CoffeeBeanService
  ) {}

  ngOnInit(): void {
    this.coffeeBeanService.getall().subscribe({
      next: data => {
        this.coffees = data;
      },
      error: error => {
        console.error('Error fetching coffee data', error);
      },
    });
  }
}
