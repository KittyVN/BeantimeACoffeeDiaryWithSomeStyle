package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.enums.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.file.AccessDeniedException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test", "generateData"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
@EnableWebMvc
@WebAppConfiguration
public class UserEndpointTest {
    @Autowired
    private WebApplicationContext webAppContext;
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @Test
    @WithMockUser(username="admin@example.com", password="password", roles="ADMIN")
    public void searchWithoutParametersWithRoleAdminReturnsAllUsers() throws Exception {
        byte[] body = mockMvc
            .perform(MockMvcRequestBuilders
                .get("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andReturn().getResponse().getContentAsByteArray();

        List<UserDto> userResult = objectMapper.readerFor(UserDto.class).<UserDto>readValues(body).readAll();

        assertThat(userResult.size()).isGreaterThanOrEqualTo(10);
        assertThat(userResult)
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
    @WithMockUser(username="admin@example.com", password="password", roles="ADMIN")
    public void searchForEmailLikeDoeWithRoleAdminReturnsMin2Users() throws Exception {
        byte[] body = mockMvc
            .perform(MockMvcRequestBuilders
                .get("/api/v1/users?email=doe")
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andReturn().getResponse().getContentAsByteArray();

        List<UserDto> userResult = objectMapper.readerFor(UserDto.class).<UserDto>readValues(body).readAll();

        assertThat(userResult.size()).isGreaterThanOrEqualTo(2);
        assertThat(userResult)
            .map(UserDto::email, UserDto::role)
            .contains(tuple("john.doe@example.com", UserRole.ADMIN))
            .contains(tuple("jane.doe@example.com", UserRole.USER));
    }

    @Test
    @WithMockUser(username="admin@example.com", password="password", roles="ADMIN")
    public void searchForEmailLikeDoeAndRoleAdminWithRoleAdminReturnsMin1User() throws Exception {
        byte[] body = mockMvc
            .perform(MockMvcRequestBuilders
                .get("/api/v1/users?email=doe&role=ADMIN")
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andReturn().getResponse().getContentAsByteArray();

        List<UserDto> userResult = objectMapper.readerFor(UserDto.class).<UserDto>readValues(body).readAll();

        assertThat(userResult.size()).isGreaterThanOrEqualTo(1);
        assertThat(userResult)
            .map(UserDto::email, UserDto::role)
            .contains(tuple("john.doe@example.com", UserRole.ADMIN))
            .doesNotContain(tuple("jane.doe@example.com", UserRole.USER));
    }

    @Test
    @WithMockUser(username="martina.musterfrau@example.com", password="password", roles="USER")
    public void searchWithoutParametersWithRoleAdminReturns403() {
        try {
            mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isForbidden());
        } catch (Exception e) {
            assertThat(e.getCause() instanceof AccessDeniedException);
        }
    }
}
