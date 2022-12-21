import { BrewMethod } from './brew-method.enum';
import { CoffeeGrindSetting } from './coffee-grind-setting.enum';

export interface ExtractionDetailDto {
  id: number;
  dateTime: Date;
  brewMethod: BrewMethod;
  grindSetting: CoffeeGrindSetting;
  waterTemperature: number;
  dose: number;
  waterAmount: number;
  brewTime: number;
  body?: number;
  acidity?: number;
  aromatics?: number;
  sweetness?: number;
  aftertaste?: number;
  ratingNotes?: string;
  overallRating?: number;
  beanId: number;
}
