import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class RedditService {
  constructor(private http: HttpClient) {}

  postToReddit() {
    let token = localStorage.getItem('redditToken');
    if (token) {
      let access_token = JSON.parse(token).access_token;
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
        .set('sr', 'test');
      console.log('trying to post');
      this.http
        .post('https://oauth.reddit.com/api/submit', data, httpOptions)
        .subscribe({
          next: data => {
            console.log('success');
            console.log(data);
          },
          error: err => {
            console.log('err');
            console.log(err);
          },
        });
    } else {
      console.log('Not logged in with Reddit');
    }
  }
}
