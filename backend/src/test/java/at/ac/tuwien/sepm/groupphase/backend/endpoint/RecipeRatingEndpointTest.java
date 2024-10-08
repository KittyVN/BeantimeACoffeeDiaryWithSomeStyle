package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeRatingListDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.AuthorizationException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ConflictException;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test", "generateData"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
@EnableWebMvc
@WebAppConfiguration
public class RecipeRatingEndpointTest {
    @Autowired
    private WebApplicationContext webAppContext;
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    RecipeRatingEndpoint endpoint;
    @Autowired
    JwtTokenizer jwtTokenizer;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).apply(springSecurity()).build();
    }

    /* GET tests */
    @Test
    @Transactional
    @WithMockUser(username = "admin@example.com", password = "password", roles = "ADMIN")
    public void getByExistentRatingIdReturnsStream() throws Exception {
        byte[] body = mockMvc
            .perform(MockMvcRequestBuilders
                .get("/api/v1/recipes/1/ratings")
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andReturn().getResponse().getContentAsByteArray();

        List<RecipeRatingListDto> ratingResult = objectMapper.readerFor(RecipeRatingListDto.class).<RecipeRatingListDto>readValues(body).readAll();

        assertThat(ratingResult).isNotNull();
        assertThat(ratingResult.size()).isEqualTo(6);
        assertThat(ratingResult)
            .map(RecipeRatingListDto::getId, RecipeRatingListDto::getRecipeId, RecipeRatingListDto::getAuthorId,
                RecipeRatingListDto::getAuthorUsername, RecipeRatingListDto::getRating, RecipeRatingListDto::getText)
            .contains(tuple(1L, 1L, 2L, "JohnD", 4, null))
            .contains(tuple(2L, 1L, 4L, "Südfrau", 5, "Delicious!"))
            .contains(tuple(3L, 1L, 6L, "Jane", 3, "Tried it for the first time and found out it's not good and not bad."))
            .contains(tuple(4L, 1L, 8L, "Olaf*2", 4, null))
            .contains(tuple(5L, 1L, 7L, "Jan", 1, "Seldom have I ever drank something so disgusting!"))
            .contains(tuple(6L, 1L, 3L, "Musterf", 5, null));
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin@example.com", password = "password", roles = "ADMIN")
    public void getByNonExistentRatingIdReturnsEmptyStream() throws Exception {
        byte[] body = mockMvc
            .perform(MockMvcRequestBuilders
                .get("/api/v1/recipes/0/ratings")
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andReturn().getResponse().getContentAsByteArray();

        List<RecipeRatingListDto> ratingResult = objectMapper.readerFor(RecipeRatingListDto.class).<RecipeRatingListDto>readValues(body).readAll();

        assertThat(ratingResult).isNotNull();
        assertThat(ratingResult.size()).isEqualTo(0);
    }

    /* POST tests */
    @Test
    @Transactional
    @WithMockUser(username = "john.doe@example.com", password = "password", roles = "ADMIN")
    public void createRatingWithSameRecipeIdAsInURLAndUserThatIsNotAuthorReturnsDetailDto() throws Exception {
        String auth = "Bearer " + jwtTokenizer.getAuthToken("2", "john.doe@example.com", new ArrayList<>(Arrays.asList("ROLE_ADMIN", "ROLE_USER")));
        byte[] body = mockMvc
            .perform(MockMvcRequestBuilders
                .post("/api/v1/recipes/1/ratings")
                .header(HttpHeaders.AUTHORIZATION, auth)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"recipeId\": 1, \"authorId\": 2, \"rating\": 5}")
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated())
            .andReturn().getResponse().getContentAsByteArray();

        List<RecipeRatingListDto> ratingResult = objectMapper.readerFor(RecipeRatingListDto.class).<RecipeRatingListDto>readValues(body).readAll();

        assertThat(ratingResult).isNotNull();
        assertThat(ratingResult.size()).isEqualTo(1);
        assertThat(ratingResult)
            .map(RecipeRatingListDto::getId, RecipeRatingListDto::getRecipeId, RecipeRatingListDto::getAuthorId,
                RecipeRatingListDto::getAuthorUsername, RecipeRatingListDto::getRating, RecipeRatingListDto::getText)
            .contains(tuple(7L, 1L, 2L, "JohnD", 5, null));
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin@email.com", password = "password", roles = "ADMIN")
    public void createRatingWithSameRecipeIdAsInURLAndUserThatIsAuthorReturns409() {
        String auth = "Bearer " + jwtTokenizer.getAuthToken("1", "admin@email.com", new ArrayList<>(Arrays.asList("ROLE_ADMIN", "ROLE_USER")));
        try {
            byte[] body = mockMvc
                .perform(MockMvcRequestBuilders
                    .post("/api/v1/recipes/1/ratings")
                    .header(HttpHeaders.AUTHORIZATION, auth)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"recipeId\": 1, \"authorId\": 1, \"rating\": 5}")
                    .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isConflict())
                .andReturn().getResponse().getContentAsByteArray();
        } catch (Exception e) {
            assertThat(e.getCause() instanceof ConflictException);
        }
    }

    @Test
    @Transactional
    @WithMockUser(username = "john.doe@example.com", password = "password", roles = "ADMIN")
    public void createRatingWithDifferentRecipeIdAsInURLAndUserThatIsNotAuthorReturns403() {
        String auth = "Bearer " + jwtTokenizer.getAuthToken("2", "john.doe@example.com", new ArrayList<>(Arrays.asList("ROLE_ADMIN", "ROLE_USER")));
        try {
            byte[] body = mockMvc
                .perform(MockMvcRequestBuilders
                    .post("/api/v1/recipes/1/ratings")
                    .header(HttpHeaders.AUTHORIZATION, auth)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"recipeId\": 1, \"authorId\": 1, \"rating\": 5}")
                    .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isConflict())
                .andReturn().getResponse().getContentAsByteArray();
        } catch (Exception e) {
            assertThat(e.getCause() instanceof AuthorizationException);
        }
    }

    /* DELETE tests */
    @Test
    @Transactional
    @Rollback
    public void deleteRatingByExistentIdByAuthorReturns204() throws Exception {
        String auth = "Bearer " + jwtTokenizer.getAuthToken("1", "admin@email.com", new ArrayList<>(Arrays.asList("ROLE_ADMIN", "ROLE_USER")));
        mockMvc
            .perform(MockMvcRequestBuilders
                .delete("/api/v1/recipes/1/ratings/1")
                .header(HttpHeaders.AUTHORIZATION, auth)
            ).andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteRatingByNonExistentIdReturns404() throws Exception {
        String auth = "Bearer " + jwtTokenizer.getAuthToken("1", "admin@email.com", new ArrayList<>(Arrays.asList("ROLE_ADMIN", "ROLE_USER")));
        mockMvc
            .perform(MockMvcRequestBuilders
                .delete("/api/v1/recipes/1/ratings/0")
                .header(HttpHeaders.AUTHORIZATION, auth)
            ).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteRatingByExistentIdByNotAuthorReturns403() throws Exception {
        String auth = "Bearer " + jwtTokenizer.getAuthToken("3", "martina.musterfrau@example.com", new ArrayList<>(Arrays.asList("ROLE_USER")));
        mockMvc
            .perform(MockMvcRequestBuilders
                .delete("/api/v1/recipes/1/ratings/1")
                .header(HttpHeaders.AUTHORIZATION, auth)
            ).andExpect(status().isForbidden());
    }
}
