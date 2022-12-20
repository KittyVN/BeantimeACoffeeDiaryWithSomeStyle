package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanAvgExtractionRating;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionCreateDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.ExtractionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.PermitAll;
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

    @PermitAll
    @GetMapping("avg/{id}")
    public CoffeeBeanAvgExtractionRating getAvgExtractionRatingByCoffeeBeanId(@PathVariable Long id) {
        LOGGER.info(String.format("GET %s/avg/%d", BASE_PATH, id));
        return this.service.getAvgExtractionEvaluationParamsByCoffeeBeanId(id);
    }

    @GetMapping("user/{id}")
    public Stream<ExtractionDetailDto> getAllByUserId(@PathVariable Long id) throws ResponseStatusException {
        LOGGER.info("GET " + BASE_PATH + "/" + id);
        try {
            return service.getAllByUserId(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
        }
    }

    @PostMapping
    public ExtractionCreateDto create(@RequestBody ExtractionCreateDto extractionCreateDto){
        LOGGER.info("POST " + BASE_PATH + " with RequestBody: {}", extractionCreateDto.toString());
        return service.create(extractionCreateDto);
    }

}
