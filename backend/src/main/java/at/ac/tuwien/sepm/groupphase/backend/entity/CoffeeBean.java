package at.ac.tuwien.sepm.groupphase.backend.entity;

import at.ac.tuwien.sepm.groupphase.backend.enums.CoffeeRoast;
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
import javax.persistence.Table;

@Entity
@Table(name = "coffee_bean")
public class CoffeeBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private Float price;

    @Column(name = "bean_blend")
    private String beanBlend;

    @Column
    private String origin;

    @Column
    private Integer height;

    @Enumerated(EnumType.STRING)
    private CoffeeRoast coffeeRoast;

    @Column(name = "url_to_coffee")
    private String urlToCoffee;

    @Column(name = "description", length = 5000)
    private String description;

    @Column(name = "strength")
    private String coffeeStrength;

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    public String getUrlToCoffee() {
        return urlToCoffee;
    }

    public void setUrlToCoffee(String urlToCoffee) {
        this.urlToCoffee = urlToCoffee;
    }

    public String getBeanBlend() {
        return beanBlend;
    }

    public void setBeanBlend(String beanBlend) {
        this.beanBlend = beanBlend;
    }

    public CoffeeBean() {}

    public CoffeeBean(String name, Float price, String origin, Integer height, CoffeeRoast coffeeRoast, String beanBlend, String urlToCoffee, String description, String strength, User user) {
        this.name = name;
        this.price = price;
        this.origin = origin;
        this.height = height;
        this.coffeeRoast = coffeeRoast;
        this.beanBlend = beanBlend;
        this.urlToCoffee = urlToCoffee;
        this.description = description;
        this.coffeeStrength = strength;
        this.user = user;
    }

    public CoffeeBean(Long id, String name, Float price, String origin, Integer height, CoffeeRoast coffeeRoast, String beanBlend, String urlToCoffee, String description, String strength, User user) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.origin = origin;
        this.height = height;
        this.coffeeRoast = coffeeRoast;
        this.beanBlend = beanBlend;
        this.urlToCoffee = urlToCoffee;
        this.description = description;
        this.coffeeStrength = strength;
        this.user = user;
    }


    public CoffeeBean(Long id, String name, Float price, String origin, Integer height, CoffeeRoast coffeeRoast, String description, User user) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.origin = origin;
        this.height = height;
        this.coffeeRoast = coffeeRoast;
        this.description = description;
        this.user = user;
    }

    public CoffeeBean(String name, Float price, String origin, Integer height, CoffeeRoast coffeeRoast, String description, User user) {
        this.name = name;
        this.price = price;
        this.origin = origin;
        this.height = height;
        this.coffeeRoast = coffeeRoast;
        this.description = description;
        this.user = user;
    }

    public CoffeeBean(String name, CoffeeRoast coffeeRoast) {
        this.name = name;
        this.coffeeRoast = coffeeRoast;
    }

    public CoffeeBean(Long id, String name, CoffeeRoast coffeeRoast) {
        this.id = id;
        this.name = name;
        this.coffeeRoast = coffeeRoast;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    public String getCoffeeStrength() {
        return coffeeStrength;
    }

    public void setCoffeeStrength(String coffeeStrength) {
        this.coffeeStrength = coffeeStrength;
    }
}
