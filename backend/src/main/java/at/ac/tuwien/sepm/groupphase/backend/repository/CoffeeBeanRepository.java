package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CoffeeBeanRepository extends JpaRepository<CoffeeBean, Long> {
    @Modifying
    @Transactional
    @Query("delete from CoffeeBean c where c.user.id = :id")
    void deleteCoffeeBeanByUserId(@Param("id") Long id);
}
