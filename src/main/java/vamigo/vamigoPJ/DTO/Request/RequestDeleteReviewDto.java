package vamigo.vamigoPJ.DTO.Request;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RequestDeleteReviewDto {
    private Long uid;
    private Long reviewId;

    @Builder
    public RequestDeleteReviewDto(Long uid, Long reviewId) {
        this.uid = uid;
        this.reviewId = reviewId;
    }
}
