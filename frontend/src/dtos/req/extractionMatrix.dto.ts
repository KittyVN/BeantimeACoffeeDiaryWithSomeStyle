import { ExtractionDayStatsDto } from './extractionDayStats.dto';

export interface ExtractionMatrixDto {
  sumExtractions: number;
  dailyStats: ExtractionDayStatsDto[];
}
