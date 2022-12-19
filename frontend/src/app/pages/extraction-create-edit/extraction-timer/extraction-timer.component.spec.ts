import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExtractionTimerComponent } from './extraction-timer.component';

describe('ExtractionTimerComponent', () => {
  let component: ExtractionTimerComponent;
  let fixture: ComponentFixture<ExtractionTimerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ExtractionTimerComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ExtractionTimerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
