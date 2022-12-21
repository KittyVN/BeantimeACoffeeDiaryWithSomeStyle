package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanAvgExtractionRating;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionCreateDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionDetailDto;

import java.util.stream.Stream;

public interface ExtractionService {
    /**
     * Get the average extraction evaluation parameters for a coffee bean.
     *
     * @param id of the coffee bean
     * @return a CoffeeBeanAvgExtractionEvaluationParams DTO
     */
    CoffeeBeanAvgExtractionRating getAvgExtractionEvaluationParamsByCoffeeBeanId(Long id);


    /**
     * Fetches all saved extractions, belonging to the bean, from the persistent data storage.
     *
     * @param id of the bean
     * @return a stream of the found extractions
     */
    Stream<ExtractionDetailDto> getAllByBeanId(Long id);

    /**
     * Creates a new extraction in storage.
     *
     * @param extractionCreateDto the extraction to create
     * @return the created extraction
     */
    ExtractionCreateDto create(ExtractionCreateDto extractionCreateDto);

}
