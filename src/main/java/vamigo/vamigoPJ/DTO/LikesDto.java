package vamigo.vamigoPJ.DTO;

import lombok.*;
import vamigo.vamigoPJ.entity.Review;
import vamigo.vamigoPJ.entity.User;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LikesDto {
    private List<User> user =new ArrayList<>();
    private Review review;

    @Builder
    public LikesDto(Review review){
        this.review = review;
        this.user =null;
    }

}
