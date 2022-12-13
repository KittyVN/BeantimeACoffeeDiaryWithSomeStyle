package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;
import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles({"test", "generateData"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
@EnableWebMvc
@WebAppConfiguration
public class CoffeeBeanEndpointTest {

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
    public void createValidCoffee() throws Exception {
        CoffeeBean requestJson =
            CoffeeBean.CoffeeBeanBuilder
                .aCoffeeBean()
                .withName("test")
                .withCoffeeRoast(CoffeeRoast.DARK)
                .withCustom(true)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(requestJson);
        mockMvc.perform(MockMvcRequestBuilders
            .post("/api/v1/coffee-beans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(String.valueOf(jsonString))
            .characterEncoding("utf-8")
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void createInValidCoffee() throws Exception {
        CoffeeBean requestJson =
            CoffeeBean.CoffeeBeanBuilder
                .aCoffeeBean()
                .withName("")
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(requestJson);
        mockMvc.perform(MockMvcRequestBuilders
            .post("/api/v1/coffee-beans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(String.valueOf(jsonString))
            .characterEncoding("utf-8")
        ).andDo(print()).andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void editCoffeeToValid() throws Exception {
        CoffeeBean requestJson =
            CoffeeBean.CoffeeBeanBuilder
                .aCoffeeBean()
                .withId(1L)
                .withName("New name")
                .withCoffeeRoast(CoffeeRoast.LIGHT)
                .withCustom(true)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(requestJson);
        mockMvc.perform(MockMvcRequestBuilders
            .put("/api/v1/coffee-beans/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(String.valueOf(jsonString))
            .characterEncoding("utf-8")
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void editCoffeeToInvalid() throws Exception {
        CoffeeBean requestJson =
            CoffeeBean.CoffeeBeanBuilder
                .aCoffeeBean()
                .withId(1L)
                .withName("")
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(requestJson);
        mockMvc.perform(MockMvcRequestBuilders
            .put("/api/v1/coffee-beans/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(String.valueOf(jsonString))
            .characterEncoding("utf-8")
        ).andDo(print()).andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void deleteExistingCoffee() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/v1/coffee-beans/1")
        ).andExpect(status().isOk());
    }

    @Test
    public void deleteNonExistingCoffee() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/v1/coffee-beans/-9999")
        ).andExpect(status().isNotFound());
    }
}
