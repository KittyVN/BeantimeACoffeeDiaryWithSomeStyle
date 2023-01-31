package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RecipeRatingCreateDto {
    @NotNull
    private long recipeId;

    @NotNull
    private long authorId;

    @NotNull
    @Min(value = 1, message = "Rating must be in the range between 1 and 5")
    @Max(value = 5, message = "Rating must be in the range between 1 and 5")
    private int rating;

    private String text;

    public RecipeRatingCreateDto() {
    }

    public RecipeRatingCreateDto(long recipeId, long authorId, int rating, String text) {
        this.recipeId = recipeId;
        this.authorId = authorId;
        this.rating = rating;
        this.text = text;
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
}
