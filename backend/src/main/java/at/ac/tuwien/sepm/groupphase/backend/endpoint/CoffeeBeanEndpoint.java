package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDashboardDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.AuthorizationException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ConflictException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.CoffeeBeanService;
import at.ac.tuwien.sepm.groupphase.backend.service.ExtractionService;
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
import java.util.NoSuchElementException;
import java.util.stream.Stream;


@RestController
@RequestMapping(value = CoffeeBeanEndpoint.BASE_PATH)
public class CoffeeBeanEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CoffeeBeanService coffeeBeanService;
    private final ExtractionService extractionService;

    static final String BASE_PATH = "/api/v1/coffee-beans";

    public CoffeeBeanEndpoint(CoffeeBeanService coffeeBeanService, ExtractionService extractionService) {
        this.coffeeBeanService = coffeeBeanService;
        this.extractionService = extractionService;
    }

    @PreAuthorize("(hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')) "
        + "and authentication.principal.equals(#id.toString())")
    @GetMapping("/user/{id}")
    public Stream<CoffeeBeanDashboardDto> search(@PathVariable Long id, CoffeeBeanSearchDto searchParams) throws ResponseStatusException {
        LOGGER.info("GET " + BASE_PATH);
        LOGGER.info("Request parameters: {}", searchParams);
        try {
            return coffeeBeanService.search(searchParams, id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PreAuthorize("(hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')) "
        + "and authentication.principal.equals(#coffeeBeanDto.userId.toString())")
    @PostMapping
    public CoffeeBeanDto create(@Valid @RequestBody CoffeeBeanDto coffeeBeanDto) throws ResponseStatusException {
        LOGGER.info("POST " + BASE_PATH + " with RequestBody: {}", coffeeBeanDto);
        return coffeeBeanService.create(coffeeBeanDto);
    }

    @PreAuthorize("(hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')) "
        + "and authentication.principal.equals(#coffeeBeanDto.userId.toString())")
    @PutMapping("{id}")
    public CoffeeBeanDto update(@Valid @RequestBody CoffeeBeanDto coffeeBeanDto) throws ResponseStatusException {
        LOGGER.info("PUT " + BASE_PATH + " with RequestBody: {}", coffeeBeanDto);
        try {
            return coffeeBeanService.update(coffeeBeanDto);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (ConflictException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @PreAuthorize("(hasRole('ROLE_USER') or hasRole('ROLE_ADMIN'))")
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") long id) throws ResponseStatusException {
        LOGGER.info("DELETE " + BASE_PATH + " with id: {}", id);
        try {
            coffeeBeanService.delete(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        }
    }

    @PreAuthorize("(hasRole('ROLE_USER') or hasRole('ROLE_ADMIN'))")
    @GetMapping("{id}")
    public CoffeeBeanDetailDto getById(@PathVariable("id") long id) throws ResponseStatusException {
        LOGGER.info("GET " + BASE_PATH + " with id: {}", id);
        try {
            return new CoffeeBeanDetailDto(id,
                coffeeBeanService.getById(id),
                extractionService.getAvgExtractionEvaluationParamsByCoffeeBeanId(id));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
