import { Component, OnInit } from '@angular/core';
import { debounce, interval, scan, Subject } from 'rxjs';
import { Breakpoints, BreakpointObserver } from '@angular/cdk/layout';
import { CoffeeBeanService } from 'src/services/coffee-bean.service';
import { CoffeeBeanDashboardDto } from 'src/dtos';
import { Router, RouterModule } from '@angular/router';
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
}
