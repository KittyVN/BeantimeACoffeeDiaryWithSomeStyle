import { CoffeeBeanDto } from './coffee-bean.dto';
import { CoffeeBeanAvgExtractionRatingDto } from './coffee-bean-avg-extraction-rating.dto';

export interface CoffeeBeanDetailDto {
  id: number;
  coffeeBean: CoffeeBeanDto;
  avgExtractionRating: CoffeeBeanAvgExtractionRatingDto;
}
