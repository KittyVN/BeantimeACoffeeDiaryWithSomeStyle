package at.ac.tuwien.sepm.groupphase.backend.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Recipe;
import org.springframework.stereotype.Component;

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
}
