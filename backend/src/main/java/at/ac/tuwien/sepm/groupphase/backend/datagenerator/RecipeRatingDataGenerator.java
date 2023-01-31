package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Recipe;
import at.ac.tuwien.sepm.groupphase.backend.entity.RecipeRating;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.repository.RecipeRatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Profile("generateData")
@Component
public class RecipeRatingDataGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final RecipeRatingRepository recipeRatingRepository;

    public RecipeRatingDataGenerator(RecipeRatingRepository recipeRatingRepository) {
        this.recipeRatingRepository = recipeRatingRepository;
    }

    public void generateRecipeRatings() {
        if (recipeRatingRepository.findAll().size() > 0) {
            LOGGER.debug("RecipeRatings already generated");
        } else {
            LOGGER.debug("Generating RecipeRatings");

            RecipeRating rr1 = new RecipeRating(new Recipe(1L), new User(2L), 4);
            RecipeRating rr2 = new RecipeRating(new Recipe(1L), new User(4L), 5, "Delicious!");
            RecipeRating rr3 = new RecipeRating(new Recipe(1L), new User(6L), 3, "Tried it for the first time and found out it's not good and not bad.");
            RecipeRating rr4 = new RecipeRating(new Recipe(1L), new User(8L), 4);
            RecipeRating rr5 = new RecipeRating(new Recipe(1L), new User(7L), 1, "Seldom have I ever drank something so disgusting!");
            RecipeRating rr6 = new RecipeRating(new Recipe(1L), new User(3L), 5);

            recipeRatingRepository.save(rr1);
            recipeRatingRepository.save(rr2);
            recipeRatingRepository.save(rr3);
            recipeRatingRepository.save(rr4);
            recipeRatingRepository.save(rr5);
            recipeRatingRepository.save(rr6);
        }
    }
}
