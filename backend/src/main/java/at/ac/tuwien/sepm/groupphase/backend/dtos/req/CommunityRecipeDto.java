package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class CommunityRecipeDto {
    private Long recipeId;
    private String recipeDescription;
    private Long extractionId;

    private LocalDateTime extractionDate;

    private String extractionBrewMethod;
    private String extractionGrindSetting;
    private Double extractionWaterTemperature;
    private Double extractionDose;
    private Double extractionWaterAmount;
    private Long extractionBrewTime;
    private Integer extractionBody;
    private Integer extractionAcidity;
    private Integer extractionAromatics;
    private Integer extractionSweetness;
    private Integer extractionAftertaste;
    private String extractionRatingNotes;
    private Long coffeeBeanId;
    private String coffeeBeanName;
    private Float coffeeBeanPrice;
    private String coffeeBeanBlend;
    private String coffeeBeanOrigin;
    private Integer coffeeBeanHeight;
    private String coffeeBeanRoast;
    private String coffeeBeanUrl;
    private String coffeeBeanDescription;

    public CommunityRecipeDto() {
    }

    public CommunityRecipeDto(Long recipeId, String recipeDescription, Long extractionId, LocalDateTime extractionDate,
                              String extractionBrewMethod, String extractionGrindSetting, Double extractionWaterTemperature,
                              Double extractionDose, Double extractionWaterAmount, Long extractionBrewTime, Integer extractionBody,
                              Integer extractionAcidity, Integer extractionAromatics, Integer extractionSweetness, Integer extractionAftertaste,
                              String extractionRatingNotes, Long coffeeBeanId, String coffeeBeanName, Float coffeeBeanPrice, String coffeeBeanBlend,
                              String coffeeBeanOrigin, Integer coffeeBeanHeight, String coffeeBeanRoast, String coffeeBeanUrl, String coffeeBeanDescription) {
        this.recipeId = recipeId;
        this.recipeDescription = recipeDescription;
        this.extractionId = extractionId;
        this.extractionDate = extractionDate;
        this.extractionBrewMethod = extractionBrewMethod;
        this.extractionGrindSetting = extractionGrindSetting;
        this.extractionWaterTemperature = extractionWaterTemperature;
        this.extractionDose = extractionDose;
        this.extractionWaterAmount = extractionWaterAmount;
        this.extractionBrewTime = extractionBrewTime;
        this.extractionBody = extractionBody;
        this.extractionAcidity = extractionAcidity;
        this.extractionAromatics = extractionAromatics;
        this.extractionSweetness = extractionSweetness;
        this.extractionAftertaste = extractionAftertaste;
        this.extractionRatingNotes = extractionRatingNotes;
        this.coffeeBeanId = coffeeBeanId;
        this.coffeeBeanName = coffeeBeanName;
        this.coffeeBeanPrice = coffeeBeanPrice;
        this.coffeeBeanBlend = coffeeBeanBlend;
        this.coffeeBeanOrigin = coffeeBeanOrigin;
        this.coffeeBeanHeight = coffeeBeanHeight;
        this.coffeeBeanRoast = coffeeBeanRoast;
        this.coffeeBeanUrl = coffeeBeanUrl;
        this.coffeeBeanDescription = coffeeBeanDescription;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public Long getExtractionId() {
        return extractionId;
    }

    public void setExtractionId(Long extractionId) {
        this.extractionId = extractionId;
    }

    public String getExtractionRatingNotes() {
        return extractionRatingNotes;
    }

    public void setExtractionRatingNotes(String extractionRatingNotes) {
        this.extractionRatingNotes = extractionRatingNotes;
    }

    public LocalDateTime getExtractionDate() {
        return extractionDate;
    }

    public void setExtractionDate(LocalDateTime extractionDate) {
        this.extractionDate = extractionDate;
    }

    public String getExtractionBrewMethod() {
        return extractionBrewMethod;
    }

    public void setExtractionBrewMethod(String extractionBrewMethod) {
        this.extractionBrewMethod = extractionBrewMethod;
    }

    public String getExtractionGrindSetting() {
        return extractionGrindSetting;
    }

    public void setExtractionGrindSetting(String extractionGrindSetting) {
        this.extractionGrindSetting = extractionGrindSetting;
    }

    public Double getExtractionWaterTemperature() {
        return extractionWaterTemperature;
    }

    public void setExtractionWaterTemperature(Double extractionWaterTemperature) {
        this.extractionWaterTemperature = extractionWaterTemperature;
    }

    public Double getExtractionDose() {
        return extractionDose;
    }

    public void setExtractionDose(Double extractionDose) {
        this.extractionDose = extractionDose;
    }

    public Double getExtractionWaterAmount() {
        return extractionWaterAmount;
    }

    public void setExtractionWaterAmount(Double extractionWaterAmount) {
        this.extractionWaterAmount = extractionWaterAmount;
    }

    public Long getExtractionBrewTime() {
        return extractionBrewTime;
    }

    public void setExtractionBrewTime(Long extractionBrewTime) {
        this.extractionBrewTime = extractionBrewTime;
    }

    public Integer getExtractionBody() {
        return extractionBody;
    }

    public void setExtractionBody(Integer extractionBody) {
        this.extractionBody = extractionBody;
    }

    public Integer getExtractionAcidity() {
        return extractionAcidity;
    }

    public void setExtractionAcidity(Integer extractionAcidity) {
        this.extractionAcidity = extractionAcidity;
    }

    public Integer getExtractionAromatics() {
        return extractionAromatics;
    }

    public void setExtractionAromatics(Integer extractionAromatics) {
        this.extractionAromatics = extractionAromatics;
    }

    public Integer getExtractionSweetness() {
        return extractionSweetness;
    }

    public void setExtractionSweetness(Integer extractionSweetness) {
        this.extractionSweetness = extractionSweetness;
    }

    public Integer getExtractionAftertaste() {
        return extractionAftertaste;
    }

    public void setExtractionAftertaste(Integer extractionAftertaste) {
        this.extractionAftertaste = extractionAftertaste;
    }

    public Long getCoffeeBeanId() {
        return coffeeBeanId;
    }

    public void setCoffeeBeanId(Long coffeeBeanId) {
        this.coffeeBeanId = coffeeBeanId;
    }

    public String getCoffeeBeanName() {
        return coffeeBeanName;
    }

    public void setCoffeeBeanName(String coffeeBeanName) {
        this.coffeeBeanName = coffeeBeanName;
    }

    public Float getCoffeeBeanPrice() {
        return coffeeBeanPrice;
    }

    public void setCoffeeBeanPrice(Float coffeeBeanPrice) {
        this.coffeeBeanPrice = coffeeBeanPrice;
    }

    public String getCoffeeBeanBlend() {
        return coffeeBeanBlend;
    }

    public void setCoffeeBeanBlend(String coffeeBeanBlend) {
        this.coffeeBeanBlend = coffeeBeanBlend;
    }

    public String getCoffeeBeanOrigin() {
        return coffeeBeanOrigin;
    }

    public void setCoffeeBeanOrigin(String coffeeBeanOrigin) {
        this.coffeeBeanOrigin = coffeeBeanOrigin;
    }

    public Integer getCoffeeBeanHeight() {
        return coffeeBeanHeight;
    }

    public void setCoffeeBeanHeight(Integer coffeeBeanHeight) {
        this.coffeeBeanHeight = coffeeBeanHeight;
    }

    public String getCoffeeBeanRoast() {
        return coffeeBeanRoast;
    }

    public void setCoffeeBeanRoast(String coffeeBeanRoast) {
        this.coffeeBeanRoast = coffeeBeanRoast;
    }

    public String getCoffeeBeanUrl() {
        return coffeeBeanUrl;
    }

    public void setCoffeeBeanUrl(String coffeeBeanUrl) {
        this.coffeeBeanUrl = coffeeBeanUrl;
    }

    public String getCoffeeBeanDescription() {
        return coffeeBeanDescription;
    }

    public void setCoffeeBeanDescription(String coffeeBeanDescription) {
        this.coffeeBeanDescription = coffeeBeanDescription;
    }
}
