package vamigo.vamigoPJ.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vamigo.vamigoPJ.entity.User;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Data
public class SaveRequestUserDto {
    private String mail;
    private String mbti;
    private String nickname;
    private String password;
    private String sex;
    private Integer year;
    private List<String> category = new ArrayList<String>();
    private List<String> genre = new ArrayList<String>();
    private String code;
    private String profile;


    @Builder
    public SaveRequestUserDto(String mail, String mbti, String nickname, String password, String sex, Integer year, List<String> category, List<String> genre, String code) {
        this.mail = mail;
        this.mbti = mbti;
        this.nickname = nickname;
        this.password = password;
        this.sex = sex;
        this.year = year;
        this.category = category;
        this.genre = genre;
        this.code  = code;
    }

    //dto-> entity
    public User toEntity(){
        return User.builder()
                .mail(mail)
                .mbti(mbti)
                .nickname(nickname)
                .password(password)
                .sex(sex)
                .year(year)
                .category(category)
                .genre(genre).build();

    }


}
