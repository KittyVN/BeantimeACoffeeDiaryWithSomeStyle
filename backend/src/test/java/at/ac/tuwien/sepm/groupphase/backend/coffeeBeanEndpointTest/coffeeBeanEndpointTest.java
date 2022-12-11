package at.ac.tuwien.sepm.groupphase.backend.coffeeBeanEndpointTest;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.CoffeeBeanEndpoint;
import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles({"test", "generateData"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
@EnableWebMvc
@WebAppConfiguration
public class coffeeBeanEndpointTest {

    @Autowired
    private WebApplicationContext webAppContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }


    @Autowired
    private CoffeeBeanEndpoint coffeeBeanEndpoint;

    @Test
    public void createValidCoffee() {
        CoffeeBeanDto testBean =
            new CoffeeBeanDto(
                null,
                "Test Coffee Bean",
                10.00f,
                "Mexico",
                3000,
                CoffeeRoast.DARK,
                "desc",
                true,
                null
            );
        CoffeeBeanDto storedBean = coffeeBeanEndpoint.create(testBean);
        assertThat(
            !(storedBean.id().equals(testBean.id())) && (storedBean.name().equals(testBean.name()))
        );
    }
    /**
    @Test
    public void createInValidCoffee() throws Exception {
        CoffeeBeanDto testBean =
            new CoffeeBeanDto(
                null,
                "",
                -2f,
                "",
                3000,
                null,
                "desc",
                true,
                null
            );
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(testBean);
        mockMvc.perform(MockMvcRequestBuilders
            .post("/api/v1/coffee-bean/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
        ).andExpect(status().isUnprocessableEntity());
    }**/

    @Test
    public void editCoffeeToValid() {
        CoffeeBeanDto testBean =
            new CoffeeBeanDto(
                null,
                "Test Coffee Bean",
                10.00f,
                "Mexico",
                3000,
                CoffeeRoast.DARK,
                "desc",
                true,
                null
            );
        CoffeeBeanDto storedBean = coffeeBeanEndpoint.create(testBean);
        CoffeeBeanDto editedBean = storedBean.withName("Test Coffee Bean edited name");
        CoffeeBeanDto result = coffeeBeanEndpoint.update(editedBean);
        assertThat((result.name().equals(editedBean.name())) && !(storedBean.id().equals(result.id())));
    }
}
