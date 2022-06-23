package vamigo.vamigoPJ.DTO.Response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vamigo.vamigoPJ.DTO.TargetUserfriendDto;
import vamigo.vamigoPJ.DTO.WorkInfo;

import java.util.List;

@Data
@Getter
@NoArgsConstructor
public class ResponseTargetUidReview {
    private TargetUserfriendDto user;
    private List<MyReviewResponseDto> reviews;

    public void setUser(TargetUserfriendDto user) {
        this.user = user;
    }

    public void setReviews(List<MyReviewResponseDto> reviews) {
        this.reviews = reviews;
    }
}
