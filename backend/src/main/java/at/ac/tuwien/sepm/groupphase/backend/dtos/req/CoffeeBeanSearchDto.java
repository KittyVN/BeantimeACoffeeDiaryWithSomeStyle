package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;

public class CoffeeBeanSearchDto {

    private String name;
    private CoffeeRoast coffeeRoast;
    private String description;
    private String beanBlend;

    public CoffeeBeanSearchDto() {
    }

    public CoffeeBeanSearchDto(String name, CoffeeRoast coffeeRoast, String description, String beanBlend) {
        this.name = name;
        this.coffeeRoast = coffeeRoast;
        this.description = description;
        this.beanBlend = beanBlend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getBeanBlend() {
        return beanBlend;
    }

    public void setBeanBlend(String beanBlend) {
        this.beanBlend = beanBlend;
    }
    public boolean isEmpty() {
        return (name == null && description == null && coffeeRoast == null && beanBlend == null);
    }



    @Override
    public String toString() {
        return "CoffeeBeanSearchDto{" +
            "name='" + name + '\'' +
            ", coffeeRoast=" + coffeeRoast +
            ", description='" + description + '\'' +
            ", beanBlend='" + beanBlend + '\'' +
            '}';
    }
}
