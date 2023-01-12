import { BrewMethod } from './brew-method.enum';
import { CoffeeGrindSetting } from './coffee-grind-setting.enum';

export interface ExtractionSearchDto {
  created?: Date;
  reverseCreated?: boolean;
  overallRating?: number;
  reverseOverallRating?: boolean;
  grindSetting?: CoffeeGrindSetting;
  brewMethod?: BrewMethod;
}
