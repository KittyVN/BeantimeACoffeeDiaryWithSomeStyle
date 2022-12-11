import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CoffeeBeanService } from 'src/services/cofffee-bean.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CoffeeBeanDto } from 'src/dtos';
import { Observable } from 'rxjs';
import { Roast } from 'src/dtos/req/roast-type.enum';

export enum CoffeeBeanCreateEditMode {
  create,
  edit,
}

@Component({
  selector: 'app-coffee-bean-create-edit',
  templateUrl: './coffee-bean-create-edit.component.html',
  styleUrls: ['./coffee-bean-create-edit.component.css'],
})
export class CoffeeBeanCreateEditComponent {
  constructor(
    private coffeeBeanService: CoffeeBeanService,
    private router: Router,
    private route: ActivatedRoute
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
    console.log(this.mode);
    if (this.mode === CoffeeBeanCreateEditMode.edit) {
      this.route.paramMap.subscribe(paramMap => {
        this.id = paramMap.get('id');
      });
      if (this.id != null) {
        this.coffeeBeanService.getById(this.id).subscribe({
          next: (data: CoffeeBeanDto) => {
            this.coffeeBeanDto = data;
          },
          error: (error: { message: any }) => {
            console.error('Error fetching coffee bean: ', error.message);
            this.router.navigate(['**']);
          },
        });
      } else {
        console.error('Error fetching coffeeBean: id cannot be null');
      }
    }
  }

  onSubmit() {
    console.log(this.createEditBeanForm.value);
    console.log(this.createEditBeanForm.valid);
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
          console.log(
            'Coffee bean was successfully ' + this.modeActionFinished
          );
        },
        error: err => {
          console.log('Error');
        },
      });
    }
  }

  delete() {
    if (this.id != null) {
      this.coffeeBeanService.delete(this.id).subscribe({
        next: (data: any) => {
          console.log('deleted coffee bean: ', data);
          this.router.navigate(['**']);
        },
        error: (error: { message: any }) => {
          console.error('Error deleting coffee bean: ', error.message);
        },
      });
    } else {
      console.error('Error deleting coffee bean: id cannot be null');
    }
  }

  getErrorMessage() {
    if (this.createEditBeanForm.hasError('required')) {
      return 'You must enter a value';
    }

    return '';
  }
}
