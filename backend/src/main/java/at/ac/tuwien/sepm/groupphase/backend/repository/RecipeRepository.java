package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CommunityRecipeDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query(value = "SELECT RE.* FROM COFFEE_RECIPE RE JOIN COFFEE_EXTRACTION EX on RE.EXTRACTION_ID = EX.ID WHERE RE.EXTRACTION_ID = :id", nativeQuery = true)
    Recipe findRecipeByExtractionId(@Param("id") Long id);

    @Query(
        value = "SELECT RE.* FROM COFFEE_RECIPE RE JOIN COFFEE_EXTRACTION EX on RE.EXTRACTION_ID = EX.ID",
        nativeQuery = true)
    List<Recipe> findAllRecipes();

    @Query(
        value = "SELECT RE.ID AS recipeId, RE.DESCRIPTION AS recipeDescription, EX.ID AS extractionID, EX.EXTRACTION_DATE AS extractionDate, EX.RATING_NOTES AS extractionRatingNotes FROM COFFEE_RECIPE RE JOIN COFFEE_EXTRACTION EX on RE.EXTRACTION_ID = EX.ID",
        nativeQuery = true)
    List<Object> findAllRecipesJoinedWithExtraction();

}
