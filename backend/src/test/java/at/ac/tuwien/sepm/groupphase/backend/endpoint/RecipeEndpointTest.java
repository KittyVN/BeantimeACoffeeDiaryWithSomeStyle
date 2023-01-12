package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CommunityRecipeDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test", "generateData"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
@EnableWebMvc
@WebAppConfiguration
public class RecipeEndpointTest {

    @Autowired
    private WebApplicationContext webAppContext;
    private MockMvc mockMvc;
    private RecipeDto requestJson;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        this.requestJson = new RecipeDto(null, null, null);
    }

    @Autowired
    private RecipeEndpoint recipeEndpoint;

    @Test
    @Transactional
    @Rollback
    @WithMockUser(username = "admin@example.com", password = "password", roles = "ADMIN")
    public void createRecipeForExistingExtractionReturnsConflict() throws Exception {
        requestJson.setDescription("Test");
        requestJson.setExtractionId(5L);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(requestJson);
        String body = mockMvc
            .perform(MockMvcRequestBuilders
            .post("/api/v1/recipes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(String.valueOf(jsonString))
            .characterEncoding("utf-8")
        ).andDo(print()).andExpect(status().isConflict()).andReturn().getResponse().getErrorMessage();

        assertThat(body).isEqualTo("recipe for this extraction with ID 5 already exists");

    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(username = "admin@example.com", password = "password", roles = "ADMIN")
    public void createValidRecipeReturnsRecipe() throws Exception {
        requestJson.setDescription("Test");
        requestJson.setExtractionId(2L);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(requestJson);
        byte[] body = mockMvc
            .perform(MockMvcRequestBuilders
                .post("/api/v1/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(jsonString))
                .characterEncoding("utf-8")
            ).andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsByteArray();

        List<RecipeDto> result = objectMapper.readerFor(RecipeDto.class).<RecipeDto>readValues(body).readAll();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result)
            .map(RecipeDto::getDescription)
            .contains("Test");

    }

    @Test
    @WithMockUser(username = "admin@example.com", password = "password", roles = "ADMIN")
    public void getAllRecipesReturnsRecipes() throws Exception {
        byte[] body = mockMvc
            .perform(MockMvcRequestBuilders
                .get("/api/v1/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
            ).andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsByteArray();

        List<CommunityRecipeDto> result = objectMapper.readerFor(CommunityRecipeDto.class).<CommunityRecipeDto>readValues(body).readAll();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result)
            .map(CommunityRecipeDto::getCoffeeBeanDescription, CommunityRecipeDto::getExtractionAcidity, CommunityRecipeDto::getExtractionRatingNotes)
            .contains(tuple("Unser House Blend Espresso besteht aus 100% Arabica Bohnen und vereint die Herkunftsländer Brasilien, Kolumbien, Kongo, D.R. und Laos zu einem ausgewogenen und mittelkräftigen Espresso.", 4, "Wild"));
    }

    @Test
    @WithMockUser(username = "admin@example.com", password = "password", roles = "ADMIN")
    public void getSpecificRecipeReturnsRecipe() throws Exception {
        byte[] body = mockMvc
            .perform(MockMvcRequestBuilders
                .get("/api/v1/recipes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
            ).andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsByteArray();

        List<CommunityRecipeDto> result = objectMapper.readerFor(CommunityRecipeDto.class).<CommunityRecipeDto>readValues(body).readAll();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result)
            .map(CommunityRecipeDto::getCoffeeBeanDescription, CommunityRecipeDto::getExtractionAcidity, CommunityRecipeDto::getExtractionRatingNotes)
            .contains(tuple("Unser House Blend Espresso besteht aus 100% Arabica Bohnen und vereint die Herkunftsländer Brasilien, Kolumbien, Kongo, D.R. und Laos zu einem ausgewogenen und mittelkräftigen Espresso.", 4, "Wild"));
    }
}
