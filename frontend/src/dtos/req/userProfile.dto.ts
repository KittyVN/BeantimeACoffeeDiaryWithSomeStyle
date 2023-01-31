import { ExtractionMatrixDto } from './extractionMatrix.dto';
import { ExtractionListDto } from './extractionList.dto';
import { CoffeeBeanExtractionsListDto } from './CoffeeBeanExtractionsList.dto';
import { CoffeeBeanRatingsListDto } from './CoffeeBeanRatingsList.dto';

export interface UserProfileDto {
  username: string;
  extractionMatrix?: ExtractionMatrixDto;
  topRatedExtractions?: ExtractionListDto[];
  topMostExtractedCoffees?: CoffeeBeanExtractionsListDto[];
  topRatedCoffees?: CoffeeBeanRatingsListDto[];
}
