import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoffeeBeanCreateEditComponent } from './coffee-bean-create-edit.component';

describe('CoffeeBeanCreateEditComponent', () => {
  let component: CoffeeBeanCreateEditComponent;
  let fixture: ComponentFixture<CoffeeBeanCreateEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CoffeeBeanCreateEditComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CoffeeBeanCreateEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
