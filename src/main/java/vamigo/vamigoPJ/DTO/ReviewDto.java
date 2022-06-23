package vamigo.vamigoPJ.DTO;

import lombok.*;
import vamigo.vamigoPJ.entity.Likes;
import vamigo.vamigoPJ.entity.Reply;
import vamigo.vamigoPJ.entity.Review;
import vamigo.vamigoPJ.entity.User;
import vamigo.vamigoPJ.repository.UserRepository;

import javax.persistence.*;
import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
public class ReviewDto {

    UserRepository userRepository;
    private Long uid ;
    private User user;
    private Integer rating;
    private Integer workId;
    private String workName;
    private String comment;
    private Reply reply;
    private String workCategory;
    private String[] image;
    private Boolean spoiler;
    private List<Reply> replyList;
    private Integer likes;
    private Likes likesList;
    private Float lat;
    private Float lng;




    @Builder
    public ReviewDto(Long id,User user,Integer workId ,Integer rating, String work, String comment, String category, Boolean spoiler,String[] image,Float lat, Float lng){
        this.uid = id;
        this.user = user;
        this.rating = rating;
        this.workId = workId;
        this.workName = work;
        this.comment = comment;
        this.workCategory = category;
        this.spoiler = spoiler;
        this.likes = 0;
        this.image = image;
        this.likesList = null;
        this.replyList = null;
        this.lat = lat;
        this.lng = lng;
    }
    public Review toEntity(){
        return Review.builder()
                .user(user)
                .rating(rating)
                .workId(workId)
                .work(workName)
                .comment(comment)
                .category(workCategory)
                .spoiler(spoiler)
                .image(image)
                .lat(lat)
                .lng(lng)
                .build();
    }
}
