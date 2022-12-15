package at.ac.tuwien.sepm.groupphase.backend.repository.impl;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.enums.UserRole;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Collection<User> search(UserSearchDto searchParameters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> user = query.from(User.class);

        Path<Long> idPath = user.get("id");
        Path<String> emailPath = user.get("email");
        Path<UserRole> rolePath = user.get("role");
        Path<Boolean> activePath = user.get("active");

        List<Predicate> predicates = new ArrayList<>();

        if (searchParameters.getId() != null) {
            predicates.add(cb.equal(idPath, searchParameters.getId()));
        }

        if (searchParameters.getEmail() != null) {
            predicates.add(cb.like(cb.upper(emailPath), String.format("%%%s%%", searchParameters.getEmail().toUpperCase())));
        }

        if (searchParameters.getRole() != null) {
            predicates.add(cb.equal(rolePath, searchParameters.getRole()));
        }

        if (searchParameters.getActive() != null) {
            predicates.add(cb.equal(activePath, searchParameters.getActive()));
        }

        query.select(user).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(query).getResultList();
    }
}
