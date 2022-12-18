package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanAvgExtractionRating;

public interface ExtractionService {
    /**
     * Get the average extraction evaluation parameters for a coffee bean.
     *
     * @param id of the coffee bean
     * @return a CoffeeBeanAvgExtractionEvaluationParams DTO
     */
    CoffeeBeanAvgExtractionRating getAvgExtractionEvaluationParamsByCoffeeBeanId(Long id);
}
