package vamigo.vamigoPJ.DTO.Request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@NoArgsConstructor
@Setter
public class RequestTargetUid {
    private Long uid;
    private Long targetId;
}
