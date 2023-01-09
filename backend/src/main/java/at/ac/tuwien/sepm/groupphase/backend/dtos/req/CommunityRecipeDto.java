package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class CommunityRecipeDto {
    private Long recipeId;
    private String recipeDescription;
    private Long extractionID;

    private LocalDateTime extractionDate;
    private String extractionRatingNotes;

    public CommunityRecipeDto() {
    }

    public CommunityRecipeDto(Long recipeId, String recipeDescription, Long extractionID, LocalDateTime extractionDate, String extractionRatingNotes) {
        this.recipeId = recipeId;
        this.recipeDescription = recipeDescription;
        this.extractionID = extractionID;
        this.extractionDate = extractionDate;
        this.extractionRatingNotes = extractionRatingNotes;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public Long getExtractionID() {
        return extractionID;
    }

    public void setExtractionID(Long extractionID) {
        this.extractionID = extractionID;
    }

    public String getExtractionRatingNotes() {
        return extractionRatingNotes;
    }

    public void setExtractionRatingNotes(String extractionRatingNotes) {
        this.extractionRatingNotes = extractionRatingNotes;
    }

    public LocalDateTime getExtractionDate() {
        return extractionDate;
    }

    public void setExtractionDate(LocalDateTime extractionDate) {
        this.extractionDate = extractionDate;
    }
}
