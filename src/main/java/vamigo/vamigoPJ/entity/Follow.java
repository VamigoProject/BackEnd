package vamigo.vamigoPJ.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "follow")
@NoArgsConstructor

public class Follow extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @JsonIgnoreProperties(value ={"follow"})
    @ManyToOne
    @JoinColumn(name = "myUid", updatable = false)
    private User myUid;


    @JsonIgnoreProperties(value = {"follow"})
    @ManyToOne
    @JoinColumn(name ="targetUid",updatable = false)
    private User targetUid;




    @Builder
    public Follow( User myUid, User targetUid) {
        this.myUid = myUid;
        this.targetUid = targetUid;
    }

}
