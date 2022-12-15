package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ActiveProfiles({"test", "generateData"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    public void searchWithoutParametersReturnsAllUsers() {
        List<User> users = (List<User>) userRepository.search(new UserSearchDto(null, null, null));

        assertThat(users.size()).isGreaterThanOrEqualTo(8);
        assertThat(users)
            .map(User::getEmail, User::getRole)
            .contains(tuple("admin@email.com", UserRole.ADMIN))
            .contains(tuple("john.doe@example.com", UserRole.ADMIN))
            .contains(tuple("martina.musterfrau@example.com", UserRole.USER))
            .contains(tuple("ola.nordmann@example.com", UserRole.USER))
            .contains(tuple("tommy.atkins@example.com", UserRole.USER))
            .contains(tuple("jane.doe@example.com", UserRole.USER))
            .contains(tuple("jan.jansen@example.com", UserRole.USER))
            .contains(tuple("olaf.olaf@example.com", UserRole.USER));
    }

    @Test
    @Transactional
    public void searchForEmailLikeDoeReturnsMin2Users() {
        List<User> users = (List<User>) userRepository.search(new UserSearchDto(null, "doe", null));

        assertThat(users.size()).isGreaterThanOrEqualTo(2);
        assertThat(users)
            .map(User::getEmail, User::getRole)
            .contains(tuple("john.doe@example.com", UserRole.ADMIN))
            .contains(tuple("jane.doe@example.com", UserRole.USER));
    }

    @Test
    @Transactional
    public void searchForEmailLikeDoeAndRoleAdminReturnsMin1User() {
        List<User> users = (List<User>) userRepository.search(new UserSearchDto(null, "doe", UserRole.ADMIN));

        assertThat(users.size()).isGreaterThanOrEqualTo(1);
        assertThat(users)
            .map(User::getEmail, User::getRole)
            .contains(tuple("john.doe@example.com", UserRole.ADMIN))
            .doesNotContain(tuple("jane.doe@example.com", UserRole.USER));
    }

    @Test
    @Transactional
    public void findAnExistingUserByEmail() {
        User user = userRepository.findByEmail("martina.musterfrau@example.com");

        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("martina.musterfrau@example.com");
    }


    @Test
    @Transactional
    public void deleteExistingUserWithId() {
        assertDoesNotThrow(() -> userRepository.deleteById(1L));
    }
}
