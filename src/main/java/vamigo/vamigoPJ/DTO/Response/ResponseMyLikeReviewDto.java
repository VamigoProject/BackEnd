package vamigo.vamigoPJ.DTO.Response;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import vamigo.vamigoPJ.DTO.MyReviewDto;
import vamigo.vamigoPJ.DTO.TargetUserfriendDto;

import java.util.List;

@Data
@Getter
@Setter
public class ResponseMyLikeReviewDto {

    private List<MyReviewResponseDto> reviews;




    public void setReviews(List<MyReviewResponseDto> reviews) {
        this.reviews = reviews;
    }
}
