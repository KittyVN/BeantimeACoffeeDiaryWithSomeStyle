import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Observable, of } from 'rxjs';
import { RedditService } from 'src/services/reddit.service';

@Component({
  selector: 'app-subreddits-dialog',
  templateUrl: './subreddits-dialog.component.html',
  styleUrls: ['./subreddits-dialog.component.css'],
})
export class SubredditsDialogComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<SubredditsDialogComponent>,
    private redditService: RedditService
  ) {}

  subreddits: Observable<string[]> = of(['test', 'test2']);

  selectedSubreddit: string = '';

  ngOnInit(): void {
    this.subreddits = this.redditService.getSubscribedSubreddits();
    console.log('....');
    console.log(this.subreddits);
  }

  subredditIsSelected(): boolean {
    console.log(this.selectedSubreddit);
    if (this.selectedSubreddit) {
      return true;
    } else {
      return false;
    }
  }

  onNoClick(): void {
    this.dialogRef.close('open');
  }

  postToReddit() {
    console.log(this.data.dataKey);
    this.redditService.postToReddit(this.data.dataKey, this.selectedSubreddit);
  }
}
