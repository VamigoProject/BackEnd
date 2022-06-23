package vamigo.vamigoPJ.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Table(name = "image")
@NoArgsConstructor
public class Image  {
    @Id
    @Column
    private Long id;

    @Column
    @Lob
    private String image;

    @Builder
    public Image(Long id,String image){
        this.id = id;
        this.image = image;

    }

}
