package vamigo.vamigoPJ.DTO.Response;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vamigo.vamigoPJ.DTO.TargetUserfriendDto;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@NoArgsConstructor
@Setter
public class ResponseTargetUidFriend {
    private TargetUserfriendDto user;
    private List<ResponseReplyUserDto> follower = new ArrayList<>();
    private List<ResponseReplyUserDto> following = new ArrayList<>();
    public void addfollower(ResponseReplyUserDto responseReplyUserDto){
        follower.add(responseReplyUserDto);
    }
    public void addfollowing(ResponseReplyUserDto responseReplyUserDto){
        following.add(responseReplyUserDto);
    }
}
