package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Profile("generateData")
@Component
public class GeneralDataGenerator {
    private final CoffeeBeanDataGenerator coffeeBeanDataGenerator;
    private final UserDataGenerator userDataGenerator;

    public GeneralDataGenerator(CoffeeBeanDataGenerator coffeeBeanDataGenerator, UserDataGenerator userDataGenerator) {
        this.coffeeBeanDataGenerator = coffeeBeanDataGenerator;
        this.userDataGenerator = userDataGenerator;
    }

    @PostConstruct
    private void generateData() {
        userDataGenerator.generateUser();
        coffeeBeanDataGenerator.generateCoffeeBeans();
    }
}
