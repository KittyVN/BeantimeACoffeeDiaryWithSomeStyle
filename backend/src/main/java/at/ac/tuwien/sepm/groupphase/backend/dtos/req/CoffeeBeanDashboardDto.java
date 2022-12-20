package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;

public class CoffeeBeanDashboardDto {
    private Long id;
    private String name;
    private CoffeeRoast coffeeRoast;
    private String description;

    public CoffeeBeanDashboardDto() {}

    public CoffeeBeanDashboardDto(Long id, String name, CoffeeRoast coffeeRoast, String description) {
        this.id = id;
        this.name = name;
        this.coffeeRoast = coffeeRoast;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CoffeeRoast getCoffeeRoast() {
        return coffeeRoast;
    }

    public String getDescription() {
        return description;
    }
}
