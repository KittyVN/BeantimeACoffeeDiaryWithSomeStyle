package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

public class CoffeeBeanDetailDto {
    private Long id;
    private CoffeeBeanDto coffeeBean;
    private CoffeeBeanAvgExtractionRating avgExtractionRating;

    public CoffeeBeanDetailDto() {}

    public CoffeeBeanDetailDto(Long id, CoffeeBeanDto coffeeBean, CoffeeBeanAvgExtractionRating avgExtractionRating) {
        this.id = id;
        this.coffeeBean = coffeeBean;
        this.avgExtractionRating = avgExtractionRating;
    }

    public Long getId() {
        return id;
    }

    public CoffeeBeanDto getCoffeeBean() {
        return coffeeBean;
    }

    public CoffeeBeanAvgExtractionRating getAvgExtractionRating() {
        return avgExtractionRating;
    }
}
