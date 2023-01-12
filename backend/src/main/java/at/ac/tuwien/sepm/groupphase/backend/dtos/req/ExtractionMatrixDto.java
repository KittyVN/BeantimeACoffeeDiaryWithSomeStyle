package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

public class ExtractionMatrixDto {
    private String[] monthLabels;
    private int[] extractions;
    private int[] extractionFrequencies;

    public ExtractionMatrixDto() {
    }

    public ExtractionMatrixDto(String[] monthLabels, int[] extractions, int[] extractionFrequencies) {
        this.monthLabels = monthLabels;
        this.extractions = extractions;
        this.extractionFrequencies = extractionFrequencies;
    }

    public String[] getMonthLabels() {
        return monthLabels;
    }

    public void setMonthLabels(String[] monthLabels) {
        this.monthLabels = monthLabels;
    }

    public int[] getExtractions() {
        return extractions;
    }

    public void setExtractions(int[] extractions) {
        this.extractions = extractions;
    }

    public int[] getExtractionFrequencies() {
        return extractionFrequencies;
    }

    public void setExtractionFrequencies(int[] extractionFrequencies) {
        this.extractionFrequencies = extractionFrequencies;
    }
}
