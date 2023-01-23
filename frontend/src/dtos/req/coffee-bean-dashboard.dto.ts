import { CoffeeRoast } from './coffee-roast';
import { ExtractionDetailDto } from './extraction-detail.dto';

export interface CoffeeBeanDashboardDto {
  id: number;
  name: string;
  coffeeRoast: CoffeeRoast;
  description: string;
  beanBlend: string;
  bestExtraction: ExtractionDetailDto;
  lastExtraction: ExtractionDetailDto;
  overallAverageRating: number;
}
