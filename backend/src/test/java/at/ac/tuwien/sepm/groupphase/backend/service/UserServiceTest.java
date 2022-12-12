package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserAdminEditDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.enums.UserRole;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ActiveProfiles({"test", "generateData"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;

    /* Tests for search() */

    @Test
    public void searchWithoutParametersReturnsAllUsers() {
        List<UserDetailDto> users = userService.search(new UserSearchDto(null, null, null)).toList();

        assertThat(users.size()).isGreaterThanOrEqualTo(8);
        assertThat(users)
            .map(UserDetailDto::getEmail, UserDetailDto::getRole, UserDetailDto::isActive)
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

    /* Tests for getById() */

    @Test
    public void getByIdReturnsUser() {
        UserDetailDto userResult = userService.getById(3L);

        assertThat(userResult).isNotNull();
        assertThat(userResult.getId()).isEqualTo(3L);
        assertThat(userResult.getEmail()).isEqualTo("martina.musterfrau@example.com");
        assertThat(userResult.getRole()).isEqualTo(UserRole.USER);
        assertThat(userResult.isActive()).isTrue();
    }

    @Test
    public void getUserByNonExistentIdThrowsNotFoundException() {
        try {
            userService.getById(0L);
        } catch (Exception e) {
            assertThat(e instanceof NotFoundException);
        }
    }

    /* Tests for updateByAdmin() */

    @Test
    public void updateByAdminReturnsUser() throws Exception {
        UserDetailDto userResult = userService.updateByAdmin(5L, new UserAdminEditDto(UserRole.USER, false));

        assertThat(userResult).isNotNull();
        assertThat(userResult.getId()).isEqualTo(5L);
        assertThat(userResult.getEmail()).isEqualTo("tommy.atkins@example.com");
        assertThat(userResult.getRole()).isEqualTo(UserRole.USER);
        assertThat(userResult.isActive()).isFalse();

        userService.updateByAdmin(5L, new UserAdminEditDto(UserRole.USER, true));
    }

    @Test
    public void updateByAdminWithNonexistentIdThrowsNotFoundException() {
        try {
            userService.updateByAdmin(0L, new UserAdminEditDto(UserRole.USER, false));
        } catch (Exception e) {
            assertThat(e instanceof NotFoundException);
        }
    }
}
