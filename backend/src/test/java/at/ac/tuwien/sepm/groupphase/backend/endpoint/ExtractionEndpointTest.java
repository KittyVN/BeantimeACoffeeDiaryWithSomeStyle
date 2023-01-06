package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionDetailDto;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

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

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin@example.com", password = "password", roles = "ADMIN")
    public void getAllExtractionsByUserIdReturnsAllExtractions() throws Exception {
        byte[] body = mockMvc
            .perform(MockMvcRequestBuilders
                .get("/api/v1/extractions/bean/2")
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
}
