package vamigo.vamigoPJ.DTO.Request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RequestReplyDto {
    private Long reviewId;
    private Long uid;
    private String comment;

    @Builder
    public RequestReplyDto(Long reviewId, Long uid, String comment) {
        this.reviewId = reviewId;
        this.uid = uid;
        this.comment = comment;
    }

}
