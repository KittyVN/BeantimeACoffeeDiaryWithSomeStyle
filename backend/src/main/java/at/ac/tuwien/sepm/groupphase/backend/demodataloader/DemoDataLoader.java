package at.ac.tuwien.sepm.groupphase.backend.demodataloader;

import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.enums.UserRole;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.invoke.MethodHandles;

@Profile("demoData")
@Component
public class DemoDataLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public DemoDataLoader(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    private void loadUsers() {
        CsvMapper mapper = new CsvMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(';');

        ObjectReader objectReader = mapper.readerFor(User.class).with(schema);
        try (Reader reader = new InputStreamReader(classloader.getResourceAsStream("csv/users.csv"))) {
            MappingIterator<User> mappingIterator = objectReader.readValues(reader);
            int i = 0;

            while (mappingIterator.hasNext()) {
                User user = mappingIterator.next();

                if (i == 0) {
                    user.setRole(UserRole.ADMIN);
                } else {
                    user.setRole(UserRole.USER);
                }

                user.setActive(true);
                user.setPassword(this.passwordEncoder.encode("password"));
                this.userRepository.save(user);
                i++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    private void setup() {
        this.loadUsers();
    }
}
