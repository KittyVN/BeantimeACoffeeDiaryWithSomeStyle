package at.ac.tuwien.sepm.groupphase.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ActiveProfiles({"test", "generateData"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class CoffeeBeanServiceTest {
    @Autowired
    private CoffeeBeanService coffeeBeanService;



}
