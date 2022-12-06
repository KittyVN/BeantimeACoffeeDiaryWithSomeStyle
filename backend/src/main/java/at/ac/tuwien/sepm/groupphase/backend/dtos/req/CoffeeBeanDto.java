package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;

public record CoffeeBeanDto(
    String name,
    Float price,
    String origin,
    Integer height,
    CoffeeRoast coffeeRoast,
    String description,
    Boolean custom
) { }
