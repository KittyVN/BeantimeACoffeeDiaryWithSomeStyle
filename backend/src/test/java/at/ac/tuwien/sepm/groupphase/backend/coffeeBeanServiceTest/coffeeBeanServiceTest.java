package at.ac.tuwien.sepm.groupphase.backend.coffeeBeanServiceTest;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;
import at.ac.tuwien.sepm.groupphase.backend.service.CoffeeBeanService;
import ch.qos.logback.classic.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles({"test", "generateData"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class coffeeBeanServiceTest {
    @Autowired
    private CoffeeBeanService coffeeBeanService;

    @Test
    public void createValidCoffee() {
        CoffeeBeanDto testBean =
            new CoffeeBeanDto(
                null,
                "Test Coffee Bean",
                10.00f,
                "Mexico",
                3000,
                CoffeeRoast.DARK,
                "desc",
                true,
                null
            );
        CoffeeBeanDto storedBean = coffeeBeanService.create(testBean);
        assertThat(
            !(storedBean.id().equals(testBean.id())) && (storedBean.name().equals(testBean.name()))
        );
    }

}
