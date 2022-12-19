package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.service.ExtractionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping(path = ExtractionEndpoint.BASE_PATH)
public class ExtractionEndpoint {
    static final String BASE_PATH = "/api/v1/extractions";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ExtractionService service;

    public ExtractionEndpoint(ExtractionService service) {
        this.service = service;
    }
}
