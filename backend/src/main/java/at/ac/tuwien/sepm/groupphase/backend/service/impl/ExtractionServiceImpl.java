package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanAvgExtractionRating;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionCreateDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;
import at.ac.tuwien.sepm.groupphase.backend.entity.Extraction;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.mapper.ExtractionMapper;
import at.ac.tuwien.sepm.groupphase.backend.repository.CoffeeBeanRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ExtractionRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.ExtractionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ExtractionServiceImpl implements ExtractionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ExtractionRepository extractionRepository;

    private final CoffeeBeanRepository coffeeBeanRepository;
    private final ExtractionMapper mapper;


    @Autowired
    public ExtractionServiceImpl(ExtractionRepository extractionRepository, ExtractionMapper mapper, CoffeeBeanRepository coffeeBeanRepository) {
        this.extractionRepository = extractionRepository;
        this.coffeeBeanRepository = coffeeBeanRepository;
        this.mapper = mapper;
    }

    @Override
    public CoffeeBeanAvgExtractionRating getAvgExtractionEvaluationParamsByCoffeeBeanId(Long id) {
        CoffeeBeanAvgExtractionRating avgRating = this.extractionRepository.findAvgExtractionRatingByCoffeeBeanId(id);

        if (avgRating == null) {
            return new CoffeeBeanAvgExtractionRating(id, 0D, 0D, 0D, 0D, 0D);
        } else {
            return avgRating;
        }
    }

    @Override
    public Stream<ExtractionDetailDto> getAllByBeanId(Long id) {
        LOGGER.trace("getAllByBeanId({})", id);
        if (coffeeBeanRepository.existsById(id)) {
            return extractionRepository.findAllByBeanId(id).stream().map(extraction -> mapper.entityToDto(extraction));
        } else {
            throw new NotFoundException(String.format("No user with ID %d found", id));
        }
    }

    @Override
    public ExtractionCreateDto create(ExtractionCreateDto extractionCreateDto) {
        Optional<CoffeeBean> coffeeBean = coffeeBeanRepository.findById(extractionCreateDto.getBeanId());
        Extraction extraction = new Extraction(LocalDateTime.now(), extractionCreateDto.getBrewMethod(), extractionCreateDto.getGrindSetting(),
            extractionCreateDto.getWaterTemperature(), extractionCreateDto.getDose(), extractionCreateDto.getWaterAmount(), extractionCreateDto.getBrewTime(),
            extractionCreateDto.getBody(), extractionCreateDto.getAcidity(), extractionCreateDto.getSweetness(), extractionCreateDto.getAromatics(),
            extractionCreateDto.getAftertaste(), extractionCreateDto.getRatingNotes(), coffeeBean.get());
        return mapper.entityToCreateDto(extractionRepository.save(extraction));
    }

    @Override
    public ExtractionDetailDto getById(Long id) throws NotFoundException {
        Optional<Extraction> extraction = extractionRepository.findById(id);
        if (!extraction.isPresent()) {
            throw new NotFoundException();
        }
        return mapper.entityToDto(extraction.get());
    }
}
