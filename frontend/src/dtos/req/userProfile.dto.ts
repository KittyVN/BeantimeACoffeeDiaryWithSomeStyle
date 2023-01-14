import { ExtractionMatrixDto } from './extractionMatrix.dto';
import { ExtractionListDto } from './extractionList.dto';
import { CoffeeBeanExtractionsListDto } from './CoffeeBeanExtractionsList.dto';

export interface UserProfileDto {
  email: string;
  extractionMatrix?: ExtractionMatrixDto;
  topRatedExtractions?: ExtractionListDto[];
  topMostExtractedCoffees?: CoffeeBeanExtractionsListDto[];
}
