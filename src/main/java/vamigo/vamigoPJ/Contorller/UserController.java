package vamigo.vamigoPJ.Contorller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vamigo.vamigoPJ.DTO.*;
import vamigo.vamigoPJ.DTO.Request.*;
import vamigo.vamigoPJ.DTO.Response.MyReviewResponseDto;
import vamigo.vamigoPJ.DTO.Response.ResponseLoginDto;
import vamigo.vamigoPJ.DTO.Response.ResponseMyStatistics;
import vamigo.vamigoPJ.DTO.Response.ResponseTargetUidStatistics;
import vamigo.vamigoPJ.Service.*;
import vamigo.vamigoPJ.entity.Review;
import vamigo.vamigoPJ.repository.LikesRepository;
import vamigo.vamigoPJ.repository.ReplyRepository;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Controller
@DynamicUpdate
public class UserController {


    @Autowired
    private final ImageService imageService;

    @Autowired
    private final UserService userService;

    @Autowired
    private final ReviewService reviewService;
    @Autowired
    private final JavaMailSender javaMailSender;

    @Autowired
    private final SearchService searchService;

    @Autowired
    private final LikesRepository likesRepository;

    @Autowired
    private final ReplyRepository replyRepository;

    @Autowired
    private final FollowService followService;


    private final HttpSession session;


    @PostMapping("/follow")
    @ResponseBody
    public ResponseEntity follow(@RequestBody Requestfollow requestfollow){
        return followService.followUser(requestfollow);
    }

    @PostMapping("/unfollow")
    @ResponseBody
    public ResponseEntity unfollow(@RequestBody Requestfollow requestfollow){
        return followService.unfollowUser(requestfollow);
    }

    @PostMapping("/search")
    @ResponseBody
    public ResponseEntity searchUser(@RequestBody RequestSearchUser requestSearchUser){
        return searchService.Search_User(requestSearchUser.getUid(), requestSearchUser.getNickname());
    }

