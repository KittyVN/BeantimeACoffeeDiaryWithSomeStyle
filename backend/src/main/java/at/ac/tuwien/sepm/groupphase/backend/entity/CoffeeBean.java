package at.ac.tuwien.sepm.groupphase.backend.entity;

import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "coffee_bean")
public class CoffeeBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private Float price;

    @Column
    private String origin;

    @Column
    private Integer height;

    @Enumerated(EnumType.STRING)
    private CoffeeRoast coffeeRoast;

    @Column
    private String description;

    @Column
    private Boolean custom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public CoffeeRoast getCoffeeRoast() {
        return coffeeRoast;
    }

    public void setCoffeeRoast(CoffeeRoast coffeeRoast) {
        this.coffeeRoast = coffeeRoast;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCustom() {
        return custom;
    }

    public void setCustom(Boolean custom) {
        this.custom = custom;
    }

    public static class CoffeeBeanBuilder {
        private Long id;
        private String name;
        private Float price;
        private String origin;
        private Integer height;
        private CoffeeRoast coffeeRoast;
        private String description;
        private Boolean custom;

        private CoffeeBeanBuilder() {
        }

        public static CoffeeBeanBuilder aCoffeeBean() {
            return new CoffeeBeanBuilder();
        }

        public CoffeeBeanBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public CoffeeBeanBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CoffeeBeanBuilder withPrice(Float price) {
            this.price = price;
            return this;
        }

        public CoffeeBeanBuilder withOrigin(String origin) {
            this.origin = origin;
            return this;
        }

        public CoffeeBeanBuilder withHeight(Integer height) {
            this.height = height;
            return this;
        }

        public CoffeeBeanBuilder withCoffeeRoast(CoffeeRoast coffeeRoast) {
            this.coffeeRoast = coffeeRoast;
            return this;
        }

        public CoffeeBeanBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public CoffeeBeanBuilder withCustom(Boolean custom) {
            this.custom = custom;
            return this;
        }

        public CoffeeBean build() {
            CoffeeBean bean = new CoffeeBean();
            bean.setId(id);
            bean.setName(name);
            bean.setPrice(price);
            bean.setOrigin(origin);
            bean.setHeight(height);
            bean.setCoffeeRoast(coffeeRoast);
            bean.setDescription(description);
            bean.setCustom(custom);
            return bean;
        }
    }
}
