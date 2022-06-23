package vamigo.vamigoPJ.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@RequiredArgsConstructor
@Table(name= "auth")
@Entity
public class Auth {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Auth(String refreshToken, User user){
        this.refreshToken = refreshToken;
        this.user = user ;
    }
    public void refreshUpdate(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
