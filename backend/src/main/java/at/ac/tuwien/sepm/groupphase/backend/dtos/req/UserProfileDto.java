package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

public class UserProfileDto {
    String email;
    ExtractionMatrixDto extractionMatrix;
    ExtractionListDto[] topRatedExtractions;

    public UserProfileDto() {
    }

    public UserProfileDto(String email, ExtractionMatrixDto extractionMatrix, ExtractionListDto[] topRatedExtractions) {
        this.email = email;
        this.extractionMatrix = extractionMatrix;
        this.topRatedExtractions = topRatedExtractions;
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
}
