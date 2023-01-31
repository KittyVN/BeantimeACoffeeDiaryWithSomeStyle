package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeRatingCreateDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeRatingListDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.ConflictException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    /* GET tests */
}
