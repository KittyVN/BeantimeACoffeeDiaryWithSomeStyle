package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Recipe findRecipeByExtraction_Id(long id);

    Collection<Recipe> findRecipesByExtraction_CoffeeBean_User_Id(long id);

    Collection<Recipe> findRecipesBySharedIsTrue();

    Recipe findRecipeById(long id);
}
