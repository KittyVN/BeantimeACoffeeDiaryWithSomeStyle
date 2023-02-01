package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionCreateDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserLoginDto;
import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeGrindSetting;
import at.ac.tuwien.sepm.groupphase.backend.enums.ExtractionBrewMethod;
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
    public void getAllExtractionsByBeanIdReturnsAllExtractions() throws Exception {
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

    @Test
    @Transactional
    public void searchByBeanIdShouldReturnExpectedExtractions() throws Exception {
        String auth = performLogin("admin@email.com", "password");

        byte[] emptySearch = mockMvc
            .perform(MockMvcRequestBuilders
                .get("/api/v1/extractions/bean/search/2?grindSetting=FINE")
                .header(HttpHeaders.AUTHORIZATION, auth)
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andReturn().getResponse().getContentAsByteArray();

        List<ExtractionDetailDto> emptyResult = objectMapper.readerFor(ExtractionDetailDto.class).<ExtractionDetailDto>readValues(emptySearch).readAll();

        assertThat(emptyResult.size()).isGreaterThanOrEqualTo(0);

        byte[] search = mockMvc
            .perform(MockMvcRequestBuilders
                .get("/api/v1/extractions/bean/search/2?grindSetting=MEDIUM")
                .header(HttpHeaders.AUTHORIZATION, auth)
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andReturn().getResponse().getContentAsByteArray();

        List<ExtractionDetailDto> result = objectMapper.readerFor(ExtractionDetailDto.class).<ExtractionDetailDto>readValues(search).readAll();

        assertThat(result.size()).isGreaterThanOrEqualTo(6);

        assertThat(result)
            .map(ExtractionDetailDto::getId, ExtractionDetailDto::getDose, ExtractionDetailDto::getOverallRating)
            .contains(tuple(1L, 100.0, 25))
            .contains(tuple(2L, 100.0, 15))
            .contains(tuple(3L, 100.0, 15))
            .contains(tuple(4L, 100.0, 13))
            .contains(tuple(5L, 100.0, 23))
            .contains(tuple(6L, 100.0, 10));
    }

    @Test
    @Transactional
    public void searchByBeanIdWithWrongUserShouldReturn403() throws Exception {
        String auth = performLogin("olaf.olaf@example.com", "password");
        mockMvc.perform(MockMvcRequestBuilders
            .get("/api/v1/extractions/bean/search/2?grindSetting=MEDIUM")
            .header(HttpHeaders.AUTHORIZATION, auth)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void getExtractionByIdFromOwnCoffeeShouldWork() throws Exception {
        String auth = performLogin("admin@email.com", "password");
        byte[] body = mockMvc.perform(MockMvcRequestBuilders
            .get("/api/v1/extractions/1")
            .header(HttpHeaders.AUTHORIZATION, auth)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsByteArray();

        List<ExtractionDetailDto> result = objectMapper.readerFor(ExtractionDetailDto.class).<ExtractionDetailDto>readValues(body).readAll();

        assertThat(result.size()).isGreaterThanOrEqualTo(1);

        assertThat(result)
            .map(ExtractionDetailDto::getId, ExtractionDetailDto::getDose, ExtractionDetailDto::getOverallRating)
            .contains(tuple(1L, 100.0, 25));
    }

    @Test
    @Transactional
    public void getExtractionByIdFromCoffeeOfOtherUserShouldReturn403() throws Exception {
        String auth = performLogin("olaf.olaf@example.com", "password");
        mockMvc.perform(MockMvcRequestBuilders
            .get("/api/v1/extractions/1")
            .header(HttpHeaders.AUTHORIZATION, auth)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void createExtractionWithOwnBeanShouldWork() throws Exception {
        ExtractionCreateDto requestDto =
            new ExtractionCreateDto(
                null,
                ExtractionBrewMethod.V60,
                CoffeeGrindSetting.COARSE,
                60d,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                1L);

        String requestJson = objectMapper.writeValueAsString(requestDto);
        String auth = performLogin("admin@email.com", "password");
        byte [] body= mockMvc.perform(MockMvcRequestBuilders
            .post("/api/v1/extractions")
            .header(HttpHeaders.AUTHORIZATION, auth)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsByteArray();

        List<ExtractionCreateDto> result = objectMapper.readerFor(ExtractionCreateDto.class).<ExtractionCreateDto>readValues(body).readAll();

        assertThat(result.size()).isGreaterThanOrEqualTo(1);

        assertThat(result)
            .map(ExtractionCreateDto::getBrewMethod, ExtractionCreateDto::getGrindSetting, ExtractionCreateDto::getWaterTemperature)
            .contains(tuple(ExtractionBrewMethod.V60, CoffeeGrindSetting.COARSE, 60d));
    }

    @Test
    @Transactional
    public void createExtractionWithOtherUsersBeanShouldReturn403() throws Exception {
        ExtractionCreateDto requestDto =
            new ExtractionCreateDto(
                null,
                ExtractionBrewMethod.V60,
                CoffeeGrindSetting.COARSE,
                60d,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                1L);

        String requestJson = objectMapper.writeValueAsString(requestDto);
        String auth = performLogin("olaf.olaf@example.com", "password");
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/extractions")
                .header(HttpHeaders.AUTHORIZATION, auth)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void updatingExtractionShouldWork() throws Exception {
        ExtractionCreateDto requestDto =
            new ExtractionCreateDto(
                1L,
                ExtractionBrewMethod.V60,
                CoffeeGrindSetting.COARSE,
                60d,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                2L);

        String requestJson = objectMapper.writeValueAsString(requestDto);
        String auth = performLogin("admin@email.com", "password");
        byte [] body = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/extractions")
                .header(HttpHeaders.AUTHORIZATION, auth)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(status().isOk()).andReturn().getResponse().getContentAsByteArray();

        List<ExtractionCreateDto> result = objectMapper.readerFor(ExtractionCreateDto.class).<ExtractionCreateDto>readValues(body).readAll();

        assertThat(result.size()).isGreaterThanOrEqualTo(1);

        assertThat(result)
            .map(ExtractionCreateDto::getId, ExtractionCreateDto::getBrewMethod, ExtractionCreateDto::getGrindSetting, ExtractionCreateDto::getWaterTemperature)
            .contains(tuple(1L,ExtractionBrewMethod.V60, CoffeeGrindSetting.COARSE, 60d));
    }

    @Test
    @Transactional
    public void changingBeanOfAnExtractionShouldReturn409() throws Exception {
        ExtractionCreateDto requestDto =
            new ExtractionCreateDto(
                1L,
                ExtractionBrewMethod.V60,
                CoffeeGrindSetting.COARSE,
                60d,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                1L);

        String requestJson = objectMapper.writeValueAsString(requestDto);
        String auth = performLogin("admin@email.com", "password");
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/extractions")
                .header(HttpHeaders.AUTHORIZATION, auth)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(status().isConflict());
    }

    @Test
    @Transactional
    public void changingExtractionOfOtherUserShouldReturn403() throws Exception {
        ExtractionCreateDto requestDto =
            new ExtractionCreateDto(
                1L,
                ExtractionBrewMethod.V60,
                CoffeeGrindSetting.COARSE,
                60d,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                2L);

        String requestJson = objectMapper.writeValueAsString(requestDto);
        String auth = performLogin("olaf.olaf@example.com", "password");
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/extractions")
                .header(HttpHeaders.AUTHORIZATION, auth)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void deletingExtractionShouldWork() throws Exception {

        String auth = performLogin("admin@email.com", "password");
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/extractions/1")
                .header(HttpHeaders.AUTHORIZATION, auth)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void deletingExtractionOfOtherUserShouldReturn403() throws Exception {

        String auth = performLogin("olaf.olaf@example.com", "password");
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/extractions/1")
                .header(HttpHeaders.AUTHORIZATION, auth)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());
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
