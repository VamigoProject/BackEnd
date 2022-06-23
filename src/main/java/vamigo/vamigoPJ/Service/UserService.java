package vamigo.vamigoPJ.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import vamigo.vamigoPJ.DTO.*;
import vamigo.vamigoPJ.DTO.Request.*;
import vamigo.vamigoPJ.DTO.Response.*;
import vamigo.vamigoPJ.entity.*;
import vamigo.vamigoPJ.repository.*;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService extends Exception {
    private final UserRepository userRepository;
    private final EmailCheckRepository emailCheckRepository;
    private final JavaMailSender javaMailSender;
    private final FollowRepository followRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;
    private final LikesRepository likesRepository;
    private final ReplyRepository replyRepository;
    private final ReviewReportRepository reviewReportRepository;


    @Transactional
    public String sendMail(String mail, String key){
        final EmailCheck emailCheck = new EmailCheck(mail,key);
        Optional<EmailCheck> findEmail = emailCheckRepository.findByMail(emailCheck.getMail());

        if(userUnDuplicateMail(mail)) {
            if (unDuplicateMail(emailCheck.getMail())) {
                log.info("save key = {}", key);
                emailCheckRepository.save(EmailCheck.builder()
                        .mail(mail)
                        .code(key)
                        .build());
                return "Success";
            } else {
                EmailCheck ec = findEmail.get();
                ec.reSetCode(key);
                return "Success";
            }
        }
        else return "fail";


    }


    @Transactional
    public String signup(SaveRequestUserDto request){
        final SaveRequestUserDto saveRequestUserDto = new SaveRequestUserDto(request.getMail(),request.getPassword(),request.getNickname(),request.getSex(),request.getMbti()
                ,request.getYear(),request.getGenre(),request.getCategory(), request.getCode());

        Optional<EmailCheck> emailCheck = emailCheckRepository.findByMail(request.getMail());

        if(userUnDuplicateMail(saveRequestUserDto.getMail())) {
            if(unDuplicateMail(saveRequestUserDto.getMail())){
                return "fail";
            }
            else {
                final User user = saveRequestUserDto.toEntity();
                userRepository.save(User.builder()
                        .mail(request.getMail())
                        .password(request.getPassword())
                        .nickname(request.getNickname())
                        .mbti(request.getMbti())
                        .sex(request.getSex())
                        .genre(request.getGenre())
                        .category(request.getCategory())
                        .code(request.getCode())
                        .year(request.getYear())
                        .build());
                emailCheckRepository.deleteById(emailCheck.get().getId());

                return "Success";
            }
        }
        else{
            return "fail";

        }


    }

    @Transactional
    public ResponseEntity<String> PasswordUpdate(PasswordUpdateDto passwordUpdateDto){
        Optional<User> user = userRepository.findById(passwordUpdateDto.getUid());
        if(user.isPresent()){
            if(passwordUpdateDto.getBeforePassword().equals(user.get().getPassword())){
                user.get().updatePassword(passwordUpdateDto.getAfterPassword());
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<String>("비밀번호가 일치 하지 않습니다.",HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity<String>("존재 하지 않는 사용자 입니다.",HttpStatus.BAD_REQUEST);

        }
    }
    @Transactional
    public ResponseLoginDto Login(LoginDto loginDto){
        Optional<User> user = userRepository.findByMail(loginDto.getMail());
        User userdto = user.get();
        return ResponseLoginDto.builder().uid(userdto.getUid())
                .nickname(userdto.getNickname())
                .profile(userdto.getProfile())
                .introduce(userdto.getIntroduce())
                .accessToken(userdto.getAccessToken())
                .refreshToken(userdto.getRefreshToken())
        .build();
    }

    @Transactional
    public String LoginCheck(LoginDto loginDto){
        Optional<User> user = userRepository.findByMail(loginDto.getMail());
        if(!userUnDuplicateMail(loginDto.getMail())){
            if(user.get().getPassword().equals(loginDto.getPassword())){
                return "Success";
            }
            else return "Not match";
        }
        else return "No User";

    }


    @Transactional
    public String emailAuth(SaveRequestUserDto request){
        Optional<EmailCheck> emailCheck = emailCheckRepository.findByMail(request.getMail());
        if(unDuplicateMail(request.getMail())){
            return "no authed";
        }
        if(emailCheck.get().getCode().equals(request.getCode())){
            if(signup(request).equals("Success")){
                Optional<User> user = userRepository.findByMail(request.getMail());
                user.get().emailVerifiedSuccess();
                return "Success";
            }
            else return "dup id";
        }
        else return "not match code";
    }

    @Transactional
    public String updateProfile(UpdateProfileDto updateProfileDto){
        if(!emptyId(updateProfileDto.getUid())) {
            Optional<User> user = userRepository.findById(updateProfileDto.getUid());
            user.get().profileUpdate(updateProfileDto.getNickname(),updateProfileDto.getProfile(),updateProfileDto.getIntroduce());
            return "Success";
        }
        else return "fail";
    }

    @Transactional
    public ResponseEntity<String> PasswordChangeMailAuth(RequestMailAuthDto requestMailAuthDto){
        Random random = new Random();
        String key="";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vamigoauth@gmail.com");
        message.setTo(requestMailAuthDto.getMail());
        for(int i = 0; i<3; i++){
            int index = random.nextInt(25)+65;
            key+=(char)index;
        }
        int numIndex= random.nextInt(9999)+1000;
        key+= numIndex;

        message.setSubject("인증번호 입력을 위한 메일 전송");
        message.setText("인증번호 : " +key);
        EmailCheck emailCheck = new EmailCheck(requestMailAuthDto.getMail(), key);


        if(unDuplicateMail(requestMailAuthDto.getMail())){
            if (userUnDuplicateMail(requestMailAuthDto.getMail())) {
                return new ResponseEntity<>("가입되지 않은 메일입니다.", HttpStatus.BAD_REQUEST);
            }
            else {
                emailCheckRepository.save(EmailCheck.builder()
                        .mail(requestMailAuthDto.getMail())
                        .code(key)
                        .build());
                javaMailSender.send(message);
                log.info("key = {}",key);
                return new ResponseEntity<>(HttpStatus.OK);
            }


        }
        else {
            Optional<EmailCheck> findEmail = emailCheckRepository.findByMail(emailCheck.getMail());

            if (userUnDuplicateMail(emailCheck.getMail())) {
                return new ResponseEntity<>("가입되지 않은 메일입니다.", HttpStatus.BAD_REQUEST);
            }
            else {
                javaMailSender.send(message);
                EmailCheck ec = findEmail.get();
                ec.reSetCode(key);
                return new ResponseEntity<>("재전송 하였습니다.", HttpStatus.OK);
            }
        }
    }


    public ResponseEntity<String> forgetPassword(RequestForgetPasswordDto requestForgetPasswordDto){
        Optional<User> user = userRepository.findByMail(requestForgetPasswordDto.getMail());
        if(user.isPresent()) {
            if (!unDuplicateMail(user.get().getMail())) {
                Optional<EmailCheck> emailCheck = emailCheckRepository.findByMail(user.get().getMail());
                if(emailCheck.get().getCode().equals(requestForgetPasswordDto.getCode())){
                    user.get().updatePassword(requestForgetPasswordDto.getPassword());
                    emailCheckRepository.deleteById(emailCheck.get().getId());
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                else return new ResponseEntity<>("발급된 인증코드와 다릅니다.", HttpStatus.BAD_REQUEST);
            }
            else{
                return new ResponseEntity<>("인증코드를 발급 받아주세요", HttpStatus.BAD_REQUEST);
            }

        }
        else return new ResponseEntity<>("가압되지 않은 메일입니다.", HttpStatus.BAD_REQUEST);

    }
    public ResponseEntity targetUidReview(RequestTargetUid requestTargetUid){
        Optional<User> targetUser = userRepository.findById(requestTargetUid.getTargetId());
        Optional<User> myUser = userRepository.findById(requestTargetUid.getUid());
        ResponseTargetUidReview responseTargetUidReview = new ResponseTargetUidReview();

        MyReviewDto myReviewDto = new MyReviewDto(requestTargetUid.getUid());
        TargetUserfriendDto targetUserfriendDto = new TargetUserfriendDto();
        if(targetUser.isPresent()) {
            List<Follow> followList = followRepository.findByMyUid(targetUser.get());
            if(myUser.isPresent()) {
                List<Follow> myfollowList = followRepository.findByMyUid(myUser.get());
                targetUserfriendDto.setUid(requestTargetUid.getTargetId());
                targetUserfriendDto.setProfile(targetUser.get().getProfile());
                targetUserfriendDto.setNickname(targetUser.get().getNickname());
                targetUserfriendDto.setIntroduce(targetUser.get().getIntroduce());
                targetUserfriendDto.setIsFollower(false);
                targetUserfriendDto.setIsFollowing(false);
                targetUserfriendDto.setBlocked(false);
                responseTargetUidReview.setUser(targetUserfriendDto);
                if(!followList.isEmpty()) {
                    for (int i = 0; i < followList.size(); i++) {
                        if (followList.get(i).getTargetUid().equals(myUser.get())) {
                            targetUserfriendDto.setIsFollower(true);
                            responseTargetUidReview.setUser(targetUserfriendDto);
                        }
                    }
                }
                if(!myfollowList.isEmpty()) {
                    for (int i = 0; i < myfollowList.size(); i++) {
                        if (myfollowList.get(i).getTargetUid().equals(targetUser.get())) {
                            targetUserfriendDto.setIsFollowing(true);
                            responseTargetUidReview.setUser(targetUserfriendDto);
                        }
                    }
                }
                List<Review> reviews = reviewRepository.findByUser_uid(targetUserfriendDto.getUid());
                if(!reviews.isEmpty()){
                    responseTargetUidReview.setReviews(reviewService.isLikedCheck(myReviewDto,reviews));
                }
                return new ResponseEntity(responseTargetUidReview,HttpStatus.OK);
            }
            else{
                return new ResponseEntity("잘못된 요청입니다.",HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity("존재하지 않는 사용자 입니다.",HttpStatus.BAD_REQUEST);
        }

    }
    public TargetUserfriendDto getTargetUidProfile(RequestTargetUid requestTargetUid){
        Optional<User> targetUser = userRepository.findById(requestTargetUid.getTargetId());
        Optional<User> myUser = userRepository.findById(requestTargetUid.getUid());

        TargetUserfriendDto targetUserfriendDto = new TargetUserfriendDto();
        if(targetUser.isPresent()) {
            List<Follow> followList = followRepository.findByMyUid(targetUser.get());
            if(myUser.isPresent()) {
                List<Follow> myfollowList = followRepository.findByMyUid(myUser.get());
                targetUserfriendDto.setUid(requestTargetUid.getTargetId());
                targetUserfriendDto.setProfile(targetUser.get().getProfile());
                targetUserfriendDto.setNickname(targetUser.get().getNickname());
                targetUserfriendDto.setIntroduce(targetUser.get().getIntroduce());
                targetUserfriendDto.setIsFollower(false);
                targetUserfriendDto.setIsFollowing(false);
                targetUserfriendDto.setBlocked(false);
                if(!followList.isEmpty()) {
                    for (int i = 0; i < followList.size(); i++) {
                        if (followList.get(i).getTargetUid().equals(myUser.get())) {
                            targetUserfriendDto.setIsFollower(true);
                        }
                    }
                }
                if(!myfollowList.isEmpty()) {
                    for (int i = 0; i < myfollowList.size(); i++) {
                        if (myfollowList.get(i).getTargetUid().equals(targetUser.get())) {
                            targetUserfriendDto.setIsFollowing(true);
                        }
                    }
                }
                return targetUserfriendDto;
            }

        }
        return targetUserfriendDto;

    }

    public ResponseEntity targetUidProfile(RequestTargetUid requestTargetUid){
        Optional<User> targetUser = userRepository.findById(requestTargetUid.getTargetId());
        Optional<User> myUser = userRepository.findById(requestTargetUid.getUid());

        TargetUserfriendDto targetUserfriendDto = new TargetUserfriendDto();
        if(targetUser.isPresent()) {
            List<Follow> followList = followRepository.findByMyUid(targetUser.get());
            if(myUser.isPresent()) {
                List<Follow> myfollowList = followRepository.findByMyUid(myUser.get());
                targetUserfriendDto.setUid(requestTargetUid.getTargetId());
                targetUserfriendDto.setProfile(targetUser.get().getProfile());
                targetUserfriendDto.setNickname(targetUser.get().getNickname());
                targetUserfriendDto.setIntroduce(targetUser.get().getIntroduce());
                targetUserfriendDto.setIsFollower(false);
                targetUserfriendDto.setIsFollowing(false);
                targetUserfriendDto.setBlocked(false);
                if(!followList.isEmpty()) {
                    for (int i = 0; i < followList.size(); i++) {
                        if (followList.get(i).getTargetUid().equals(myUser.get())) {
                            targetUserfriendDto.setIsFollower(true);
                        }
                    }
                }
                if(!myfollowList.isEmpty()) {
                    for (int i = 0; i < myfollowList.size(); i++) {
                        if (myfollowList.get(i).getTargetUid().equals(targetUser.get())) {
                            targetUserfriendDto.setIsFollowing(true);
                        }
                    }
                }
                return new ResponseEntity(targetUserfriendDto,HttpStatus.OK);
            }
            else{
                return new ResponseEntity("잘못된 요청입니다.",HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity("존재하지 않는 사용자 입니다.",HttpStatus.BAD_REQUEST);
        }


    }

    public ResponseEntity MyprofileUpdate(RequestUid requestUid){
        Optional<User> user = userRepository.findById(requestUid.getUid());
        if(user.isPresent()) {
            ResponseProfile responseProfile = new ResponseProfile();
            responseProfile.setProfile(user.get().getProfile());
            responseProfile.setNickname(user.get().getNickname());
            responseProfile.setIntroduce(user.get().getIntroduce());

            return new ResponseEntity(responseProfile, HttpStatus.OK);
        }
        else return new ResponseEntity("존재하지 않는 사용자 입니다.",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity targetUidProfilefriend(RequestTargetUid requestTargetUid){
        Optional<User> targetUser = userRepository.findById(requestTargetUid.getTargetId());
        Optional<User> myUser = userRepository.findById(requestTargetUid.getUid());
        ResponseTargetUidFriend responseTargetUidFriend = new ResponseTargetUidFriend();
        TargetUserfriendDto targetUserfriendDto = new TargetUserfriendDto();
        if (targetUser.isPresent()) {
            List<Follow> followList = followRepository.findByMyUid(targetUser.get());
            List<Follow> followingList = followRepository.findByTargetUid(targetUser.get());
            ResponseMyfriend responseMyfriend = new ResponseMyfriend();
            for(int i = 0; i < followList.size(); i++){
                responseMyfriend.addfollower(ResponseReplyUserDto.builder().uid(followList.get(i).getTargetUid().getUid())
                        .nickname(followList.get(i).getTargetUid().getNickname())
                        .profile(followList.get(i).getTargetUid().getProfile()).build());
            }
            for(int i = 0; i < followingList.size(); i++){
                responseMyfriend.addfollowing(ResponseReplyUserDto.builder().uid(followingList.get(i).getMyUid().getUid())
                        .nickname(followingList.get(i).getMyUid().getNickname())
                        .profile(followingList.get(i).getMyUid().getProfile()).build());
            }
            //결과 뒤집어야 제대로 실행 됨 (춧후 로직 변경 예정)
            responseTargetUidFriend.setFollower(responseMyfriend.getFollowing());
            responseTargetUidFriend.setFollowing(responseMyfriend.getFollower());
            if(myUser.isPresent()) {
                List<Follow> myfollowList = followRepository.findByMyUid(myUser.get());
                targetUserfriendDto.setUid(requestTargetUid.getTargetId());
                targetUserfriendDto.setProfile(targetUser.get().getProfile());
                targetUserfriendDto.setNickname(targetUser.get().getNickname());
                targetUserfriendDto.setIntroduce(targetUser.get().getIntroduce());
                targetUserfriendDto.setIsFollower(false);
                targetUserfriendDto.setIsFollowing(false);
                targetUserfriendDto.setBlocked(false);
                responseTargetUidFriend.setUser(targetUserfriendDto);
                if(!followList.isEmpty()) {
                    for (int i = 0; i < followList.size(); i++) {
                        if (followList.get(i).getTargetUid().equals(myUser.get())) {
                            targetUserfriendDto.setIsFollower(true);
                            responseTargetUidFriend.setUser(targetUserfriendDto);

                        }
                    }
                }
                if(!myfollowList.isEmpty()) {
                    for (int i = 0; i < myfollowList.size(); i++) {
                        if (myfollowList.get(i).getTargetUid().equals(targetUser.get())) {
                            targetUserfriendDto.setIsFollowing(true);
                            responseTargetUidFriend.setUser(targetUserfriendDto);
                        }
                    }
                }
                return new ResponseEntity(responseTargetUidFriend,HttpStatus.OK);
            }
            else{
                return new ResponseEntity("잘못된 요청입니다.",HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity("존재하지 않는 회원입니다.",HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity myLikeReview(MyReviewDto myReviewDto){
        Optional<User> myUser = userRepository.findById(myReviewDto.getUid());
        List<Likes> likes = likesRepository.findByUser_uid(myReviewDto.getUid());
        List<Review> reviewList = new ArrayList<>();
        for(int i = 0; i < likes.size(); i++){
            Optional<Review> review = reviewRepository.findById(likes.get(i).getReview().getId());
            review.ifPresent(reviewList::add);
        }
        ResponseMyLikeReviewDto responseMyLikeReviewDto = new ResponseMyLikeReviewDto();

        responseMyLikeReviewDto.setReviews(reviewService.isLikedCheck(myReviewDto,reviewList));


        return new ResponseEntity(responseMyLikeReviewDto,HttpStatus.OK);

    }



    public ResponseEntity TargetLikeReview(RequestTargetUid requestTargetUid){
        Optional<User> myUser = userRepository.findById(requestTargetUid.getUid());
        List<Likes> likes = likesRepository.findByUser_uid(requestTargetUid.getTargetId());
        List<Review> reviewList = new ArrayList<>();
        for(int i = 0; i < likes.size(); i++){
            Optional<Review> review = reviewRepository.findById(likes.get(i).getReview().getId());
            review.ifPresent(reviewList::add);
        }
        MyReviewDto myReviewDto = new MyReviewDto(requestTargetUid.getUid());
        ResponseTargetUidReview responseTargetUidReview = new ResponseTargetUidReview();
        responseTargetUidReview.setUser(getTargetUidProfile(requestTargetUid));
        responseTargetUidReview.setReviews(reviewService.isLikedCheck(myReviewDto,reviewList));


        return new ResponseEntity(responseTargetUidReview,HttpStatus.OK);

    }
    public List<Statistics> MyStatistics(Long uid){
        List<String> category = Arrays.asList("movie","book","animation","drama","game");
        List<String> k_category =Arrays.asList("영화","책","만화","드라마","게임");
        List<Statistics> StatisticsList = new ArrayList<>();
        Optional<User> user = userRepository.findById(uid);

        for(int i = 0 ; i < category.size(); i++) {
            List<Review> reviewList = reviewRepository.findByCategoryAndUser_uid(category.get(i),uid);
            Statistics statistics = new Statistics();
            if(!reviewList.isEmpty()){
                statistics.setId(k_category.get(i));
                statistics.setLabel(k_category.get(i));
                statistics.setValue(reviewList.size());
                StatisticsList.add(statistics);
            }

        }
        return StatisticsList;
    }

    public List<TargetUserStatistics> TargetUserStatistics(Long uid, Long TargetUserUid){
        List<String> category = Arrays.asList("movie","book","animation","drama","game");
        List<String> k_category = Arrays.asList("영화","책","만화","드라마","게임");
        List<TargetUserStatistics> targetUserStatisticsList = new ArrayList<>();


            for (int i = 0; i < category.size(); i++) {
                List<Review> MyreviewList = reviewRepository.findByCategoryAndUser_uid(category.get(i), uid);
                List<Review> TargetUserReviewList = reviewRepository.findByCategoryAndUser_uid(category.get(i),TargetUserUid);
                TargetUserStatistics targetUserStatistics = new TargetUserStatistics();
                if (!MyreviewList.isEmpty()&&!TargetUserReviewList.isEmpty()) {
                    targetUserStatistics.setCategory(k_category.get(i));
                    targetUserStatistics.setMe(MyreviewList.size());
                    targetUserStatistics.setYou(TargetUserReviewList.size());
                    targetUserStatisticsList.add(targetUserStatistics);
                }
                else if(!MyreviewList.isEmpty()){
                    targetUserStatistics.setCategory(k_category.get(i));
                    targetUserStatistics.setMe(MyreviewList.size());
                    targetUserStatistics.setYou(0);
                    targetUserStatisticsList.add(targetUserStatistics);


                }
                else if(!TargetUserReviewList.isEmpty()){
                    targetUserStatistics.setCategory(k_category.get(i));
                    targetUserStatistics.setMe(0);
                    targetUserStatistics.setYou(TargetUserReviewList.size());
                    targetUserStatisticsList.add(targetUserStatistics);
                }
                else {
                    targetUserStatistics.setCategory(k_category.get(i));
                    targetUserStatistics.setMe(0);
                    targetUserStatistics.setYou(0);
                    targetUserStatisticsList.add(targetUserStatistics);
                }
            }
        return targetUserStatisticsList;
    }

    private boolean emptyId(Long id){
        Optional<User> findId = userRepository.findById(id);
        return findId.isEmpty();
    }
    private boolean unDuplicateMail(String mail){
        Optional<EmailCheck> findEmail = emailCheckRepository.findByMail(mail);
        return findEmail.isEmpty();
    }
    public boolean userUnDuplicateMail(String mail){
        Optional<User> findEmail = userRepository.findByMail(mail);
        return findEmail.isEmpty();
    }

    @Transactional
    public String deleteUser(RequestDeleteUserDto requestDeleteUserDto){

        Optional<User> user = userRepository.findById(requestDeleteUserDto.getUid());
        if(user.get().getPassword().equals(requestDeleteUserDto.getPassword())) {
            List<Review> review = reviewRepository.findByUser_uid(requestDeleteUserDto.getUid());
            List<Reply> replyList = replyRepository.findByUser_uid(requestDeleteUserDto.getUid());
            List<Likes> likes = likesRepository.findByUser_uid(requestDeleteUserDto.getUid());

            reviewReportRepository.deleteByUser_Uid(user.get().getUid());
            followRepository.deleteByMyUid(user.get());
            followRepository.deleteByTargetUid(user.get());
            replyRepository.deleteByUser_Uid(user.get().getUid());
            likesRepository.deleteByUser_Uid(user.get().getUid());
            reviewRepository.deleteByUser_Uid(user.get().getUid());
            userRepository.deleteById(user.get().getUid());
            return "Success";


        }
        else return "fail";
    }


    /*public  findBymail(String mail){
        Optional<User> user = userRepository.findByMail(mail);
        return user;
    }*/








}
