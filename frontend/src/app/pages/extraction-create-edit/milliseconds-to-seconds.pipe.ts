import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'millisecondsToSeconds',
})
export class MillisecondsToSecondsPipe implements PipeTransform {
  transform(milliseconds: number): number {
    let millisecondsToSeconds = 1000;
    return Math.trunc(milliseconds / millisecondsToSeconds);
  }
}
