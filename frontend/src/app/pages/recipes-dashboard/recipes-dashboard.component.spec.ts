import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipesDashboardComponent } from './recipes-dashboard.component';

describe('RecipesDashboardComponent', () => {
  let component: RecipesDashboardComponent;
  let fixture: ComponentFixture<RecipesDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RecipesDashboardComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(RecipesDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
