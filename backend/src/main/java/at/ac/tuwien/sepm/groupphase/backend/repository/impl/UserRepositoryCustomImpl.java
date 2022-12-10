package at.ac.tuwien.sepm.groupphase.backend.repository.impl;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.enums.UserRole;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Collection<User> search(UserDto searchParameters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> user = query.from(User.class);

        Path<Long> idPath = user.get("id");
        Path<String> emailPath = user.get("email");
        Path<UserRole> rolePath = user.get("role");

        List<Predicate> predicates = new ArrayList<>();

        if (searchParameters.id() != null) {
            predicates.add(cb.equal(idPath, searchParameters.id()));
        }

        if (searchParameters.email() != null) {
            predicates.add(cb.like(cb.upper(emailPath), String.format("%%%s%%", searchParameters.email().toUpperCase())));
        }

        if (searchParameters.role() != null) {
            predicates.add(cb.equal(rolePath, searchParameters.role()));
        }

        query.select(user).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(query).getResultList();
    }
}
