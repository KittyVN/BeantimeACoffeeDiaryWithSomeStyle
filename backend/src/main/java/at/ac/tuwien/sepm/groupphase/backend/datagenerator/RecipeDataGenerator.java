package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Extraction;
import at.ac.tuwien.sepm.groupphase.backend.entity.Recipe;
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

    public RecipeDataGenerator(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public void generateRecipes() {
        if (this.recipeRepository.findAll().size() > 0) {
            LOGGER.debug("Recipes already generated");
        } else {
            LOGGER.debug("Generating recipes");

            Recipe e1 = new Recipe(
                true,
                new Extraction(5L)
            );

            Recipe e2 = new Recipe(
                false,
                new Extraction(4L)
            );

            Recipe e3 = new Recipe(
                true,
                new Extraction(15L)
            );

            this.recipeRepository.save(e1);
            this.recipeRepository.save(e2);
            this.recipeRepository.save(e3);
        }
    }
}
