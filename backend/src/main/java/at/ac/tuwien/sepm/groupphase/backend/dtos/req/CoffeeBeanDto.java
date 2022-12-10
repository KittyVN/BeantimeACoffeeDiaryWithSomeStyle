package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record CoffeeBeanDto(


    Long id,
    @NotNull(message = "Name must not be null")
    @NotBlank(message = "Name must not be blank")
    @Size(min = 1, max = 255, message = "Name cannot be longer than 255 characters")
    String name,
    @Min(value = 0, message = "Price cannot be negative")
    Float price,
    @Size(min = 1, max = 255, message = "Origin cannot be longer than 255 characters")
    String origin,
    Integer height,
    CoffeeRoast coffeeRoast,
    @Size(min = 1, max = 255, message = "Description cannot be longer than 255 characters")
    String description,
    @NotNull
    Boolean custom,
    Long userId
) { }
