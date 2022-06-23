package vamigo.vamigoPJ.DTO.Response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import vamigo.vamigoPJ.DTO.Statistics;
import vamigo.vamigoPJ.DTO.TargetUserStatistics;

import java.util.List;


@Data
@Getter
@Setter
public class ResponseMyStatistics {
    private List<Statistics> StatisticsList;
}
