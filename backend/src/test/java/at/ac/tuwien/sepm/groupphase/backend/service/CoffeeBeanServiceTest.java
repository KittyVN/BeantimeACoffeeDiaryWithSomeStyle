package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanExtractionsListDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanRatingListDto;
import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;
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
public class CoffeeBeanServiceTest {
    @Autowired
    private CoffeeBeanService coffeeBeanService;


    @Test
    @Transactional
    @Rollback
    public void createValidCoffee() {
        CoffeeBeanDto coffeeBeanDto = new CoffeeBeanDto(
            null,
            "testing",
            12.22f,
            "Mexico",
            null,
            CoffeeRoast.DARK,
            null,
            null,
            null,
            null,
            null
        );
        CoffeeBeanDto result = coffeeBeanService.create(coffeeBeanDto);
        result.setId(null);
        assertThat(result.getName()).isEqualTo(coffeeBeanDto.getName());
    }

    @Test
    @Transactional
    @Rollback
    public void editCoffeeToValid() {
        CoffeeBeanDto coffeeBeanDto = new CoffeeBeanDto(
            1L,
            "testing",
            12.22f,
            "Mexico",
            null,
            CoffeeRoast.DARK,
            null,
            null,
            null,
            null,
            1L
        );
        CoffeeBeanDto result = coffeeBeanService.update(coffeeBeanDto);
        result.setId(null);
        assertThat(result.getName()).isEqualTo(coffeeBeanDto.getName());
    }


    @Test
    @Transactional
    public void getCoffeeBeanByNonExistentIdReturnsNotFoundException() {
        try {
            coffeeBeanService.getById(0L);
        } catch (Exception e) {
            assertThat(e instanceof NotFoundException);
        }
    }

    @Test
    @Transactional
    public void getTop5RatedByExistentUserIdReturnsList() {
        List<CoffeeBeanRatingListDto> top5coffees = coffeeBeanService.getTop5RatedByUserId(1L);

        assertThat(top5coffees).isNotNull();
        assertThat(top5coffees.size()).isEqualTo(4);
        assertThat(top5coffees.size()).isLessThanOrEqualTo(5);
        assertThat(top5coffees)
            .map(CoffeeBeanRatingListDto::getId, CoffeeBeanRatingListDto::getName, CoffeeBeanRatingListDto::getRating)
            .contains(tuple(2L, "Espresso House Blend", 16.83))
            .contains(tuple(4L, "El Vergel Coffee", 16.0))
            .contains(tuple(5L, "Cerrado Coffee & Espresso", 15.0))
            .contains(tuple(6L, "Limu Coffee", 15.0));
    }

    @Test
    @Transactional
    public void getTop5RatedByNonExistentUserIdReturnsEmptyList() {
        List<CoffeeBeanRatingListDto> top5coffees = coffeeBeanService.getTop5RatedByUserId(0L);

        assertThat(top5coffees).isNotNull();
        assertThat(top5coffees.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    public void getTop5ExtractedByExistentUserIdReturnsList() {
        List<CoffeeBeanExtractionsListDto> top5coffees = coffeeBeanService.getTop5ExtractedByUserId(1L);

        assertThat(top5coffees).isNotNull();
        assertThat(top5coffees.size()).isEqualTo(4);
        assertThat(top5coffees.size()).isLessThanOrEqualTo(5);
        assertThat(top5coffees)
            .map(CoffeeBeanExtractionsListDto::getId, CoffeeBeanExtractionsListDto::getName, CoffeeBeanExtractionsListDto::getNumExtractions)
            .contains(tuple(2L, "Espresso House Blend", 6))
            .contains(tuple(5L, "Cerrado Coffee & Espresso", 3))
            .contains(tuple(4L, "El Vergel Coffee", 3))
            .contains(tuple(6L, "Limu Coffee", 3));
    }

    @Test
    @Transactional
    public void getTop5ExtractedByNonExistentUserReturnsEmptyList() {
        List<CoffeeBeanExtractionsListDto> top5coffees = coffeeBeanService.getTop5ExtractedByUserId(0L);

        assertThat(top5coffees).isNotNull();
        assertThat(top5coffees.size()).isEqualTo(0);
    }
}
