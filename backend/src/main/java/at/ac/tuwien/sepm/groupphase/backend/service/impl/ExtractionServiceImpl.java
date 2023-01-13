package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanAvgExtractionRating;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionCreateDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionDayStatsDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionMatrixDto;
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

import javax.persistence.Tuple;
import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
    public ExtractionDetailDto getById(Long id) {
        Optional<Extraction> extraction = extractionRepository.findById(id);
        if (!extraction.isPresent()) {
            throw new NotFoundException(String.format("No extraction with ID %d found", id));
        }
        return mapper.entityToDto(extraction.get());
    }

    @Override
    public ExtractionMatrixDto getExtractionMatrixByUserId(Long id) {
        List<Tuple> dayStatsTuples = extractionRepository.findDailyCountsForLast53WeeksByUserId(id);
        List<ExtractionDayStatsDto> dayStatsList = new ArrayList<>(dayStatsTuples
            .stream()
            .map(t -> new ExtractionDayStatsDto(
                t.get(0, Date.class).toLocalDate(),
                t.get(1, BigInteger.class).intValue(),
                t.get(2, Integer.class)
            ))
            .toList());

        int max = 0;
        int sumExtractions = 0;
        for (ExtractionDayStatsDto dayStat : dayStatsList) {
            sumExtractions += dayStat.getNumExtractions();
            if (dayStat.getNumExtractions() > max) {
                max = dayStat.getNumExtractions();
            }
        }

        for (ExtractionDayStatsDto dayStat : dayStatsList) {
            if (dayStat.getNumExtractions() > 0) {
                dayStat.setRelFrequency((int) ((double) dayStat.getNumExtractions() / (double) max * 3) + 1);
            }
        }

        List<LocalDate> dates = dayStatsList.stream().map(dto -> dto.getDate()).toList();
        LocalDate today = LocalDate.now();
        LocalDate start = today.minusDays(today.getDayOfWeek().getValue() - 1 + 52 * 7);

        while (start.isBefore(today) || start.isEqual(today)) {
            if (!dates.contains(start)) {
                dayStatsList.add(new ExtractionDayStatsDto(start, 0, 0));
            }

            start = start.plusDays(1);
        }

        Collections.sort(dayStatsList, Comparator.comparing(ExtractionDayStatsDto::getDate));

        return new ExtractionMatrixDto(sumExtractions,
            dayStatsList.toArray(new ExtractionDayStatsDto[dayStatsList.size()]));
    }
}
