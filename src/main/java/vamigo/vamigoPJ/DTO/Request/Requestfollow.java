package vamigo.vamigoPJ.DTO.Request;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Requestfollow {
    private long myUid;
    private long targetUid;

    @Builder
    public Requestfollow(Long myUid, Long targetUid) {
        this.myUid = myUid;
        this.targetUid = targetUid;
    }
}
