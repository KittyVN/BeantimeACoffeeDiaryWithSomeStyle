package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import javax.validation.constraints.NotNull;

public class RecipeRatingCreateDto {
    @NotNull
    private long recipeId;

    @NotNull
    private long userId;

    @NotNull
    private int rating;

    private String text;

    public RecipeRatingCreateDto() {
    }

    public RecipeRatingCreateDto(long recipeId, long userId, int rating, String text) {
        this.recipeId = recipeId;
        this.userId = userId;
        this.rating = rating;
        this.text = text;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
