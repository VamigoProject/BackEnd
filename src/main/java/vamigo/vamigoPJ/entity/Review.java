package vamigo.vamigoPJ.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Table(name = "review")
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rid")
    private Long id;

    @JsonIgnoreProperties(value = {"review"})
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name ="user_id", updatable = false)
    private User user;

    @Column
    private Integer rating;


    @Column
    private Integer workid;

    @Column
    private String work;

    @Column
    private String comment;

    @JsonIgnoreProperties(value = {"review"})
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name ="reply")
    private List<Reply> Reply = new ArrayList<Reply>();

    @Column
    private String category;

    @Column
    private Boolean spoiler;

    @Column(precision = 9, scale = 6)
    //위도
    private Float lat;


    @Column(precision = 9, scale = 6)
    //경도
    private Float lng;


    @Column
    @Lob
    private String[] image;

    @Column
    private Integer likes = 0;

    @JsonIgnoreProperties(value = {"review"})
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name ="likes_id")
    private List<Likes> likesList;


    @Builder
    public Review (User user, Integer rating,Integer workId ,String work, String comment,String category, Boolean spoiler, String[] image,Float lat,Float lng, Integer like,List<Likes> likesList,List<Reply> reply){
        this.user = user;
        this.rating = rating;
        this.work = work;
        this.comment = comment;
        this.Reply = reply;
        this.workid = workId;
        this.category = category;
        this.spoiler = spoiler;
        this.lat = lat;
        this.lng = lng;
        this.image = image;
        this.likesList = likesList;
        this.likes = 0;

    }

    public void UpdateReview(Integer rating, String comment, String[] image, Boolean spoiler,Float lat, Float lng){
        this.rating = rating;
        this.comment = comment;
        this.image = image;
        this.spoiler = spoiler;
        this.lat = lat;
        this.lng = lng;
    }

    public Likes createLikesLiset(Likes likes){
        this.likesList.add(likes);
        return likes;
    }

    public void addReply(Reply reply) {
        this.Reply.add(reply);
    }
    public void DoLikes(int size){
        this.likes = size;
    }
    public void setSpoiler (Boolean spoiler){
        this.spoiler = spoiler;
    }
    public void UndoLike(int size){
        this.likes = size;
    }




}
