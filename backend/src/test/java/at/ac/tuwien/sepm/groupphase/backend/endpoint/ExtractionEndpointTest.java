package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserLoginDto;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test", "generateData"})
// enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
@EnableWebMvc
@WebAppConfiguration
public class ExtractionEndpointTest {
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

    @Test
    @Transactional
    public void getAllExtractionsByUserIdReturnsAllExtractions() throws Exception {
        String auth = performLogin("admin@email.com", "password");
        byte[] body = mockMvc
            .perform(MockMvcRequestBuilders
                .get("/api/v1/extractions/bean/2")
                .header(HttpHeaders.AUTHORIZATION, auth)
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andReturn().getResponse().getContentAsByteArray();

        List<ExtractionDetailDto> userResult = objectMapper.readerFor(ExtractionDetailDto.class).<ExtractionDetailDto>readValues(body).readAll();

        assertThat(userResult.size()).isGreaterThanOrEqualTo(6);
        assertThat(userResult)
            .map(ExtractionDetailDto::getId, ExtractionDetailDto::getDose, ExtractionDetailDto::getOverallRating)
            .contains(tuple(1L, 100.0, 25))
            .contains(tuple(2L, 100.0, 15))
            .contains(tuple(3L, 100.0, 15))
            .contains(tuple(4L, 100.0, 13))
            .contains(tuple(5L, 100.0, 23))
            .contains(tuple(6L, 100.0, 10));
    }


    private String performLogin(String username, String password) throws Exception {
        UserLoginDto loginRequestDto = new UserLoginDto(username, password);
        String loginJson = objectMapper.writeValueAsString(loginRequestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
            .andExpect(status().isOk())
            .andReturn();

        return "Bearer " + result.getResponse().getContentAsString();
    }
}
