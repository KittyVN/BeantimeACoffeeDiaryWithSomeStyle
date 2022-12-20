import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from 'src/services/user.service';
import { CoffeeBeanDto } from 'src/dtos';
import { Roast } from 'src/dtos/req/roast-type.enum';
import { mixinTabIndex } from '@angular/material/core';
import { CoffeeBeanService } from 'src/services/coffee-bean.service';

export enum ExtractionCreateEditMode {
  create,
  edit,
}

@Component({
  selector: 'app-extraction-create-edit',
  templateUrl: './extraction-create-edit.component.html',
  styleUrls: ['./extraction-create-edit.component.css'],
})
export class ExtractionCreateEditComponent implements OnInit {
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar,
    private userService: UserService,
    private coffeeBeanService: CoffeeBeanService
  ) {}

  id: string | null = null;
  coffeeId: number | null = null;
  coffee: CoffeeBeanDto = {
    name: '',
    custom: true,
    coffeeRoast: Roast.LIGHT,
  };
  mode: ExtractionCreateEditMode = ExtractionCreateEditMode.create;
  parameterForm = new FormGroup({
    brewMethod: new FormControl('', Validators.required),
  });
  ratingForm = new FormGroup({
    acidity: new FormControl('', [Validators.min(0)]),
    aromatics: new FormControl('', [Validators.min(0)]),
    sweetness: new FormControl('', [Validators.min(0)]),
    aftertaste: new FormControl('', [Validators.min(0)]),
    ratingNotes: new FormControl('', Validators.maxLength(255)),
  });

  get modeIsCreate(): boolean {
    return this.mode === ExtractionCreateEditMode.create;
  }

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      this.mode = data['mode'];
      this.coffeeId = data['coffeeId'];
    });
    if (this.coffeeId != null) {
      this.coffeeBeanService.getById(this.coffeeId.toString()).subscribe({
        next: (data: CoffeeBeanDto) => {
          this.coffee = data;
        },
        error: err => {
          this.snackBar.open(
            'You cannot start a extraction without a coffee',
            'Close',
            {
              duration: 5000,
            }
          );
          this.router.navigate(['/home']);
        },
      });
    } else {
      this.snackBar.open(
        'The coffee you tried extract with does not exist',
        'Close',
        {
          duration: 5000,
        }
      );
    }
    if (this.mode === ExtractionCreateEditMode.edit) {
      this.route.paramMap.subscribe(paramMap => {
        this.id = paramMap.get('id');
      });
    }
  }
}
