package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query(value = "SELECT RE.* FROM COFFEE_RECIPE RE JOIN COFFEE_EXTRACTION EX on RE.ID = EX.RECIPE_ID WHERE EX.ID = :id", nativeQuery = true)
    Recipe findRecipeByExtractionId(@Param("id") Long id);

    @Query(
        value = "SELECT RE.* FROM COFFEE_RECIPE RE JOIN COFFEE_EXTRACTION EX on RE.ID = EX.RECIPE_ID",
        nativeQuery = true)
    List<Recipe> findAllRecipes();
}
