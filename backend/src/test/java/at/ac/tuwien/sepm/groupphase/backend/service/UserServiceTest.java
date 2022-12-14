package at.ac.tuwien.sepm.groupphase.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserResetPasswordDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
            .map(UserDetailDto::getEmail, UserDetailDto::getRole)
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

    @Test
    public void resetPasswordOfNoneExistingUser() {
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.resetPassword(new UserResetPasswordDto("martia.musterfrau@example.com"));
        });
    }

    @Test
    public void findAnExistingUserByEmail() {
        User user = userService.findApplicationUserByEmail("martina.musterfrau@example.com");

        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("martina.musterfrau@example.com");
    }

    @Test
    public void deleteExistingUserWithId() {
        assertDoesNotThrow(() -> userService.deleteUser(1L));
    }
}
