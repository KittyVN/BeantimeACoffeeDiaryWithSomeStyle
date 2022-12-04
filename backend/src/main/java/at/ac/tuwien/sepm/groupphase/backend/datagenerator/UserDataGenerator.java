package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.enums.UserRole;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;

@Profile("generateData")
@Component
public class UserDataGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDataGenerator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void generateUser() {
        if (userRepository.findAll().size() > 0) {
            LOGGER.debug("Users already generated");
        } else {
            LOGGER.debug("Generating users");
            User admin = User
                .UserBuilder
                .aUser()
                .withEmail("admin@email.com")
                .withPassword(passwordEncoder.encode("password"))
                .withRole(UserRole.ADMIN)
                .build();

            User user = User
                .UserBuilder
                .aUser()
                .withEmail("user@email.com")
                .withPassword(passwordEncoder.encode("password"))
                .withRole(UserRole.USER)
                .build();

            userRepository.save(admin);
            userRepository.save(user);
        }
    }
}
