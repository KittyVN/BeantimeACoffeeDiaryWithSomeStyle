package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.enums.UserRole;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles({"test", "generateData"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;

    /* Tests for search() */

    @Test
    @Transactional
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
    @Transactional
    public void searchForEmailLikeDoeReturnsMin2Users() {
        List<UserDetailDto> users = userService.search(new UserSearchDto(null, "doe", null)).toList();

        assertThat(users.size()).isGreaterThanOrEqualTo(2);
        assertThat(users)
            .map(UserDetailDto::getEmail, UserDetailDto::getRole)
            .contains(tuple("john.doe@example.com", UserRole.ADMIN))
            .contains(tuple("jane.doe@example.com", UserRole.USER));
    }

    @Test
    @Transactional
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
    @Transactional
    public void getByIdReturnsUser() {
        UserDetailDto userResult = userService.getById(3L);

        assertThat(userResult).isNotNull();
        assertThat(userResult.getId()).isEqualTo(3L);
        assertThat(userResult.getEmail()).isEqualTo("martina.musterfrau@example.com");
        assertThat(userResult.getRole()).isEqualTo(UserRole.USER);
        assertThat(userResult.isActive()).isTrue();
    }

    @Test
    @Transactional
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
    @Transactional
    public void updateByAdminWithNonexistentIdThrowsNotFoundException() {
        try {
            userService.updateByAdmin(0L, new UserAdminEditDto(UserRole.USER, false));
        } catch (Exception e) {
            assertThat(e instanceof NotFoundException);
        }
    }

    @Test
    @Transactional
    public void resetPasswordOfNoneExistingUser() {
        assertThrows(UsernameNotFoundException.class, () -> userService.resetPassword(new UserResetPasswordDto("martia.musterfrau@example.com")));
    }

    @Test
    @Transactional
    public void findAnExistingUserByEmail() {
        User user = userService.findApplicationUserByEmail("martina.musterfrau@example.com");

        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("martina.musterfrau@example.com");
    }

    @Test
    @Transactional
    public void deleteExistingUserWithId() {
        assertDoesNotThrow(() -> userService.deleteUser(1L));
    }

    @Test
    @Transactional
    public void getProfileByExistentIdReturnsUserProfileDto() {
        UserProfileDto profile = userService.getProfileById(1L);
        LocalDate today = LocalDate.now();
        int days = today.getDayOfWeek().getValue() - 1 + 52 * 7;
        LocalDate start = today.minusDays(days);

        assertThat(profile).isNotNull();
        assertThat(profile.getEmail()).isEqualTo("admin@email.com");

        assertThat(profile.getExtractionMatrix()).isNotNull();
        assertThat(profile.getExtractionMatrix().getSumExtractions()).isEqualTo(15);
        assertThat(profile.getExtractionMatrix().getDailyStats().length).isEqualTo(days + 1);
        assertThat(profile.getExtractionMatrix().getDailyStats())
            .extracting(ExtractionDayStatsDto::getDate, ExtractionDayStatsDto::getNumExtractions, ExtractionDayStatsDto::getRelFrequency)
            .doesNotContain(tuple(start.minusDays(1), 0, 0))
            .contains(tuple(start, 0, 0))
            .contains(tuple(LocalDate.parse("2022-12-11"), 1, 1))
            .contains(tuple(LocalDate.parse("2022-12-12"), 1, 1))
            .contains(tuple(LocalDate.parse("2022-12-13"), 1, 1))
            .contains(tuple(LocalDate.parse("2022-12-14"), 1, 1))
            .contains(tuple(LocalDate.parse("2022-12-15"), 1, 1))
            .contains(tuple(LocalDate.parse("2022-12-16"), 10, 4))
            .doesNotContain(tuple(today.plusDays(1), 0, 0));

        assertThat(profile.getTopRatedExtractions()).isNotNull();
        assertThat(profile.getTopRatedExtractions().length).isLessThanOrEqualTo(5);
        assertThat(profile.getTopRatedExtractions())
            .extracting(ExtractionListDto::getId, ExtractionListDto::getDateTime, ExtractionListDto::getBeanName, ExtractionListDto::getBeanId, ExtractionListDto::getRating)
            .contains(tuple(8L, LocalDateTime.parse("2022-12-16T14:50:00"), "El Vergel Coffee", 4L, 25))
            .contains(tuple(1L, LocalDateTime.parse("2022-12-11T14:50:00"), "Espresso House Blend", 2L, 25))
            .contains(tuple(5L, LocalDateTime.parse("2022-12-15T14:50:00"), "Espresso House Blend", 2L, 23))
            .contains(tuple(10L, LocalDateTime.parse("2022-12-16T14:50:00"), "Cerrado Coffee & Espresso", 5L, 22))
            .contains(tuple(13L, LocalDateTime.parse("2022-12-16T14:50:00"), "Limu Coffee", 6L, 22));

        assertThat(profile.getTopRatedCoffees()).isNotNull();
        assertThat(profile.getTopRatedCoffees().length).isLessThanOrEqualTo(5);
        assertThat(profile.getTopRatedCoffees())
            .extracting(CoffeeBeanRatingListDto::getId, CoffeeBeanRatingListDto::getName, CoffeeBeanRatingListDto::getRating)
            .contains(tuple(2L, "Espresso House Blend", 16.83))
            .contains(tuple(4L, "El Vergel Coffee", 16.0))
            .contains(tuple(5L, "Cerrado Coffee & Espresso", 15.0))
            .contains(tuple(6L, "Limu Coffee", 15.0));

        assertThat(profile.getTopMostExtractedCoffees()).isNotNull();
        assertThat(profile.getTopMostExtractedCoffees().length).isLessThanOrEqualTo(5);
        assertThat(profile.getTopMostExtractedCoffees())
            .extracting(CoffeeBeanExtractionsListDto::getId, CoffeeBeanExtractionsListDto::getName, CoffeeBeanExtractionsListDto::getNumExtractions)
            .contains(tuple(2L, "Espresso House Blend", 6))
            .contains(tuple(5L, "Cerrado Coffee & Espresso", 3))
            .contains(tuple(4L, "El Vergel Coffee", 3))
            .contains(tuple(6L, "Limu Coffee", 3));
    }

    @Test
    @Transactional
    public void getProfileByNonExistentIdThrowsNotFoundException() {
        try {
            userService.getProfileById(0L);
        } catch (Exception e) {
            assertThat(e instanceof NotFoundException);
        }
    }
}
