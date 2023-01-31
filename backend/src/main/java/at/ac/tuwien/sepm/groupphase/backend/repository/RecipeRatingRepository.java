package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.RecipeRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface RecipeRatingRepository extends JpaRepository<RecipeRating, Long> {
    Collection<RecipeRating> findRecipeRatingByRecipe_IdOrderByTimestampDesc(long recipeId);

    RecipeRating findRecipeRatingById(long id);
}
