package at.ac.tuwien.sepm.groupphase.backend.service.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;

public class CoffeeBeanMapper {

    public CoffeeBeanDto entityToDto(CoffeeBean coffeeBean) {
        return new CoffeeBeanDto(
            coffeeBean.getName(),
            coffeeBean.getPrice(),
            coffeeBean.getOrigin(),
            coffeeBean.getHeight(),
            coffeeBean.getCoffeeRoast(),
            coffeeBean.getDescription(),
            coffeeBean.getCustom()
        );
    }
}
