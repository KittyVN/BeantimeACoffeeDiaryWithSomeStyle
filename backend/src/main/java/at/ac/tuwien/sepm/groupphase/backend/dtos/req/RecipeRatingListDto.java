package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class RecipeRatingListDto {
    @NotNull
    private long id;

    @NotNull
    private long recipeId;

    @NotNull
    private long authorId;

    @NotNull
    private String authorUsername;

    @NotNull
    private LocalDateTime timestamp;

    @NotNull
    @Min(1)
    @Max(5)
    private int rating;

    private String text;

    public RecipeRatingListDto() {
    }

    public RecipeRatingListDto(long id, long recipeId, long authorId, String authorUsername, LocalDateTime timestamp,
                               int rating, String text) {
        this.id = id;
        this.recipeId = recipeId;
        this.authorId = authorId;
        this.authorUsername = authorUsername;
        this.timestamp = timestamp;
        this.rating = rating;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
