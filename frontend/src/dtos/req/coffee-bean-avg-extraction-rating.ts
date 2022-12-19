export class CoffeeBeanAvgExtractionRating {
  id: number;
  body: number;
  acidity: number;
  aromatics: number;
  sweetness: number;
  aftertaste: number;

  constructor(
    id: number,
    body: number,
    acidity: number,
    aromatics: number,
    sweetness: number,
    aftertaste: number
  ) {
    this.id = id;
    this.body = body;
    this.acidity = acidity;
    this.aromatics = aromatics;
    this.sweetness = sweetness;
    this.aftertaste = aftertaste;
  }

  getChartData(): number[] {
    return [
      this.body,
      this.acidity,
      this.aromatics,
      this.sweetness,
      this.aftertaste,
    ];
  }
}
