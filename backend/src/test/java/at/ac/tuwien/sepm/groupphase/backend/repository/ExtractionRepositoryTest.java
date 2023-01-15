package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanAvgExtractionRating;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionDayStatsDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionListDto;
import at.ac.tuwien.sepm.groupphase.backend.mapper.UserProfileMapper;
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
public class ExtractionRepositoryTest {
    @Autowired
    private ExtractionRepository extractionRepository;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Test
    @Transactional
    public void findAvgExtractionRatingByCoffeeBeanIdWithExtractionsReturnsDto() {
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

    @Test
    @Transactional
    public void findTop5RatedByExistentUserIdReturnsList() {
        List<Tuple> top5extractions = extractionRepository.findTop5RatedByUserId(1L);

        assertThat(top5extractions).isNotNull();
        assertThat(top5extractions.size()).isLessThanOrEqualTo(5);
        assertThat(top5extractions)
            .map(userProfileMapper::tupleToExtractionListDto)
            .extracting(ExtractionListDto::getId, ExtractionListDto::getDateTime, ExtractionListDto::getBeanName, ExtractionListDto::getBeanId, ExtractionListDto::getRating)
            .contains(tuple(8L, LocalDateTime.parse("2022-12-16T14:50:00"), "Jingle Beans Holiday Blend", 4L, 25))
            .contains(tuple(1L, LocalDateTime.parse("2022-12-11T14:50:00"), "Espresso House Blend", 2L, 25))
            .contains(tuple(5L, LocalDateTime.parse("2022-12-15T14:50:00"), "Espresso House Blend", 2L, 23))
            .contains(tuple(13L, LocalDateTime.parse("2022-12-16T14:50:00"), "TIME & TEMPERATURE", 6L, 22))
            .contains(tuple(10L, LocalDateTime.parse("2022-12-16T14:50:00"), "West End Blues", 5L, 22));
    }

    @Test
    @Transactional
    public void findTop5RatedByNonExistentUserIdReturnsEmptyList() {
        List<Tuple> top5extractions = extractionRepository.findTop5RatedByUserId(0L);

        assertThat(top5extractions).isNotNull();
        assertThat(top5extractions.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    public void findDailyCountsForLast53WeeksByExistentUserIdReturnsList() {
        List<Tuple> dayStatsTuples = extractionRepository.findDailyCountsForLast53WeeksByUserId(1L);

        assertThat(dayStatsTuples).isNotNull();
        assertThat(dayStatsTuples.size()).isLessThanOrEqualTo(6);
        assertThat(dayStatsTuples)
            .map(userProfileMapper::tupleToExtractionDayStatsDto)
            .extracting(ExtractionDayStatsDto::getDate, ExtractionDayStatsDto::getNumExtractions, ExtractionDayStatsDto::getRelFrequency)
            .contains(tuple(LocalDate.parse("2022-12-11"), 1, 0))
            .contains(tuple(LocalDate.parse("2022-12-12"), 1, 0))
            .contains(tuple(LocalDate.parse("2022-12-13"), 1, 0))
            .contains(tuple(LocalDate.parse("2022-12-14"), 1, 0))
            .contains(tuple(LocalDate.parse("2022-12-15"), 1, 0))
            .contains(tuple(LocalDate.parse("2022-12-16"), 10, 0));
    }

    @Test
    @Transactional
    public void findDailyCountsForLast53WeeksByNonExistentUserIdReturnsEmptyList() {
        List<Tuple> dayStatsTuples = extractionRepository.findDailyCountsForLast53WeeksByUserId(0L);

        assertThat(dayStatsTuples).isNotNull();
        assertThat(dayStatsTuples.size()).isEqualTo(0);
    }
}
