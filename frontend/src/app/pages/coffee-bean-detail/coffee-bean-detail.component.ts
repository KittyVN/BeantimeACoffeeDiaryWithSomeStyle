import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { CoffeeBeanDto } from '../../../dtos';
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

  constructor(
    private service: CoffeeBeanService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(({ id }) => {
      this.service.getById(id).subscribe({
        next: data => {
          this.coffee = data;
          console.log(this.coffee);
        },
        error: error => {
          if (error.status == 404) {
            // TODO: Error handling
          }
        },
      });
    });
  }
}
