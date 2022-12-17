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

    public void generateUser() {
        if (userRepository.findAll().size() > 0) {
            LOGGER.debug("Users already generated");
        } else {
            LOGGER.debug("Generating users");
            User admin1 = User
                .UserBuilder
                .aUser()
                .withEmail("admin@email.com")
                .withPassword(passwordEncoder.encode("password"))
                .withRole(UserRole.ADMIN)
                .build();

            User admin2 = User
                .UserBuilder
                .aUser()
                .withEmail("john.doe@example.com")
                .withPassword(passwordEncoder.encode("password"))
                .withRole(UserRole.ADMIN)
                .build();

            User user1 = User
                .UserBuilder
                .aUser()
                .withEmail("martina.musterfrau@example.com")
                .withPassword(passwordEncoder.encode("password"))
                .withRole(UserRole.USER)
                .build();

            User user2 = User
                .UserBuilder
                .aUser()
                .withEmail("ola.nordmann@example.com")
                .withPassword(passwordEncoder.encode("password"))
                .withRole(UserRole.USER)
                .build();

            User user3 = User
                .UserBuilder
                .aUser()
                .withEmail("tommy.atkins@example.com")
                .withPassword(passwordEncoder.encode("password"))
                .withRole(UserRole.USER)
                .build();

            User user4 = User
                .UserBuilder
                .aUser()
                .withEmail("jane.doe@example.com")
                .withPassword(passwordEncoder.encode("password"))
                .withRole(UserRole.USER)
                .build();

            User user5 = User
                .UserBuilder
                .aUser()
                .withEmail("jan.jansen@example.com")
                .withPassword(passwordEncoder.encode("password"))
                .withRole(UserRole.USER)
                .build();

            User user6 = User
                .UserBuilder
                .aUser()
                .withEmail("olaf.olaf@example.com")
                .withPassword(passwordEncoder.encode("password"))
                .withRole(UserRole.USER)
                .build();

            User user7 = User
                .UserBuilder
                .aUser()
                .withEmail("user@example.com")
                .withPassword(passwordEncoder.encode("password"))
                .withRole(UserRole.USER)
                .withActive(false)
                .build();

            User user8 = User
                .UserBuilder
                .aUser()
                .withEmail("john.smith@example.com")
                .withPassword(passwordEncoder.encode("password"))
                .withRole(UserRole.USER)
                .withActive(false)
                .build();

            User userDaniel = User
                .UserBuilder
                .aUser()
                .withEmail("daniel.schermann12@gmail.com")
                .withPassword(passwordEncoder.encode("password"))
                .withRole(UserRole.USER)
                .build();

            userRepository.save(admin1);
            userRepository.save(admin2);
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);
            userRepository.save(user5);
            userRepository.save(user6);
            userRepository.save(user7);
            userRepository.save(user8);
            userRepository.save(userDaniel);
        }
    }
}
