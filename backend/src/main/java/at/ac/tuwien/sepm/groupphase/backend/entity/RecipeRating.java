package at.ac.tuwien.sepm.groupphase.backend.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "recipe_rating")
public class RecipeRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User author;

    @Column(name = "timestamp")
    @CreationTimestamp
    private LocalDateTime timestamp;

    @Column(name = "rating")
    private int rating;

    @Column(name = "text", length = 5000)
    private String text;

    public RecipeRating() {
    }

    public RecipeRating(Recipe recipe, User author, int rating) {
        this.recipe = recipe;
        this.author = author;
        this.rating = rating;
    }

    public RecipeRating(Recipe recipe, User author, int rating, String text) {
        this.recipe = recipe;
        this.author = author;
        this.rating = rating;
        this.text = text;
    }

    public RecipeRating(Long id, Recipe recipe, User author, int rating, String text) {
        this.id = id;
        this.recipe = recipe;
        this.author = author;
        this.rating = rating;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
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
