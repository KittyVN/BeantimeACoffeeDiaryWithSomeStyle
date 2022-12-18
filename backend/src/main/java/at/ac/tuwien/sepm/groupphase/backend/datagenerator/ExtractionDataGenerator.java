package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Extraction;
import at.ac.tuwien.sepm.groupphase.backend.repository.ExtractionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Profile("generateData")
@Component
public class ExtractionDataGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ExtractionRepository extractionRepository;

    public ExtractionDataGenerator(ExtractionRepository extractionRepository) {
        this.extractionRepository = extractionRepository;
    }

    public void generateExtractions() {
        if (this.extractionRepository.findAll().size() > 0) {
            LOGGER.debug("Extractions already generated");
        } else {
            LOGGER.debug("Generating extractions");
        }
    }
}
