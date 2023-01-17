import { TestBed } from '@angular/core/testing';

import { RedditAuthService } from './reddit-auth.service';

describe('RedditAuthService', () => {
  let service: RedditAuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RedditAuthService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
