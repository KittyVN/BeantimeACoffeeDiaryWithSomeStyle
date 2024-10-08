package at.ac.tuwien.sepm.groupphase.backend.entity;

import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeGrindSetting;
import at.ac.tuwien.sepm.groupphase.backend.enums.ExtractionBrewMethod;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "coffee_extraction")
public class Extraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "extraction_date")
    private LocalDateTime extractionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "brew_method")
    private ExtractionBrewMethod brewMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "grind_setting")
    private CoffeeGrindSetting grindSetting;

    @Column(name = "water_temperature")
    private Double waterTemperature;

    @Column(name = "dose")
    private Double dose;

    @Column(name = "water_amount")
    private Double waterAmount;

    @Column(name = "brew_time")
    private Long brewTime;

    @Column(name = "body")
    private Integer body;

    @Column(name = "acidity")
    private Integer acidity;

    @Column(name = "aromatics")
    private Integer aromatics;

    @Column(name = "sweetness")
    private Integer sweetness;

    @Column(name = "aftertaste")
    private Integer aftertaste;

    @Column(name = "rating_notes", length = 5000)
    private String ratingNotes;

    @Column(name = "recipe_steps", length = 5000)
    private String recipeSteps;

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "coffee_bean_id")
    private CoffeeBean coffeeBean;

    @OneToOne(mappedBy = "extraction")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Recipe recipe;

    public Extraction() {}

    public Extraction(LocalDateTime dateTime, ExtractionBrewMethod brewMethod, CoffeeGrindSetting grindSetting,
                      Double waterTemperature, Double dose, Double waterAmount, Long brewTime, Integer body, Integer acidity,
                      Integer sweetness, Integer aromatics, Integer aftertaste, String ratingNotes, String recipeSteps,
                      CoffeeBean coffeeBean) {
        this.extractionDate = dateTime;
        this.brewMethod = brewMethod;
        this.grindSetting = grindSetting;
        this.waterTemperature = waterTemperature;
        this.dose = dose;
        this.waterAmount = waterAmount;
        this.brewTime = brewTime;
        this.body = body;
        this.acidity = acidity;
        this.sweetness = sweetness;
        this.aromatics = aromatics;
        this.aftertaste = aftertaste;
        this.ratingNotes = ratingNotes;
        this.recipeSteps = recipeSteps;
        this.coffeeBean = coffeeBean;
    }

    public Extraction(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getExtractionDate() {
        return extractionDate;
    }

    public void setExtractionDate(LocalDateTime extractionDate) {
        this.extractionDate = extractionDate;
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

    public Double getWaterAmount() {
        return waterAmount;
    }

    public void setWaterAmount(Double waterAmount) {
        this.waterAmount = waterAmount;
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

    public CoffeeBean getCoffeeBean() {
        return coffeeBean;
    }

    public void setCoffeeBean(CoffeeBean coffeeBean) {
        this.coffeeBean = coffeeBean;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(String recipeSteps) {
        this.recipeSteps = recipeSteps;
    }
}
