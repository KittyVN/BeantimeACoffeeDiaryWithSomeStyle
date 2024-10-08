package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanAvgExtractionRating;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionDayStatsDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionListDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionMatrixDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

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

    @Test
    @Transactional
    public void getTop5RatedByExistentUserIdReturnsList() {
        List<ExtractionListDto> top5extractions = extractionService.getTop5RatedByUserId(1L);

        assertThat(top5extractions).isNotNull();
        assertThat(top5extractions.size()).isLessThanOrEqualTo(5);
        assertThat(top5extractions)
            .map(ExtractionListDto::getId, ExtractionListDto::getDateTime, ExtractionListDto::getBeanName, ExtractionListDto::getBeanId, ExtractionListDto::getRating)
            .contains(tuple(8L, LocalDateTime.parse("2022-12-16T14:50:00"), "El Vergel Coffee", 4L, 25))
            .contains(tuple(1L, LocalDateTime.parse("2022-12-11T14:50:00"), "Espresso House Blend", 2L, 25))
            .contains(tuple(5L, LocalDateTime.parse("2022-12-15T14:50:00"), "Espresso House Blend", 2L, 23))
            .contains(tuple(10L, LocalDateTime.parse("2022-12-16T14:50:00"), "Cerrado Coffee & Espresso", 5L, 22))
            .contains(tuple(13L, LocalDateTime.parse("2022-12-16T14:50:00"), "Limu Coffee", 6L, 22));
    }

    @Test
    @Transactional
    public void getTop5RatedByNonExistentUserIdReturnsEmptyList() {
        List<ExtractionListDto> top5extractions = extractionService.getTop5RatedByUserId(0L);

        assertThat(top5extractions).isNotNull();
        assertThat(top5extractions.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    public void getExtractionMatrixByExistentUserIdReturnsMatrixWithNonDefaultStats() {
        ExtractionMatrixDto extractionMatrix = extractionService.getExtractionMatrixByUserId(1L);
        LocalDate today = LocalDate.now();
        int days = today.getDayOfWeek().getValue() - 1 + 52 * 7;
        LocalDate start = today.minusDays(days);

        assertThat(extractionMatrix).isNotNull();
        assertThat(extractionMatrix.getSumExtractions()).isEqualTo(15);
        assertThat(extractionMatrix.getDailyStats().length).isEqualTo(days + 1);
        assertThat(extractionMatrix.getDailyStats())
            .extracting(ExtractionDayStatsDto::getDate, ExtractionDayStatsDto::getNumExtractions, ExtractionDayStatsDto::getRelFrequency)
            .doesNotContain(tuple(start.minusDays(1), 0, 0))
            .contains(tuple(start, 0, 0))
            .contains(tuple(LocalDate.parse("2022-12-11"), 1, 1))
            .contains(tuple(LocalDate.parse("2022-12-12"), 1, 1))
            .contains(tuple(LocalDate.parse("2022-12-13"), 1, 1))
            .contains(tuple(LocalDate.parse("2022-12-14"), 1, 1))
            .contains(tuple(LocalDate.parse("2022-12-15"), 1, 1))
            .contains(tuple(LocalDate.parse("2022-12-16"), 10, 4))
            .doesNotContain(tuple(today.plusDays(1), 0, 0));
    }

    @Test
    @Transactional
    public void getExtractionMatrixByNonExistentUserIdReturnsMatrixWithOnlyDefaultStats() {
        ExtractionMatrixDto extractionMatrix = extractionService.getExtractionMatrixByUserId(0L);
        LocalDate today = LocalDate.now();
        int days = today.getDayOfWeek().getValue() - 1 + 52 * 7;
        LocalDate start = today.minusDays(days);

        assertThat(extractionMatrix).isNotNull();
        assertThat(extractionMatrix.getSumExtractions()).isEqualTo(0);
        assertThat(extractionMatrix.getDailyStats().length).isEqualTo(days + 1);
        assertThat(extractionMatrix.getDailyStats())
            .extracting(ExtractionDayStatsDto::getDate, ExtractionDayStatsDto::getNumExtractions, ExtractionDayStatsDto::getRelFrequency)
            .doesNotContain(tuple(start.minusDays(1), 0, 0))
            .contains(tuple(start, 0, 0))
            .doesNotContain(tuple(today.plusDays(1), 0, 0));
        assertThat(extractionMatrix.getDailyStats())
            .extracting(ExtractionDayStatsDto::getNumExtractions, ExtractionDayStatsDto::getRelFrequency)
            .contains(tuple(0, 0));
    }
}
