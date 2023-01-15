package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

public class CoffeeBeanRatingListDto {
    private long id;
    private String name;
    private Double rating;

    public CoffeeBeanRatingListDto() {
    }

    public CoffeeBeanRatingListDto(long id, String name, Double rating) {
        this.id = id;
        this.name = name;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
