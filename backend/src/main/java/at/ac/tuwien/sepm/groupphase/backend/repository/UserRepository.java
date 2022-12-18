package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    /**
     * Find a user by email.
     *
     * @param email the email of the user
     * @return the user with the given email
     */
    User findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update User u set u.password = :password WHERE u.email = :email")
    void updatePasswordByEmail(@Param("email") String email, @Param("password") String password);


    void deleteById(Long id);


    /**
     * Get a user by id.
     *
     * @param id the id of the user
     * @return the user with the given id
     */
    User findFirstById(Long id);
}

