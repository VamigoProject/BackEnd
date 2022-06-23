package vamigo.vamigoPJ.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TargetUserStatistics {
    private String category;
    private Integer me;
    private Integer you;
}
