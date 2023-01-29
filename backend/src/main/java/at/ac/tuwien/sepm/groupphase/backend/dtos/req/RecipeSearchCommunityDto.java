package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;
import at.ac.tuwien.sepm.groupphase.backend.enums.ExtractionBrewMethod;

public class RecipeSearchCommunityDto {

    private String name;

    private String blend;

    private ExtractionBrewMethod brewMethod;

    private CoffeeRoast roast;

    public RecipeSearchCommunityDto() {
    }

    public RecipeSearchCommunityDto(String name, String blend, ExtractionBrewMethod brewMethod, CoffeeRoast roast) {
        this.name = name;
        this.blend = blend;
        this.brewMethod = brewMethod;
        this.roast = roast;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlend() {
        return blend;
    }

    public void setBlend(String blend) {
        this.blend = blend;
    }

    public ExtractionBrewMethod getBrewMethod() {
        return brewMethod;
    }

    public void setBrewMethod(ExtractionBrewMethod brewMethod) {
        this.brewMethod = brewMethod;
    }

    public CoffeeRoast getRoast() {
        return roast;
    }

    public void setRoast(CoffeeRoast roast) {
        this.roast = roast;
    }
}
