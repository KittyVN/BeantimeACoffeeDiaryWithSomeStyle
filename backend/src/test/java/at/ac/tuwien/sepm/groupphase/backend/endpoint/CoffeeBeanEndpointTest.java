package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


import javax.transaction.Transactional;

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

    private CoffeeBeanDto requestJson;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        this.requestJson = new CoffeeBeanDto(null, null, null, null, null, null, null, null, null);
    }


    @Autowired
    private CoffeeBeanEndpoint coffeeBeanEndpoint;

    @Test
    @Transactional
    @Rollback
    public void createValidCoffee() throws Exception {
        requestJson.setName("TestBean");
        requestJson.setCoffeeRoast(CoffeeRoast.DARK);
        requestJson.setCustom(true);
        requestJson.setUserId(1L);
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
        requestJson.setName("");
        requestJson.setCoffeeRoast(CoffeeRoast.DARK);
        requestJson.setCustom(true);
        requestJson.setUserId(1L);
        //send request with valid parameters but invalid name
        sendInvalidCoffeeBeanCreateRequest(requestJson);
        requestJson.setName(null);
        //send request with valid parameters but invalid CoffeeRoast
        requestJson.setName("Test");
        requestJson.setCoffeeRoast(null);
        sendInvalidCoffeeBeanCreateRequest(requestJson);
        //send request with valid parameters but invalid custom boolean
        requestJson.setCoffeeRoast(CoffeeRoast.DARK);
        requestJson.setCustom(null);
        sendInvalidCoffeeBeanCreateRequest(requestJson);
        //send request with valid parameters but invalid user
        requestJson.setCustom(true);
        requestJson.setUserId(-99L);

    }

    private void sendInvalidCoffeeBeanCreateRequest(CoffeeBeanDto requestBean) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(requestBean);
        mockMvc.perform(MockMvcRequestBuilders
            .post("/api/v1/coffee-beans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(String.valueOf(jsonString))
            .characterEncoding("utf-8")
        ).andDo(print()).andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional
    @Rollback
    public void editCoffeeToValid() throws Exception {
        requestJson.setName("Test");
        requestJson.setId(1L);
        requestJson.setCoffeeRoast(CoffeeRoast.DARK);
        requestJson.setCustom(true);
        requestJson.setUserId(1L);
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
        CoffeeBean requestBean =
            CoffeeBean.CoffeeBeanBuilder
                .aCoffeeBean()
                .withId(1L)
                .withName("")
                .build();
        //Send coffee bean with invalid name
        sendInvalidCoffeeBeanCreateRequest(requestBean);
        //Send coffee bean with invalid roast
        requestBean.setName("Test");
        requestBean.setCoffeeRoast(null);
        sendInvalidCoffeeBeanCreateRequest(requestBean);
        //Send coffee bean with invalid custom boolean
        requestBean.setCoffeeRoast(CoffeeRoast.DARK);
        requestBean.setCustom(null);
        sendInvalidCoffeeBeanCreateRequest(requestBean);
    }

    private void sendInvalidCoffeeBeanUpdateRequest(CoffeeBean requestBean) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(requestBean);
        mockMvc.perform(MockMvcRequestBuilders
            .put("/api/v1/coffee-beans/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(String.valueOf(jsonString))
            .characterEncoding("utf-8")
        ).andDo(print()).andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteExistingCoffee() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/v1/coffee-beans/1")
        ).andExpect(status().isOk());
    }

    @Test
    public void deleteNonExistingCoffee() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/v1/coffee-beans/-9999")
        ).andExpect(status().isNotFound());
    }
}
