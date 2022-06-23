package vamigo.vamigoPJ.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "likes")
@NoArgsConstructor
@AllArgsConstructor
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @JsonIgnoreProperties(value = {"likes"})
    @ManyToOne
    @JoinColumn(name ="review_id", updatable = false)
    private Review review;

    @JsonIgnoreProperties(value = {"likes"})
    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private User user;




    @Builder
    public Likes(Review review, User user){
        this.review = review;
        this.user= user;

    }




}
