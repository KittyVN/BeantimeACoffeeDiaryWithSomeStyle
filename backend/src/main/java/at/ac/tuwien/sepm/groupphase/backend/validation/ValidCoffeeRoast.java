package at.ac.tuwien.sepm.groupphase.backend.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = CoffeeRoastValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCoffeeRoast {
    String message() default "Invalid coffee roast";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
