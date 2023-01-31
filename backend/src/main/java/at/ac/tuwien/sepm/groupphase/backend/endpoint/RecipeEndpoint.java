package at.ac.tuwien.sepm.groupphase.backend.endpoint;


import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeListDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.AuthorizationException;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeSearchCommunityDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
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

    @PreAuthorize("(hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')) ")
    @PostMapping
    public RecipeListDto create(@RequestBody RecipeListDto recipeListDto) throws ResponseStatusException {
        LOGGER.info("POST " + BASE_PATH + " with RequestBody: {}", recipeListDto.toString());
        try {
            return service.create(recipeListDto);
        } catch (FileAlreadyExistsException e) {
            LOGGER.warn(e.toString());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        }
    }

    @PreAuthorize("(hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')) ")
    @PutMapping("extraction/{id}")
    public RecipeListDto update(@Valid @PathVariable("id") long id, @RequestBody RecipeListDto recipeListDto) throws ResponseStatusException {
        LOGGER.info("PUT " + BASE_PATH + " with RequestBody: {}", recipeListDto);
        try {
            return service.update(recipeListDto);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        }
    }

    @GetMapping
    public Stream<RecipeDetailDto> getAll() {
        LOGGER.info("GET " + BASE_PATH);
        return service.getAllWithExtractions();
    }

    @GetMapping("search")
    public Stream<RecipeDetailDto> searchCommunityPage(RecipeSearchCommunityDto searchParams) {
        LOGGER.info("Request parameters: {}", searchParams);
        return service.searchCommunityRecipes(searchParams);
    }

    @GetMapping("extraction/{id}")
    public RecipeListDto getByExtractionId(@PathVariable("id") long id) throws ResponseStatusException {
        LOGGER.info("GET " + BASE_PATH + " with extraction id: {}", id);
        try {
            return service.getByExtractionId(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("{id}")
    public RecipeDetailDto getById(@PathVariable("id") long id) throws ResponseStatusException {
        LOGGER.info("GET " + BASE_PATH + " with id: {}", id);
        try {
            return service.getById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") long id) throws ResponseStatusException {
        LOGGER.info("DELETE " + BASE_PATH + " with id: {}", id);
        try {
            service.delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PreAuthorize("(hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')) "
        + "and authentication.principal.equals(#id.toString())")
    @GetMapping("user/{id}")
    public Stream<RecipeDetailDto> getAllByUserId(@PathVariable Long id) throws ResponseStatusException {
        LOGGER.info("GET " + BASE_PATH + " with user id: {}", id);
        try {
            return service.getAllByUserId(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
