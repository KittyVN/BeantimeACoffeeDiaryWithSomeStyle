package at.ac.tuwien.sepm.groupphase.backend.service.validation;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Component
public class CoffeeBeanValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * Checks if the {@code coffeeBean} given violates some invariant constraints
     *
     * @param coffeeBean to validate
     * @throws ValidationException if any of the validations are failed
     */
    public void validateForUpdate(CoffeeBeanDto coffeeBean) throws ValidationException {
        LOGGER.trace("validateForUpdate {}", coffeeBean);
        List<String> validationErrors = new ArrayList<>();

        if (coffeeBean.name() == null || coffeeBean.name().isBlank()) {
            validationErrors.add("The coffee bean needs to have a name");
        }

        if (!(coffeeBean.coffeeRoast() == CoffeeRoast.DARK || coffeeBean.coffeeRoast() == CoffeeRoast.LIGHT ||
            coffeeBean.coffeeRoast() == CoffeeRoast.MEDIUM)) {
            validationErrors.add("The roast type of the bean needs to be one of the following: LIGHT, MEDIUM or DARK");
        }

        if (!validationErrors.isEmpty()) {
            throw new ValidationException("Validation of CoffeeBean failed", validationErrors);
        }
    }
}