    @PostMapping("/mailauth")
    public ResponseEntity sendEmail(@RequestBody EmailCheckDto request){
        log.info("mail = {}",request.getMail());
        Random random = new Random();
        String key="";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vamigoauth@gmail.com");
        message.setTo(request.getMail());
        for(int i = 0; i<3; i++){
            int index = random.nextInt(25)+65;
            key+=(char)index;
        }
        int numIndex= random.nextInt(9999)+1000;
        key+= numIndex;

        message.setSubject("인증번호 입력을 위한 메일 전송");
        message.setText("인증번호 : " +key);

        if(userService.sendMail(request.getMail(),key).equals("Success")){

            javaMailSender.send(message);
            log.info("key = {}",key);
            return new ResponseEntity(HttpStatus.OK);
        }
        else return new ResponseEntity("이미 회원가입된 메일입니다.",HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody LoginDto loginDto){
        String result = userService.LoginCheck(loginDto);

        log.info("mail = {} password = {}",loginDto.getMail(),loginDto.getPassword());
        if(result.equals("Success")){
            ResponseLoginDto response = userService.Login(loginDto);
            return new ResponseEntity<ResponseLoginDto>(response,HttpStatus.OK);
        }
        else if(result.equals("No User")){
            return new ResponseEntity<>("미등록된 사용자 메일입니다.", HttpStatus.BAD_REQUEST);
        }
        else if(result.equals("Not match")){
            return new ResponseEntity<>("메일이나 비밀번호를 재확인 해주세요.",HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>("잘못된 요청입니다.",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity signup(@RequestBody SaveRequestUserDto user){
        String result = userService.emailAuth(user);
        if(result.equals("Success")){
                return new ResponseEntity<>(HttpStatus.OK);
        }
        else if(result.equals("dup id")) {
            return new ResponseEntity("중복된 메일입니다.", HttpStatus.BAD_REQUEST);
        }
        else if(result.equals("not match code")){
            return new ResponseEntity("인증코드를 재확인 해주세요", HttpStatus.BAD_REQUEST);
        }
        else if(result.equals("no authed")){
            return new ResponseEntity("인증코드를 받아주세요", HttpStatus.BAD_REQUEST);
        }
        else
            return new ResponseEntity("잘못된 요청입니다", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity deleteUser(@RequestBody RequestDeleteUserDto requestDeleteUserDto){
        String result = userService.deleteUser(requestDeleteUserDto);
        if(result.equals("Success")){
            return new ResponseEntity("회원 삭제가 정상적으로 되었습니다.",HttpStatus.OK);
        }
        else return  new ResponseEntity("비밀번호가 다릅니다.",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/profile/update")
    @ResponseBody
    public ResponseEntity updateProfile(@RequestBody UpdateProfileDto updateProfileDto){
        log.info("uid = {} , nickname = {} , profile = {} introduce = {} ",updateProfileDto.getUid(), updateProfileDto.getNickname(), updateProfileDto.getProfile(),updateProfileDto.getIntroduce() );
        String result = userService.updateProfile(updateProfileDto);
        if(result.equals("Success")){
            return new ResponseEntity(HttpStatus.OK);
        }
        else return new ResponseEntity("잘못된 요청입니다.",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/profile/myreview")
    @ResponseBody
    public ResponseEntity myreview(@RequestBody MyReviewDto myReviewDto){
        List<Review> review = reviewService.myreview(myReviewDto);
        if(!review.isEmpty()) {
            List<MyReviewResponseDto> myReviewResponseDtoList;
            myReviewResponseDtoList = reviewService.isLikedCheck(myReviewDto, review);
            return new ResponseEntity<>(myReviewResponseDtoList, HttpStatus.OK);
        }
        else return new ResponseEntity("None",HttpStatus.OK);


    }

    @PostMapping("/profile/myprofile")
    @ResponseBody
    public ResponseEntity MyProfileUpdate(@RequestBody RequestUid requestUid){
        return userService.MyprofileUpdate(requestUid);
    }

    @PostMapping("/profile/myfriend")
    @ResponseBody
    public ResponseEntity myfriend(@RequestBody RequestUid requestUid){
        return followService.followList(requestUid);
    }

    @PostMapping("/profile/statistics")
    @ResponseBody
    public ResponseEntity MyProfileStatistics(@RequestBody RequestUid requestUid){
        ResponseMyStatistics responseMyStatistics = new ResponseMyStatistics();
        responseMyStatistics.setStatisticsList(userService.MyStatistics(requestUid.getUid()));
        return new ResponseEntity(responseMyStatistics,HttpStatus.OK);
    }

    @PostMapping("/{targetId}/statistics")
    @ResponseBody
    public ResponseEntity TargetProfileStatistics(@RequestBody RequestTargetUid requestTargetUid,@PathVariable(value = "targetId") Long targetId){
        requestTargetUid.setTargetId(targetId);
        ResponseTargetUidStatistics responseTargetUidStatistics = new ResponseTargetUidStatistics();
        responseTargetUidStatistics.setTargetUserStatisticsList(userService.TargetUserStatistics(requestTargetUid.getUid(),targetId));
        responseTargetUidStatistics.setUser(userService.getTargetUidProfile(requestTargetUid));

        return new ResponseEntity(responseTargetUidStatistics, HttpStatus.OK);

    }



    @PostMapping("/{targetId}/profile")
    @ResponseBody
    public ResponseEntity targetProfile(@RequestBody RequestTargetUid requestTargetUid
            ,@PathVariable(value = "targetId") Long targetId) {
        requestTargetUid.setTargetId(targetId);
        return userService.targetUidProfile(requestTargetUid);
    }

    @PostMapping("/{targetId}/friend")
    @ResponseBody
    public ResponseEntity targetProfilefriend(@RequestBody RequestTargetUid requestTargetUid,
                                              @PathVariable(value = "targetId") Long targetId){
        requestTargetUid.setTargetId(targetId);
        return userService.targetUidProfilefriend(requestTargetUid);
    }

    @PostMapping("/{targetId}/review")
    @ResponseBody
    public ResponseEntity targetProfileReview(@RequestBody RequestTargetUid requestTargetUid,
                                              @PathVariable(value = "targetId") Long targetId){
        requestTargetUid.setTargetId(targetId);

        return userService.targetUidReview(requestTargetUid);
    }


    @PostMapping("/profile/like")
    @ResponseBody
    public ResponseEntity MyLikeReview(@RequestBody MyReviewDto myReviewDto){
        return userService.myLikeReview(myReviewDto);
    }

    @PostMapping("/{targetId}/like")
    @ResponseBody
    public ResponseEntity TargetLikeReview(@RequestBody RequestTargetUid requestTargetUid,
                                           @PathVariable(value = "targetId") Long targetId){
        requestTargetUid.setTargetId(targetId);

        return userService.TargetLikeReview(requestTargetUid);
    }


    @PostMapping("/check")
    @ResponseBody
    public ResponseEntity mailDupCheck(@RequestBody RequestMailAuthDto requestMailAuthDto){
        //imageService.ImageToDB();
        if(userService.userUnDuplicateMail(requestMailAuthDto.getMail())){
            return new ResponseEntity(HttpStatus.OK);
        }
        else return new ResponseEntity("회원가입 되어 있는 메일입니다.",HttpStatus.BAD_REQUEST);
    }






}
