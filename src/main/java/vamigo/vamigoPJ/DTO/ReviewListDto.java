package vamigo.vamigoPJ.DTO;

import lombok.Builder;
import vamigo.vamigoPJ.entity.Review;

import java.util.List;

public class ReviewListDto {
    private List<Review> review;


    @Builder
    public ReviewListDto(List review) {
        this.review = review;

    }
}
