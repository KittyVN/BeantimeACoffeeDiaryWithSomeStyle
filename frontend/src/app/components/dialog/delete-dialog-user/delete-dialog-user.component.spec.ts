import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteDialogUserComponent } from './delete-dialog-user.component';

describe('DeleteDialogUserComponent', () => {
  let component: DeleteDialogUserComponent;
  let fixture: ComponentFixture<DeleteDialogUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DeleteDialogUserComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DeleteDialogUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
