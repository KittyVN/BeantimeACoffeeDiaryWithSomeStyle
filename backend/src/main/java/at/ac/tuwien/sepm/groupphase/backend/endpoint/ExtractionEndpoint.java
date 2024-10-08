package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionCreateDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.AuthorizationException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ConflictException;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
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
@RequestMapping(path = ExtractionEndpoint.BASE_PATH)
public class ExtractionEndpoint {
    static final String BASE_PATH = "/api/v1/extractions";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ExtractionService service;

    public ExtractionEndpoint(ExtractionService service) {
        this.service = service;
    }

    @PreAuthorize("(hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')) ")
    @GetMapping("bean/{id}")
    public Stream<ExtractionDetailDto> getAllByBeanId(@PathVariable Long id) throws ResponseStatusException {
        LOGGER.info("GET " + BASE_PATH + "/bean" + id);
        try {
            return service.getAllByBeanId(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        }
    }

    @PreAuthorize("(hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')) ")
    @GetMapping("bean/search/{id}")
    public Stream<ExtractionDetailDto> search(@PathVariable Long id, ExtractionSearchDto searchParams) throws ResponseStatusException {
        LOGGER.info("GET " + BASE_PATH + "/" + id);
        LOGGER.info("Request parameters: {}", searchParams);
        try {
            return service.searchByBeanId(searchParams, id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        }
    }

    @PreAuthorize("(hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')) ")
    @GetMapping("{id}")
    public ExtractionDetailDto getById(@PathVariable Long id) throws ResponseStatusException {
        LOGGER.info("GET " + BASE_PATH + "/" + id);
        try {
            return service.getById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        }
    }

    @PreAuthorize("(hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')) ")
    @PutMapping
    public ExtractionCreateDto update(@Valid @RequestBody ExtractionCreateDto extractionCreateDto) throws ResponseStatusException {
        LOGGER.info("PUT " + BASE_PATH);
        try {
            return service.update(extractionCreateDto);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (ConflictException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        }
    }


    @PreAuthorize("(hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')) ")
    @PostMapping
    public ExtractionCreateDto create(@RequestBody ExtractionCreateDto extractionCreateDto) throws ResponseStatusException {
        LOGGER.info("POST " + BASE_PATH + " with RequestBody: {}", extractionCreateDto.toString());
        try {
            return service.create(extractionCreateDto);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        }
    }

    @PreAuthorize("(hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')) ")
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") long id) throws ResponseStatusException {
        LOGGER.info("DELETE " + BASE_PATH + " with id: {}", id);
        try {
            service.delete(id);
        } catch (EmptyResultDataAccessException | NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        }
    }
}
