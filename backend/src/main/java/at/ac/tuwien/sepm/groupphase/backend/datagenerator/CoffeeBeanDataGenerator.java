package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;
import at.ac.tuwien.sepm.groupphase.backend.repository.CoffeeBeanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

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
            CoffeeBean cb1 = new CoffeeBean("Another coffee bean", 9.90F, "Brasil", 5, CoffeeRoast.MEDIUM,
                "100% Arabica", null, "A description goes here", new User(1L));

            CoffeeBean cb2 = new CoffeeBean("Espresso House Blend", 9.90F, "Brasil, Colombia, Congo, Laos", null, CoffeeRoast.DARK,
                "100 % Arabica, Blend", "https://www.coffeecircle.com/de/p/house-blend-espresso",
                "Unser House Blend Espresso besteht aus 100% Arabica Bohnen und vereint die Herkunftsländer Brasilien, Kolumbien, Kongo, D.R. und Laos"
                + " zu einem ausgewogenen und mittelkräftigen Espresso.",
                new User(1L));

            CoffeeBean cb3 = new CoffeeBean("Yet another coffee bean", 0F, "Everywhere", 2, CoffeeRoast.DARK,
                "A normal description goes here again", new User(1L));

            CoffeeBean cb4 = new CoffeeBean("Jingle Beans Holiday Blend", 21.50F,
                "Ethiopia, Smallholder Farmers", 1900, CoffeeRoast.LIGHT,
                "Two merry coffees that triumphantly coalesce into the best interpretation of aqueous fruit cake in a cup.",
                new User(1L));

            CoffeeBean cb5 = new CoffeeBean("West End Blues", 17.75F, "JIMMA, ETHIOPIA", null, CoffeeRoast.LIGHT,
                "Traditional in name only,West End Blues is a complex blend of flavors and textures.", new User(1L));

            CoffeeBean cb6 = new CoffeeBean("TIME & TEMPERATURE", 19F, "JIMMA, ETHIOPIA / INZÁ, COLOMBIA",
                null, CoffeeRoast.LIGHT, "Effervescent, electric, and just plain cool, Time and Temperature is Tandem’s signature espresso blend.",
                new User(1L));

            coffeeBeanRepository.save(cb1);
            coffeeBeanRepository.save(cb2);
            coffeeBeanRepository.save(cb3);
            coffeeBeanRepository.save(cb4);
            coffeeBeanRepository.save(cb5);
            coffeeBeanRepository.save(cb6);
        }

    }
}
