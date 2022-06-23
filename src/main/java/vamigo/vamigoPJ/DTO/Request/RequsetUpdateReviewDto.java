package vamigo.vamigoPJ.DTO.Request;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RequsetUpdateReviewDto {
    private Long uid;
    private Long reviewId;
    private Integer rating;
    private String comment;
    private String[] image;
    private Boolean spoiler;
    private Float lat;
    private Float lng;

    @Builder
    public RequsetUpdateReviewDto(Long uid, Long reviewId, Integer rating, String comment, String[] image, Boolean spoiler, Float lat, Float lng) {
        this.uid = uid;
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
        this.image = image;
        this.spoiler = spoiler;
        this.lat = lat;
        this.lng = lng;
    }
}
