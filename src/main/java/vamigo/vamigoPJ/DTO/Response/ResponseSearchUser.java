package vamigo.vamigoPJ.DTO.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseSearchUser {
    Long uid;
    String nickname;
    String profile;
    Boolean isFollower;
    Boolean isFollowing;

    @Builder
    public ResponseSearchUser(Long uid, String nickname, String profile, Boolean isFollower, Boolean isFollowing) {
        this.uid = uid;
        this.nickname = nickname;
        this.profile = profile;
        this.isFollower = isFollower;
        this.isFollowing = isFollowing;
    }
}
