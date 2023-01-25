import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CoffeeBeanService } from 'src/services/coffee-bean.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CoffeeBeanDto } from 'src/dtos';
import { Observable } from 'rxjs';
import { Roast } from 'src/dtos/req/roast-type.enum';
import { MatSnackBar } from '@angular/material/snack-bar';
import { JwtHelperService } from '@auth0/angular-jwt';

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
    private snackBar: MatSnackBar,
    private jwtHelper: JwtHelperService
  ) {}

  id: string | null = null;
  mode: CoffeeBeanCreateEditMode = CoffeeBeanCreateEditMode.create;
  coffeeBeanDto: CoffeeBeanDto = {
    name: '',
    coffeeRoast: Roast.LIGHT,
  };

  createEditBeanForm = new FormGroup({
    name: new FormControl('', [
      Validators.required,
      Validators.maxLength(150),
      Validators.minLength(5),
    ]),
    price: new FormControl('', [Validators.min(0)]),
    origin: new FormControl('', Validators.maxLength(255)),
    height: new FormControl('', [Validators.min(0), Validators.max(10000)]),
    coffeeRoast: new FormControl('LIGHT', [Validators.required]),
    description: new FormControl('', Validators.maxLength(5000)),
    beanBlend: new FormControl('', Validators.maxLength(255)),
    url: new FormControl('', Validators.maxLength(255)),
    coffeeStrength: new FormControl('', Validators.maxLength(255)),
  });

  public get heading(): string {
    switch (this.mode) {
      case CoffeeBeanCreateEditMode.create:
        return 'Create New Coffee';
      case CoffeeBeanCreateEditMode.edit:
        return 'Edit Coffee';
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

  getErrorMessage() {
    return this.coffeeBeanDto.name.length < 5
      ? 'Name has to be at least 5 characters long!'
      : 'Name is too long!';
  }

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      this.mode = data['mode'];
    });
    const token = localStorage.getItem('token');
    if (token != null) {
      const payload = this.jwtHelper.decodeToken(token);
      this.coffeeBeanDto.userId = payload.jti;
    }
    if (this.mode === CoffeeBeanCreateEditMode.edit) {
      this.route.paramMap.subscribe(paramMap => {
        this.id = paramMap.get('id');
      });
      if (this.id != null) {
        this.coffeeBeanService.getById(this.id).subscribe({
          next: data => {
            this.coffeeBeanDto = data.coffeeBean;
          },
          error: err => {
            this.snackBar.open(err.error, 'Close', {
              duration: 5000,
            });
            this.router.navigate(['/home']);
          },
        });
      } else {
        this.snackBar.open(
          'The coffee you tried to edit does not exist',
          'Close',
          {
            duration: 5000,
          }
        );
      }
    }
  }

  onSubmit() {
    if (this.coffeeBeanDto.price) {
      let roundedInput = Math.round(this.coffeeBeanDto.price * 100) / 100;
      this.coffeeBeanDto.price = roundedInput;
    }
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
            'Coffee was successfully ' + this.modeActionFinished,
            'Close',
            {
              duration: 5000,
            }
          );
          this.router.navigate(['/home']);
        },
        error: err => {
          this.snackBar.open(err.error.match('\\[.*?\\]'), 'Close', {
            duration: 5000,
          });
        },
      });
    }
  }
}
