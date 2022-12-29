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
            User admin1 = new User("admin@email.com", passwordEncoder.encode("password"), UserRole.ADMIN);

            User admin2 = new User("john.doe@example.com", passwordEncoder.encode("password"), UserRole.ADMIN);

            User user1 = new User("martina.musterfrau@example.com", passwordEncoder.encode("password"), UserRole.USER);

            User user2 = new User("ola.nordmann@example.com", passwordEncoder.encode("password"), UserRole.USER);

            User user3 = new User("tommy.atkins@example.com", passwordEncoder.encode("password"), UserRole.USER);

            User user4 = new User("jane.doe@example.com", passwordEncoder.encode("password"), UserRole.USER);

            User user5 = new User("jan.jansen@example.com", passwordEncoder.encode("password"), UserRole.USER);

            User user6 = new User("olaf.olaf@example.com", passwordEncoder.encode("password"), UserRole.USER);

            User user7 = new User("user@example.com", passwordEncoder.encode("password"), UserRole.USER, false);

            User user8 = new User("john.smith@example.com", passwordEncoder.encode("password"), UserRole.USER, false);

            User userDaniel = new User("daniel.schermann12@gmail.com", passwordEncoder.encode("password"), UserRole.USER);

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
