package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Profile("generateData")
@Component
public class GeneralDataGenerator {
    private final CoffeeBeanDataGenerator coffeeBeanDataGenerator;
    private final UserDataGenerator userDataGenerator;

    private final ExtractionDataGenerator extractionDataGenerator;
    private final RecipeDataGenerator recipeDataGenerator;

    public GeneralDataGenerator(CoffeeBeanDataGenerator coffeeBeanDataGenerator,
                                UserDataGenerator userDataGenerator,
                                ExtractionDataGenerator extractionDataGenerator, RecipeDataGenerator recipeDataGenerator) {
        this.coffeeBeanDataGenerator = coffeeBeanDataGenerator;
        this.userDataGenerator = userDataGenerator;
        this.extractionDataGenerator = extractionDataGenerator;
        this.recipeDataGenerator = recipeDataGenerator;
    }

    @PostConstruct
    private void generateData() {
        userDataGenerator.generateUser();
        coffeeBeanDataGenerator.generateCoffeeBeans();
        extractionDataGenerator.generateExtractions();
        recipeDataGenerator.generateRecipes();
    }
}
