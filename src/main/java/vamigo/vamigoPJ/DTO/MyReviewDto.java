package vamigo.vamigoPJ.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class MyReviewDto {
    private Long uid;

    public MyReviewDto(long uid){
        this.uid = uid;
    }
}
