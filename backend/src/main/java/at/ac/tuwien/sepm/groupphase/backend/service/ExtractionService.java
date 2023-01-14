package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanAvgExtractionRating;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionCreateDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionListDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionMatrixDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;

import java.util.List;
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

    /**
     * Fetches a saved extraction with the specific id from the persistent data storage.
     *
     * @param id of the extraction
     * @return the found extraction
     */
    ExtractionDetailDto getById(Long id) throws NotFoundException;

    /**
     * Fetches the extraction matrix dto for the specified user id.
     *
     * @param id of the user
     * @return an ExtractionMatrixDto containing the stats about the extraction of ca. the last 53 weeks.
     */
    ExtractionMatrixDto getExtractionMatrixByUserId(Long id);

    /**
     * Fetches the top 10 rated extractions for the specified user id.
     *
     * @param id of the user
     * @return a Stream of the found extractions represented as ExtractionListDto.
     */
    List<ExtractionListDto> getTop10RatedByUserId(Long id);
}
