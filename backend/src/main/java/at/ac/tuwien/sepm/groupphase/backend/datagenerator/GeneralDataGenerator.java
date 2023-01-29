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
    private final RecipeRatingDataGenerator recipeRatingDataGenerator;

    public GeneralDataGenerator(CoffeeBeanDataGenerator coffeeBeanDataGenerator,
                                UserDataGenerator userDataGenerator,
                                ExtractionDataGenerator extractionDataGenerator,
                                RecipeDataGenerator recipeDataGenerator,
                                RecipeRatingDataGenerator recipeRatingDataGenerator) {
        this.coffeeBeanDataGenerator = coffeeBeanDataGenerator;
        this.userDataGenerator = userDataGenerator;
        this.extractionDataGenerator = extractionDataGenerator;
        this.recipeDataGenerator = recipeDataGenerator;
        this.recipeRatingDataGenerator = recipeRatingDataGenerator;
    }

    @PostConstruct
    private void generateData() {
        userDataGenerator.generateUser();
        coffeeBeanDataGenerator.generateCoffeeBeans();
        extractionDataGenerator.generateExtractions();
        recipeDataGenerator.generateRecipes();
        recipeRatingDataGenerator.generateRecipeRatings();
    }
}
