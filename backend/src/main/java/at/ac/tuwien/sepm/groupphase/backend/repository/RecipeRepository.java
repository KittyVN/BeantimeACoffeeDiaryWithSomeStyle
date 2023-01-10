package at.ac.tuwien.sepm.groupphase.backend.repository;

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
        value = "SELECT RE.ID AS recipeId, RE.DESCRIPTION AS recipeDescription, EX.ID AS extractionID, EX.EXTRACTION_DATE AS extractionDate,"
            + " EX.BREW_METHOD AS extractionBrewMethod, EX.GRIND_SETTING AS extractionGrindSetting, Ex.WATER_TEMPERATURE AS extractionWaterTemperature,"
            + " EX.DOSE AS extractionDose, EX.WATER_AMOUNT AS extractionWaterAmount, EX.BREW_TIME AS extractionBrewTime, EX.BODY AS extractionBody, EX.ACIDITY AS extractionAcidity,"
            + " EX.AROMATICS AS extractionAromatics, EX.SWEETNESS AS extractionSweetness, EX.AFTERTASTE AS extractionAftertaste,"
            + " EX.RATING_NOTES AS extractionRatingNotes, CB.ID AS coffeeBeanId, CB.NAME AS coffeeBeanName, CB.PRICE AS coffeeBeanPrice, CB.BEAN_BLEND AS coffeeBeanBlend,"
            + " CB.ORIGIN AS coffeeBeanOrigin, CB.HEIGHT AS coffeeBeanHeight, CB.COFFEE_ROAST AS coffeeBeanRoast, CB.URL_TO_COFFEE AS coffeeBeanUrl,"
            + " CB.DESCRIPTION AS coffeeBeanDescription"
            + " FROM COFFEE_RECIPE RE JOIN COFFEE_EXTRACTION EX on RE.EXTRACTION_ID = EX.ID"
            + " JOIN COFFEE_BEAN CB on CB.ID = EX.COFFEE_BEAN_ID",
        nativeQuery = true)
    List<Object> findAllRecipesJoinedWithExtraction();

}
