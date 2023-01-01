import { Roast } from './roast-type.enum';

export interface CoffeeBeanDto {
  id?: number;
  name: string;
  price?: number;
  origin?: string;
  height?: number;
  coffeeRoast: Roast;
  description?: string;
  beanBlend?: string;
  urlToCoffee?: string;
  userId?: number;
}
