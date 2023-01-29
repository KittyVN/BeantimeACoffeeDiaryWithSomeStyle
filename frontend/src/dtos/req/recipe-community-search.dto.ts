import { BrewMethod } from './brew-method.enum';
import { Roast } from './roast-type.enum';

export interface RecipeCommunitySearchDto {
  name?: String;
  blend?: String;
  brewMethod?: BrewMethod;
  roast?: Roast;
}
