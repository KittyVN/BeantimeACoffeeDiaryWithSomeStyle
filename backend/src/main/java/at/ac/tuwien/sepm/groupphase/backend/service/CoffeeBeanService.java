package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;

public interface CoffeeBeanService {
    /**
     * Adds a new CoffeeBean to the persistent data storage
     *
     * @param coffeeBeanDto to add
     * @return the created coffee bean
     * @throws ValidationException if something fails to be validated
     */
    CoffeeBeanDto create(CoffeeBeanDto coffeeBeanDto) throws ValidationException;

    /**
     * Updates a CoffeeBean that is already persistent in data storage
     *
     * @param coffeeBeanDto to update
     * @return the updated CoffeeBean
     * @throws ValidationException if something fails to be validated
     */
    CoffeeBeanDto update(CoffeeBeanDto coffeeBeanDto) throws ValidationException;

    /**
     * Deletes a already persisted coffee bean by its id
     *
     * @param id of the coffee bean to be deleted
     * @throws NotFoundException in case the coffee bean with given id is not found
     */
    void delete(Long id) throws NotFoundException;


}
