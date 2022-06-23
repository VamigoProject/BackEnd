package vamigo.vamigoPJ.DTO.Response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@NoArgsConstructor
@Setter
public class ResponseMyfriend {
    private List<ResponseReplyUserDto> follower = new ArrayList<>();
    private List<ResponseReplyUserDto> following = new ArrayList<>();
    public void addfollower(ResponseReplyUserDto responseReplyUserDto){
        follower.add(responseReplyUserDto);
    }
    public void addfollowing(ResponseReplyUserDto responseReplyUserDto){
        following.add(responseReplyUserDto);
    }
}
