package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import javax.validation.constraints.NotNull;

public class RecipeDto {
    private Long id;

    private boolean shared;

    @NotNull
    private Long extractionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExtractionId() {
        return extractionId;
    }

    public void setExtractionId(Long extractionId) {
        this.extractionId = extractionId;
    }

    public RecipeDto(Long id, boolean shared, Long extractionId) {
        this.id = id;
        this.shared = shared;
        this.extractionId = extractionId;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public RecipeDto() {
    }

    @Override
    public String toString() {
        return "RecipeDto{"
            + "id=" + id
            + ", shared=" + shared
            + ", extractionId=" + extractionId
            + '}';
    }
}
