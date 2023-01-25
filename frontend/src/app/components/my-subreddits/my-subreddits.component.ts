import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CommunityRecipeDto } from 'src/dtos/req/community-recipe.dto';
import { RedditService } from 'src/services/reddit.service';

@Component({
  selector: 'app-my-subreddits',
  templateUrl: './my-subreddits.component.html',
  styleUrls: ['./my-subreddits.component.css'],
})
export class MySubredditsComponent {
  constructor(private redditService: RedditService) {}

  subredditForm = new FormGroup({
    name: new FormControl('', [Validators.required, Validators.maxLength(255)]),
  });

  recipe: CommunityRecipeDto = {
    recipeId: 0,
  };
  subredditName: string = '';

  onSubmit() {
    this.redditService.postToReddit(this.recipe, this.subredditName);
  }
}
