import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

import { RedditAuthService } from './auth/reddit-auth.service';

@Injectable({
  providedIn: 'root',
})
export class RedditService {
  constructor(
    private http: HttpClient,
    private snackBar: MatSnackBar,
    private redditAuthService: RedditAuthService
  ) {}

  postToReddit() {
    let access_token = this.redditAuthService.getStoredAccessToken();
    if (access_token) {
      let httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/x-www-form-urlencoded',
          Authorization: 'Bearer ' + access_token,
        }),
      };
      let data = new HttpParams()
        .set('text', 'testing my webapp')
        .set('title', 'test for bean')
        .set('kind', 'self')
        .set('sr', 'privateApiTestingBean');
      this.http
        .post('https://oauth.reddit.com/api/submit', data, httpOptions)
        .subscribe({
          next: data => {},
          error: err => {
            console.log(err);
          },
        });
    } else {
      console.log('Not logged in with Reddit');
      this.snackBar.open(
        'You need to be connected to a Reddit Account in order to post!',
        'Close',
        {
          duration: 5000,
        }
      );
    }
  }
}
