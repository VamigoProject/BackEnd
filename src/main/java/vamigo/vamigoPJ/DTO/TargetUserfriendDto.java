package vamigo.vamigoPJ.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class TargetUserfriendDto {
    private Long uid;
    private String nickname;
    private String profile;
    private String introduce;
    private Boolean isFollower;
    private Boolean isFollowing;
    private Boolean blocked = false;

    @Builder
    public TargetUserfriendDto(Long uid, String nickname, String profile, String introduce, Boolean isFollower, Boolean isFollowing) {
        this.uid = uid;
        this.nickname = nickname;
        this.profile = profile;
        this.introduce = introduce;
        this.isFollower = isFollower;
        this.isFollowing = isFollowing;
        this.blocked = false;
    }
}
