package vamigo.vamigoPJ.DTO.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseTargetUidProfile {
    private Long uid;
    private String nickname;
    private String profile;
    private String introduce;
    private Boolean block = false;

    @Builder
    public ResponseTargetUidProfile(Long uid, String nickname, String profile, String introduce) {
        this.uid = uid;
        this.nickname = nickname;
        this.profile = profile;
        this.introduce = introduce;
        this.block = false;
    }
}
