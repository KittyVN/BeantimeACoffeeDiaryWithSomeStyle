package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import java.time.LocalDateTime;

public class ExtractionListDto {
    private Long id;
    private LocalDateTime dateTime;
    private String beanName;
    private Integer rating;

    public ExtractionListDto() {
    }

    public ExtractionListDto(Long id, LocalDateTime dateTime, String beanName, Integer rating) {
        this.id = id;
        this.dateTime = dateTime;
        this.beanName = beanName;
        this.rating = rating;
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

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
