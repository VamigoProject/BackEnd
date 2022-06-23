package vamigo.vamigoPJ.entity;


import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name ="EmailCheck")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class EmailCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @NotNull
    private String mail;
    @NotNull
    private String code;

    @Builder
    public EmailCheck(String mail, String code){
        this.mail =mail;
        this.code = code;
    }

    public void reSetCode(String code){this.code = code;}
}
