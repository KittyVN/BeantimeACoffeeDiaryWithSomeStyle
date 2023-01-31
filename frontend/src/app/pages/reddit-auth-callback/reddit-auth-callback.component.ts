import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RedditAuthService } from 'src/services/auth/reddit-auth.service';
import { RedditService } from 'src/services/reddit.service';

@Component({
  selector: 'app-reddit-auth-callback',
  templateUrl: './reddit-auth-callback.component.html',
  styleUrls: ['./reddit-auth-callback.component.css'],
})
export class RedditAuthCallbackComponent implements OnInit {
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private redditAuthService: RedditAuthService,
    private redditService: RedditService
  ) {}

  ngOnInit() {
    localStorage.removeItem('redditToken');
    this.route.queryParamMap.subscribe(params => {
      if (params.get('state') !== localStorage.getItem('redditAuthString')) {
        console.log('Wrong state string');
      } else {
        let code = params.get('code');
        if (code)
          this.redditAuthService.getAccessToken(code).subscribe({
            next: data => {
              console.log(data);
              localStorage.setItem('redditToken', JSON.stringify(data));
              localStorage.removeItem('redditTokenExpiration');
              localStorage.setItem(
                'redditTokenExpiration',
                this.addMinutes(new Date(), 59).toString()
              );
              this.router.navigate(['/home']);
            },
          });
      }
    });
  }

  addMinutes(date: Date, minutes: number) {
    date.setMinutes(date.getMinutes() + minutes);
    return date;
  }
}
