package at.ac.tuwien.sepm.groupphase.backend.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CommunityRecipeDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Recipe;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.sql.Timestamp;

@Component
public class RecipeMapper {

    /**
     * Converts a Recipe Entity object to a {@link at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeDto}.
     *
     * @param recipe the object to convert
     * @return the converted Recipe {@link RecipeDto}
     */
    public RecipeDto entityToDto(Recipe recipe) {
        Long id;
        if (recipe.getExtraction() == null) {
            id = null;
        } else {
            id = recipe.getExtraction().getId();
        }
        return new RecipeDto(
            recipe.getId(),
            recipe.isShared(),
            id
        );
    }

    /**
     * Converts an Object Entity object to a {@link at.ac.tuwien.sepm.groupphase.backend.dtos.req.CommunityRecipeDto}.
     *
     * @param obj the object to convert
     * @return the converted Recipe {@link CommunityRecipeDto}
     */
    public CommunityRecipeDto objectToDto(Object obj) {
        Object[] objects = (Object[]) obj;
        BigInteger recipeId = (BigInteger) objects[0];
        Boolean recipeShared = (Boolean) objects[1];
        BigInteger extractionId = (BigInteger) objects[2];
        Timestamp extractionDate = (Timestamp) objects[3];
        String extractionBrewMethod = (String) objects[4];
        String extractionGrindSetting = (String) objects[5];
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
        return new CommunityRecipeDto(recipeId.longValue(), recipeShared, extractionId.longValue(), extractionDate.toLocalDateTime(), extractionBrewMethod, extractionGrindSetting,
                extractionWaterTemperature, extractionDose, extractionWaterAmount, extractionBrewTime.longValue(), extractionBody, extractionAcidity,
                extractionAromatics, extractionSweetness, extractionAftertaste, extractionRatingNotes, extractionRecipeSteps, coffeeBeanId.longValue(), coffeeBeanName,
                coffeeBeanPrice, coffeeBeanBlend, coffeeBeanOrigin, coffeeBeanHeight, coffeeBeanRoast, coffeeBeanUrl, coffeeBeanDescription, coffeeBeanStrength);
    }
}
