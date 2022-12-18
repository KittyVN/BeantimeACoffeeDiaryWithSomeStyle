package at.ac.tuwien.sepm.groupphase.backend.validation;

import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class CoffeeRoastValidator implements ConstraintValidator<ValidCoffeeRoast, CoffeeRoast> {
    @Override
    public boolean isValid(CoffeeRoast value, ConstraintValidatorContext context) {
        return value != null && Arrays.asList(CoffeeRoast.values()).contains(value);
    }
}
