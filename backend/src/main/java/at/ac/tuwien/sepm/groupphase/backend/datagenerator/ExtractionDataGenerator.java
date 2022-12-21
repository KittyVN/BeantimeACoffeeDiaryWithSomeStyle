package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Extraction;
import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeGrindSetting;
import at.ac.tuwien.sepm.groupphase.backend.enums.ExtractionBrewMethod;
import at.ac.tuwien.sepm.groupphase.backend.repository.CoffeeBeanRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ExtractionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Profile("generateData")
@Component
public class ExtractionDataGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ExtractionRepository extractionRepository;
    private final CoffeeBeanRepository coffeeBeanRepository;

    public ExtractionDataGenerator(ExtractionRepository extractionRepository, CoffeeBeanRepository coffeeBeanRepository) {
        this.extractionRepository = extractionRepository;
        this.coffeeBeanRepository = coffeeBeanRepository;
    }

    public void generateExtractions() {
        if (this.extractionRepository.findAll().size() > 0) {
            LOGGER.debug("Extractions already generated");
        } else {
            LOGGER.debug("Generating extractions");

            Extraction e1 = new Extraction(
                LocalDateTime.of(2022, 12, 11, 14, 50),
                ExtractionBrewMethod.DRIP,
                CoffeeGrindSetting.MEDIUM,
                60D,
                100D,
                200D,
                15018250L,
                5, 5, 5, 5, 5, "Very good!",
                this.coffeeBeanRepository.findById(2L).get()
            );

            Extraction e2 = new Extraction(
                LocalDateTime.of(2022, 12, 12, 9, 0),
                ExtractionBrewMethod.TURKISH,
                CoffeeGrindSetting.MEDIUM,
                60D,
                100D,
                200D,
                600L,
                1, 2, 3, 4, 5, "Ascending!",
                this.coffeeBeanRepository.findById(2L).get()
            );

            Extraction e3 = new Extraction(
                LocalDateTime.of(2022, 12, 13, 14, 50),
                ExtractionBrewMethod.INSTANT,
                CoffeeGrindSetting.MEDIUM,
                60D,
                100D,
                200D,
                600L,
                5, 4, 3, 2, 1, "Descending!",
                this.coffeeBeanRepository.findById(2L).get()
            );

            Extraction e4 = new Extraction(
                LocalDateTime.of(2022, 12, 14, 14, 50),
                ExtractionBrewMethod.V60,
                CoffeeGrindSetting.MEDIUM,
                60D,
                100D,
                200D,
                600L,
                1, 3, 5, 3, 1, "Triangular!",
                this.coffeeBeanRepository.findById(2L).get()
            );

            Extraction e5 = new Extraction(
                LocalDateTime.of(2022, 12, 15, 14, 50),
                ExtractionBrewMethod.ESPRESSO_MACHINE,
                CoffeeGrindSetting.MEDIUM,
                60D,
                100D,
                200D,
                600L,
                4, 4, 5, 5, 5, "Wild",
                this.coffeeBeanRepository.findById(2L).get()
            );

            Extraction e6 = new Extraction(
                LocalDateTime.of(2022, 12, 16, 14, 50),
                ExtractionBrewMethod.POD_MACHINE,
                CoffeeGrindSetting.MEDIUM,
                60D,
                100D,
                200D,
                600L,
                1, 2, 2, 1, 4, "Bad",
                this.coffeeBeanRepository.findById(2L).get()
            );

            this.extractionRepository.save(e1);
            this.extractionRepository.save(e2);
            this.extractionRepository.save(e3);
            this.extractionRepository.save(e4);
            this.extractionRepository.save(e5);
            this.extractionRepository.save(e6);
        }
    }
}
