package at.ac.tuwien.sepm.groupphase.backend.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeListDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Recipe;
import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeGrindSetting;
import at.ac.tuwien.sepm.groupphase.backend.enums.ExtractionBrewMethod;
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
    public RecipeDetailDto entityToDetailDto(Recipe recipe) {
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

    /**
     * Converts an Object Entity object to a {@link RecipeDetailDto}.
     *
     * @param obj the object to convert
     * @return the converted Recipe {@link RecipeDetailDto}
     */
    public RecipeDetailDto objectToDto(Object obj) {
        Object[] objects = (Object[]) obj;
        BigInteger recipeId = (BigInteger) objects[0];
        Boolean recipeShared = (Boolean) objects[1];
        BigInteger extractionId = (BigInteger) objects[2];
        Timestamp extractionDate = (Timestamp) objects[3];
        ExtractionBrewMethod extractionBrewMethod = ExtractionBrewMethod.valueOf((String) objects[4]);
        CoffeeGrindSetting extractionGrindSetting = CoffeeGrindSetting.valueOf((String) objects[5]);
        Double extractionWaterTemperature = (Double) objects[6];
        Double extractionDose = (Double) objects[7];
        Double extractionWaterAmount = (Double) objects[8];
        BigInteger extractionBrewTime = (BigInteger) objects[9];
        Integer extractionBody = (Integer) objects[10];
        Integer extractionAcidity = (Integer) objects[11];
        Integer extractionAromatics = (Integer) objects[12];
        Integer extractionSweetness = (Integer) objects[13];
        Integer extractionAftertaste = (Integer) objects[14];
        String extractionRatingNotes = (String) objects[15];
        String extractionRecipeSteps = (String) objects[16];
        BigInteger coffeeBeanId = (BigInteger) objects[17];
        String coffeeBeanName = (String) objects[18];
        Float coffeeBeanPrice = (Float) objects[19];
        String coffeeBeanBlend = (String) objects[20];
        String coffeeBeanOrigin = (String) objects[21];
        Integer coffeeBeanHeight = (Integer) objects[22];
        String coffeeBeanRoast = (String) objects[23];
        String coffeeBeanUrl = (String) objects[24];
        String coffeeBeanDescription = (String) objects[25];
        String coffeeBeanStrength = (String) objects[26];
        return new RecipeDetailDto(recipeId.longValue(), recipeShared, extractionId.longValue(), extractionBrewMethod,
            extractionGrindSetting, extractionBody, extractionAcidity, extractionAromatics, extractionSweetness,
            extractionAftertaste, extractionRecipeSteps,  extractionRatingNotes, extractionWaterAmount,
            extractionWaterTemperature, extractionDose, extractionBrewTime.longValue(), coffeeBeanId.longValue(),
            coffeeBeanName, coffeeBeanBlend, coffeeBeanRoast, coffeeBeanUrl, coffeeBeanStrength, coffeeBeanOrigin,
            coffeeBeanHeight, coffeeBeanDescription, 0L, "");
    }

}
