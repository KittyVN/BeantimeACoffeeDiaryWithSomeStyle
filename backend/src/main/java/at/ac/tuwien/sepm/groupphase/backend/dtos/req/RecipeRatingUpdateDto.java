package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RecipeRatingUpdateDto {
    @NotNull
    private long id;

    @NotNull
    private long authorId;

    @NotNull
    @Min(1)
    @Max(5)
    private int rating;

    private String text;

    public RecipeRatingUpdateDto() {
    }

    public RecipeRatingUpdateDto(long id, long authorId, int rating, String text) {
        this.id = id;
        this.authorId = authorId;
        this.rating = rating;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }
}
