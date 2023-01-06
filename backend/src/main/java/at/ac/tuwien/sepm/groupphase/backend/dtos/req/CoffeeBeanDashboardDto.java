package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;

public class CoffeeBeanDashboardDto {
    private Long id;
    private String name;
    private CoffeeRoast coffeeRoast;
    private String description;

    private String beanBlend;

    private ExtractionDetailDto bestExtraction;

    private ExtractionDetailDto lastExtraction;

    private Integer overallAverageRating;

    public CoffeeBeanDashboardDto() {}

    public CoffeeBeanDashboardDto(Long id, String name, CoffeeRoast coffeeRoast, String description, String beanBlend) {
        this.id = id;
        this.name = name;
        this.coffeeRoast = coffeeRoast;
        this.description = description;
        this.beanBlend = beanBlend;
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

    public void setId(Long id) {
        this.id = id;
    }

    public ExtractionDetailDto getBestExtraction() {
        return bestExtraction;
    }

    public void setBestExtraction(ExtractionDetailDto bestExtraction) {
        this.bestExtraction = bestExtraction;
    }

    public ExtractionDetailDto getLastExtraction() {
        return lastExtraction;
    }

    public void setLastExtraction(ExtractionDetailDto lastExtraction) {
        this.lastExtraction = lastExtraction;
    }

    public Integer getOverallAverageRating() {
        return overallAverageRating;
    }

    public void setOverallAverageRating(Integer overallAverageRating) {
        this.overallAverageRating = overallAverageRating;
    }

    public String getBeanBlend() {
        return beanBlend;
    }

    public void setBeanBlend(String beanBlend) {
        this.beanBlend = beanBlend;
    }
}
