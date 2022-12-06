package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CoffeeBeanRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.CoffeeBeanService;
import at.ac.tuwien.sepm.groupphase.backend.mapper.CoffeeBeanMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.validation.CoffeeBeanValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class CoffeeBeanServiceImpl implements CoffeeBeanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private CoffeeBeanRepository coffeeBeanRepository;
    private final CoffeeBeanMapper mapper;
    private final CoffeeBeanValidator validator;

    @Autowired
    public CoffeeBeanServiceImpl(CoffeeBeanRepository coffeeBeanRepository, CoffeeBeanMapper mapper, CoffeeBeanValidator validator) {
        this.coffeeBeanRepository = coffeeBeanRepository;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Override
    public CoffeeBeanDto create(CoffeeBeanDto coffeeBeanDto) throws ValidationException {
        LOGGER.trace("create {}", coffeeBeanDto);
        this.validator.validateForUpdate(coffeeBeanDto);
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
