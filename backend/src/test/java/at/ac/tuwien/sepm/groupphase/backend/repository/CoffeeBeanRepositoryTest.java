package at.ac.tuwien.sepm.groupphase.backend.repository;


import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanExtractionsListDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanRatingListDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;
import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;
import at.ac.tuwien.sepm.groupphase.backend.mapper.UserProfileMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ActiveProfiles({"test", "generateData"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class CoffeeBeanRepositoryTest {
    @Autowired
    private CoffeeBeanRepository coffeeBeanRepository;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Test
    @Transactional
    @Rollback
    public void createValidCoffee() {
        CoffeeBean coffeeBean = new CoffeeBean("test", CoffeeRoast.DARK);
        CoffeeBean result = coffeeBeanRepository.save(coffeeBean);
        result.setId(null);
        assertThat(result.getName()).isEqualTo(coffeeBean.getName());
    }

    @Test
    @Transactional
    @Rollback
    public void editCoffeeToValid() {
        CoffeeBean coffeeBean = new CoffeeBean(1L, "test", CoffeeRoast.DARK);
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
    public void getCoffeeBeanByExistentIdReturnsCoffeeBeanDto() {
        CoffeeBean result = coffeeBeanRepository.findById(2L).get();

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Espresso House Blend");
        assertThat(result.getPrice()).isEqualTo(31.90F);
        assertThat(result.getOrigin()).isEqualTo("Brasil, Colombia, Congo, Laos");
        assertThat(result.getHeight()).isEqualTo(null);
        assertThat(result.getCoffeeRoast()).isEqualTo(CoffeeRoast.DARK);
        assertThat(result.getUser().getId()).isEqualTo(1L);
    }

    @Test
    @Transactional
    public void getCoffeeBeanByNonExistentIdReturnsEmptyOptional() {
        assertThat(coffeeBeanRepository.findById(0L).isPresent()).isFalse();
    }

    @Test
    @Transactional
    public void findTop5RatedByExistentUserIdReturnsList() {
        List<Tuple> top5coffees = coffeeBeanRepository.findTop5RatedByUserId(1L);

        assertThat(top5coffees).isNotNull();
        assertThat(top5coffees.size()).isEqualTo(4);
        assertThat(top5coffees.size()).isLessThanOrEqualTo(5);
        assertThat(top5coffees)
            .map(userProfileMapper::tupleToCoffeeBeanRatingListDto)
            .extracting(CoffeeBeanRatingListDto::getId, CoffeeBeanRatingListDto::getName, CoffeeBeanRatingListDto::getRating)
            .contains(tuple(2L, "Espresso House Blend", 16.83))
            .contains(tuple(5L, "Cerrado Coffee & Espresso", 15.0))
            .contains(tuple(4L, "El Vergel Coffee", 16.0));
    }

    @Test
    @Transactional
    public void findTop5RatedByNonExistentUserIdReturnsEmptyList() {
        List<Tuple> top5coffees = coffeeBeanRepository.findTop5RatedByUserId(0L);

        assertThat(top5coffees).isNotNull();
        assertThat(top5coffees.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    public void findTop5ExtractedByExistentUserIdReturnsList() {
        List<Tuple> top5coffees = coffeeBeanRepository.findTop5ExtractedByUserId(1L);

        assertThat(top5coffees).isNotNull();
        assertThat(top5coffees.size()).isEqualTo(4);
        assertThat(top5coffees.size()).isLessThanOrEqualTo(5);
        assertThat(top5coffees)
            .map(userProfileMapper::tupleToCoffeeBeanExtractionsListDto)
            .extracting(CoffeeBeanExtractionsListDto::getId, CoffeeBeanExtractionsListDto::getName, CoffeeBeanExtractionsListDto::getNumExtractions)
            .contains(tuple(2L, "Espresso House Blend", 6))
            .contains(tuple(5L, "Cerrado Coffee & Espresso", 3))
            .contains(tuple(4L, "El Vergel Coffee", 3))
            .contains(tuple(6L, "Limu Coffee", 3));
    }

    @Test
    @Transactional
    public void findTop5ExtractedByNonExistentUserReturnsEmptyList() {
        List<Tuple> top5coffees = coffeeBeanRepository.findTop5ExtractedByUserId(0L);

        assertThat(top5coffees).isNotNull();
        assertThat(top5coffees.size()).isEqualTo(0);
    }
}
