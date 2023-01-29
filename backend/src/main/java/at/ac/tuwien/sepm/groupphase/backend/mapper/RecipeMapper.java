package at.ac.tuwien.sepm.groupphase.backend.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeListDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Recipe;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.sql.Timestamp;

@Component
public class RecipeMapper {

    /**
     * Converts a Recipe Entity object to a {@link RecipeListDto}.
     *
     * @param recipe the entity to convert
     * @return the converted Recipe {@link RecipeListDto}
     */
    public RecipeListDto entityToListDto(Recipe recipe) {
        Long id;
        if (recipe.getExtraction() == null) {
            id = null;
        } else {
            id = recipe.getExtraction().getId();
        }
        return new RecipeListDto(
            recipe.getId(),
            recipe.isShared(),
            id
        );
    }

    /**
     * Converts an Object Entity object to a {@link RecipeDetailDto}.
     *
     * @param recipe the entity to convert
     * @return the converted Recipe {@link RecipeDetailDto}
     */
    public RecipeDetailDto recipeToDetailDto(Recipe recipe) {
        return new RecipeDetailDto(recipe.getId(), recipe.isShared(), recipe.getExtraction().getId(),
            recipe.getExtraction().getBrewMethod(), recipe.getExtraction().getGrindSetting(),
            recipe.getExtraction().getBody(), recipe.getExtraction().getAcidity(),
            recipe.getExtraction().getAromatics(), recipe.getExtraction().getSweetness(),
            recipe.getExtraction().getAftertaste(), recipe.getExtraction().getRecipeSteps(),
            recipe.getExtraction().getRatingNotes(), recipe.getExtraction().getWaterAmount(),
            recipe.getExtraction().getWaterTemperature(), recipe.getExtraction().getDose(),
            recipe.getExtraction().getBrewTime(),
            recipe.getExtraction().getCoffeeBean().getId(), recipe.getExtraction().getCoffeeBean().getName(),
            recipe.getExtraction().getCoffeeBean().getBeanBlend(),
            recipe.getExtraction().getCoffeeBean().getCoffeeRoast().name(),
            recipe.getExtraction().getCoffeeBean().getUrlToCoffee(),
            recipe.getExtraction().getCoffeeBean().getCoffeeStrength(),
            recipe.getExtraction().getCoffeeBean().getOrigin(),
            recipe.getExtraction().getCoffeeBean().getHeight(),
            recipe.getExtraction().getCoffeeBean().getDescription(),
            recipe.getExtraction().getCoffeeBean().getUser().getId(),
            recipe.getExtraction().getCoffeeBean().getUser().getUsername());
    }
}
