import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteDialogCoffeeComponent } from './delete-dialog-coffee.component';

describe('DeleteDialogCoffeeComponent', () => {
  let component: DeleteDialogCoffeeComponent;
  let fixture: ComponentFixture<DeleteDialogCoffeeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeleteDialogCoffeeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeleteDialogCoffeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
