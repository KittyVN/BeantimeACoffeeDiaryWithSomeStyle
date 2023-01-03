package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RecipeDto {
    private Long id;

    @Size(max = 5000, message = "Description cannot be longer than 5000 characters")
    private String description;

    @NotNull
    private Long extractionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getExtractionId() {
        return extractionId;
    }

    public void setExtractionId(Long extractionId) {
        this.extractionId = extractionId;
    }

    public RecipeDto(Long id, String description, Long extractionId) {
        this.id = id;
        this.description = description;
        this.extractionId = extractionId;
    }

    public RecipeDto() {
    }

    @Override
    public String toString() {
        return "RecipeDto{"
            + "id=" + id
            + ", description='" + description + '\''
            + ", extractionId=" + extractionId
            + '}';
    }
}
