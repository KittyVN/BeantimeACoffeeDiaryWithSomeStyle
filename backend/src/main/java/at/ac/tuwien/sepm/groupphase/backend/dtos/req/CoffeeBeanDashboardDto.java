package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;

public class CoffeeBeanDashboardDto {
    private Long id;
    private String name;
    private CoffeeRoast coffeeRoast;
    private String description;
    private String beanBlend;
    private String coffeeBeanUrl;
    private String coffeeStrength;
    private ExtractionDetailDto bestExtraction;
    private ExtractionDetailDto lastExtraction;
    private Integer overallAverageRating;

    public CoffeeBeanDashboardDto() {}

    public CoffeeBeanDashboardDto(Long id, String name, CoffeeRoast coffeeRoast, String description, String beanBlend, String coffeeStrength, String coffeeBeanUrl) {
        this.id = id;
        this.name = name;
        this.coffeeRoast = coffeeRoast;
        this.description = description;
        this.beanBlend = beanBlend;
        this.coffeeStrength = coffeeStrength;
        this.coffeeBeanUrl = coffeeBeanUrl;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setCoffeeRoast(CoffeeRoast coffeeRoast) {
        this.coffeeRoast = coffeeRoast;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoffeeBeanUrl() {
        return coffeeBeanUrl;
    }

    public void setCoffeeBeanUrl(String coffeeBeanUrl) {
        this.coffeeBeanUrl = coffeeBeanUrl;
    }

    public String getCoffeeStrength() {
        return coffeeStrength;
    }

    public void setCoffeeStrength(String coffeeStrength) {
        this.coffeeStrength = coffeeStrength;
    }
}
