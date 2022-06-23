package vamigo.vamigoPJ.DTO.Request;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PasswordUpdateDto {
    private Long uid;
    private String beforePassword;
    private String afterPassword;
}
