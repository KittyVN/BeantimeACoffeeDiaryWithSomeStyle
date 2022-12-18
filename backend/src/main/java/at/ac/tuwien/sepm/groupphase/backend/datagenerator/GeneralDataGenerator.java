package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.service.impl.ExtractionServiceImpl;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Profile("generateData")
@Component
public class GeneralDataGenerator {
    private final CoffeeBeanDataGenerator coffeeBeanDataGenerator;
    private final UserDataGenerator userDataGenerator;

    private final ExtractionDataGenerator extractionDataGenerator;

    public GeneralDataGenerator(CoffeeBeanDataGenerator coffeeBeanDataGenerator,
                                UserDataGenerator userDataGenerator,
                                ExtractionDataGenerator extractionDataGenerator) {
        this.coffeeBeanDataGenerator = coffeeBeanDataGenerator;
        this.userDataGenerator = userDataGenerator;
        this.extractionDataGenerator = extractionDataGenerator;
    }

    @PostConstruct
    private void generateData() {
        userDataGenerator.generateUser();
        coffeeBeanDataGenerator.generateCoffeeBeans();
        extractionDataGenerator.generateExtractions();
    }
}
