package at.ac.tuwien.sepm.groupphase.backend.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDashboardDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;
import org.springframework.stereotype.Component;

@Component
public class CoffeeBeanMapper {

    /**
     * Converts a CoffeeBean Entity object to a {@link CoffeeBeanDto}.
     *
     * @param coffeeBean the object to convert
     * @return the converted CoffeeBean {@link CoffeeBeanDto}
     */
    public CoffeeBeanDto entityToDto(CoffeeBean coffeeBean) {
        Long id;
        if (coffeeBean.getUser() == null) {
            id = null;
        } else {
            id = coffeeBean.getUser().getId();
        }
        return new CoffeeBeanDto(
            coffeeBean.getId(),
            coffeeBean.getName(),
            coffeeBean.getPrice(),
            coffeeBean.getOrigin(),
            coffeeBean.getHeight(),
            coffeeBean.getCoffeeRoast(),
            coffeeBean.getDescription(),
            coffeeBean.getBeanBlend(),
            coffeeBean.getUrlToCoffee(),
            coffeeBean.getCoffeeStrength(),
            id
        );
    }

    /**
     * Converts a CoffeeBean Entity object to a {@link CoffeeBeanDashboardDto}.
     *
     * @param coffeeBean the object to convert
     * @return the converted CoffeeBean {@link CoffeeBeanDashboardDto}
     */
    public CoffeeBeanDashboardDto entityToDashboardDto(CoffeeBean coffeeBean) {
        return new CoffeeBeanDashboardDto(
            coffeeBean.getId(),
            coffeeBean.getName(),
            coffeeBean.getCoffeeRoast(),
            coffeeBean.getDescription(),
            coffeeBean.getBeanBlend(),
            coffeeBean.getCoffeeStrength(),
            coffeeBean.getUrlToCoffee()
            );
    }
}
