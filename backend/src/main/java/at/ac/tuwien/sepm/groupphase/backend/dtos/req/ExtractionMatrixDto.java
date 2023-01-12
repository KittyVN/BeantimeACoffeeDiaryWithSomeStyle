package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

public class ExtractionMatrixDto {
    private String[] monthLabels;
    private ExtractionDayStatsDto[] dailyStats;

    public ExtractionMatrixDto() {
    }

    public ExtractionMatrixDto(String[] monthLabels, ExtractionDayStatsDto[] dailyStats) {
        this.monthLabels = monthLabels;
        this.dailyStats = dailyStats;
    }

    public String[] getMonthLabels() {
        return monthLabels;
    }

    public void setMonthLabels(String[] monthLabels) {
        this.monthLabels = monthLabels;
    }

    public ExtractionDayStatsDto[] getDailyStats() {
        return dailyStats;
    }

    public void setDailyStats(ExtractionDayStatsDto[] dailyStats) {
        this.dailyStats = dailyStats;
    }
}
