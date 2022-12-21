package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;
import at.ac.tuwien.sepm.groupphase.backend.repository.CoffeeBeanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;

@Profile("generateData")
@Component
public class CoffeeBeanDataGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final CoffeeBeanRepository coffeeBeanRepository;

    public CoffeeBeanDataGenerator(CoffeeBeanRepository coffeeBeanRepository) {
        this.coffeeBeanRepository = coffeeBeanRepository;
    }

    public void generateCoffeeBeans() {
        if (coffeeBeanRepository.findAll().size() > 0) {
            LOGGER.debug("CoffeeBeans already generated");
        } else {
            LOGGER.debug("Generating Coffee beans");
            CoffeeBean cb1 = CoffeeBean
                .CoffeeBeanBuilder
                .aCoffeeBean()
                .withName("A coffee bean")
                .withPrice(0F)
                .withOrigin("Here")
                .withHeight(11)
                .withCoffeeRoast(CoffeeRoast.MEDIUM)
                .withDescription("A description goes here")
                .withCustom(true)
                .withUser(User.UserBuilder.aUser().withId(1L).build())
                .build();

            CoffeeBean cb2 = CoffeeBean
                .CoffeeBeanBuilder
                .aCoffeeBean()
                .withName("Another coffee bean")
                .withPrice(0F)
                .withOrigin("There")
                .withHeight(5)
                .withCoffeeRoast(CoffeeRoast.LIGHT)
                .withDescription("A longer description goes here because I need characters for testing."
                    + " Lets add even more because its practical to see how many lines this box can actually hold.")
                .withCustom(true)
                .withUser(User.UserBuilder.aUser().withId(1L).build())
                .build();

            CoffeeBean cb3 = CoffeeBean
                .CoffeeBeanBuilder
                .aCoffeeBean()
                .withName("Yet another coffee bean")
                .withPrice(0F)
                .withOrigin("Everywhere")
                .withHeight(2)
                .withCoffeeRoast(CoffeeRoast.DARK)
                .withDescription("A normal description goes here again")
                .withCustom(true)
                .withUser(User.UserBuilder.aUser().withId(1L).build())
                .build();

            CoffeeBean cb4 = CoffeeBean
                .CoffeeBeanBuilder
                .aCoffeeBean()
                .withName("Jingle Beans Holiday Blend")
                .withPrice(21.50F)
                .withOrigin("Ethiopia, Smallholder Farmers")
                .withHeight(1900)
                .withCoffeeRoast(CoffeeRoast.LIGHT)
                .withDescription("Two merry coffees that triumphantly coalesce into the best interpretation of aqueous fruit cake in a cup. ")
                .withCustom(true)
                .withUser(User.UserBuilder.aUser().withId(1L).build())
                .build();

            CoffeeBean cb5 = CoffeeBean
                .CoffeeBeanBuilder
                .aCoffeeBean()
                .withName("West End Blues")
                .withPrice(17.75F)
                .withOrigin("JIMMA, ETHIOPIA")
                .withHeight(null)
                .withCoffeeRoast(CoffeeRoast.LIGHT)
                .withDescription("Traditional in name only,West End Blues is a complex blend" +
                    " of flavors and textures.")
                .withCustom(true)
                .withUser(User.UserBuilder.aUser().withId(1L).build())
                .build();

            CoffeeBean cb6 = CoffeeBean
                .CoffeeBeanBuilder
                .aCoffeeBean()
                .withName("TIME & TEMPERATURE")
                .withPrice(19F)
                .withOrigin("JIMMA, ETHIOPIA / INZÁ, COLOMBIA")
                .withHeight(null)
                .withCoffeeRoast(CoffeeRoast.LIGHT)
                .withDescription("Effervescent, electric, and just plain cool, Time and Temperature is Tandem’s signature espresso blend.")
                .withCustom(true)
                .withUser(User.UserBuilder.aUser().withId(1L).build())
                .build();

            coffeeBeanRepository.save(cb1);
            coffeeBeanRepository.save(cb2);
            coffeeBeanRepository.save(cb3);
            coffeeBeanRepository.save(cb4);
            coffeeBeanRepository.save(cb5);
            coffeeBeanRepository.save(cb6);
        }

    }
}
