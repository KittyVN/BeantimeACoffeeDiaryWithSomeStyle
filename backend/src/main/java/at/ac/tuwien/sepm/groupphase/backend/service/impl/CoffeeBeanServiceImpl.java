package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDashboardDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;
import at.ac.tuwien.sepm.groupphase.backend.entity.Extraction;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.mapper.ExtractionMapper;
import at.ac.tuwien.sepm.groupphase.backend.repository.CoffeeBeanRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ExtractionRepository;
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
import java.util.List;
import java.util.stream.Stream;
import java.util.Optional;

@Service
public class CoffeeBeanServiceImpl implements CoffeeBeanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CoffeeBeanRepository coffeeBeanRepository;
    private final ExtractionRepository extractionRepository;
    private final UserRepository userRepository;
    private final CoffeeBeanMapper mapper;
    private final ExtractionMapper extractionMapper;

    @Autowired
    public CoffeeBeanServiceImpl(CoffeeBeanRepository coffeeBeanRepository, ExtractionRepository extractionRepository, CoffeeBeanMapper mapper, UserRepository userRepository, ExtractionMapper extractionMapper) {
        this.coffeeBeanRepository = coffeeBeanRepository;
        this.extractionRepository = extractionRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.extractionMapper = extractionMapper;
    }

    @Override
    public Stream<CoffeeBeanDashboardDto> getAllByUser(Long id) {
        LOGGER.trace("getAllByUser({})", id);
        return coffeeBeanRepository.findAllByUser(id).stream().map(mapper::entityToDashboardDto);
    }

    @Override
    public Stream<CoffeeBeanDashboardDto> search(CoffeeBeanSearchDto searchParams, Long id) {
        LOGGER.trace("search coffee beans with params: {}", searchParams);
        List<CoffeeBeanDashboardDto> coffees;
        if (userRepository.existsById(id)) {
            if (searchParams.isEmpty()) {
                coffees = getAllByUser(id).toList();
            } else {
                coffees = coffeeBeanRepository.search(searchParams, id).stream().map(mapper::entityToDashboardDto).toList();
            }
            for (int coffeeSize = 0; coffeeSize < coffees.size(); coffeeSize++) {
                List<ExtractionDetailDto> extractions;
                extractions = extractionRepository.findAllByBeanId(coffees.get(coffeeSize).getId()).stream().map(extraction -> extractionMapper.entityToDto(extraction)).toList();
                ExtractionDetailDto bestExtraction;
                ExtractionDetailDto lastExtraction;
                double averageRating = 0;
                if (!extractions.isEmpty()) {
                    bestExtraction = extractions.get(0);
                    lastExtraction = extractions.get(0);
                    for (int i = 0; i < extractions.size(); i++) {
                        if (extractions.get(i).getOverallRating() > bestExtraction.getOverallRating()) {
                            bestExtraction = extractions.get(i);
                        }
                        if (extractions.get(i).getDateTime().isAfter(lastExtraction.getDateTime())) {
                            lastExtraction = extractions.get(i);
                        }
                        averageRating += extractions.get(i).getOverallRating();
                    }
                    averageRating = averageRating / extractions.size();
                    coffees.get(coffeeSize).setBestExtraction(bestExtraction);
                    coffees.get(coffeeSize).setLastExtraction(lastExtraction);
                    coffees.get(coffeeSize).setOverallAverageRating((int) Math.round(averageRating));
                }
            }
            return coffees.stream();
        } else {
            throw new NotFoundException(String.format("No user with ID %d found", id));
        }
    }

    @Override
    public CoffeeBeanDto create(CoffeeBeanDto coffeeBeanDto) {
        LOGGER.trace("create {}", coffeeBeanDto);
        User user = this.userRepository.findFirstById(coffeeBeanDto.getUserId());
        CoffeeBean coffeeBean = CoffeeBean
            .CoffeeBeanBuilder
            .aCoffeeBean()
            .withName(coffeeBeanDto.getName())
            .withPrice(coffeeBeanDto.getPrice())
            .withOrigin(coffeeBeanDto.getOrigin())
            .withHeight(coffeeBeanDto.getHeight())
            .withCoffeeRoast(coffeeBeanDto.getCoffeeRoast())
            .withDescription(coffeeBeanDto.getDescription())
            .withCustom(coffeeBeanDto.getCustom())
            .withUser(user)
            .build();
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
