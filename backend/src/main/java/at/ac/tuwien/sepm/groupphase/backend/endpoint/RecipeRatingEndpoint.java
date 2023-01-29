package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeRatingCreateDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeRatingListDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeRatingUpdateDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.ConflictException;
import at.ac.tuwien.sepm.groupphase.backend.service.RecipeRatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = RecipeRatingEndpoint.BASE_PATH)
@Validated
public class RecipeRatingEndpoint {
    static final String BASE_PATH = "/api/v1/recipes";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final RecipeRatingService service;

    public RecipeRatingEndpoint(RecipeRatingService service) {
        this.service = service;
    }

    @PreAuthorize("(hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')) "
        + "and #recipeId.toString().equals(#recipeRatingCreateDto.getRecipeId().toString())")
    @PostMapping("{recipe_id}/ratings")
    public RecipeRatingListDto create(@PathVariable("recipe_id") long recipeId,
                                      @Valid @RequestBody RecipeRatingCreateDto recipeRatingCreateDto) {
        LOGGER.info("POST {}/{}/ratings with RequestBody: {}", BASE_PATH, recipeId, recipeRatingCreateDto);
        try {
            return service.create(recipeRatingCreateDto);
        } catch (ConflictException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @PreAuthorize("(hasRole('ROLE_USER') or hasRole('ROLE_ADMIN'))")
    @GetMapping("{recipe_id}/ratings")
    public Stream<RecipeRatingListDto> getByRatingId(@PathVariable("recipe_id") long id) {
        return service.getByRecipeId(id);
    }

    @PreAuthorize("(hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')) "
        + "and authentication.principal.equals(#recipeRatingUpdateDto.getAuthorId().toString())")
    @PutMapping("{recipe_id}/ratings/{id}")
    public RecipeRatingListDto update(@PathVariable("recipe_id") long recipeId,
                                      @PathVariable("id") long id,
                                      @RequestBody RecipeRatingUpdateDto recipeRatingUpdateDto) {
        return null;
    }

    @PreAuthorize("(hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')) "
        + "and authentication.principal.equals(#recipeRatingUpdateDto.getAuthorId().toString())")
    @DeleteMapping("{recipe_id}/ratings/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("recipe_id") long recipeId,
                       @PathVariable("id") long id) {
    }
}
