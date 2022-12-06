package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.service.CoffeeBeanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;


@RestController
@RequestMapping(value = "/custom-bean")
public class CustomCoffeeBeanEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CoffeeBeanService coffeeBeanService;

    public CustomCoffeeBeanEndpoint(CoffeeBeanService coffeeBeanService) {
        this.coffeeBeanService = coffeeBeanService;
    }

    @PostMapping
    public CoffeeBeanDto create(@RequestBody CoffeeBeanDto coffeeBeanDto) {
        LOGGER.info("create {}", coffeeBeanDto);
        return coffeeBeanService.create(coffeeBeanDto);
    }

}
