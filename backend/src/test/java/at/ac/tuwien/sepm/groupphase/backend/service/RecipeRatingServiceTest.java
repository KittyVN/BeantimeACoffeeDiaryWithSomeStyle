package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeRatingCreateDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeRatingListDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.AuthorizationException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ConflictException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ActiveProfiles({"test", "generateData"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class RecipeRatingServiceTest {
    @Autowired
    RecipeRatingService service;

    /* CREATE tests */
    @Test
    @Transactional
    public void createRatingByUserThatIsNotAuthorReturnsDetailDto() {
        RecipeRatingListDto ratingResult = service.create(new RecipeRatingCreateDto(1L, 2L, 5, null));

        assertThat(ratingResult).isNotNull();
        assertThat(ratingResult.getId()).isEqualTo(7L);
        assertThat(ratingResult.getAuthorId()).isEqualTo(2L);
        assertThat(ratingResult.getAuthorUsername()).isEqualTo("JohnD");
        assertThat(ratingResult.getRating()).isEqualTo(5);
        assertThat(ratingResult.getText()).isNull();
        assertThat(ratingResult.getTimestamp()).isNotNull();
    }

    @Test
    @Transactional
    public void createRatingByUserThatIsAuthorThrowsConflictException() {
        try {
            service.create(new RecipeRatingCreateDto(1L, 1L, 5, null));
        } catch (Exception e) {
            assertThat(e instanceof ConflictException);
        }
    }

    /* DELETE tests */
    @Test
    @Transactional
    @Rollback
    public void deleteRatingByExistentIdByAuthor() {
        assertThat(service.getByRecipeId(1L).toList().size()).isEqualTo(6);
        service.delete(1L, 1L);
        assertThat(service.getByRecipeId(1L).toList().size()).isEqualTo(5);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteRatingByNonExistentIdThrowsNotFoundException() {
        try {
            service.delete(0L, 1L);
        } catch (Exception e) {
            assertThat(e instanceof NotFoundException);
        }
    }

    @Test
    @Transactional
    @Rollback
    public void deleteRatingByExistentIdByNotAuthorThrowsAuthorizationException() throws Exception {
        try {
            service.delete(1L, 3L);
        } catch (Exception e) {
            assertThat(e instanceof AuthorizationException);
        }
    }

    /* GET tests */
    @Test
    @Transactional
    public void getByExistentRatingIdReturnsStream() {
        List<RecipeRatingListDto> ratingResult = service.getByRecipeId(1L).toList();

        assertThat(ratingResult).isNotNull();
        assertThat(ratingResult.size()).isEqualTo(6);
        assertThat(ratingResult)
            .map(RecipeRatingListDto::getId, RecipeRatingListDto::getRecipeId, RecipeRatingListDto::getAuthorId,
                RecipeRatingListDto::getAuthorUsername, RecipeRatingListDto::getRating, RecipeRatingListDto::getText)
            .contains(tuple(1L, 1L, 2L, "JohnD", 4, null))
            .contains(tuple(2L, 1L, 4L, "Südfrau", 5, "Delicious!"))
            .contains(tuple(3L, 1L, 6L, "Jane", 3, "Tried it for the first time and found out it's not good and not bad."))
            .contains(tuple(4L, 1L, 8L, "Olaf*2", 4, null))
            .contains(tuple(5L, 1L, 7L, "Jan", 1, "Seldom have I ever drank something so disgusting!"))
            .contains(tuple(6L, 1L, 3L, "Musterf", 5, null));
    }

    @Test
    @Transactional
    public void getByNonExistentRatingIdReturnsEmptyStream() {
        List<RecipeRatingListDto> ratingResult = service.getByRecipeId(0L).toList();

        assertThat(ratingResult).isNotNull();
        assertThat(ratingResult.size()).isEqualTo(0);
    }
}
