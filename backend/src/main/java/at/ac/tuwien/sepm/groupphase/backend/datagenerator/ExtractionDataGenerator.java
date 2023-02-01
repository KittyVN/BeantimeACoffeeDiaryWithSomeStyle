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
import java.time.LocalDateTime;

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
                "Test Anleitung",
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
                "Test Anleitung",
                this.coffeeBeanRepository.findById(2L).get()
            );

            Extraction e3 = new Extraction(
                LocalDateTime.of(2022, 12, 13, 14, 50),
                ExtractionBrewMethod.INSTANT,
                CoffeeGrindSetting.MEDIUM,
                60D,
                100D,
                200D,
                54000L,
                5, 4, 3, 2, 1, "Descending!",
                "Test Anleitung",
                this.coffeeBeanRepository.findById(2L).get()
            );

            Extraction e4 = new Extraction(
                LocalDateTime.of(2022, 12, 14, 14, 50),
                ExtractionBrewMethod.V60,
                CoffeeGrindSetting.MEDIUM,
                60D,
                100D,
                200D,
                18000L,
                1, 3, 5, 3, 1, "Triangular!",
                "Test Anleitung",
                this.coffeeBeanRepository.findById(2L).get()
            );

            Extraction e5 = new Extraction(
                LocalDateTime.of(2022, 12, 15, 14, 50),
                ExtractionBrewMethod.ESPRESSO_MACHINE,
                CoffeeGrindSetting.MEDIUM,
                60D,
                100D,
                200D,
                30000L,
                4, 4, 5, 5, 5, "Wild",
                "Test Anleitung",
                this.coffeeBeanRepository.findById(2L).get()
            );

            Extraction e6 = new Extraction(
                LocalDateTime.of(2022, 12, 16, 14, 50),
                ExtractionBrewMethod.POD_MACHINE,
                CoffeeGrindSetting.MEDIUM,
                60D,
                100D,
                200D,
                21000L,
                1, 2, 2, 1, 4, "Bad",
                "Test Anleitung",
                this.coffeeBeanRepository.findById(2L).get()
            );

            Extraction e7 = new Extraction(
                LocalDateTime.of(2022, 12, 16, 14, 50),
                ExtractionBrewMethod.PRESSURE,
                CoffeeGrindSetting.MEDIUM,
                40D,
                15D,
                100D,
                16000L,
                1, 2, 2, 1, 1, "Not my best one",
                "Test Anleitung",
                this.coffeeBeanRepository.findById(4L).get()
            );

            Extraction e8 = new Extraction(
                LocalDateTime.of(2022, 12, 16, 14, 50),
                ExtractionBrewMethod.MOKA,
                CoffeeGrindSetting.MEDIUM,
                80D,
                16D,
                150D,
                5100L,
                5, 5, 5, 5, 5, "Excellent",
                "Test Anleitung",
                this.coffeeBeanRepository.findById(4L).get()
            );

            Extraction e9 = new Extraction(
                LocalDateTime.of(2022, 12, 16, 14, 50),
                ExtractionBrewMethod.POUR_OVER,
                CoffeeGrindSetting.MEDIUM,
                60D,
                13D,
                200D,
                11600L,
                5, 2, 3, 1, 5, "Decent",
                "Test Anleitung",
                this.coffeeBeanRepository.findById(4L).get()
            );

            Extraction e10 = new Extraction(
                LocalDateTime.of(2022, 12, 16, 14, 50),
                ExtractionBrewMethod.V60,
                CoffeeGrindSetting.EXTRA_FINE,
                60D,
                13D,
                200D,
                20000L,
                5, 3, 4, 5, 5, "Proud of that one",
                "Test Anleitung",
                this.coffeeBeanRepository.findById(5L).get()
            );
            Extraction e11 = new Extraction(
                LocalDateTime.of(2022, 12, 16, 14, 50),
                ExtractionBrewMethod.ESPRESSO_MACHINE,
                CoffeeGrindSetting.COARSE,
                60D,
                13D,
                200D,
                30000L,
                3, 2, 3, 3, 3, "Okay",
                "Test Anleitung",
                this.coffeeBeanRepository.findById(5L).get()
            );
            Extraction e12 = new Extraction(
                LocalDateTime.of(2022, 12, 16, 14, 50),
                ExtractionBrewMethod.INSTANT,
                CoffeeGrindSetting.MEDIUM,
                60D,
                13D,
                200D,
                22220L,
                2, 3, 2, 1, 1, "Instant coffee only gets that good....",
                "Test Anleitung",
                this.coffeeBeanRepository.findById(5L).get()
            );

            Extraction e13 = new Extraction(
                LocalDateTime.of(2022, 12, 16, 14, 50),
                ExtractionBrewMethod.V60,
                CoffeeGrindSetting.EXTRA_FINE,
                60D,
                13D,
                200D,
                17777L,
                5, 3, 4, 5, 5, "Proud of that one",
                "Test Anleitung",
                this.coffeeBeanRepository.findById(6L).get()
            );
            Extraction e14 = new Extraction(
                LocalDateTime.of(2022, 12, 16, 14, 50),
                ExtractionBrewMethod.ESPRESSO_MACHINE,
                CoffeeGrindSetting.COARSE,
                60D,
                13D,
                200D,
                28000L,
                3, 2, 3, 3, 3, "Okay",
                "Test Anleitung",
                this.coffeeBeanRepository.findById(6L).get()
            );
            Extraction e15 = new Extraction(
                LocalDateTime.of(2022, 12, 16, 14, 50),
                ExtractionBrewMethod.INSTANT,
                CoffeeGrindSetting.MEDIUM,
                60D,
                13D,
                200D,
                33000L,
                2, 3, 2, 1, 1, "Instant coffee only gets that good....",
                "1. Grab your favorite coffee cup \n"
                    + "2. Put in 13 grams of the powder and 200 ml of hot water. \n"
                    + "3. Gently stir until the powder is dissolved and enjoy... or don't. It's really just instant coffee.",
                this.coffeeBeanRepository.findById(6L).get()
            );

            this.extractionRepository.save(e1);
            this.extractionRepository.save(e2);
            this.extractionRepository.save(e3);
            this.extractionRepository.save(e4);
            this.extractionRepository.save(e5);
            this.extractionRepository.save(e6);
            this.extractionRepository.save(e7);
            this.extractionRepository.save(e8);
            this.extractionRepository.save(e9);
            this.extractionRepository.save(e10);
            this.extractionRepository.save(e11);
            this.extractionRepository.save(e12);
            this.extractionRepository.save(e13);
            this.extractionRepository.save(e14);
            this.extractionRepository.save(e15);

        }
    }
}
