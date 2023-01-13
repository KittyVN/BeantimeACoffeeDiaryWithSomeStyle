package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import java.util.HashMap;

public class ExtractionMatrixDto {
    private HashMap<Integer, String> monthLabels; // <week, MMM>
    private ExtractionDayStatsDto[] dailyStats;

    public ExtractionMatrixDto() {
    }

    public ExtractionMatrixDto(HashMap<Integer, String> monthLabels, ExtractionDayStatsDto[] dailyStats) {
        this.monthLabels = monthLabels;
        this.dailyStats = dailyStats;
    }

    public HashMap<Integer, String> getMonthLabels() {
        return monthLabels;
    }

    public void setMonthLabels(HashMap<Integer, String> monthLabels) {
        this.monthLabels = monthLabels;
    }

    public ExtractionDayStatsDto[] getDailyStats() {
        return dailyStats;
    }

    public void setDailyStats(ExtractionDayStatsDto[] dailyStats) {
        this.dailyStats = dailyStats;
    }
}
