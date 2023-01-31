import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Observable, of } from 'rxjs';
import { RedditService } from 'src/services/reddit.service';

@Component({
  selector: 'app-subreddits-dialog',
  templateUrl: './subreddits-dialog.component.html',
  styleUrls: ['./subreddits-dialog.component.css'],
})
export class SubredditsDialogComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<SubredditsDialogComponent>,
    private redditService: RedditService
  ) {}

  subreddits: Observable<string[]> = of(['test', 'test2']);

  selectedSubreddit: string = 'test';

  ngOnInit(): void {
    this.subreddits = this.redditService.getSubscribedSubreddits();
    console.log('....');
    console.log(this.subreddits);
  }

  subredditIsSelected(): boolean {
    return this.selectedSubreddit.length == 0;
  }

  onNoClick(): void {
    this.dialogRef.close('open');
  }

  postToReddit() {}
}
