package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.Collection;
import java.util.List;

@Repository
public interface CoffeeBeanRepository extends JpaRepository<CoffeeBean, Long>, CoffeeBeanRepositoryCustom {
    @Modifying
    @Transactional
    @Query("delete from CoffeeBean c where c.user.id = :id")
    void deleteCoffeeBeanByUserId(@Param("id") Long id);

    @Query("select c from CoffeeBean c where c.user.id = :id")
    Collection<CoffeeBean> findAllByUser(@Param("id") Long id);

    /**
     * Find the 10 top most extracted coffees of a specific user.
     *
     * @param id of the user
     * @return a List of Tuples
     */
    @Query(value = "SELECT b.ID, b.NAME, COUNT(e.ID) AS numExtractions FROM COFFEE_EXTRACTION e "
        + "JOIN COFFEE_BEAN b on e.COFFEE_BEAN_ID = b.ID WHERE b.USER_ID = :id "
        + "GROUP BY b.ID ORDER BY numExtractions DESC, b.NAME LIMIT 10", nativeQuery = true)
    List<Tuple> findTop10ExtractedByUserId(@Param("id") Long id);
}
