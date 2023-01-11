package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanAvgExtractionRating;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionCreateDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;

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
     * Fetches all saved extractions matching the given search parameters,
     * belonging to the bean, from the persistent data storage.
     *
     * @param id of the bean
     * @param searchParams the parameters to search extractions by
     * @return a stream of the found extractions
     */
    Stream<ExtractionDetailDto> searchByBeanId(ExtractionSearchDto searchParams, Long id);

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

    /**
     * Fetches a saved extraction with the specific id from the persistent data storage.
     *
     * @param id of the extraction
     * @return the found extraction
     */
    ExtractionDetailDto getById(Long id) throws NotFoundException;
}
