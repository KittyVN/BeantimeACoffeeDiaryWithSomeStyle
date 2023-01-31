package at.ac.tuwien.sepm.groupphase.backend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"test", "generateData"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class RecipeRatingRepositoryTest {
    @Autowired
    RecipeRatingRepository repository;

    /* FIND tests */
}
