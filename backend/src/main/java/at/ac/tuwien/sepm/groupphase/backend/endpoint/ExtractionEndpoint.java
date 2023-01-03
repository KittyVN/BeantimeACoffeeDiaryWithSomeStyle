package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionCreateDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.ExtractionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;
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
        LOGGER.info("GET " + BASE_PATH + "/" + id);
        try {
            return service.getAllByBeanId(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bean not found", e);
        }
    }

    @PostMapping
    public ExtractionCreateDto create(@RequestBody ExtractionCreateDto extractionCreateDto) {
        LOGGER.info("POST " + BASE_PATH + " with RequestBody: {}", extractionCreateDto.toString());
        return service.create(extractionCreateDto);
    }

    @GetMapping("{id}")
    public ExtractionDetailDto getById(@PathVariable("id") long id) {
        LOGGER.info("GET " + BASE_PATH + " with id: {}", id);
        try {
            return service.getById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
