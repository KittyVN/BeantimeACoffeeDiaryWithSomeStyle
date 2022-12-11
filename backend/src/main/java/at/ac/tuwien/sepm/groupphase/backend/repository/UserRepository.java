package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    /**
     * Find a user by email.
     *
     * @param email the email of the user
     * @return the user with the given email
     */
    User findByEmail(String email);

    /**
     * Get a user by id.
     * @param id the id of the user
     * @return the user with the given id
     */
    User findFirstById(Long id);
}

