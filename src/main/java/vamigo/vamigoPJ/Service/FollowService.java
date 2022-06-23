package vamigo.vamigoPJ.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vamigo.vamigoPJ.DTO.Request.RequestUid;
import vamigo.vamigoPJ.DTO.Request.Requestfollow;
import vamigo.vamigoPJ.DTO.Response.ResponseMyfriend;
import vamigo.vamigoPJ.DTO.Response.ResponseReplyUserDto;
import vamigo.vamigoPJ.entity.Follow;
import vamigo.vamigoPJ.entity.User;
import vamigo.vamigoPJ.repository.FollowRepository;
import vamigo.vamigoPJ.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;


    public ResponseEntity followList(RequestUid requestUid){
        Optional<User> user = userRepository.findById(requestUid.getUid());
        List<Follow> followList = followRepository.findByTargetUid(user.get());
        List<Follow> followingList = followRepository.findByMyUid(user.get());
        ResponseMyfriend responseMyfriend = new ResponseMyfriend();
        for(int i = 0; i < followList.size(); i++){
            responseMyfriend.addfollower(ResponseReplyUserDto.builder().uid(followList.get(i).getMyUid().getUid())
                    .nickname(followList.get(i).getMyUid().getNickname())
                    .profile(followList.get(i).getMyUid().getProfile()).build());
        }
        for(int i = 0; i < followingList.size(); i++){
            responseMyfriend.addfollowing(ResponseReplyUserDto.builder().uid(followingList.get(i).getTargetUid().getUid())
                    .nickname(followingList.get(i).getTargetUid().getNickname())
                    .profile(followingList.get(i).getTargetUid().getProfile()).build());
        }
        return new ResponseEntity(responseMyfriend,HttpStatus.OK);

    }




    public ResponseEntity<String> followUser(Requestfollow requestfollow){
        Optional<User> user = userRepository.findByUid(requestfollow.getMyUid());
        Optional<User> targetuser = userRepository.findById(requestfollow.getTargetUid());
        if(user.isPresent() && targetuser.isPresent()) {
            List<Follow> follow = followRepository.findByMyUid(user.get());
            if (!follow.isEmpty()) {
                for(int i = 0; i < follow.size(); i++) {
                    if (follow.get(i).getTargetUid().equals(targetuser.get())) {
                        return new ResponseEntity<String>("이미 팔로우 된 회원입니다.", HttpStatus.BAD_REQUEST);
                    }
                }
                followRepository.save(Follow.builder()
                        .myUid(user.get())
                        .targetUid(targetuser.get())
                        .build());

            } else {
                followRepository.save(Follow.builder()
                        .myUid(user.get())
                        .targetUid(targetuser.get())
                        .build());
            }
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity<String> unfollowUser(Requestfollow requestfollow){
        Optional<User> user = userRepository.findByUid(requestfollow.getMyUid());
        Optional<User> targetuser = userRepository.findById(requestfollow.getTargetUid());
        if(user.isPresent()&& targetuser.isPresent()) {
            List<Follow> follow = followRepository.findByMyUid(user.get());
            if (!follow.isEmpty()) {
                for(int i = 0 ; i < follow.size(); i++) {
                    if (follow.get(i).getTargetUid().equals(targetuser.get())) {
                        followRepository.delete(follow.get(i));
                        return new ResponseEntity(HttpStatus.OK);
                    }
                }
            }
            return new ResponseEntity<String>("팔로우 되어 있지 않습니다.",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("잘못된 요청입니다.",HttpStatus.BAD_REQUEST);
    }

}
