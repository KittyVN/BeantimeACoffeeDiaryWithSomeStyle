import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class RedditAuthService {
  constructor(private http: HttpClient) {}
  client_id = 'OQifFFFryiwul98sZqfusg';
  client_secret = '90riMTELyKi8H28LPZ555ktRu1n_9g';
  redirectURI = 'http://localhost:4200/socials/reddit/auth';
  authUrlOne =
    'https://www.reddit.com/api/v1/authorize?client_id=OQifFFFryiwul98sZqfusg&response_type=code&state=';
  authUrlTwo =
    '&redirect_uri=' +
    this.redirectURI +
    '&duration=temporary&scope=submit,mysubreddits,save,flair';

  characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      Authorization: 'Basic ' + btoa(this.client_id + ':' + this.client_secret),
    }),
  };

  generateString(): string {
    let result = '';
    const charactersLength = this.characters.length;
    for (let i = 0; i < 10; i++) {
      result += this.characters.charAt(
        Math.floor(Math.random() * charactersLength)
      );
    }
    console.log(result);
    return result;
  }

  redirectToAuth() {
    let randString = this.generateString();
    localStorage.removeItem('redditAuthString');
    localStorage.setItem('redditAuthString', randString);
    window.location.href = this.authUrlOne + randString + this.authUrlTwo;
  }

  isAuthenticated() {
    let token = localStorage.getItem('redditToken');
    if (token) {
      let expirationDate = localStorage.getItem('redditTokenExpiration');
      if (expirationDate) {
        let now = new Date();
        if (now > new Date(expirationDate)) {
          localStorage.removeItem('redditToken');
          localStorage.removeItem('redditTokenExpiration');
          return false;
        } else {
          return true;
        }
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  getStoredAccessToken() {
    let token = localStorage.getItem('redditToken');
    if (token) {
      return JSON.parse(token).access_token;
    } else {
      return undefined;
    }
  }

  getAccessToken(code: string) {
    let data = new HttpParams()
      .set('grant_type', 'authorization_code')
      .set('code', code)
      .set('redirect_uri', this.redirectURI);
    return this.http.post(
      'https://www.reddit.com/api/v1/access_token',
      data,
      this.httpOptions
    );
  }
}
