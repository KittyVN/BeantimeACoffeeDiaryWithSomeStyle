import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MySubredditsComponent } from './my-subreddits.component';

describe('MySubredditsComponent', () => {
  let component: MySubredditsComponent;
  let fixture: ComponentFixture<MySubredditsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MySubredditsComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(MySubredditsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
