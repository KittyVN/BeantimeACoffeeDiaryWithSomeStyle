package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanAvgExtractionRating;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionCreateDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionListDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionMatrixDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.ConflictException;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import org.springframework.web.server.ResponseStatusException;

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
     * Fetches all saved extractions matching the given search parameters,
     * belonging to the bean, from the persistent data storage.
     *
     * @param id of the bean
     * @param searchParams the parameters to search extractions by
     * @return a stream of the found extractions
     * @throws ResponseStatusException if bean by id doesnt exist
     */
    Stream<ExtractionDetailDto> searchByBeanId(ExtractionSearchDto searchParams, Long id)  throws ResponseStatusException;

    /**
     * Fetches all saved extractions, belonging to the bean, from the persistent data storage.
     *
     * @param id of the bean
     * @return a stream of the found extractions
     * @throws ResponseStatusException if bean by id doesnt exist
     */
    Stream<ExtractionDetailDto> getAllByBeanId(Long id) throws ResponseStatusException;

    /**
     * Fetches extraction from persistent data storage by its id.
     *
     * @param id of the extraction
     * @return the found extraction
     * @throws NotFoundException in case the extraction with the id is not found
     */
    ExtractionDetailDto getById(Long id) throws NotFoundException;

    /**
     * Creates a new extraction in storage.
     *
     * @param extractionCreateDto the extraction to create
     * @return the created extraction
     * @throws NotFoundException in case the bean with the id is not found
     */
    ExtractionCreateDto create(ExtractionCreateDto extractionCreateDto) throws NotFoundException;

    /**
     * Updates a persisted extraction in storage.
     *
     * @param extractionCreateDto the extraction with updated values
     * @return the updated extraction
     * @throws NotFoundException in case the extraction with the given id is not found
     * @throws ConflictException in case some external data conflicts with persisted data
     */
    ExtractionCreateDto update(ExtractionCreateDto extractionCreateDto) throws NotFoundException, ConflictException;


    /**
     * Fetches the extraction matrix dto for the specified user id.
     *
     * @param id of the user
     * @return an ExtractionMatrixDto containing the stats about the extraction of ca. the last 53 weeks.
     */
    ExtractionMatrixDto getExtractionMatrixByUserId(Long id);

    /**
     * Fetches the top 5 rated extractions for the specified user id.
     *
     * @param id of the user
     * @return a Stream of the found extractions represented as ExtractionListDto.
     */
    List<ExtractionListDto> getTop5RatedByUserId(Long id);
}
