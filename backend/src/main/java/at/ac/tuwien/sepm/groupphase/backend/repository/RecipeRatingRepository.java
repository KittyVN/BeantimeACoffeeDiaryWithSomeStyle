package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.RecipeRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRatingRepository extends JpaRepository<RecipeRating, Long> {
    List<RecipeRating> findRecipeRatingByRecipe_Id(long recipeId);
}
