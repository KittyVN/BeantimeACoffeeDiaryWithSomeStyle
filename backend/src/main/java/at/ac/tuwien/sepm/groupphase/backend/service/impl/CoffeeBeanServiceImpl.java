package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;
import at.ac.tuwien.sepm.groupphase.backend.repository.CoffeeBeanRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.CoffeeBeanService;
import at.ac.tuwien.sepm.groupphase.backend.service.mapper.CoffeeBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class CoffeeBeanServiceImpl implements CoffeeBeanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private CoffeeBeanRepository coffeeBeanRepository;
    private static final CoffeeBeanMapper mapper = new CoffeeBeanMapper();

    @Autowired
    public CoffeeBeanServiceImpl(CoffeeBeanRepository coffeeBeanRepository) {
        this.coffeeBeanRepository = coffeeBeanRepository;
    }

    @Override
    public CoffeeBeanDto create(CoffeeBeanDto coffeeBeanDto) {
        CoffeeBean coffeeBean = CoffeeBean
            .CoffeeBeanBuilder
            .aCoffeeBean()
            .withName(coffeeBeanDto.name())
            .withPrice(coffeeBeanDto.price())
            .withOrigin(coffeeBeanDto.origin())
            .withHeight(coffeeBeanDto.height())
            .withCoffeeRoast(coffeeBeanDto.coffeeRoast())
            .withDescription(coffeeBeanDto.description())
            .withCustom(coffeeBeanDto.custom())
            .build();
        return mapper.entityToDto(coffeeBeanRepository.save(coffeeBean));
    }
}
