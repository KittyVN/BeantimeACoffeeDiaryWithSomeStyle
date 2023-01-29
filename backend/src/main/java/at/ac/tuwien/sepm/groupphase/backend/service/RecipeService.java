package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeListDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CommunityRecipeDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeSearchCommunityDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.FileAlreadyExistsException;
import java.util.stream.Stream;

public interface RecipeService {

    /**
     * Creates a new recipe in storage.
     *
     * @param recipeListDto the recipe to create
     * @return the created recipe
     * @throws FileAlreadyExistsException if a recipe already exists for the extraction
     */
    RecipeListDto create(RecipeListDto recipeListDto) throws FileAlreadyExistsException;

    /**
     * Updates a recipe in storage.
     *
     * @param recipeListDto the recipe to update
     * @return the updated recipe
     * @throws NotFoundException if an extraction to the recipe doesn't exist for the recipe
     */
    RecipeListDto update(RecipeListDto recipeListDto) throws NotFoundException;

    /**
     * Fetches all saved recipes from the persistent data storage.
     *
     * @return a stream of the found recipes
     */
    Stream<RecipeListDto> getAll();

    /**
     * Fetches all saved recipes joined with extractions that have been shared to the community page
     * and are matching the given search parameters from the persistent data storage.
     *
     * @param searchParams the parameters to search recipes by
     * @return a stream of the found recipes
     */
    Stream<RecipeDetailDto> searchCommunityRecipes(RecipeSearchCommunityDto searchParams);

    /**
     * Fetches all saved recipes from the persistent data storage joined with extraction.
     *
     * @return a stream of the found recipes
     */
    Stream<RecipeDetailDto> getAllWithExtractions();

    /**
     * Fetches a saved recipe with the specific extraction id from the persistent data storage.
     *
     * @param id of the extraction
     * @return the found recipe
     * @throws NotFoundException if extraction by id doesnt exist
     */
    RecipeListDto getByExtractionId(Long id) throws NotFoundException;

    /**
     * Fetches a saved recipe with the specific id from the persistent data storage.
     *
     * @param id of the recipe
     * @return the found recipe
     * @throws NotFoundException if id doesnt exist
     */
    RecipeDetailDto getById(Long id) throws NotFoundException;

    /**
     * Deletes an already persisted recipe by its id.
     *
     * @param id of the recipe to be deleted
     */
    void delete(Long id);

    /**
     * Fetches all saved recipes with specific user id from the persistent data storage.
     *
     * @param id of the user
     * @return a stream of the found recipes
     * @throws NotFoundException if id doesnt exist
     */
    Stream<RecipeDetailDto> getAllByUserId(Long id) throws NotFoundException;
}
