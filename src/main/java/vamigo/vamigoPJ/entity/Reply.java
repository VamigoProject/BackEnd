package vamigo.vamigoPJ.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name ="reply")
@Getter
@NoArgsConstructor
public class Reply extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long replyId;

    @JsonIgnoreProperties(value = {"reply"})
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;




    @JsonIgnoreProperties(value = {"reply"})
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @Builder
    public Reply(User user, Review review, String comment){
        this.user = user;
        this.review = review;
        this.comment = comment;
    }

    private String comment;
}
