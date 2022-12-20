package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

public class CoffeeBeanDetailDto {
    private Long id;
    private CoffeeBeanDto coffeeBean;

    public CoffeeBeanDetailDto() {}

    public CoffeeBeanDetailDto(Long id, CoffeeBeanDto coffeeBean) {
        this.id = id;
        this.coffeeBean = coffeeBean;
    }

    public Long getId() {
        return id;
    }

    public CoffeeBeanDto getCoffeeBean() {
        return coffeeBean;
    }
}
