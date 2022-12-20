package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanAvgExtractionRating;
import at.ac.tuwien.sepm.groupphase.backend.entity.Extraction;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExtractionRepository extends JpaRepository<Extraction, Long> {
    /**
     * Find the average extraction ratings of a coffee bean.
     *
     * @param id of the coffee bean
     * @return a CoffeeBeanAvgExtractionRating object with the results
     */
    @Query("SELECT new at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanAvgExtractionRating("
        + "e.coffeeBean.id, AVG(e.body), AVG(e.acidity), AVG(e.aromatics), AVG(e.sweetness), AVG(e.aftertaste)) "
        + "FROM Extraction e WHERE e.coffeeBean.id = :id GROUP BY e.coffeeBean.id")
    CoffeeBeanAvgExtractionRating findAvgExtractionRatingByCoffeeBeanId(@Param("id") Long id);

    @Query(
        value = "SELECT * FROM COFFEE_EXTRACTION e JOIN COFFEE_BEAN b ON e.COFFEE_BEAN_ID = b.ID JOIN APPLICATION_USER u ON u.ID = b.USER_ID WHERE u.ID = :id",
        nativeQuery = true)
    List<Extraction> findAllByUserId(@Param("id") Long id);

}
