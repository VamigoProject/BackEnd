package vamigo.vamigoPJ.DTO.Request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RequestDeleteUserDto {
    private Long uid;
    private String password;
}
