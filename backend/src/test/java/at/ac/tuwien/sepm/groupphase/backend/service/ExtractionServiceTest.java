package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanAvgExtractionRating;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles({"test", "generateData"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class ExtractionServiceTest {
    @Autowired
    private ExtractionService extractionService;

    @Test
    @Transactional
    public void findAvgExtractionRatingByCoffeeBeanIdWithExtractionsReturnsDto() {
        CoffeeBeanAvgExtractionRating result = extractionService.getAvgExtractionEvaluationParamsByCoffeeBeanId(2L);

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
    public void findAvgExtractionRatingByCoffeeBeanIdWithoutExtractionsReturnsDefault0Dto() {
        CoffeeBeanAvgExtractionRating result = extractionService.getAvgExtractionEvaluationParamsByCoffeeBeanId(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getAcidity()).isEqualTo(0);
        assertThat(result.getAftertaste()).isEqualTo(0);
        assertThat(result.getAromatics()).isEqualTo(0);
        assertThat(result.getBody()).isEqualTo(0);
        assertThat(result.getSweetness()).isEqualTo(0);
    }
}
