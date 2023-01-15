package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

public class CoffeeBeanExtractionsListDto {
    private long id;
    private String name;
    private int numExtractions;

    public CoffeeBeanExtractionsListDto() {
    }

    public CoffeeBeanExtractionsListDto(long id, String name, int numExtractions) {
        this.id = id;
        this.name = name;
        this.numExtractions = numExtractions;
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

    public int getNumExtractions() {
        return numExtractions;
    }

    public void setNumExtractions(int numExtractions) {
        this.numExtractions = numExtractions;
    }
}
