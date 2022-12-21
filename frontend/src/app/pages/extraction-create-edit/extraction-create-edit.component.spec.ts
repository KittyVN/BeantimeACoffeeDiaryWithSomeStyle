import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExtractionCreateEditComponent } from './extraction-create-edit.component';

describe('ExtractionCreateEditComponent', () => {
  let component: ExtractionCreateEditComponent;
  let fixture: ComponentFixture<ExtractionCreateEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ExtractionCreateEditComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ExtractionCreateEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
