package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CommunityRecipeDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;

import java.nio.file.FileAlreadyExistsException;
import java.util.stream.Stream;

public interface RecipeService {

    /**
     * Creates a new recipe in storage.
     *
     * @param recipeDto the recipe to create
     * @return the created recipe
     * @throws FileAlreadyExistsException if a recipe already exists for the extraction
     */
    RecipeDto create(RecipeDto recipeDto) throws FileAlreadyExistsException;

    /**
     * Updates a recipe in storage.
     *
     * @param recipeDto the recipe to update
     * @return the updated recipe
     * @throws NotFoundException if an extraction to the recipe doesn't exist for the recipe
     */
    RecipeDto update(RecipeDto recipeDto) throws NotFoundException;

    /**
     * Fetches all saved recipes from the persistent data storage.
     *
     * @return a stream of the found recipes
     */
    Stream<RecipeDto> getAll();

    /**
     * Fetches all saved recipes from the persistent data storage joined with extraction.
     *
     * @return a stream of the found recipes
     */
    Stream<CommunityRecipeDto> getAllWithExtractions();

    /**
     * Fetches a saved recipe with the specific extraction id from the persistent data storage.
     *
     * @param id of the extraction
     * @return the found recipe
     */
    RecipeDto getByExtractionId(Long id);

    /**
     * Fetches a saved recipe with the specific id from the persistent data storage.
     *
     * @param id of the recipe
     * @return the found recipe
     * @throws NotFoundException if id doesnt exist
     */
    CommunityRecipeDto getById(Long id) throws NotFoundException;
}
