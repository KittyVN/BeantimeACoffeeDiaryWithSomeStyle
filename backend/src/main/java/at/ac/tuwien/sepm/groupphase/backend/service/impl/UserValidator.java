package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
@Component
public class UserValidator {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public void validateEmail(String email) throws ValidationException {
        LOG.trace("validateEmail({})", email);
        List<String> validationErrors = new ArrayList<>();

        if (email != null) {
            if (email.isBlank()) {
                validationErrors.add("Email is given but blank");
            }
            if (email.length() > 4095) {
                validationErrors.add("Email is too long: longer than 4095 characters");
            }
            String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
            Pattern pattern = Pattern.compile(regex);
            if (!pattern.matcher(email).matches()) {
                validationErrors.add("Email is not a correct email address");
            }
        } else {
            validationErrors.add("Email not given although it is required");
        }

        if (!validationErrors.isEmpty()) {
            throw new ValidationException("Validation of the email failed", validationErrors);
        }
    }

}
