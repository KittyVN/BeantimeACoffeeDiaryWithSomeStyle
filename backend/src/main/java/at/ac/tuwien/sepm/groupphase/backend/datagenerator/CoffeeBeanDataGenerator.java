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
            CoffeeBean cb1 = new CoffeeBean("Buna Dimaa Coffee", 26.90F, "Columbia, Ethiopia, Peru", null, CoffeeRoast.DARK,
                "100 % Arabica, Blend", "https://www.coffeecircle.com/en/p/buna-dimaa", "Buna Dimaa is our dark filter coffee roasting. The mixture of 50% Ethiopian and 50% Colombian highland "
                + "Arabica beans results in a full-bodied and strong, yet low-acid filter coffee.", "6/6", new User(1L));

            CoffeeBean cb2 = new CoffeeBean("Espresso House Blend", 31.90F, "Brasil, Colombia, Congo, Laos", null, CoffeeRoast.DARK,
                "100 % Arabica, Blend", "https://www.coffeecircle.com/en/p/house-blend-espresso",
                "Our House Blend Espresso consists of 100% Arabica beans and combines varietals from Ethiopia and Colombia to create a well balanced and medium-strong espresso.", "4/6",
                new User(1L));

            CoffeeBean cb3 = new CoffeeBean("Sierra Nevada Coffee", 26.90F, "Brasil, Colombia, Congo, Laos", null, CoffeeRoast.MEDIUM,
                "100 % Arabica, Single Origin", "https://www.coffeecircle.com/en/p/sierra-nevada",
                "Colombia is one of the best-known and most popular coffee-growing countries in the world. The balanced, slightly nutty-caramel taste enriches our coffee selection.", "3/6",
                new User(1L));

            CoffeeBean cb4 = new CoffeeBean("El Vergel Coffee", 43.90F, "Colombia", null, CoffeeRoast.MEDIUM,
                "100 % Arabica, Single Origin", "https://www.coffeecircle.com/en/p/el-vergel",
                "Our new Christmas coffee comes from Colombia. The filter coffee is named after the farm El Vergel, which we visited on our trip to Colombia in 2021."
                + " We are excited about the innovative work of the farm and are very happy to offer you this very balanced and spicy coffee!", "4/6",
                new User(1L));

            CoffeeBean cb5 = new CoffeeBean("Cerrado Coffee & Espresso", 36.90F, "Brazil", null, CoffeeRoast.MEDIUM,
                "100 % Arabica, Single Origin", "https://www.coffeecircle.com/en/p/cerrado",
                "The Cerrado is our first 100% Arabica coffee from Brazil and is some of the best quality coffee the country has to offer."
                + " Cerrado is nutty, chocolatey and suitable for both filter coffee and espresso.", "4/6",
                new User(1L));

            CoffeeBean cb6 = new CoffeeBean("Limu Coffee", 31.90F, "Ethiopia", null, CoffeeRoast.MEDIUM,
                "100 % Arabica, Single Origin", "https://www.coffeecircle.com/en/p/limu",
                "This coffee comes from the highlands of the Limu region in western Ethiopia. It is one of the first coffees we ever imported and still inspires us with its quality.", "3/6",
                new User(1L));

            CoffeeBean cb7 = new CoffeeBean("APAS Coffee", 43.90F, "Brazil", null, CoffeeRoast.MEDIUM,
                "100 % Arabica, Single Origin", "https://www.coffeecircle.com/en/p/apas",
                "APAS is a naturally processed coffee from Brazil and one of our highlights of the last harvest. The coffee is extremely complex. "
                + "The taste captivates with nutty-chocolaty aromas, which are rounded off by elegant fruity flavours."
                + " We source this coffee directly from our close partners Alessandro and Ademilson from the APAS cooperative.", "4/6",
                new User(1L));

            coffeeBeanRepository.save(cb1);
            coffeeBeanRepository.save(cb2);
            coffeeBeanRepository.save(cb3);
            coffeeBeanRepository.save(cb4);
            coffeeBeanRepository.save(cb5);
            coffeeBeanRepository.save(cb6);
            coffeeBeanRepository.save(cb7);
        }
    }
}
