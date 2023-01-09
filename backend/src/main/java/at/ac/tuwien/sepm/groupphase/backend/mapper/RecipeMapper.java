package at.ac.tuwien.sepm.groupphase.backend.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CommunityRecipeDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Recipe;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Iterator;

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
            recipe.getDescription(),
            id
        );
    }

    public CommunityRecipeDto objectToDto(Object obj) {
        Object[] objects = (Object[]) obj;
        BigInteger recipeId = (BigInteger) objects[0];
        String recipeDescription = (String) objects[1];
        BigInteger extractionID = (BigInteger) objects[2];
        Timestamp extractionDate = (Timestamp) objects[3];
        String extractionRatingNotes = (String) objects[4];
        return new CommunityRecipeDto(recipeId.longValue(), recipeDescription, extractionID.longValue(), extractionDate.toLocalDateTime(), extractionRatingNotes);
    }
}
