package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanAvgExtractionRating;
import at.ac.tuwien.sepm.groupphase.backend.entity.Extraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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

    @Query(
        value = "SELECT * FROM COFFEE_EXTRACTION e WHERE e.COFFEE_BEAN_ID = :id",
        nativeQuery = true)
    List<Extraction> findAllByBeanId(@Param("id") Long beanId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM COFFEE_EXTRACTION WHERE EXISTS(SELECT * FROM COFFEE_BEAN b WHERE b.USER_ID = :id)", nativeQuery = true)
    void deleteByUserId(@Param("id") Long id);

    /**
     * Find the daily extraction counts of a specific user from 53 weeks before the current week's Monday
     *
     * @param id of the user
     * @return a List of Extraction instances
     */
    @Query(value = "SELECT CAST(e.EXTRACTION_DATE as DATE), COUNT(e.ID) FROM COFFEE_EXTRACTION e "
        + "JOIN COFFEE_BEAN b on e.COFFEE_BEAN_ID = b.ID JOIN APPLICATION_USER u on u.ID = b.USER_ID "
        + "WHERE u.ID = :id AND e.EXTRACTION_DATE >= (CURRENT_DATE - (ISO_DAY_OF_WEEK(CURRENT_DATE) - 1) - 53 * 7) "
        + "GROUP BY e.EXTRACTION_DATE ORDER BY CAST(e.EXTRACTION_DATE as DATE)",
        nativeQuery = true)
    List<Extraction> findDailyCountsForLast53WeeksByUserId(@Param("id") Long id);

}
