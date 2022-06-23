package vamigo.vamigoPJ.DTO.Response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TrendReview {
    private Integer id;
    private String name;
    private String category;

    @Builder
    public TrendReview(Integer id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }
}
