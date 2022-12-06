package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;

public interface CoffeeBeanService {
    /**
     * @param coffeeBeanDto to create
     * @return the created coffee bean
     */
    CoffeeBeanDto create(CoffeeBeanDto coffeeBeanDto);

}
