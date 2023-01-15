package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

public class UserProfileDto {
    String email;
    ExtractionMatrixDto extractionMatrix;
    ExtractionListDto[] topRatedExtractions;
    CoffeeBeanExtractionsListDto[] topMostExtractedCoffees;
    CoffeeBeanRatingListDto[] topRatedCoffees;

    public UserProfileDto() {
    }

    public UserProfileDto(
        String email,
        ExtractionMatrixDto extractionMatrix,
        ExtractionListDto[] topRatedExtractions,
        CoffeeBeanExtractionsListDto[] topMostExtractedCoffees,
        CoffeeBeanRatingListDto[] topRatedCoffees
    ) {
        this.email = email;
        this.extractionMatrix = extractionMatrix;
        this.topRatedExtractions = topRatedExtractions;
        this.topMostExtractedCoffees = topMostExtractedCoffees;
        this.topRatedCoffees = topRatedCoffees;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ExtractionMatrixDto getExtractionMatrix() {
        return extractionMatrix;
    }

    public void setExtractionMatrix(ExtractionMatrixDto extractionMatrix) {
        this.extractionMatrix = extractionMatrix;
    }

    public ExtractionListDto[] getTopRatedExtractions() {
        return topRatedExtractions;
    }

    public void setTopRatedExtractions(ExtractionListDto[] topRatedExtractions) {
        this.topRatedExtractions = topRatedExtractions;
    }

    public CoffeeBeanExtractionsListDto[] getTopMostExtractedCoffees() {
        return topMostExtractedCoffees;
    }

    public void setTopMostExtractedCoffees(CoffeeBeanExtractionsListDto[] topMostExtractedCoffees) {
        this.topMostExtractedCoffees = topMostExtractedCoffees;
    }

    public CoffeeBeanRatingListDto[] getTopRatedCoffees() {
        return topRatedCoffees;
    }

    public void setTopRatedCoffees(CoffeeBeanRatingListDto[] topRatedCoffees) {
        this.topRatedCoffees = topRatedCoffees;
    }
}
