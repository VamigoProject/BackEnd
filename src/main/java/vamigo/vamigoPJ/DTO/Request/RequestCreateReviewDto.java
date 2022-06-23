package vamigo.vamigoPJ.DTO.Request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import vamigo.vamigoPJ.DTO.ReviewDto;

@Data
@Getter
@Setter
public class RequestCreateReviewDto {
    private Long uid;
    private String comment;
    private String workName;
    private String workCategory;
    private Integer rating;
    private Boolean spoiler;
    private Integer workId;
    private Float lat;
    private Float lng;

    @Builder
    public RequestCreateReviewDto(Long uid, String comment, String workName, String workCategory, Integer rating, Boolean spoiler, Integer workId,Float lat, Float lng) {
        this.uid = uid;
        this.comment = comment;
        this.workName = workName;
        this.workCategory = workCategory;
        this.rating = rating;
        this.spoiler = spoiler;
        this.workId = workId;
        this.lat = lat;
        this.lng = lng;
    }

    public ReviewDto toEntity(RequestCreateReviewDto requestCreateReviewDto){
        return ReviewDto.builder()
                .id(uid)
                .comment(comment)
                .work(workName)
                .category(workCategory)
                .rating(rating)
                .spoiler(spoiler)
                .workId(workId)
                .lat(lat)
                .lng(lng)
        .build();

    }


}
