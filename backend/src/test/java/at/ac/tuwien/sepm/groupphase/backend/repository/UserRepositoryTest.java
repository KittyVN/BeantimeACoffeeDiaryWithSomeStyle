package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ActiveProfiles({"test", "generateData"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    public void searchWithoutParametersReturnsAllUsers() {
        List<User> users = (List<User>) userRepository.search(new UserDto(null, null, null, null));

        assertThat(users.size()).isGreaterThanOrEqualTo(10);
        assertThat(users)
            .map(User::getEmail, User::getRole, User::isActive)
            .contains(tuple("admin@email.com", UserRole.ADMIN, true))
            .contains(tuple("john.doe@example.com", UserRole.ADMIN, true))
            .contains(tuple("martina.musterfrau@example.com", UserRole.USER, true))
            .contains(tuple("ola.nordmann@example.com", UserRole.USER, true))
            .contains(tuple("tommy.atkins@example.com", UserRole.USER, true))
            .contains(tuple("jane.doe@example.com", UserRole.USER, true))
            .contains(tuple("jan.jansen@example.com", UserRole.USER, true))
            .contains(tuple("olaf.olaf@example.com", UserRole.USER, true))
            .contains(tuple("user@example.com", UserRole.USER, false))
            .contains(tuple("john.smith@example.com", UserRole.USER, false));
    }

    @Test
    public void searchForEmailLikeDoeReturnsMin2Users() {
        List<User> users = (List<User>) userRepository.search(new UserDto(null, "doe", null, null));

        assertThat(users.size()).isGreaterThanOrEqualTo(2);
        assertThat(users)
            .map(User::getEmail, User::getRole)
            .contains(tuple("john.doe@example.com", UserRole.ADMIN))
            .contains(tuple("jane.doe@example.com", UserRole.USER));
    }

    @Test
    public void searchForEmailLikeDoeAndRoleAdminReturnsMin1User() {
        List<User> users = (List<User>) userRepository.search(new UserDto(null, "doe", UserRole.ADMIN, null));

        assertThat(users.size()).isGreaterThanOrEqualTo(1);
        assertThat(users)
            .map(User::getEmail, User::getRole)
            .contains(tuple("john.doe@example.com", UserRole.ADMIN))
            .doesNotContain(tuple("jane.doe@example.com", UserRole.USER));
    }
}
