package at.ac.tuwien.sepm.groupphase.backend.repository.impl;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;
import at.ac.tuwien.sepm.groupphase.backend.repository.CoffeeBeanRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Join;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CoffeeBeanRepositoryCustomImpl implements CoffeeBeanRepositoryCustom {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Collection<CoffeeBean> search(CoffeeBeanSearchDto searchParameters, Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CoffeeBean> query = cb.createQuery(CoffeeBean.class);
        Root<CoffeeBean> bean = query.from(CoffeeBean.class);
        Join<CoffeeBean,User> user = bean.join("user", JoinType.INNER);

        Path<Long> idPath = user.get("id");
        Path<String> namePath = bean.get("name");
        Path<String> descriptionPath = bean.get("description");
        Path<CoffeeRoast> coffeeRoastPath = bean.get("coffeeRoast");

        List<Predicate> predicates = new ArrayList<>();

        if (id != null) {
            predicates.add(cb.equal(idPath, id));
        }

        if (searchParameters.getName() != null) {
            predicates.add(cb.like(cb.upper(namePath), String.format("%%%s%%", searchParameters.getName().toUpperCase())));
        }

        if (searchParameters.getDescription() != null) {
            predicates.add(cb.like(cb.upper(descriptionPath), String.format("%%%s%%", searchParameters.getDescription().toUpperCase())));
        }

        if (searchParameters.getCoffeeRoast() != null) {
            predicates.add(cb.equal(coffeeRoastPath, searchParameters.getCoffeeRoast()));
        }

        query.select(bean).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(query).getResultList();
    }
}
