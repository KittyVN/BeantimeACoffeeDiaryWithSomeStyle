package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;

import java.util.Collection;

public interface UserRepositoryCustom {
    /**
     * Search for users matching the criteria in {@code searchParameters}.
     * <p>
     *     A user is considered matched,
     *     if its id matches {@code searchParameters.id},
     *     if its email contains {@code searchParameters.email},
     *     if its role matches {@code searchParameters.role}.
     *     All parameters are optional.
     * </p>
     *
     * @param searchParameters object containing the search parameters to match
     * @return a collection containing users matching the criteria in {@code searchParameters}
     */
    Collection<User> search(UserDto searchParameters);
}
