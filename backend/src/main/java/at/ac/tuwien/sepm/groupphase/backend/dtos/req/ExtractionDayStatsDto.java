package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import java.time.LocalDate;

public class ExtractionDayStatsDto {
    private LocalDate date;
    private int numExtractions;
    private int relFrequency;

    public ExtractionDayStatsDto() {
    }

    public ExtractionDayStatsDto(LocalDate date, int numExtractions, int relFrequency) {
        this.date = date;
        this.numExtractions = numExtractions;
        this.relFrequency = relFrequency;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getNumExtractions() {
        return numExtractions;
    }

    public void setNumExtractions(int numExtractions) {
        this.numExtractions = numExtractions;
    }

    public int getRelFrequency() {
        return relFrequency;
    }

    public void setRelFrequency(int relFrequency) {
        this.relFrequency = relFrequency;
    }
}
