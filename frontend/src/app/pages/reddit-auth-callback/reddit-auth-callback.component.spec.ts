import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RedditAuthCallbackComponent } from './reddit-auth-callback.component';

describe('RedditAuthCallbackComponent', () => {
  let component: RedditAuthCallbackComponent;
  let fixture: ComponentFixture<RedditAuthCallbackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RedditAuthCallbackComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(RedditAuthCallbackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
