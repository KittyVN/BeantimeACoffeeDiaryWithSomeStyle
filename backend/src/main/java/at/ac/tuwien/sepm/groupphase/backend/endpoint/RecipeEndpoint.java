package at.ac.tuwien.sepm.groupphase.backend.endpoint;


import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;
import java.nio.file.FileAlreadyExistsException;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = RecipeEndpoint.BASE_PATH)
public class RecipeEndpoint {
    static final String BASE_PATH = "/api/v1/recipes";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final RecipeService service;

    public RecipeEndpoint(RecipeService service) {
        this.service = service;
    }

    @PostMapping
    public RecipeDto create(@RequestBody RecipeDto recipeDto) {
        LOGGER.info("POST " + BASE_PATH + " with RequestBody: {}", recipeDto.toString());
        try {
            return service.create(recipeDto);
        } catch (FileAlreadyExistsException e) {
            LOGGER.warn(e.toString());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @GetMapping
    public Stream<RecipeDto> getAll() throws ResponseStatusException {
        LOGGER.info("GET " + BASE_PATH);
        return service.getAll();
    }
}
