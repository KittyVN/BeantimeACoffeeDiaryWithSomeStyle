package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanAvgExtractionRating;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles({"test", "generateData"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class ExtractionRepositoryTest {
    @Autowired
    private ExtractionRepository extractionRepository;

    @Test
    @Transactional
    public void findAvgExtractionRatingByCoffeeBeanIdWithExtractionsReturnsDTO() {
        CoffeeBeanAvgExtractionRating result = extractionRepository.findAvgExtractionRatingByCoffeeBeanId(2L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(2L);
        assertThat(result.getAcidity()).isEqualTo(3.3333333333333335);
        assertThat(result.getAftertaste()).isEqualTo(3.5);
        assertThat(result.getAromatics()).isEqualTo(3.3333333333333335);
        assertThat(result.getBody()).isEqualTo(2.8333333333333335);
        assertThat(result.getSweetness()).isEqualTo(3.8333333333333335);
    }

    @Test
    @Transactional
    public void findAvgExtractionRatingByCoffeeBeanIdWithoutExtractionsReturnsNull() {
        CoffeeBeanAvgExtractionRating result = extractionRepository.findAvgExtractionRatingByCoffeeBeanId(1L);

        assertThat(result).isEqualTo(null);
    }
}
