package at.ac.tuwien.sepm.groupphase.backend.repository;


import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;
import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;
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
public class CoffeeBeanRepositoryTest {
    @Autowired
    private CoffeeBeanRepository coffeeBeanRepository;

    @Test
    @Transactional
    @Rollback
    public void createValidCoffee() {
        CoffeeBean coffeeBean = CoffeeBean
            .CoffeeBeanBuilder
            .aCoffeeBean()
            .withName("test")
            .withCustom(true)
            .withCoffeeRoast(CoffeeRoast.DARK)
            .build();
        CoffeeBean result = coffeeBeanRepository.save(coffeeBean);
        result.setId(null);
        assertThat(result.getName()).isEqualTo(coffeeBean.getName());
    }

    @Test
    @Transactional
    @Rollback
    public void editCoffeeToValid() {
        CoffeeBean coffeeBean = CoffeeBean
            .CoffeeBeanBuilder
            .aCoffeeBean()
            .withId(1L)
            .withName("test")
            .withCustom(true)
            .withCoffeeRoast(CoffeeRoast.DARK)
            .build();
        CoffeeBean result = coffeeBeanRepository.save(coffeeBean);
        assertThat(result.getName()).isEqualTo(coffeeBean.getName());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteExistentCoffeeBean() {
        assertDoesNotThrow(() -> coffeeBeanRepository.deleteById(1L));
    }

    @Test
    @Transactional
    public void getCoffeeBeanByExistentIdReturnsCoffeeBeanDTO() {
        CoffeeBean result = coffeeBeanRepository.findById(2L).get();

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Another coffee bean");
        assertThat(result.getPrice()).isEqualTo(0F);
        assertThat(result.getOrigin()).isEqualTo("There");
        assertThat(result.getHeight()).isEqualTo(5);
        assertThat(result.getCoffeeRoast()).isEqualTo(CoffeeRoast.LIGHT);
        assertThat(result.getCustom()).isTrue();
        assertThat(result.getUser().getId()).isEqualTo(1L);
    }

    @Test
    @Transactional
    public void getCoffeeBeanByNonExistentIdReturnsEmptyOptional() {
        assertThat(coffeeBeanRepository.findById(0L).isPresent()).isFalse();
    }

}
