package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Extraction;

import java.util.Collection;

public interface ExtractionRepositoryCustom {
    /**
     * Search for extractions matching the criteria in {@code searchParameters}
     * belonging to the coffee with Id {@code id}.
     * <p>
     * An extraction is considered matched,
     * if it was created before {@code searchParameters.created},
     * OR after if {@code searchParameters.reverseCreated} is true,
     * if its overall score is above or equal to {@code searchParameters.overallRating},
     * OR lesser than if {@code searchParameters.reverseOverallRating} is true,
     * if its grind setting matches {@code searchParameters.grindSetting},
     * if its brew method matches {@code searchParameters.brewMethod}.
     * All parameters are optional.
     * </p>
     *
     * @param id id of the coffee the extractions are attached to
     * @param searchParameters object containing the search parameters to match
     * @return a collection containing extractions matching the criteria in {@code searchParameters}
     */
    Collection<Extraction> search(ExtractionSearchDto searchParameters, Long id);
}
