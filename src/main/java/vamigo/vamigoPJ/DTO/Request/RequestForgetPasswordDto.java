package vamigo.vamigoPJ.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestForgetPasswordDto {
    private String mail;
    private String password;
    private String code;


}
