package vamigo.vamigoPJ.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileDto {
    private Long uid;
    private String nickname;
    private String profile;
    private String introduce;

    @Builder
    public UpdateProfileDto (Long uid, String nickname, String profile, String introduce){
        this.uid = uid;
        this.nickname = nickname;
        this.profile = profile;
        this.introduce = introduce;
    }


}
