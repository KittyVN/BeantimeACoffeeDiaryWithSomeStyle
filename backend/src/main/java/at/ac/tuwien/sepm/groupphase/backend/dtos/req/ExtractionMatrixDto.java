package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

public class ExtractionMatrixDto {
    private int sumExtractions;
    private ExtractionDayStatsDto[] dailyStats;

    public ExtractionMatrixDto() {
    }

    public ExtractionMatrixDto(int sumExtractions, ExtractionDayStatsDto[] dailyStats) {
        this.sumExtractions = sumExtractions;
        this.dailyStats = dailyStats;
    }

    public int getSumExtractions() {
        return sumExtractions;
    }

    public void setSumExtractions(int sumExtractions) {
        this.sumExtractions = sumExtractions;
    }

    public ExtractionDayStatsDto[] getDailyStats() {
        return dailyStats;
    }

    public void setDailyStats(ExtractionDayStatsDto[] dailyStats) {
        this.dailyStats = dailyStats;
    }
}
