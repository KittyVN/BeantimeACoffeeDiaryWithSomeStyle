package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanExtractionsListDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanRatingListDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionDayStatsDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionListDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserProfileDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserResetPasswordDto;
import at.ac.tuwien.sepm.groupphase.backend.enums.UserRole;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test", "generateData"})
// enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
@EnableWebMvc
@WebAppConfiguration
public class UserEndpointTest {
    @Autowired
    private WebApplicationContext webAppContext;
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    JwtTokenizer jwtTokenizer;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).apply(springSecurity()).build();
    }

    /* Tests for GET /users */

    @Test
    @Transactional
    @WithMockUser(username = "admin@example.com", password = "password", roles = "ADMIN")
    public void searchWithoutParametersWithRoleAdminReturnsAllUsers() throws Exception {
        byte[] body = mockMvc
            .perform(MockMvcRequestBuilders
                .get("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andReturn().getResponse().getContentAsByteArray();

        List<UserDetailDto> userResult = objectMapper.readerFor(UserDetailDto.class).<UserDetailDto>readValues(body).readAll();

        assertThat(userResult.size()).isGreaterThanOrEqualTo(10);
        assertThat(userResult)
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
    @WithMockUser(username = "admin@example.com", password = "password", roles = "ADMIN")
    public void searchForEmailLikeDoeWithRoleAdminReturnsMin2Users() throws Exception {
        byte[] body = mockMvc
            .perform(MockMvcRequestBuilders
                .get("/api/v1/users?email=doe")
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andReturn().getResponse().getContentAsByteArray();

        List<UserDetailDto> userResult = objectMapper.readerFor(UserDetailDto.class).<UserDetailDto>readValues(body).readAll();

        assertThat(userResult.size()).isGreaterThanOrEqualTo(2);
        assertThat(userResult)
            .map(UserDetailDto::getEmail, UserDetailDto::getRole)
            .contains(tuple("john.doe@example.com", UserRole.ADMIN))
            .contains(tuple("jane.doe@example.com", UserRole.USER));
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin@example.com", password = "password", roles = "ADMIN")
    public void searchForEmailLikeDoeAndRoleAdminWithRoleAdminReturnsMin1User() throws Exception {
        byte[] body = mockMvc
            .perform(MockMvcRequestBuilders
                .get("/api/v1/users?email=doe&role=ADMIN")
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andReturn().getResponse().getContentAsByteArray();

        List<UserDetailDto> userResult = objectMapper.readerFor(UserDetailDto.class).<UserDetailDto>readValues(body).readAll();

        assertThat(userResult.size()).isGreaterThanOrEqualTo(1);
        assertThat(userResult)
            .map(UserDetailDto::getEmail, UserDetailDto::getRole)
            .contains(tuple("john.doe@example.com", UserRole.ADMIN))
            .doesNotContain(tuple("jane.doe@example.com", UserRole.USER));
    }


    @Test
    @Transactional
    @WithMockUser(username = "martina.musterfrau@example.com", password = "password", roles = "USER")
    public void searchWithoutParametersWithoutRoleAdminReturns403() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users")).andExpect(status().isForbidden());
        } catch (Exception e) {
            assertThat(e.getCause() instanceof AccessDeniedException);
        }
    }

    /* Tests for GET /users/{id} */

    @Test
    @Transactional
    @WithMockUser(username = "admin@example.com", password = "password", roles = "ADMIN")
    @Rollback
    public void getByIdReturnsUser() throws Exception {
        byte[] body = mockMvc
            .perform(MockMvcRequestBuilders
                .get("/api/v1/users/admin/3")
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andReturn().getResponse().getContentAsByteArray();

        List<UserDetailDto> userResult = objectMapper.readerFor(UserDetailDto.class).<UserDetailDto>readValues(body).readAll();
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin@example.com", password = "password", roles = "ADMIN")
    public void getUserByNonExistentIdReturns404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/admin/0")).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithMockUser(username = "martina.musterfrau@example.com", password = "password", roles = "USER")
    public void getByIdWithoutRoleAdminReturns403() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/5")).andExpect(status().isForbidden());
        } catch (Exception e) {
            assertThat(e.getCause() instanceof AccessDeniedException);
        }
    }


    /* Tests for PUT /users/{id} */

    @Test
    @Transactional
    @WithMockUser(username = "admin@example.com", password = "password", roles = "ADMIN")
    public void updateByAdminReturnsUser() throws Exception {
        byte[] body = mockMvc
            .perform(MockMvcRequestBuilders
                .patch("/api/v1/users/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"role\":\"ADMIN\",\"active\":true}")
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andReturn().getResponse().getContentAsByteArray();

        List<UserDetailDto> userResult = objectMapper.readerFor(UserDetailDto.class).<UserDetailDto>readValues(body).readAll();

        assertThat(userResult).isNotNull();
        assertThat(userResult.size()).isEqualTo(1);
        assertThat(userResult)
            .map(UserDetailDto::getId, UserDetailDto::getEmail, UserDetailDto::getRole, UserDetailDto::isActive)
            .contains(tuple(4L, "ola.nordmann@example.com", UserRole.ADMIN, true));

        mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/v1/users/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"role\":\"ADMIN\",\"active\":false}")
                .accept(MediaType.APPLICATION_JSON));
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin@example.com", password = "password", roles = "ADMIN")
    public void updateByAdminWithNonexistentIdReturns404() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/users/0").contentType(MediaType.APPLICATION_JSON)
                .content("{\"role\": \"ADMIN\"}")).andExpect(status().isNotFound());
        } catch (Exception e) {
            assertThat(e.getCause() instanceof NotFoundException);
        }
    }

    @Test
    @Transactional
    @WithMockUser(username = "martina.musterfrau@example.com", password = "password", roles = "USER")
    public void updateByAdminWithoutRoleAdminReturns403() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/users/3").contentType(MediaType.APPLICATION_JSON)
                .content("{\"role\": \"ADMIN\"}")).andExpect(status().isForbidden());
        } catch (Exception e) {
            assertThat(e.getCause() instanceof AccessDeniedException);
        }
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin@example.com", password = "password", roles = "ADMIN")
    public void checkIfEmailExistsOfAnUserAccount() throws Exception {
        byte[] body = mockMvc
            .perform(MockMvcRequestBuilders
                .get("/api/v1/users/checkemail?email=martina.musterfrau@example.com")
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andReturn().getResponse().getContentAsByteArray();

        List<UserResetPasswordDto> userResult = objectMapper.readerFor(UserResetPasswordDto.class).<UserResetPasswordDto>readValues(body).readAll();

        assertThat(userResult.size()).isEqualTo(1);
        assertThat(userResult.get(0).getEmail()).isEqualTo("martina.musterfrau@example.com");
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin@example.com", password = "password", roles = "ADMIN")
    public void resetPasswordOfAnExistingAccount() throws Exception {
        mockMvc
            .perform(MockMvcRequestBuilders
                .put("/api/v1/users/resetpassword")
                .content(asJsonString(new UserResetPasswordDto("martina.musterfrau@example.com")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());
    }

    public static String asJsonString(UserResetPasswordDto obj) {
        try {
            UserResetPasswordDto temp =
                new UserResetPasswordDto(obj.getEmail());
            return new ObjectMapper().writeValueAsString(temp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    @Transactional
    @WithMockUser(username = "martina.musterfrau@example.com", password = "password", roles = "USER")
    public void tryDeleteExistingUserWithWrongHeader() {
        try {
            mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/users/1")
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isForbidden());
        } catch (Exception e) {
            assertThat(e.getCause() instanceof AccessDeniedException);
        }
    }

    @Test
    @Transactional
    @WithMockUser(username = "martina.musterfrau@example.com", password = "password", roles = "USER")
    public void getProfileByOtherUserIdReturns403() throws Exception {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/profile/1")).andExpect(status().isForbidden());
        } catch (Exception e) {
            assertThat(e.getCause() instanceof AccessDeniedException);
        }
    }

    @Test
    @Transactional
    public void getProfileByOwnUserIdReturnsUserProfileDto() throws Exception {
        String auth = "Bearer " + jwtTokenizer.getAuthToken("1", "admin@email.com", new ArrayList<>(Arrays.asList("ROLE_ADMIN", "ROLE_USER")));
        byte[] body = mockMvc
            .perform(MockMvcRequestBuilders
                .get("/api/v1/users/profile/1")
                .header(HttpHeaders.AUTHORIZATION, auth)
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andReturn().getResponse().getContentAsByteArray();

        List<UserProfileDto> profileResult = objectMapper.readerFor(UserProfileDto.class).<UserProfileDto>readValues(body).readAll();

        assertThat(profileResult).isNotNull();
        assertThat(profileResult.size()).isEqualTo(1);

        UserProfileDto profile = profileResult.get(0);
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
            .contains(tuple(8L, LocalDateTime.parse("2022-12-16T14:50:00"), "Jingle Beans Holiday Blend", 4L, 25))
            .contains(tuple(1L, LocalDateTime.parse("2022-12-11T14:50:00"), "Espresso House Blend", 2L, 25))
            .contains(tuple(5L, LocalDateTime.parse("2022-12-15T14:50:00"), "Espresso House Blend", 2L, 23))
            .contains(tuple(13L, LocalDateTime.parse("2022-12-16T14:50:00"), "TIME & TEMPERATURE", 6L, 22))
            .contains(tuple(10L, LocalDateTime.parse("2022-12-16T14:50:00"), "West End Blues", 5L, 22));

        assertThat(profile.getTopRatedCoffees()).isNotNull();
        assertThat(profile.getTopRatedCoffees().length).isLessThanOrEqualTo(5);
        assertThat(profile.getTopRatedCoffees())
            .extracting(CoffeeBeanRatingListDto::getId, CoffeeBeanRatingListDto::getName, CoffeeBeanRatingListDto::getRating)
            .contains(tuple(2L, "Espresso House Blend", 16.83))
            .contains(tuple(4L, "Jingle Beans Holiday Blend", 16.0))
            .contains(tuple(6L, "TIME & TEMPERATURE", 15.0))
            .contains(tuple(5L, "West End Blues", 15.0));

        assertThat(profile.getTopMostExtractedCoffees()).isNotNull();
        assertThat(profile.getTopMostExtractedCoffees().length).isLessThanOrEqualTo(5);
        assertThat(profile.getTopMostExtractedCoffees())
            .extracting(CoffeeBeanExtractionsListDto::getId, CoffeeBeanExtractionsListDto::getName, CoffeeBeanExtractionsListDto::getNumExtractions)
            .contains(tuple(2L, "Espresso House Blend", 6))
            .contains(tuple(4L, "Jingle Beans Holiday Blend", 3))
            .contains(tuple(6L, "TIME & TEMPERATURE", 3))
            .contains(tuple(5L, "West End Blues", 3));
    }
}
