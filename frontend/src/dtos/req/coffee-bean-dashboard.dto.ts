import { CoffeeRoast } from './coffee-roast';
import { ExtractionDetailDto } from './extraction-detail.dto';

export interface CoffeeBeanDashboardDto {
  id: number;
  name: string;
  coffeeRoast: CoffeeRoast;
  description: string;
  beanBlend: string;
  coffeeStrength: string;
  coffeeBeanUrl: string;
  bestExtraction: ExtractionDetailDto;
  lastExtraction: ExtractionDetailDto;
  overallAverageRating: number;
}
