package at.ac.tuwien.sepm.groupphase.backend.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeBeanDashboardDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;
import org.springframework.stereotype.Component;

@Component
public class CoffeeBeanMapper {

    /**
     * Converts a CoffeeBean Entity object to a {@link CoffeeBeanDto}
     *
     * @param coffeeBean the object to convert
     * @return the converted CoffeeBean {@link CoffeeBeanDto}
     */
    public CoffeeBeanDto entityToDto(CoffeeBean coffeeBean) {
        return new CoffeeBeanDto(
            coffeeBean.getId(),
            coffeeBean.getName(),
            coffeeBean.getPrice(),
            coffeeBean.getOrigin(),
            coffeeBean.getHeight(),
            coffeeBean.getCoffeeRoast(),
            coffeeBean.getDescription(),
            coffeeBean.getCustom(),
            coffeeBean.getUserId()
        );
    }

    /**
     * Converts a CoffeeBean Entity object to a {@link CoffeBeanDashboardDto}
     *
     * @param coffeeBean the object to convert
     * @return the converted CoffeeBean {@link CoffeBeanDashboardDto}
     */
    public CoffeBeanDashboardDto entityToDashboardDto(CoffeeBean coffeeBean) {
        return new CoffeBeanDashboardDto(
            coffeeBean.getId(),
            coffeeBean.getName(),
            coffeeBean.getCoffeeRoast(),
            coffeeBean.getDescription()
        );
    }
}
