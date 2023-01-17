import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router, RouterModule } from '@angular/router';
import { RecipeService } from 'src/app/services/recipe.service';
import { CommunityRecipeDto } from 'src/dtos/req/community-recipe.dto';
import { RedditService } from 'src/services/reddit.service';

@Component({
  selector: 'app-community-dashboard',
  templateUrl: './community-dashboard.component.html',
  styleUrls: ['./community-dashboard.component.css'],
})
export class CommunityDashboardComponent implements OnInit {
  recipes: CommunityRecipeDto[] = [];

  constructor(
    private router: Router,
    private route: RouterModule,
    private snackBar: MatSnackBar,
    private recipeService: RecipeService,
    private redditService: RedditService
  ) {}

  redditLoggedIn: boolean = false;

  ngOnInit(): void {
    let token = localStorage.getItem('redditToken');
    if (token) {
      let expirationDate = localStorage.getItem('redditTokenExpiration');
      if (expirationDate) {
        let now = new Date();
        if (now > new Date(expirationDate)) {
          localStorage.removeItem('redditToken');
          localStorage.removeItem('redditTokenExpiration');
          this.redditLoggedIn = false;
        } else {
          this.redditLoggedIn = true;
        }
      }
    } else {
      this.redditLoggedIn = false;
    }
    this.recipeService.getAll().subscribe({
      next: data => {
        this.recipes = data;
        console.log(this.recipes);
      },
      error: error => {
        console.error('Error fetching recipes', error);
        this.snackBar.open(
          'Unable to fetch recipe data, try again later',
          'Close',
          {
            duration: 5000,
          }
        );
      },
    });
  }

  shareOnReddit() {
    this.redditService.postToReddit();
  }
}
