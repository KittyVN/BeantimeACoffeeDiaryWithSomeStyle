package at.ac.tuwien.sepm.groupphase.backend.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeRatingListDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.RecipeRating;
import org.springframework.stereotype.Component;

@Component
public class RecipeRatingMapper {
    public RecipeRatingListDto entityToDto(RecipeRating rating) {
        return new RecipeRatingListDto(rating.getId(), rating.getRecipe().getId(), rating.getAuthor().getId(),
            rating.getRating(), rating.getText());
    }
}
