import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-star-rating',
  templateUrl: './star-rating.component.html',
  styleUrls: ['./star-rating.component.css'],
})
export class StarRatingComponent {
  @Input() rating: number = 3;
  @Output() newRatingEvent = new EventEmitter<number>();

  onRatingChange(value: number) {
    this.rating = value;
    this.newRatingEvent.emit(value);
  }

  changeRatingEmptyStars(value: number) {
    if (this.rating) this.rating += value;
    this.newRatingEvent.emit(value);
  }

  ratingArray(): Array<number> {
    return [].constructor(5);
  }

  emptyRatingArray(): Array<number> {
    if (this.rating) return [].constructor(5 - this.rating);
    else return [].constructor(0);
  }
}
