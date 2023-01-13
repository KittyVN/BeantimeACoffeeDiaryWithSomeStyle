import { ExtractionMatrixDto } from './extractionMatrix.dto';

export interface UserProfileDto {
  email: string;
  extractionMatrix: ExtractionMatrixDto;
}
