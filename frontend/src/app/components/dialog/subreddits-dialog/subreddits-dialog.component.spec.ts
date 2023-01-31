import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubredditsDialogComponent } from './subreddits-dialog.component';

describe('SubredditsDialogComponent', () => {
  let component: SubredditsDialogComponent;
  let fixture: ComponentFixture<SubredditsDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubredditsDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubredditsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
