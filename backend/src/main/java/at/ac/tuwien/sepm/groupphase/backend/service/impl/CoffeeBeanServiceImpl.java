package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeBeanDashboardDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CoffeeBeanRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.CoffeeBeanService;
import at.ac.tuwien.sepm.groupphase.backend.mapper.CoffeeBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;
import java.util.stream.Stream;
import java.util.Optional;

@Service
public class CoffeeBeanServiceImpl implements CoffeeBeanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private CoffeeBeanRepository coffeeBeanRepository;

    private UserRepository userRepository;
    private final CoffeeBeanMapper mapper;

    @Autowired
    public CoffeeBeanServiceImpl(CoffeeBeanRepository coffeeBeanRepository, CoffeeBeanMapper mapper, UserRepository userRepository) {
        this.coffeeBeanRepository = coffeeBeanRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public Stream<CoffeBeanDashboardDto> getAll() {
        LOGGER.trace("getAll()");
        return coffeeBeanRepository.findAll().stream().map(coffeeBean -> mapper.entityToDashboardDto(coffeeBean));
    }

    @Override
    public CoffeeBeanDto create(CoffeeBeanDto coffeeBeanDto) {
        LOGGER.trace("create {}", coffeeBeanDto);
        User user = this.userRepository.findFirstById(coffeeBeanDto.getUserId());
        CoffeeBean coffeeBean = new CoffeeBean(coffeeBeanDto.getName(), coffeeBeanDto.getPrice(),
            coffeeBeanDto.getOrigin(), coffeeBeanDto.getHeight(), coffeeBeanDto.getCoffeeRoast(),
            coffeeBeanDto.getDescription(), coffeeBeanDto.getBeanBlend(), coffeeBeanDto.getUrlToCoffee(), user);
        return mapper.entityToDto(coffeeBeanRepository.save(coffeeBean));
    }

    @Override
    public CoffeeBeanDto update(CoffeeBeanDto coffeeBeanDto) {
        LOGGER.trace("update {}", coffeeBeanDto);
        Optional<CoffeeBean> coffeeBean = coffeeBeanRepository.findById(coffeeBeanDto.getId());
        if (!coffeeBean.isPresent()) {
            throw new NotFoundException(String.format("No coffee bean with ID %d found", coffeeBeanDto.getId()));
        } else {
            CoffeeBean newBean = coffeeBean.get();
            if (coffeeBeanDto.getUserId() != null) {
                if (newBean.getUser() == null) {
                    throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "This coffee bean has no creator");
                } else if (newBean.getUser().getId() != coffeeBeanDto.getUserId()) {
                    throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "User who created the bean cannot be changed");
                }
            } else {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "User who created the bean cannot be changed");
            }
            newBean.setName(coffeeBeanDto.getName());
            newBean.setPrice(coffeeBeanDto.getPrice());
            newBean.setOrigin(coffeeBeanDto.getOrigin());
            newBean.setHeight(coffeeBeanDto.getHeight());
            newBean.setCoffeeRoast(coffeeBeanDto.getCoffeeRoast());
            newBean.setDescription(coffeeBeanDto.getDescription());
            newBean.setBeanBlend(coffeeBeanDto.getBeanBlend());
            newBean.setUrlToCoffee(coffeeBeanDto.getUrlToCoffee());
            return mapper.entityToDto(coffeeBeanRepository.save(newBean));
        }
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
