package vamigo.vamigoPJ.DTO.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseLoginDto {
    private Long uid;
    private String nickname;
    private String profile;
    private String introduce;
    private String accessToken;
    private String refreshToken;

    @Builder
    public ResponseLoginDto(Long uid, String nickname, String profile,String introduce, String accessToken, String refreshToken ){
        this.uid = uid;
        this.nickname = nickname;
        this.profile = profile;
        this.introduce = introduce;
        this.accessToken = accessToken;
        this. refreshToken = refreshToken;
    }
}
