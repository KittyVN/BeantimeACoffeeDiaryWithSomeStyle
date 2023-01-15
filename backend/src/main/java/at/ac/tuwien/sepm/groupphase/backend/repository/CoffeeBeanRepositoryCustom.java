package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;

import java.util.Collection;

public interface CoffeeBeanRepositoryCustom {
    /**
     * Search for coffee beans matching the criteria in {@code searchParameters}
     * belonging to the user with Id {@code id}.
     * <p>
     * A bean is considered matched,
     * if its name contains {@code searchParameters.name},
     * if its description contains {@code searchParameters.description},
     * if its Roast matches {@code searchParameters.coffeeRoast}.
     * All parameters are optional.
     * </p>
     *
     * @param id id of the user requesting the search
     * @param searchParameters object containing the search parameters to match
     * @return a collection containing coffee beans matching the criteria in {@code searchParameters}
     */
    Collection<CoffeeBean> search(CoffeeBeanSearchDto searchParameters, Long id);
}
