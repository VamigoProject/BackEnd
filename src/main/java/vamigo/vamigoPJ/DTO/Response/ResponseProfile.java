package vamigo.vamigoPJ.DTO.Response;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ResponseProfile {
    private String nickname;
    private String profile;
    private String introduce;

}
