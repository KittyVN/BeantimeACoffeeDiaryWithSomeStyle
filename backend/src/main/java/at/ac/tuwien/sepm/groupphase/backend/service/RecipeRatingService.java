package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeRatingCreateDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeRatingListDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;

import java.util.stream.Stream;

public interface RecipeRatingService {
    /**
     * Adds a new rating to the system.
     *
     * @param recipeRatingCreateDto to add
     * @return the RecipeRatingListDto of the new rating
     */
    RecipeRatingListDto create(RecipeRatingCreateDto recipeRatingCreateDto);

    /**
     * Get the ratings by a specific recipe id.
     *
     * @param recipeId of the recipe
     * @return a List of RecipeRatingListDtos
     */
    Stream<RecipeRatingListDto> getByRecipeId(long recipeId);

    /**
     * Delete rating by id.
     *
     * @param id of the rating to delete
     * @throws NotFoundException if id does not exist
     */
    void delete(long id, long userId) throws NotFoundException;
}
