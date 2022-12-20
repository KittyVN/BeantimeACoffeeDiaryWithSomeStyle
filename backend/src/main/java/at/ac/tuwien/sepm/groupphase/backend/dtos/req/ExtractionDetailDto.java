package at.ac.tuwien.sepm.groupphase.backend.dtos.req;


import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeGrindSetting;
import at.ac.tuwien.sepm.groupphase.backend.enums.ExtractionBrewMethod;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDateTime;

public class ExtractionDetailDto {
    private Long id;

    private LocalDateTime dateTime;

    private ExtractionBrewMethod brewMethod;

    private CoffeeGrindSetting grindSetting;

    private Double waterTemperature;

    @Min(value = 0, message = "dosage cannot be negative")
    private Double dose;

    private Double waterAmount;

    @Min(value = 0, message = "brewTime cannot be negative")
    private Long brewTime;

    @Min(value = 0, message = "body cannot be negative")
    @Max(value = 5, message = "body cannot be higher than 5")
    private Integer body;

    @Min(value = 0, message = "acidity cannot be negative")
    @Max(value = 5, message = "acidity cannot be higher than 5")
    private Integer acidity;

    @Min(value = 0, message = "aromatics cannot be negative")
    @Max(value = 5, message = "aromatics cannot be higher than 5")
    private Integer aromatics;

    @Min(value = 0, message = "sweetness cannot be negative")
    @Max(value = 5, message = "sweetness cannot be higher than 5")
    private Integer sweetness;

    @Min(value = 0, message = "aftertaste cannot be negative")
    @Max(value = 5, message = "aftertaste cannot be higher than 5")
    private Integer aftertaste;

    @Size(max = 1500, message = "Note cannot be longer than 1500 characters")
    private String ratingNotes;

    @Min(value = 0, message = "overallRating cannot be negative")
    @Max(value = 25, message = "overallRating cannot be higher than 25")
    private Integer overallRating;

    @NotNull
    private Long beanId;

    public ExtractionDetailDto() {
    }

    public ExtractionDetailDto(Long id, LocalDateTime dateTime, ExtractionBrewMethod brewMethod,
                               CoffeeGrindSetting grindSetting, Double waterTemperature, Double dose,
                               Double waterAmount, Long brewTime, Integer body, Integer acidity,
                               Integer aromatics, Integer sweetness, Integer aftertaste,
                               String ratingNotes, Integer overallRating, Long beanId) {
        this.id = id;
        this.dateTime = dateTime;
        this.brewMethod = brewMethod;
        this.grindSetting = grindSetting;
        this.waterTemperature = waterTemperature;
        this.dose = dose;
        this.waterAmount = waterAmount;
        this.brewTime = brewTime;
        this.body = body;
        this.acidity = acidity;
        this.aromatics = aromatics;
        this.sweetness = sweetness;
        this.aftertaste = aftertaste;
        this.ratingNotes = ratingNotes;
        this.overallRating = overallRating;
        this.beanId = beanId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public ExtractionBrewMethod getBrewMethod() {
        return brewMethod;
    }

    public void setBrewMethod(ExtractionBrewMethod brewMethod) {
        this.brewMethod = brewMethod;
    }

    public CoffeeGrindSetting getGrindSetting() {
        return grindSetting;
    }

    public void setGrindSetting(CoffeeGrindSetting grindSetting) {
        this.grindSetting = grindSetting;
    }

    public Double getWaterTemperature() {
        return waterTemperature;
    }

    public void setWaterTemperature(Double waterTemperature) {
        this.waterTemperature = waterTemperature;
    }

    public Double getDose() {
        return dose;
    }

    public void setDose(Double dose) {
        this.dose = dose;
    }

    public Long getBrewTime() {
        return brewTime;
    }

    public void setBrewTime(Long brewTime) {
        this.brewTime = brewTime;
    }

    public Integer getBody() {
        return body;
    }

    public void setBody(Integer body) {
        this.body = body;
    }

    public Integer getAcidity() {
        return acidity;
    }

    public void setAcidity(Integer acidity) {
        this.acidity = acidity;
    }

    public Integer getAromatics() {
        return aromatics;
    }

    public void setAromatics(Integer aromatics) {
        this.aromatics = aromatics;
    }

    public Integer getSweetness() {
        return sweetness;
    }

    public void setSweetness(Integer sweetness) {
        this.sweetness = sweetness;
    }

    public Integer getAftertaste() {
        return aftertaste;
    }

    public void setAftertaste(Integer aftertaste) {
        this.aftertaste = aftertaste;
    }

    public String getRatingNotes() {
        return ratingNotes;
    }

    public void setRatingNotes(String ratingNotes) {
        this.ratingNotes = ratingNotes;
    }

    public Integer getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(Integer overallRating) {
        this.overallRating = overallRating;
    }

    public Long getBeanId() {
        return beanId;
    }

    public void setBeanId(Long beanId) {
        this.beanId = beanId;
    }
}
