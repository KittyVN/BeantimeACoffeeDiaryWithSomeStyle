package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Modifying
    @Transactional
    @Query("delete from Recipe c where c.extraction.coffeeBean.user.id = :id")
    void deleteByUserId(@Param("id") Long id);
}
