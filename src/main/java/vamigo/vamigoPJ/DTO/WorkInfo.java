package vamigo.vamigoPJ.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
public class WorkInfo {
    private String image;
    private String name;
    private String[] genre;
    private Float rating;
    private String category;


    public WorkInfo(String name, String category, Float rating) {
        this.image = null;
        this.name = name;
        this.genre =null;
        this.rating = rating;
        this.category = category;
    }

    public WorkInfo(String image, String name, String category, Float rating) {
        this.image = image;
        this.name = name;
        this.rating = rating;
        this.genre = null;
        this.category = category;
    }
}
