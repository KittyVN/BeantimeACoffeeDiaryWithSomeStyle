package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.assertj.core.api.Assertions.assertThat;
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
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        this.requestJson = new CoffeeBeanDto(null, null, null, null, null, null, null, null, null, null, null);
    }


    @Test
    @Transactional
    @Rollback
    public void createValidCoffee() throws Exception {
        requestJson.setName("TestBean");
        requestJson.setCoffeeRoast(CoffeeRoast.DARK);
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
        requestJson.setUserId(1L);
        //send request with valid parameters but invalid name
        sendInvalidCoffeeBeanCreateRequest(requestJson);
        requestJson.setName(null);
        //send request with valid parameters but invalid CoffeeRoast
        requestJson.setName("Test");
        requestJson.setCoffeeRoast(null);
        sendInvalidCoffeeBeanCreateRequest(requestJson);
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
        requestJson.setId(1L);
        requestJson.setUserId(1L);
        requestJson.setName("");
        requestJson.setCoffeeRoast(CoffeeRoast.LIGHT);
        //Send coffee bean with invalid name
        sendInvalidCoffeeBeanUpdateRequest(requestJson);
        //Send coffee bean with invalid roast
        requestJson.setName("ValidName");
        requestJson.setCoffeeRoast(null);
        sendInvalidCoffeeBeanUpdateRequest(requestJson);
        requestJson.setCoffeeRoast(CoffeeRoast.DARK);
        //Send coffee bean with invalid user
        requestJson.setUserId(-99L);
        sendInvalidCoffeeBeanUpdateRequest(requestJson);
    }

    private void sendInvalidCoffeeBeanUpdateRequest(CoffeeBeanDto requestBean) throws Exception {
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
    @Transactional
    public void deleteNonExistingCoffee() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/v1/coffee-beans/-9999")
        ).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void getCoffeeBeanByExistentIdReturnsCoffeeBeanDetail() throws Exception {
        byte[] body = mockMvc.perform(MockMvcRequestBuilders
            .get("/api/v1/coffee-beans/2")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsByteArray();

        CoffeeBeanDetailDto coffeeBeanResult = objectMapper.readerFor(CoffeeBeanDetailDto.class).<CoffeeBeanDetailDto>readValues(body).readAll().get(0);

        assertThat(coffeeBeanResult).isNotNull();
        assertThat(coffeeBeanResult.getId()).isEqualTo(2L);
        assertThat(coffeeBeanResult.getCoffeeBean()).isNotNull();
        assertThat(coffeeBeanResult.getCoffeeBean().getName()).isEqualTo("Espresso House Blend");
        assertThat(coffeeBeanResult.getCoffeeBean().getPrice()).isEqualTo(31.9F);
        assertThat(coffeeBeanResult.getCoffeeBean().getOrigin()).isEqualTo("Brasil, Colombia, Congo, Laos");
        assertThat(coffeeBeanResult.getCoffeeBean().getHeight()).isEqualTo(null);
        assertThat(coffeeBeanResult.getCoffeeBean().getCoffeeRoast()).isEqualTo(CoffeeRoast.DARK);
        assertThat(coffeeBeanResult.getCoffeeBean().getUserId()).isEqualTo(1L);
        assertThat(coffeeBeanResult.getAvgExtractionRating()).isNotNull();
        assertThat(coffeeBeanResult.getAvgExtractionRating().getId()).isEqualTo(2L);
        assertThat(coffeeBeanResult.getAvgExtractionRating().getId()).isEqualTo(2L);
        assertThat(coffeeBeanResult.getAvgExtractionRating().getAcidity()).isEqualTo(3.3333333333333335);
        assertThat(coffeeBeanResult.getAvgExtractionRating().getAftertaste()).isEqualTo(3.5);
        assertThat(coffeeBeanResult.getAvgExtractionRating().getAromatics()).isEqualTo(3.3333333333333335);
        assertThat(coffeeBeanResult.getAvgExtractionRating().getBody()).isEqualTo(2.8333333333333335);
        assertThat(coffeeBeanResult.getAvgExtractionRating().getSweetness()).isEqualTo(3.8333333333333335);
    }

    @Test
    @Transactional
    public void getCoffeeBeanByNonExistentIdReturns404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/coffee-beans/0")).andExpect(status().isNotFound());
    }
}
