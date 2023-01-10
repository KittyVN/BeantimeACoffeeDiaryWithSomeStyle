package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Extraction;
import at.ac.tuwien.sepm.groupphase.backend.entity.Recipe;
import at.ac.tuwien.sepm.groupphase.backend.repository.ExtractionRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Profile("generateData")
@Component
public class RecipeDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final RecipeRepository recipeRepository;
    private final ExtractionRepository extractionRepository;


    public RecipeDataGenerator(RecipeRepository recipeRepository, ExtractionRepository extractionRepository) {
        this.recipeRepository = recipeRepository;
        this.extractionRepository = extractionRepository;
    }

    public void generateRecipes() {
        if (this.recipeRepository.findAll().size() > 0) {
            LOGGER.debug("Recipes already generated");
        } else {
            LOGGER.debug("Generating recipes");

            Recipe e1 = new Recipe(
                "This is my favorite recipe for brewing cold black coffee. There are no extra steps like stiring, or any additional things like sugar or cream needed.",
                new Extraction(5L)
            );

            this.recipeRepository.save(e1);
        }
    }
}
