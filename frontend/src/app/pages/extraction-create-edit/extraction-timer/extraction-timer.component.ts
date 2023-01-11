import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-extraction-timer',
  templateUrl: './extraction-timer.component.html',
  styleUrls: ['./extraction-timer.component.css'],
})
export class ExtractionTimerComponent {
  @Output() newBrewTimeEvent = new EventEmitter<number>();
  ms: any = '0' + 0;
  s: any = '0' + 0;
  m: any = '0' + 0;
  startTimer: any;
  running: boolean = false;

  startStop(): void {
    if (!this.running) {
      this.running = true;
      this.startTimer = setInterval(() => {
        this.ms++;
        this.ms = this.ms < 10 ? 0 + this.ms : this.ms;

        if (this.ms === 100) {
          this.s++;
          this.s = this.s < 10 ? '0' + this.s : this.s;
          this.ms = '0' + 0;
        }

        if (this.s === 60) {
          this.m++;
          this.m = this.s < 10 ? '0' + this.m : this.m;
          this.s = '0' + 0;
        }
      }, 10);
    } else {
      this.stop();
      this.newBrewTimeEvent.emit(
        parseInt(this.ms) +
          parseInt(this.s) * 1000 +
          parseInt(this.m) * 60 * 1000
      );
    }
  }

  stop(): void {
    clearInterval(this.startTimer);
    this.running = false;
  }

  reset(): void {
    clearInterval(this.startTimer);
    this.running = false;
    this.m = this.s = this.ms = '0' + 0;
    this.newBrewTimeEvent.emit(0);
  }
}
