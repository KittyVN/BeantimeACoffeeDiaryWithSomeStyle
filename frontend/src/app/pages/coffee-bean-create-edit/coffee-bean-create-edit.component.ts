import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CoffeeBeanService } from 'src/services/coffee-bean.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CoffeeBeanDto } from 'src/dtos';
import { Observable } from 'rxjs';
import { Roast } from 'src/dtos/req/roast-type.enum';
import { MatSnackBar } from '@angular/material/snack-bar';

export enum CoffeeBeanCreateEditMode {
  create,
  edit,
}

@Component({
  selector: 'app-coffee-bean-create-edit',
  templateUrl: './coffee-bean-create-edit.component.html',
  styleUrls: ['./coffee-bean-create-edit.component.css'],
})
export class CoffeeBeanCreateEditComponent implements OnInit {
  constructor(
    private coffeeBeanService: CoffeeBeanService,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) {}

  id: string | null = null;
  mode: CoffeeBeanCreateEditMode = CoffeeBeanCreateEditMode.create;
  coffeeBeanDto: CoffeeBeanDto = {
    name: '',
    custom: true,
    coffeeRoast: Roast.LIGHT,
  };

  createEditBeanForm = new FormGroup({
    name: new FormControl('', [Validators.required]),
    price: new FormControl('', [Validators.min(0)]),
    origin: new FormControl('', Validators.maxLength(255)),
    height: new FormControl('', [Validators.min(0)]),
    coffeeRoast: new FormControl('LIGHT', [Validators.required]),
    description: new FormControl('', Validators.maxLength(255)),
  });

  public get heading(): string {
    switch (this.mode) {
      case CoffeeBeanCreateEditMode.create:
        return 'Create New Coffee Bean';
      case CoffeeBeanCreateEditMode.edit:
        return 'Edit Coffee Bean';
      default:
        return '?';
    }
  }

  public get submitButtonText(): string {
    switch (this.mode) {
      case CoffeeBeanCreateEditMode.create:
        return 'Create';
      case CoffeeBeanCreateEditMode.edit:
        return 'Edit';
      default:
        return '?';
    }
  }

  private get modeActionFinished(): string {
    switch (this.mode) {
      case CoffeeBeanCreateEditMode.create:
        return 'created';
      case CoffeeBeanCreateEditMode.edit:
        return 'edited';
      default:
        return '?';
    }
  }

  get modeIsCreate(): boolean {
    return this.mode === CoffeeBeanCreateEditMode.create;
  }

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      this.mode = data['mode'];
    });
    if (this.mode === CoffeeBeanCreateEditMode.edit) {
      this.route.paramMap.subscribe(paramMap => {
        this.id = paramMap.get('id');
      });
      if (this.id != null) {
        this.coffeeBeanService.getById(this.id).subscribe({
          next: (data: CoffeeBeanDto) => {
            this.coffeeBeanDto = data;
          },
          error: err => {
            this.snackBar.open(err.error, 'Close', {
              duration: 5000,
            });
            this.router.navigate(['**']);
          },
        });
      } else {
        this.snackBar.open(
          'The coffee bean you tried to edit does not exist',
          'Close',
          {
            duration: 5000,
          }
        );
      }
    }
  }

  onSubmit() {
    if (this.createEditBeanForm.valid) {
      let observable: Observable<string>;
      switch (this.mode) {
        case CoffeeBeanCreateEditMode.create:
          observable = this.coffeeBeanService.create(this.coffeeBeanDto);
          break;
        case CoffeeBeanCreateEditMode.edit:
          observable = this.coffeeBeanService.edit(this.coffeeBeanDto);
          break;
        default:
          console.error('Unknown CoffeeBeanCreateEditMode', this.mode);
          return;
      }
      observable.subscribe({
        next: data => {
          this.snackBar.open(
            'Coffee bean was successfully ' + this.modeActionFinished,
            'Close',
            {
              duration: 5000,
            }
          );
        },
        error: err => {
          this.snackBar.open(err.error.match('\\[.*?\\]'), 'Close', {
            duration: 5000,
          });
        },
      });
    }
  }

  getErrorMessage() {
    if (this.createEditBeanForm.hasError('required')) {
      return 'You must enter a value';
    }

    return '';
  }
}
