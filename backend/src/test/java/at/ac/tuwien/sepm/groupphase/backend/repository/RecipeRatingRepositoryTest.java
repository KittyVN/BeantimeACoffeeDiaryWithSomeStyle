package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.RecipeRating;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ActiveProfiles({"test", "generateData"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class RecipeRatingRepositoryTest {
    @Autowired
    RecipeRatingRepository repository;

    /* FIND tests */
    @Test
    @Transactional
    public void findRecipeRatingByExistentRecipeIdReturnsCollection() {
        List<RecipeRating> ratingResult = (List<RecipeRating>) repository.findRecipeRatingByRecipe_IdOrderByTimestampDesc(1L);

        assertThat(ratingResult).isNotNull();
        assertThat(ratingResult.size()).isEqualTo(6);

        assertThat(ratingResult)
            .map(rating -> tuple(rating.getId(), rating.getRecipe().getId(), rating.getAuthor().getId(),
                rating.getRating(), rating.getText()))
            .contains(tuple(1L, 1L, 2L, 4, null))
            .contains(tuple(2L, 1L, 4L, 5, "Delicious!"))
            .contains(tuple(3L, 1L, 6L, 3, "Tried it for the first time and found out it's not good and not bad."))
            .contains(tuple(4L, 1L, 8L, 4, null))
            .contains(tuple(5L, 1L, 7L, 1, "Seldom have I ever drank something so disgusting!"))
            .contains(tuple(6L, 1L, 3L, 5, null));
    }

    @Test
    @Transactional
    public void findRecipeRatingByNonExistentRecipeIdReturnsEmptyCollection() {
        List<RecipeRating> ratingResult = (List<RecipeRating>) repository.findRecipeRatingByRecipe_IdOrderByTimestampDesc(0L);

        assertThat(ratingResult).isNotNull();
        assertThat(ratingResult.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    public void findRecipeRatingByExistentIdReturnsObject() {
        RecipeRating ratingResult = repository.findRecipeRatingById(1L);

        assertThat(ratingResult).isNotNull();
        assertThat(ratingResult.getId()).isEqualTo(1L);
        assertThat(ratingResult.getRecipe().getId()).isEqualTo(1L);
        assertThat(ratingResult.getAuthor().getId()).isEqualTo(2L);
        assertThat(ratingResult.getTimestamp()).isNotNull();
        assertThat(ratingResult.getRating()).isEqualTo(4);
        assertThat(ratingResult.getText()).isNull();
    }

    @Test
    @Transactional
    public void findRecipeRatingByNonExistentIdReturnsNull() {
        RecipeRating ratingResult = repository.findRecipeRatingById(0L);

        assertThat(ratingResult).isNull();
    }
}
