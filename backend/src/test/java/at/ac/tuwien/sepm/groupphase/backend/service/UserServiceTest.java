package at.ac.tuwien.sepm.groupphase.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles({"test", "generateData"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    public void searchWithoutParametersReturnsAllUsers() {
        List<UserDetailDto> users = userService.search(new UserSearchDto(null, null, null)).toList();

        assertThat(users.size()).isGreaterThanOrEqualTo(8);
        assertThat(users)
            .map(UserDto::email, UserDto::role, UserDto::isActive)
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
        List<UserDetailDto> users = userService.search(new UserSearchDto(null, "doe", null)).toList();

        assertThat(users.size()).isGreaterThanOrEqualTo(2);
        assertThat(users)
            .map(UserDetailDto::getEmail, UserDetailDto::getRole)
            .contains(tuple("john.doe@example.com", UserRole.ADMIN))
            .contains(tuple("jane.doe@example.com", UserRole.USER));
    }

    @Test
    public void searchForEmailLikeDoeAndRoleAdminReturnsMin1User() {
        List<UserDetailDto> users = userService.search(new UserSearchDto(null, "doe", UserRole.ADMIN)).toList();

        assertThat(users.size()).isGreaterThanOrEqualTo(1);
        assertThat(users)
            .map(UserDetailDto::getEmail, UserDetailDto::getRole)
            .contains(tuple("john.doe@example.com", UserRole.ADMIN))
            .doesNotContain(tuple("jane.doe@example.com", UserRole.USER));
    }
}
