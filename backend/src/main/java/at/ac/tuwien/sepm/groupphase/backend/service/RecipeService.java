package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeDto;

import java.util.stream.Stream;

public interface RecipeService {

    /**
     * Creates a new recipe in storage.
     *
     * @param recipeDto the recipe to create
     * @return the created recipe
     */
    RecipeDto create(RecipeDto recipeDto);

    /**
     * Fetches all saved recipes from the persistent data storage.
     *
     * @return a stream of the found recipes
     */
    Stream<RecipeDto> getAll();
}
