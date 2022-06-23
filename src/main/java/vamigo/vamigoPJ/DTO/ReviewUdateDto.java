package vamigo.vamigoPJ.DTO;

import lombok.Builder;

public class ReviewUdateDto {

    private Integer rating;
    private String comment;


    @Builder
    public ReviewUdateDto(Integer rating, String  comment){
        this.rating = rating;
        this.comment = comment;
    }
}
