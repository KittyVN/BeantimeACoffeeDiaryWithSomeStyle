package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

public class CoffeeBeanAvgExtractionRating {
    private Long id;
    private Double body;
    private Double acidity;
    private Double aromatics;
    private Double sweetness;
    private Double aftertaste;

    public CoffeeBeanAvgExtractionRating() {
    }

    public CoffeeBeanAvgExtractionRating(Long id, Double body, Double acidity, Double aromatics, Double sweetness, Double aftertaste) {
        this.id = id;
        this.body = body;
        this.acidity = acidity;
        this.aromatics = aromatics;
        this.sweetness = sweetness;
        this.aftertaste = aftertaste;
    }

    public Long getId() {
        return id;
    }

    public Double getBody() {
        return body;
    }

    public Double getAcidity() {
        return acidity;
    }

    public Double getAromatics() {
        return aromatics;
    }

    public Double getSweetness() {
        return sweetness;
    }

    public Double getAftertaste() {
        return aftertaste;
    }
}
