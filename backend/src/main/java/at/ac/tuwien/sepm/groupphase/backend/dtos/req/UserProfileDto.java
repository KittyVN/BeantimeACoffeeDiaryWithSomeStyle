package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

public class UserProfileDto {
    String email;
    ExtractionMatrixDto extractionMatrix;

    public UserProfileDto(String email, ExtractionMatrixDto extractionMatrix) {
        this.email = email;
        this.extractionMatrix = extractionMatrix;
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
}
