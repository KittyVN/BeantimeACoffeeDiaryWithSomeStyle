package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeGrindSetting;
import at.ac.tuwien.sepm.groupphase.backend.enums.ExtractionBrewMethod;

import java.time.LocalDateTime;

public class ExtractionSearchDto {

    private LocalDateTime created;

    private boolean reverseDate;

    private Integer overallRating;

    private boolean reverseOverallRating;

    private CoffeeGrindSetting grindSetting;

    private ExtractionBrewMethod brewMethod;

    public ExtractionSearchDto(LocalDateTime created, boolean reverseDate, Integer overallRating,
                               boolean reverseOverallRating, CoffeeGrindSetting grindSetting,
                               ExtractionBrewMethod brewMethod) {
        this.created = created;
        this.reverseDate = reverseDate;
        this.overallRating = overallRating;
        this.reverseOverallRating = reverseOverallRating;
        this.grindSetting = grindSetting;
        this.brewMethod = brewMethod;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public boolean isReverseDate() {
        return reverseDate;
    }

    public void setReverseDate(boolean reverseDate) {
        this.reverseDate = reverseDate;
    }

    public Integer getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(Integer overallRating) {
        this.overallRating = overallRating;
    }

    public boolean isReverseOverallRating() {
        return reverseOverallRating;
    }

    public void setReverseOverallRating(boolean reverseOverallRating) {
        this.reverseOverallRating = reverseOverallRating;
    }

    public CoffeeGrindSetting getGrindSetting() {
        return grindSetting;
    }

    public void setGrindSetting(CoffeeGrindSetting grindSetting) {
        this.grindSetting = grindSetting;
    }

    public ExtractionBrewMethod getBrewMethod() {
        return brewMethod;
    }

    public void setBrewMethod(ExtractionBrewMethod brewMethod) {
        this.brewMethod = brewMethod;
    }
}
