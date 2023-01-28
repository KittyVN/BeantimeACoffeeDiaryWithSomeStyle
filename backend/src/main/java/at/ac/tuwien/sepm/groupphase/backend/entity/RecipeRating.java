package at.ac.tuwien.sepm.groupphase.backend.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
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
    private LocalDateTime timestamp;

    @Column(name = "rating")
    private int rating;

    @Column(name = "text", length = 5000)
    private String text;

    public RecipeRating() {
    }

    public RecipeRating(Long id, Recipe recipe, User author) {
        this.id = id;
        this.recipe = recipe;
        this.author = author;
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
}
