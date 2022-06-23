package vamigo.vamigoPJ.DTO.Request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RequestDoLikeDto {

    private Long reviewId;
    private Long uid;

    @Builder
    public RequestDoLikeDto(Long reviewId, Long uid) {
        this.reviewId = reviewId;
        this.uid = uid;
    }
}
