package vamigo.vamigoPJ.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vamigo.vamigoPJ.DTO.Response.ResponseSearchUser;
import vamigo.vamigoPJ.entity.Follow;
import vamigo.vamigoPJ.entity.User;
import vamigo.vamigoPJ.repository.FollowRepository;
import vamigo.vamigoPJ.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {
    private final UserRepository userRepository;

    private final FollowRepository followRepository;

    @Transactional
    public ResponseEntity Search_User(Long uid, String nickname) {
        //검색 결과
        List<User> SearchList = userRepository.findByNicknameContain(nickname);
        Optional<User> MyUser = userRepository.findById(uid);
        List<ResponseSearchUser> responseSearchUserList = new ArrayList<>();
        boolean isFollower = false; //상대방이 나를 팔로우 하고 있는지
        boolean isFollowing = false; //내가 상대방을 팔로우 하고 있는지
        //검색 결과가 있고
        if(!SearchList.isEmpty()){
            log.info("SearchList = {}", SearchList);
            //검색한 사람이 존재한다면
            if(MyUser.isPresent()){
                //검색 결과 목록 유저의 팔로우 리스트를 하나씩 불러와서 검사
                for(int i = 0 ; i < SearchList.size(); i++ ){
                    List<Follow> MyUserFollow = followRepository.findByMyUid(MyUser.get());
                    List<Follow> targetUserFollow = followRepository.findByMyUid(SearchList.get(i));
                    if(!MyUserFollow.isEmpty() && !targetUserFollow.isEmpty()) {
                        for (int u = 0; u < targetUserFollow.size(); u++) {
                            if (targetUserFollow.get(u).getTargetUid().equals(MyUser.get())){
                                isFollower = true;
                                break;
                            }
                        }
                        for (int j = 0; j < MyUserFollow.size(); j++) {
                            if (MyUserFollow.get(j).getTargetUid().equals(SearchList.get(i))) {
                                isFollowing = true;
                                break;
                            }
                        }

                    }
                    else if(!MyUserFollow.isEmpty() & targetUserFollow.isEmpty()){
                        for (int j = 0; j < MyUserFollow.size(); j++) {
                            if (MyUserFollow.get(j).getTargetUid().equals(SearchList.get(i))) {
                                isFollowing = true;
                                break;
                            }
                        }
                    }
                    else if(!targetUserFollow.isEmpty()){
                        for (int u = 0; u < targetUserFollow.size(); u++) {
                            if (targetUserFollow.get(u).getTargetUid().equals(MyUser.get())){
                                isFollower = true;
                                break;
                            }
                        }
                    }
                    responseSearchUserList.add(ResponseSearchUser.builder()
                            .uid(SearchList.get(i).getUid())
                            .nickname(SearchList.get(i).getNickname())
                            .profile(SearchList.get(i).getProfile())
                            .isFollower(isFollower).isFollowing(isFollowing).build());
                    isFollower = false;
                    isFollowing = false;
                }

            }
        }
        if(!responseSearchUserList.isEmpty()) {
            for (int i = 0; i < responseSearchUserList.size(); i++) {
                if (responseSearchUserList.get(i).getUid().equals(MyUser.get().getUid())) {
                    responseSearchUserList.remove(i);
                    break;
                }

            }
        }

        return new ResponseEntity(responseSearchUserList,HttpStatus.OK);

    }
        /*//검색결과
        List<User> UserList = userRepository.findByNicknameContain(nickname);
        log.info("Search_result = {}", UserList);
        Optional<User> SearchedUser = userRepository.findByNickname(nickname);
        //검색한 사람
        Optional<User> MyUser = userRepository.findById(uid);

        //반환값
        List<ResponseSearchUser> responseSearchUserList = new ArrayList<>();
        boolean isFollower = false;
        boolean isFollowing = false;
        //검색한 사람의 팔로우 DB
        List<Follow> follow = followRepository.findByMyUid(MyUser.get());

        if (!UserList.isEmpty()) {
            log.info("!UserList.isEmpty()");
            for (int i = 0; i < UserList.size(); i++) {
                log.info("i = {} UserList.size() = {}", i , UserList.size());
                //자신이 검색리스트에 포함되어있는것 방지
                if (UserList.get(i).getUid() == MyUser.get().getUid()) {
                    UserList.remove(i);
                    i -= 1;
                    continue;
                }
                //팔로우한 사람이 존재하고
                if (!follow.isEmpty()) {
                    //내가 검색한 사람의 검색 결과중 1명이 팔로잉 하는 사람 이고
                    for(int u = 0; u < follow.size() ; u++)
                    if (follow.get(u).getTargetUid().equals(UserList.get(i))) {
                        log.info("//내가 검색한 사람의 검색 결과중 1명이 팔로잉 하는 사람 이고");
                        for (int j = 0; j < follow.size(); j++) {
                            //검색 당한 사람
                            Optional<User> targetUser = userRepository.findById(follow.get(u).getTargetUid().getUid());
                            if (targetUser.isPresent()) {
                                //검색 당한 사람의 팔로우 DB
                                List<Follow> targetfollow = followRepository.findByMyUid(targetUser.get());
                                if (!targetfollow.isEmpty()) {
                                    //검색 당한 사람이 나를 팔로우 했다면
                                    for(int )
                                    if (targetfollow.get().getTargetUid().contains(MyUser.get())) {
                                        responseSearchUserList.add(ResponseSearchUser.builder()
                                                .uid(UserList.get(i).getUid())
                                                .nickname(UserList.get(i).getNickname())
                                                .profile(UserList.get(i).getProfile())
                                                .isFollwer(true).isFollwing(true).build());
                                    } else {
                                        responseSearchUserList.add(ResponseSearchUser.builder()
                                                .uid(UserList.get(i).getUid())
                                                .nickname(UserList.get(i).getNickname())
                                                .profile(UserList.get(i).getProfile())
                                                .isFollwer(false).isFollwing(true).build());
                                    }
                                } else {
                                    responseSearchUserList.add(ResponseSearchUser.builder()
                                            .uid(UserList.get(i).getUid())
                                            .nickname(UserList.get(i).getNickname())
                                            .profile(UserList.get(i).getProfile())
                                            .isFollwer(false).isFollwing(true).build());
                                }
                                //검색 당한 사람이 나를 팔로우 하지 않았다면

                            }
                        }
                    }
                    //검색한 사람의 검색 결과중 1명이 내가 팔로잉 하는 사람이 아니고
                    else {
                        log.info("검색한 사람의 검색 결과중 1명이 내가 팔로잉 하는 사람이 아니고");
                        for (int j = 0; j < follow.get().getTargetUid().size(); j++) {
                            //검색 당한 사람
                            Optional<User> targetUser = userRepository.findById(follow.get().getTargetUid().get(j).getUid());
                            if (targetUser.isPresent()) {
                                //검색 당한 사람의 팔로우 DB
                                Optional<Follow> targetfollow = followRepository.findByMyUid(targetUser.get());
                                //검색 당한 사람이 나를 팔로우 했다면
                                if(targetfollow.isPresent()) {
                                    if (targetfollow.get().getTargetUid().equals(MyUser.get())) {
                                        responseSearchUserList.add(ResponseSearchUser.builder()
                                                .uid(UserList.get(i).getUid())
                                                .nickname(UserList.get(i).getNickname())
                                                .profile(UserList.get(i).getProfile())
                                                .isFollwer(true).isFollwing(false).build());
                                    }
                                    //검색 당한 사람이 나를 팔로우 하지 않았다면
                                    else {
                                        responseSearchUserList.add(ResponseSearchUser.builder()
                                                .uid(UserList.get(i).getUid())
                                                .nickname(UserList.get(i).getNickname())
                                                .profile(UserList.get(i).getProfile())
                                                .isFollwer(false).isFollwing(false).build());
                                    }
                                }
                            } else {
                                responseSearchUserList.add(ResponseSearchUser.builder()
                                        .uid(UserList.get(i).getUid())
                                        .nickname(UserList.get(i).getNickname())
                                        .profile(UserList.get(i).getProfile())
                                        .isFollwer(false).isFollwing(false).build());
                            }
                        }
                    }
                }
                //내가 팔로우한 사람이 존재하지 않고
                else {
                    //검색 당한 사람 중에서
                    Optional<Follow> targetUserFollow = followRepository.findByMyUid(UserList.get(i));
                    //검색 당한 사람이 팔로잉 하는 유저가 있다면
                    if (targetUserFollow.isPresent()) {
                        //검색 당한 사람이 나를 팔로잉 한다면
                        if (targetUserFollow.get().getTargetUid().equals(MyUser.get())) {
                            responseSearchUserList.add(ResponseSearchUser.builder()
                                    .uid(UserList.get(i).getUid())
                                    .nickname(UserList.get(i).getNickname())
                                    .profile(UserList.get(i).getProfile())
                                    .isFollwer(true).isFollwing(false).build());
                        } else {
                            responseSearchUserList.add(ResponseSearchUser.builder()
                                    .uid(UserList.get(i).getUid())
                                    .nickname(UserList.get(i).getNickname())
                                    .profile(UserList.get(i).getProfile())
                                    .isFollwer(false).isFollwing(false).build());
                        }
                    } else {
                        responseSearchUserList.add(ResponseSearchUser.builder()
                                .uid(UserList.get(i).getUid())
                                .nickname(UserList.get(i).getNickname())
                                .profile(UserList.get(i).getProfile())
                                .isFollwer(false).isFollwing(false).build());
                    }


                }
            }

        }
        return new ResponseEntity<List>(responseSearchUserList, HttpStatus.OK);

    }
    */

}