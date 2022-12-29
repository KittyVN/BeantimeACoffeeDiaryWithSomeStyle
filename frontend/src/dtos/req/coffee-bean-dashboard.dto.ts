import { CoffeeRoast } from './coffee-roast';

export interface CoffeeBeanDashboardDto {
  id: number;
  name: string;
  coffeeRoast: CoffeeRoast;
  description: string;
  beanBlend: string;
}
