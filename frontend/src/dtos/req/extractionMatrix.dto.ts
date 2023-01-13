import { ExtractionDayStatsDto } from './extractionDayStats.dto';

export interface ExtractionMatrixDto {
  monthLabels: object;
  dailyStats: ExtractionDayStatsDto[];
}
