package vamigo.vamigoPJ.DTO.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vamigo.vamigoPJ.entity.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ResponseUserDto {
    private String mail;
    private String mbti;
    private String nickname;
    private String password;
    private String sex;
    private int year;
    private List<String> category = new ArrayList<String>();
    private List<String> genre = new ArrayList<String>();
    private Boolean mailAuth;
    private String code;
    //entity->dto
    @Builder
    public ResponseUserDto(User entity){
        this.mail = entity.getMail();
        this.mbti = entity.getMbti();
        this.nickname = entity.getNickname();
        this.password = entity.getPassword();
        this.sex = entity.getSex();
        this.year = entity.getYear();
        this.category = entity.getCategory();
        this.genre = entity.getGenre();
        this.mailAuth = entity.getMailAuth();
        this.code =entity.getCode();
    }
}
