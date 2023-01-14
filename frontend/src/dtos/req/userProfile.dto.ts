import { ExtractionMatrixDto } from './extractionMatrix.dto';
import { ExtractionListDto } from './extractionList.dto';

export interface UserProfileDto {
  email: string;
  extractionMatrix?: ExtractionMatrixDto;
  topRatedExtractions?: ExtractionListDto[];
}
