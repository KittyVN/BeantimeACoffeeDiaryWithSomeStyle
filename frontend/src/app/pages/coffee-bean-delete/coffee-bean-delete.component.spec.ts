import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoffeeBeanDeleteComponent } from './coffee-bean-delete.component';

describe('CoffeeBeanDeleteComponent', () => {
  let component: CoffeeBeanDeleteComponent;
  let fixture: ComponentFixture<CoffeeBeanDeleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CoffeeBeanDeleteComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CoffeeBeanDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
