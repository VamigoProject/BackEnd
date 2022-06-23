package vamigo.vamigoPJ.DTO.Response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vamigo.vamigoPJ.DTO.TargetUserStatistics;
import vamigo.vamigoPJ.DTO.TargetUserfriendDto;

import java.util.List;

@Data
@Getter
@NoArgsConstructor
public class ResponseTargetUidStatistics {
    private TargetUserfriendDto user;

    private List<TargetUserStatistics> StatisticsList;

    public void setUser(TargetUserfriendDto user) {
        this.user = user;
    }

    public void setTargetUserStatisticsList(List<TargetUserStatistics> statistics){
        this.StatisticsList = statistics;
    }
}
