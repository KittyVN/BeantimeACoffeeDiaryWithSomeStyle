package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeBeanDashboardDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.CoffeeBeanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
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
import java.util.stream.Stream;


@RestController
@RequestMapping(value = CoffeeBeanEndpoint.BASE_PATH)
public class CoffeeBeanEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CoffeeBeanService coffeeBeanService;

    static final String BASE_PATH = "/api/v1/coffee-beans";

    public CoffeeBeanEndpoint(CoffeeBeanService coffeeBeanService) {
        this.coffeeBeanService = coffeeBeanService;
    }

    @GetMapping
    public Stream<CoffeBeanDashboardDto> getAll() throws ResponseStatusException {
        LOGGER.info("GET " + BASE_PATH);
        return coffeeBeanService.getAll();
    }

    @PostMapping
    public CoffeeBeanDto create(@Valid @RequestBody CoffeeBeanDto coffeeBeanDto) throws ResponseStatusException {
        LOGGER.info("POST " + BASE_PATH + " with RequestBody: {}", coffeeBeanDto);
        return coffeeBeanService.create(coffeeBeanDto);
    }

    @PutMapping("{id}")
    public CoffeeBeanDto update(@Valid @RequestBody CoffeeBeanDto coffeeBeanDto) throws ResponseStatusException {
        LOGGER.info("PUT " + BASE_PATH + " with RequestBody: {}", coffeeBeanDto);
        try {
            return coffeeBeanService.update(coffeeBeanDto);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") long id) {
        LOGGER.info("DELETE " + BASE_PATH + " with id: {}", id);
        try {
            coffeeBeanService.delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("{id}")
    public CoffeeBeanDto getById(@PathVariable("id") long id) {
        LOGGER.info("GET " + BASE_PATH + " with id: {}", id);
        try {
            return coffeeBeanService.getById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


}
