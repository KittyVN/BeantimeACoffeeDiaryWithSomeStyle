package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeBeanDashboardDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import org.aspectj.weaver.ast.Not;

import java.util.stream.Stream;

public interface CoffeeBeanService {
    /**
     * Fetches all saved coffee beans from the persistent data storage
     *
     * @return a stream of the found coffee beans
     */
    Stream<CoffeBeanDashboardDto> getAll();

    /**
     * Adds a new CoffeeBean to the persistent data storage
     *
     * @param coffeeBeanDto to add
     * @return the created coffee bean
     */
    CoffeeBeanDto create(CoffeeBeanDto coffeeBeanDto);

    /**
     * Updates a CoffeeBean that is already persistent in data storage.
     *
     * @param coffeeBeanDto to update
     * @return the updated CoffeeBean
     */
    CoffeeBeanDto update(CoffeeBeanDto coffeeBeanDto);

    /**
     * Deletes an already persisted coffee bean by its id.
     *
     * @param id of the coffee bean to be deleted
     * @throws NotFoundException in case the coffee bean with given id is not found
     */
    void delete(Long id) throws NotFoundException;

    /**
     * Gets an already persisted coffee bean by its id.
     *
     * @param id the id of the coffee bean to get
     * @return the coffee bean that has been found
     * @throws NotFoundException in case no coffee bean is found with the given id
     */
    CoffeeBeanDto getById(Long id) throws NotFoundException;

}
