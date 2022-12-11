package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;

public record CoffeBeanDashboardDto(

    Long id,
    String name,
    CoffeeRoast coffeeRoast,
    String description
) {
}
