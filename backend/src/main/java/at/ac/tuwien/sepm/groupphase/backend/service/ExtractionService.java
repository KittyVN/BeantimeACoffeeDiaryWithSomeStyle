package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanAvgExtractionRating;
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
     * Fetches all saved extractions from the persistent data storage.
     *
     * @return a stream of the found extractions
     */
    Stream<ExtractionDetailDto> getAll();
}
