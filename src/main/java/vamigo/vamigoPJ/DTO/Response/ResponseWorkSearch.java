package vamigo.vamigoPJ.DTO.Response;

import lombok.*;
import vamigo.vamigoPJ.DTO.WorkInfo;
import vamigo.vamigoPJ.entity.Review;

import java.util.List;

@Data
@Getter
@NoArgsConstructor
public class ResponseWorkSearch {
    private WorkInfo workInfo;
    private List<MyReviewResponseDto> reviews;

    @Builder
    public ResponseWorkSearch(WorkInfo workInfo, List<MyReviewResponseDto> reviews) {
        this.workInfo = workInfo;
        this.reviews = reviews;
    }


    public void setWorkInfo(WorkInfo workInfo) {
        this.workInfo = workInfo;
    }

    public void setReviews(List<MyReviewResponseDto> reviews) {
        this.reviews = reviews;
    }
}
