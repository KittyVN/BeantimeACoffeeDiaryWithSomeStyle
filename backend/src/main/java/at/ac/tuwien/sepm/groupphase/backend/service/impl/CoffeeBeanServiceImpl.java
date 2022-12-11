package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeBeanDashboardDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CoffeeBeanRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.CoffeeBeanService;
import at.ac.tuwien.sepm.groupphase.backend.mapper.CoffeeBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.stream.Stream;
import java.util.Optional;

@Service
public class CoffeeBeanServiceImpl implements CoffeeBeanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private CoffeeBeanRepository coffeeBeanRepository;
    private final CoffeeBeanMapper mapper;

    @Autowired
    public CoffeeBeanServiceImpl(CoffeeBeanRepository coffeeBeanRepository, CoffeeBeanMapper mapper) {
        this.coffeeBeanRepository = coffeeBeanRepository;
        this.mapper = mapper;
    }

    @Override
    public Stream<CoffeBeanDashboardDto> getAll(){
        LOGGER.trace("getAll()");
        return coffeeBeanRepository.findAll().stream().map(coffeeBean -> mapper.entityToDashboardDto(coffeeBean));
    }

    @Override
    public CoffeeBeanDto create(CoffeeBeanDto coffeeBeanDto) {
        LOGGER.trace("create {}", coffeeBeanDto);

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

    @Override
    public CoffeeBeanDto update(CoffeeBeanDto coffeeBeanDto) {
        LOGGER.trace("update {}", coffeeBeanDto);
        CoffeeBean coffeeBean = CoffeeBean
            .CoffeeBeanBuilder
            .aCoffeeBean()
            .withId(coffeeBeanDto.id())
            .withName(coffeeBeanDto.name())
            .withPrice(coffeeBeanDto.price())
            .withOrigin(coffeeBeanDto.origin())
            .withHeight(coffeeBeanDto.height())
            .withCoffeeRoast(coffeeBeanDto.coffeeRoast())
            .withDescription(coffeeBeanDto.description())
            .withCustom(coffeeBeanDto.custom())
            .withUserId(coffeeBeanDto.userId())
            .build();
        return mapper.entityToDto(coffeeBeanRepository.save(coffeeBean));
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        coffeeBeanRepository.deleteById(id);
    }

    @Override
    public CoffeeBeanDto getById(Long id) throws NotFoundException {
        Optional<CoffeeBean> coffeeBean = coffeeBeanRepository.findById(id);
        if (!coffeeBean.isPresent()) {
            throw new NotFoundException();
        }
        return mapper.entityToDto(coffeeBean.get());
    }


}
