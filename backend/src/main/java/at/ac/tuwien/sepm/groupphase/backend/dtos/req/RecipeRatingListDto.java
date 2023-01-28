package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RecipeRatingListDto {
    @NotNull
    private long id;

    @NotNull
    private long recipeId;

    @NotNull
    private long userId;

    @NotNull
    @Min(1)
    @Max(5)
    private int rating;

    private String text;

    public RecipeRatingListDto() {
    }

    public RecipeRatingListDto(long id, long recipeId, long userId, int rating, String text) {
        this.id = id;
        this.recipeId = recipeId;
        this.userId = userId;
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
