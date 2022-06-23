package vamigo.vamigoPJ.DTO.Response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ResponseReplyUserDto {
    private Long uid;
    private String nickname;
    private String profile;

    @Builder
    public ResponseReplyUserDto(Long uid, String nickname, String profile) {
        this.uid = uid;
        this.nickname = nickname;
        this.profile = profile;
    }
}
