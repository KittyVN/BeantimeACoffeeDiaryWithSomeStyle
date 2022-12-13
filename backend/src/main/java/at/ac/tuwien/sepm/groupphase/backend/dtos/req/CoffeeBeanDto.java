package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CoffeeBeanDto {
    private Long id;
    @NotNull(message = "Name must not be null")
    @NotBlank(message = "Name must not be blank")
    @Size(max = 255, message = "Name cannot be longer than 255 characters")
    private String name;
    @Min(value = 0, message = "Price cannot be negative")
    @Digits(integer = Integer.MAX_VALUE, fraction = 2)
    private Float price;
    @Size(max = 255, message = "Origin cannot be longer than 255 characters")
    private String origin;
    private Integer height;
    private CoffeeRoast coffeeRoast;
    @Size(max = 255, message = "Description cannot be longer than 255 characters")
    private String description;
    @NotNull
    private Boolean custom;
    private Long userId;

    public CoffeeBeanDto() {

    }

    public CoffeeBeanDto(Long id, String name, Float price, String origin, Integer height, CoffeeRoast coffeeRoast, String description, Boolean custom,
                         Long userId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.origin = origin;
        this.height = height;
        this.coffeeRoast = coffeeRoast;
        this.description = description;
        this.custom = custom;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public CoffeeRoast getCoffeeRoast() {
        return coffeeRoast;
    }

    public void setCoffeeRoast(CoffeeRoast coffeeRoast) {
        this.coffeeRoast = coffeeRoast;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCustom() {
        return custom;
    }

    public void setCustom(Boolean custom) {
        this.custom = custom;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
