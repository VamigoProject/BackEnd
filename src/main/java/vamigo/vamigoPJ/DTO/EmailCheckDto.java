package vamigo.vamigoPJ.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailCheckDto {


    private String mail;

    private String code;
}
