package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import javax.validation.constraints.NotNull;

public class RecipeListDto {
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

    public RecipeListDto(Long id, boolean shared, Long extractionId) {
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

    public RecipeListDto() {
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
