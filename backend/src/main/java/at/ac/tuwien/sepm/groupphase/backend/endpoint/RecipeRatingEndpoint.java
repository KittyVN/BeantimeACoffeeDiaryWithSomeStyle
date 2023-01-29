package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeRatingCreateDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeRatingListDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeRatingUpdateDto;
import at.ac.tuwien.sepm.groupphase.backend.service.RecipeRatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = RecipeRatingEndpoint.BASE_PATH)
public class RecipeRatingEndpoint {
    static final String BASE_PATH = "/api/v1/recipes";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final RecipeRatingService service;

    public RecipeRatingEndpoint(RecipeRatingService service) {
        this.service = service;
    }

    @PostMapping("{recipe_id}/ratings")
    public RecipeRatingListDto create(@PathVariable("recipe_id") long recipeId,
                                      @RequestBody RecipeRatingCreateDto recipeRatingCreateDto) {
        return null;
    }

    @GetMapping("{recipe_id}/ratings")
    public Stream<RecipeRatingListDto> getByRatingId(@PathVariable("recipe_id") long id) {
        return null;
    }

    @PutMapping("{recipe_id}/ratings/{id}")
    public RecipeRatingListDto update(@PathVariable("recipe_id") long recipeId,
                                      @PathVariable("id") long id,
                                      @RequestBody RecipeRatingUpdateDto recipeRatingUpdateDto) {
        return null;
    }

    @DeleteMapping("{recipe_id}/ratings/{id}")
    public void delete(@PathVariable("recipe_id") long recipeId,
                       @PathVariable("id") long id) {
    }

    // TODO: Preauthenticate
}
