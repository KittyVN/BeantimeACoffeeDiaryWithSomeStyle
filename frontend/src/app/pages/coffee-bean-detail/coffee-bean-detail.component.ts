import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ExtractionService } from 'src/services/extraction.service';

import { CoffeeBeanDto, ExtractionDetailDto } from '../../../dtos';
import { Roast } from '../../../dtos/req/roast-type.enum';
import { CoffeeBeanService } from '../../../services/coffee-bean.service';

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

  extractions: ExtractionDetailDto[] = [];

  constructor(
    private coffeeService: CoffeeBeanService,
    private extractionService: ExtractionService,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(({ id }) => {
      this.coffee.id = id;
      this.coffeeService.getById(id).subscribe({
        next: data => {
          this.coffee = data;
          this.extractionService.getAllByCoffeeId(id).subscribe({
            next: data => {
              this.extractions = data;
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
  }
}
