import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RedditAuthService } from 'src/services/reddit-auth.service';

@Component({
  selector: 'app-reddit-auth-callback',
  templateUrl: './reddit-auth-callback.component.html',
  styleUrls: ['./reddit-auth-callback.component.css'],
})
export class RedditAuthCallbackComponent {
  constructor(
    private route: ActivatedRoute,
    private redditAuthService: RedditAuthService
  ) {}
  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      if (params.get('state') !== localStorage.getItem('redditAuthString')) {
        console.log('Wrong state string');
      } else {
        let code = params.get('code');
        if (code) this.redditAuthService.getAccessToken(code);
      }
    });
  }
}
