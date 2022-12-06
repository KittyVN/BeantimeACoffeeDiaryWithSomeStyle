package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;
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

}
