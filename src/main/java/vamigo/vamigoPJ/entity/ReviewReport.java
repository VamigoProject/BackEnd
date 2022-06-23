package vamigo.vamigoPJ.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "review_report")
@NoArgsConstructor
@AllArgsConstructor
public class ReviewReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rrid")
    private Long rrid;

    @JsonIgnoreProperties(value = {"review_report"})
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name ="user_id", updatable = false)
    private User user;

    @JsonIgnoreProperties(value = {"review_report"})
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(name = "ero")
    private Boolean ero;

    @Column(name = "spoiler")
    private Boolean spoiler;

    @Column(name = "curse")
    private Boolean curse;

    @Column(name = "etc")
    private Boolean etc;

    @Builder
    public ReviewReport(User user, Review review, Boolean ero, Boolean spoiler, Boolean curse, Boolean etc) {
        this.user = user;
        this.review = review;
        this.ero = ero;
        this.spoiler = spoiler;
        this.curse = curse;
        this.etc = etc;
    }

    public void setCategory(Boolean ero, Boolean spoiler, Boolean curse, Boolean etc){
        this.ero = ero;
        this.spoiler = spoiler;
        this.curse = curse;
        this.etc = etc;
    }
}
