package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
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
            true,
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
            true,
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
        assertThat(result.getName()).isEqualTo("Another coffee bean");
        assertThat(result.getPrice()).isEqualTo(0F);
        assertThat(result.getOrigin()).isEqualTo("There");
        assertThat(result.getHeight()).isEqualTo(5);
        assertThat(result.getCoffeeRoast()).isEqualTo(CoffeeRoast.LIGHT);
        assertThat(result.getCustom()).isTrue();
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
}
