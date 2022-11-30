package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Long> {
    /**
     * Find a user by email.
     *
     * @param email the email of the user
     * @return the user with the given email
     */
    ApplicationUser findByEmail(String email);
}

