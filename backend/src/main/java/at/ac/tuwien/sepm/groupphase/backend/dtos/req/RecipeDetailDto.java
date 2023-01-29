package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeGrindSetting;
import at.ac.tuwien.sepm.groupphase.backend.enums.ExtractionBrewMethod;

public class RecipeDetailDto {
    private Long id;
    private Boolean shared;
    private Long extractionId;
    private ExtractionBrewMethod extractionBrewMethod;
    private CoffeeGrindSetting extractionGrindSettings;
    private Integer extractionBody;
    private Integer extractionAcidity;
    private Integer extractionAromatics;
    private Integer extractionSweetness;
    private Integer extractionAftertaste;
    private String extractionRecipeSteps;
    private String extractionRatingNotes;
    private Double extractionWaterAmount;
    private Double extractionWaterTemperature;
    private Double extractionDose;
    private Long extractionBrewTime;
    private Long coffeeBeanId;
    private String coffeeBeanName;
    private String coffeeBeanBlend;
    private String coffeeBeanRoast;
    private String coffeeBeanUrl;
    private String coffeeBeanStrength;
    private String coffeeBeanOrigin;
    private Integer coffeeBeanHeight;
    private String coffeeBeanDescription;
    private Long coffeeBeanUserId;
    private String coffeeBeanUserUsername;

    public RecipeDetailDto() {
    }

    public RecipeDetailDto(Long id, Boolean shared, Long extractionId, ExtractionBrewMethod extractionBrewMethod,
                           CoffeeGrindSetting extractionGrindSettings, Integer extractionBody,
                           Integer extractionAcidity, Integer extractionAromatics, Integer extractionSweetness,
                           Integer extractionAftertaste, String extractionRecipeSteps, String extractionRatingNotes,
                           Double extractionWaterAmount, Double extractionWaterTemperature, Double extractionDose,
                           Long extractionBrewTime, Long coffeeBeanId, String coffeeBeanName, String coffeeBeanBlend,
                           String coffeeBeanRoast, String coffeeBeanUrl, String coffeeBeanStrength,
                           String coffeeBeanOrigin, Integer coffeeBeanHeight, String coffeeBeanDescription,
                           Long coffeeBeanUserId, String coffeeBeanUserUsername) {
        this.id = id;
        this.shared = shared;
        this.extractionId = extractionId;
        this.extractionBrewMethod = extractionBrewMethod;
        this.extractionGrindSettings = extractionGrindSettings;
        this.extractionBody = extractionBody;
        this.extractionAcidity = extractionAcidity;
        this.extractionAromatics = extractionAromatics;
        this.extractionSweetness = extractionSweetness;
        this.extractionAftertaste = extractionAftertaste;
        this.extractionRecipeSteps = extractionRecipeSteps;
        this.extractionRatingNotes = extractionRatingNotes;
        this.extractionWaterAmount = extractionWaterAmount;
        this.extractionWaterTemperature = extractionWaterTemperature;
        this.extractionDose = extractionDose;
        this.extractionBrewTime = extractionBrewTime;
        this.coffeeBeanId = coffeeBeanId;
        this.coffeeBeanName = coffeeBeanName;
        this.coffeeBeanBlend = coffeeBeanBlend;
        this.coffeeBeanRoast = coffeeBeanRoast;
        this.coffeeBeanUrl = coffeeBeanUrl;
        this.coffeeBeanStrength = coffeeBeanStrength;
        this.coffeeBeanOrigin = coffeeBeanOrigin;
        this.coffeeBeanHeight = coffeeBeanHeight;
        this.coffeeBeanDescription = coffeeBeanDescription;
        this.coffeeBeanUserId = coffeeBeanUserId;
        this.coffeeBeanUserUsername = coffeeBeanUserUsername;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }

    public Long getExtractionId() {
        return extractionId;
    }

    public void setExtractionId(Long extractionId) {
        this.extractionId = extractionId;
    }

    public ExtractionBrewMethod getExtractionBrewMethod() {
        return extractionBrewMethod;
    }

    public void setExtractionBrewMethod(ExtractionBrewMethod extractionBrewMethod) {
        this.extractionBrewMethod = extractionBrewMethod;
    }

    public CoffeeGrindSetting getExtractionGrindSettings() {
        return extractionGrindSettings;
    }

    public void setExtractionGrindSettings(CoffeeGrindSetting extractionGrindSettings) {
        this.extractionGrindSettings = extractionGrindSettings;
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

    public String getExtractionRecipeSteps() {
        return extractionRecipeSteps;
    }

    public void setExtractionRecipeSteps(String extractionRecipeSteps) {
        this.extractionRecipeSteps = extractionRecipeSteps;
    }

    public String getExtractionRatingNotes() {
        return extractionRatingNotes;
    }

    public void setExtractionRatingNotes(String extractionRatingNotes) {
        this.extractionRatingNotes = extractionRatingNotes;
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

    public String getCoffeeBeanBlend() {
        return coffeeBeanBlend;
    }

    public void setCoffeeBeanBlend(String coffeeBeanBlend) {
        this.coffeeBeanBlend = coffeeBeanBlend;
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

    public String getCoffeeBeanStrength() {
        return coffeeBeanStrength;
    }

    public void setCoffeeBeanStrength(String coffeeBeanStrength) {
        this.coffeeBeanStrength = coffeeBeanStrength;
    }

    public Long getCoffeeBeanUserId() {
        return coffeeBeanUserId;
    }

    public void setCoffeeBeanUserId(Long coffeeBeanUserId) {
        this.coffeeBeanUserId = coffeeBeanUserId;
    }

    public String getCoffeeBeanUserUsername() {
        return coffeeBeanUserUsername;
    }

    public void setCoffeeBeanUserUsername(String coffeeBeanUserUsername) {
        this.coffeeBeanUserUsername = coffeeBeanUserUsername;
    }

    public Double getExtractionWaterAmount() {
        return extractionWaterAmount;
    }

    public void setExtractionWaterAmount(Double extractionWaterAmount) {
        this.extractionWaterAmount = extractionWaterAmount;
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

    public Long getExtractionBrewTime() {
        return extractionBrewTime;
    }

    public void setExtractionBrewTime(Long extractionBrewTime) {
        this.extractionBrewTime = extractionBrewTime;
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

    public String getCoffeeBeanDescription() {
        return coffeeBeanDescription;
    }

    public void setCoffeeBeanDescription(String coffeeBeanDescription) {
        this.coffeeBeanDescription = coffeeBeanDescription;
    }
}
