package vamigo.vamigoPJ.DTO.Response;

import lombok.*;
import vamigo.vamigoPJ.entity.Reply;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
public class MyReviewResponseDto  {

    private Long reviewId;
    private LocalDateTime time;
    private Long uid;
    private String nickname;
    private String profile;
    private Integer workId;
    private String workName;
    private String workCategory;
    private String comment;
    private Integer rating;
    private String[] image;
    private Float lat;
    private Float lng;
    private Integer likes;
    private Boolean isLiked;
    private Boolean spoiler;
    private List<Reply> reply;


    @Builder
    public MyReviewResponseDto(Long reviewId,Integer workId ,LocalDateTime time, Long uid, String nickname, String profile, String workName, String workCategory, String comment, Integer rating, String[] image, Integer likes,  Boolean spoiler, List<Reply> reply,Boolean isLiked, Float lat, Float lng) {
        this.reviewId = reviewId;
        this.time = time;
        this.uid = uid;
        this.nickname = nickname;
        this.profile = profile;
        this.workId = workId;
        this.workName = workName;
        this.workCategory = workCategory;
        this.comment = comment;
        this.rating = rating;
        this.image = image;
        this.likes = likes;
        this.lat = lat;
        this.lng = lng;
        this.isLiked = isLiked;
        this.spoiler = spoiler;
        this.reply = null;
    }



}
