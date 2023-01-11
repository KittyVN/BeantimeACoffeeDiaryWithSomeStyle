import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Injectable({
  providedIn: 'root',
})
export class RedditAuthService {
  constructor(private sanitizer: DomSanitizer, private http: HttpClient) {}
  authUrlOne =
    'https://www.reddit.com/api/v1/authorize?client_id=OQifFFFryiwul98sZqfusg&response_type=code&state=';
  authUrlTwo =
    '&redirect_uri=http://localhost:4200/socials/reddit/auth&duration=temporary&scope=submit,mysubreddits,save,flair';
  // declare all characters
  characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';

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
    localStorage.setItem('redditAuthString', randString);
    console.log(this.authUrlOne + randString + this.authUrlTwo);
    window.location.href = this.authUrlOne + randString + this.authUrlTwo;
  }

  getAccessToken(code: string) {
    this.http.post('https://www.reddit.com/api/v1/access_token', code);
  }
}
