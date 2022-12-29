package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;

public class CoffeBeanDashboardDto {
    private Long id;
    private String name;
    private CoffeeRoast coffeeRoast;
    private String description;

    private String beanBlend;

    public CoffeBeanDashboardDto() {}

    public CoffeBeanDashboardDto(Long id, String name, CoffeeRoast coffeeRoast, String description, String blend) {
        this.id = id;
        this.name = name;
        this.coffeeRoast = coffeeRoast;
        this.description = description;
        this.beanBlend = blend;
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

    public String getBeanBlend() {
        return beanBlend;
    }
}
