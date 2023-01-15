package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


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
            1L
        );
        CoffeeBeanDto result = coffeeBeanService.update(coffeeBeanDto);
        result.setId(null);
        assertThat(result.getName()).isEqualTo(coffeeBeanDto.getName());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteExistentCoffee() {
        assertDoesNotThrow(() -> coffeeBeanService.delete(1L));
    }

    @Test
    @Transactional
    public void getCoffeeBeanByExistentIdReturnsCoffeeBeanDto() {
        CoffeeBeanDto result = coffeeBeanService.getById(2L);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Espresso House Blend");
        assertThat(result.getPrice()).isEqualTo(9.90F);
        assertThat(result.getOrigin()).isEqualTo("Brasil, Colombia, Congo, Laos");
        assertThat(result.getHeight()).isEqualTo(null);
        assertThat(result.getCoffeeRoast()).isEqualTo(CoffeeRoast.DARK);
        assertThat(result.getUserId()).isEqualTo(1L);
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
            .contains(tuple(4L, "Jingle Beans Holiday Blend", 16.0))
            .contains(tuple(6L, "TIME & TEMPERATURE", 15.0))
            .contains(tuple(5L, "West End Blues", 15.0));
    }

    @Test
    @Transactional
    public void getTop5RatedByNonExistentUserIdReturnsEmptyList() {
        List<CoffeeBeanRatingListDto> top5coffees = coffeeBeanService.getTop5RatedByUserId(0L);

        assertThat(top5coffees).isNotNull();
        assertThat(top5coffees.size()).isEqualTo(0);
    }
}
