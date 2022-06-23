package vamigo.vamigoPJ.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="user")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private long uid;


    @NotNull
    private String mail;

    //남자 여자 비밀

    @NotNull
    private String sex;


    @NotNull
    private String password;


    @NotNull
    private String mbti;


    @NotNull
    private Integer year;


    @ElementCollection
    @Column (name = "interestCategory")
    private List<String> category = new ArrayList<String>();

    @ElementCollection
    @Column (name = "interestGenre")
    private List<String> genre = new ArrayList<String>();



    @NotNull
    private String nickname;

    @Column
    private String refreshToken;

    @Column
    private Boolean mailAuth;

    @Column
    private String code;

    @Column
    @Lob
    private String profile;

    @Column
    private String accessToken;

    @Column
    private String introduce;

    @Column
    private Boolean influencer;


    @JsonIgnoreProperties(value = {"user"})
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "review")
    private List<Review> review = new ArrayList<>();

    @JsonIgnoreProperties(value = {"user"})
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name ="likes")
    private List<Likes> likes = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name ="reply")
    private List<Reply> reply = new ArrayList<>();

    @JsonIgnoreProperties(value = {"user"})
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "follow")
    private List<Follow> follows = new ArrayList<>();

    @Builder
    public User(String mail, String sex, String password, String mbti, Integer year, List<String> category, List<String> genre, String nickname,String code) {
        this.mail = mail;
        this.sex = sex;
        this.password = password;
        this.mbti = mbti;
        this.year = year;
        this.category = category;
        this.genre = genre;
        this.nickname = nickname;
        this.mailAuth = false;
        this.code = code;
        this.profile =null;
        this.accessToken = "hub";
        this.refreshToken = "git";
        this.introduce = "";
        this.likes = null;
        this.review = null;
        this.influencer = false;
        this.reply = null;

    }



    public void addReview(Review review){
        this.review.add(review);
    }
    public void addLikes(Likes likes){
        this.likes.add(likes);
    }
    public void profileUpdate(String nickname, String profile, String introduce){
        this.nickname = nickname;
        this.profile = profile;
        this.introduce = introduce;
    }
    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
    public void resSetCode(String code){this.code = code;}
    public void emailVerifiedSuccess(){
        this.mailAuth = true;
    }
    public void updatePassword(String password){ this.password = password;}
}
