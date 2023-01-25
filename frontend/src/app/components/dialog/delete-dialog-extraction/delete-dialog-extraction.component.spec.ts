import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteDialogExtractionComponent } from './delete-dialog-extraction.component';

describe('DeleteDialogExtractionComponent', () => {
  let component: DeleteDialogExtractionComponent;
  let fixture: ComponentFixture<DeleteDialogExtractionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DeleteDialogExtractionComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DeleteDialogExtractionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
