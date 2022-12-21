import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExtractionCardComponent } from './extraction-card.component';

describe('ExtractionCardComponent', () => {
  let component: ExtractionCardComponent;
  let fixture: ComponentFixture<ExtractionCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ExtractionCardComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ExtractionCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
