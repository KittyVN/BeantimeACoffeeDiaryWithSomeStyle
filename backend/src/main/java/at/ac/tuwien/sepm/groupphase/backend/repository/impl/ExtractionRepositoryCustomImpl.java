package at.ac.tuwien.sepm.groupphase.backend.repository.impl;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;
import at.ac.tuwien.sepm.groupphase.backend.entity.Extraction;
import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeGrindSetting;
import at.ac.tuwien.sepm.groupphase.backend.enums.ExtractionBrewMethod;
import at.ac.tuwien.sepm.groupphase.backend.repository.ExtractionRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExtractionRepositoryCustomImpl implements ExtractionRepositoryCustom {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Collection<Extraction> search(ExtractionSearchDto searchParameters, Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Extraction> query = cb.createQuery(Extraction.class);
        Root<Extraction> extraction = query.from(Extraction.class);
        Join<Extraction, CoffeeBean> bean = extraction.join("coffeeBean", JoinType.INNER);

        Path<Long> idPath = bean.get("id");
        Path<LocalDateTime> createdPath = extraction.get("extractionDate");
        Path<Integer> bodyPath = extraction.get("body");
        Path<Integer> acidityPath = extraction.get("acidity");
        Path<Integer> aromaticsPath = extraction.get("aromatics");
        Path<Integer> sweetnessPath = extraction.get("sweetness");
        Path<Integer> aftertastePath = extraction.get("aftertaste");
        Expression<Integer> overallRating = cb.sum(bodyPath,
            cb.sum(acidityPath,
            cb.sum(sweetnessPath,
            cb.sum(aromaticsPath,
            aftertastePath))));
        Path<CoffeeGrindSetting> grindSettingPath = extraction.get("grindSetting");
        Path<ExtractionBrewMethod> brewMethodPath = extraction.get("brewMethod");

        List<Predicate> predicates = new ArrayList<>();

        if (id != null) {
            predicates.add(cb.equal(idPath, id));
        }

        if (searchParameters.getCreated() != null) {
            if (searchParameters.isReverseDate()) {
                predicates.add(cb.lessThan(createdPath, searchParameters.getCreated().atStartOfDay()));
            } else {
                predicates.add(cb.greaterThanOrEqualTo(createdPath, searchParameters.getCreated().atStartOfDay()));
            }
        }

        if (searchParameters.getOverallRating() != null) {
            if (searchParameters.isReverseOverallRating()) {
                predicates.add(cb.lessThan(overallRating, searchParameters.getOverallRating()));
            } else {
                predicates.add(cb.greaterThanOrEqualTo(overallRating, searchParameters.getOverallRating()));
            }
        }

        if (searchParameters.getGrindSetting() != null) {
            predicates.add(cb.equal(grindSettingPath, searchParameters.getGrindSetting()));
        }

        if (searchParameters.getBrewMethod() != null) {
            predicates.add(cb.equal(brewMethodPath, searchParameters.getBrewMethod()));
        }

        query.select(extraction).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(query).getResultList();
    }
}
