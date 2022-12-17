package at.ac.tuwien.sepm.groupphase.backend.entity;

import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;
import at.ac.tuwien.sepm.groupphase.backend.enums.ExtractionBrewMethod;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Duration;

@Entity
@Table(name = "coffee_extraction")
public class Extraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "brew_method")
    private ExtractionBrewMethod brewMethod;

    @Column(name = "grind_setting")
    private String grindSetting;

    @Column(name = "water_temperature")
    private String waterTemperature;

    @Column(name = "dose")
    private String dose;

    @Column(name = "brew_time")
    private Duration brewTime;

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

    @Column(name = "rating_notes")
    private Integer ratingNotes;

    @ManyToOne
    @JoinColumn(name = "coffee_bean_id")
    private CoffeeBean coffeeBean;

    public Integer getBody() {
        return body;
    }

    public void setBody(Integer body) {
        this.body = body;
    }

    public Duration getBrewTime() {
        return brewTime;
    }

    public void setBrewTime(Duration brewTime) {
        this.brewTime = brewTime;
    }

    public CoffeeBean getCoffeeBean() {
        return coffeeBean;
    }

    public void setCoffeeBean(CoffeeBean coffeeBean) {
        this.coffeeBean = coffeeBean;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
