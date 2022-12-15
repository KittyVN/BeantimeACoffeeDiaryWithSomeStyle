package at.ac.tuwien.sepm.groupphase.backend.repository;



import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;

import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;


import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles({"test", "generateData"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class CoffeeBeanRepositoryTest {
    @Autowired
    private CoffeeBeanRepository coffeeBeanRepository;

    @Test
    @Rollback
    public void createValidCoffee() throws Exception {
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
    @Rollback
    public void editCoffeeToValid() throws Exception {
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

}
