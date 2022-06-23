package vamigo.vamigoPJ.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vamigo.vamigoPJ.DTO.*;
import vamigo.vamigoPJ.DTO.Request.*;
import vamigo.vamigoPJ.DTO.Response.MyReviewResponseDto;
import vamigo.vamigoPJ.DTO.Response.ResponseMyLikeReviewDto;
import vamigo.vamigoPJ.DTO.Response.ResponseWorkSearch;
import vamigo.vamigoPJ.DTO.Response.TrendReview;
import vamigo.vamigoPJ.entity.*;
import vamigo.vamigoPJ.entity.ReviewReport;
import vamigo.vamigoPJ.repository.*;
import vamigo.vamigoPJ.util.MyReviewResponseDtoDateComparator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import java.util.*;


@RequiredArgsConstructor
@Service
@Slf4j
public class ReviewService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final LikesRepository likesRepository;
    private final ReplyRepository replyRepository;
    private final FollowRepository followRepository;
    private final ImageRepository imageRepository;
    private final MovieService movieService;
    private final ReviewReportRepository reviewReportRepository;



    public List<TrendReview> RankingReview(){


        List<rankingInterface> reviewList = reviewRepository.reviewRanking();
        List<ranking> rankingList = new ArrayList<>();
        for(int i = 0; i< reviewList.size(); i++){
            ranking rankingvo = new ranking(reviewList.get(i).getCategory(),reviewList.get(i).getWork());
            rankingList.add(rankingvo);
        }

        List<TrendReview> trendReviews = new ArrayList<>();


        for(int i = 0; i< rankingList.size(); i++){

            List<Review> reviews = reviewRepository.findByWorkAndCategory(rankingList.get(i).getWork(),rankingList.get(i).getCategory());
                for (int j = 0; j < reviews.size(); j++) {

                    if(j != 0 && !reviews.get(j).getWorkid().equals(reviews.get(j-1).getWorkid()) ) {
                            TrendReview trendReview = new TrendReview(reviews.get(j).getWorkid(), rankingList.get(i).getWork(), rankingList.get(i).getCategory());
                            trendReviews.add(trendReview);
                    }
                    else if(j == 0){
                        TrendReview trendReview = new TrendReview(reviews.get(j).getWorkid(), rankingList.get(i).getWork(), rankingList.get(i).getCategory());
                        trendReviews.add(trendReview);
                    }
                }
        }
        return trendReviews;
    }




    @Transactional
    public String DoLike(RequestDoLikeDto requestDoLikeDto){
        Optional<Review> review = reviewRepository.findById(requestDoLikeDto.getReviewId());
        Optional<User> user =userRepository.findById(requestDoLikeDto.getUid());
        List<Likes> ReviewLikeList = likesRepository.findByUser_uid(user.get().getUid());
        if(!ReviewLikeList.isEmpty()){
            for (int i = 0; i < ReviewLikeList.size(); i++) {
                if (ReviewLikeList.get(i).getReview().equals(review.get())) {
                    return "bad";
                }
                else{
                    likesRepository.save(Likes.builder().user(user.get()).review(review.get()).build());
                    review.get().DoLikes(likesRepository.findByReview_Id(review.get().getId()).size());
                    return "do";
                }
            }
        }
        else{
            likesRepository.save(Likes.builder().user(user.get()).review(review.get()).build());
            review.get().DoLikes(likesRepository.findByReview_Id(review.get().getId()).size());
            return "do";
        }
        return "bad";



    }

    @Transactional
    public String UnDoLike(RequestDoLikeDto requestDoLikeDto){
        Optional<Review> review = reviewRepository.findById(requestDoLikeDto.getReviewId());
        Optional<User> user =userRepository.findById(requestDoLikeDto.getUid());
        List<Likes> likes = likesRepository.findByUser_uid(user.get().getUid());
        if(!likes.isEmpty()){
            for(int i = 0; i < likes.size(); i++){
                if(likes.get(i).getUser().equals(user.get())&&likes.get(i).getReview().equals(review.get())){
                   likesRepository.delete(likes.get(i));
                    review.get().DoLikes(likesRepository.findByReview_Id(review.get().getId()).size());
                   break;
                }
            }
            return "undo";
        }
        return "bad";
    }
    @Transactional
    public Long create(RequestCreateReviewDto requestCreateReviewDto){
        Optional<User> user = userRepository.findById(requestCreateReviewDto.getUid());
        ReviewDto reviewDto = requestCreateReviewDto.toEntity(requestCreateReviewDto);
        reviewDto.setUser(user.get());

        Long id = reviewRepository.save(reviewDto.toEntity()).getId();

        return id;
    }

    @Transactional
    public String updateReview(RequsetUpdateReviewDto requsetUpdateReviewDto){
        Optional<Review> review = reviewRepository.findById(requsetUpdateReviewDto.getReviewId());
        Optional<User> user = userRepository.findById(requsetUpdateReviewDto.getUid());
        if(review.get().getUser().equals(user.get())) {
            review.get().UpdateReview(requsetUpdateReviewDto.getRating(), requsetUpdateReviewDto.getComment(), requsetUpdateReviewDto.getImage()
                    , requsetUpdateReviewDto.getSpoiler(), requsetUpdateReviewDto.getLat(), requsetUpdateReviewDto.getLng());
            return "Success";
        }
        else return "Fail";
    }
    @Transactional
    public String deleteReview(RequestDeleteReviewDto requestDeleteReviewDto){
        Optional<User> user = userRepository.findById(requestDeleteReviewDto.getUid());
        Optional<Review> review = reviewRepository.findById(requestDeleteReviewDto.getReviewId());

        if(review.get().getUser().equals(user.get())){
            reviewReportRepository.deleteByReview_Id(review.get().getId());
            replyRepository.deleteByReview_Id(review.get().getId());
            likesRepository.deleteByReview_Id(review.get().getId());
            reviewRepository.deleteById(requestDeleteReviewDto.getReviewId());
            return "Success";
        }
        else
            return "Fail";
    }
    @Transactional
    public void deleteReportReview(RequestDeleteReviewDto requestDeleteReviewDto){
        Optional<User> user = userRepository.findById(requestDeleteReviewDto.getUid());
        Optional<Review> review = reviewRepository.findById(requestDeleteReviewDto.getReviewId());


        reviewReportRepository.deleteByReview_Id(review.get().getId());
        replyRepository.deleteByReview_Id(review.get().getId());
        likesRepository.deleteByReview_Id(review.get().getId());
        reviewRepository.deleteById(requestDeleteReviewDto.getReviewId());


    }

    @Transactional
    public ResponseEntity reviewReport(RequestReviewReportDto requestReviewReportDto){

        RequestReviewReportDto betweenRequestReviewReportDto;
        Optional<Review> review = reviewRepository.findById(requestReviewReportDto.getReviewId());
        Optional<User> user = userRepository.findById(requestReviewReportDto.getUid());
        Optional<ReviewReport> reviewReport = reviewReportRepository.findByUser_uidAndReview_Id(requestReviewReportDto.getUid(),requestReviewReportDto.getReviewId());
        Boolean BTrue = true;


        if(review.isEmpty()){
            return new ResponseEntity("존재하지 않는 리뷰입니다.", HttpStatus.BAD_REQUEST);
        }
        if(user.isEmpty()){
            return new ResponseEntity("잘못된 요청입니다.",HttpStatus.BAD_REQUEST);
        }
        if(reviewReport.isEmpty()) {
            Integer ero = 0;
            Integer spoiler = 0;
            Integer curse = 0;
            Integer etc = 0;

            ReviewReport reviewReportList = ReviewReport.builder().user(user.get())
                    .review(review.get())
                    .curse(requestReviewReportDto.getCurse())
                    .ero(requestReviewReportDto.getEro())
                    .etc(requestReviewReportDto.getEtc())
                    .spoiler(requestReviewReportDto.getSpoiler())
                    .build();
            reviewReportRepository.save(reviewReportList);
            List<ReviewReport> targetReviewReport = reviewReportRepository.findByReview_Id(review.get().getId());
            for(int i =0; i < targetReviewReport.size(); i++){
                if(targetReviewReport.get(i).getEro().equals(BTrue)){
                    ero = ero+1;
                }
                if(targetReviewReport.get(i).getSpoiler().equals(BTrue)){
                    spoiler = spoiler +1;
                }
                if(targetReviewReport.get(i).getCurse().equals(BTrue)){
                    curse = curse +1;
                }
                if(targetReviewReport.get(i).getEtc().equals(BTrue)){
                    etc = etc +1;
                }
            }
            if(spoiler >= 2){
                review.get().setSpoiler(BTrue);
            }
            if(ero >= 2 | curse >= 2 | etc >=2){
                RequestDeleteReviewDto requestDeleteReviewDto = new RequestDeleteReviewDto(requestReviewReportDto.getUid(),requestReviewReportDto.getReviewId());
                deleteReportReview(requestDeleteReviewDto);
            }

            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            Boolean ero = false;
            Boolean spoiler = false;
            Boolean etc = false;
            Boolean curse = false;
            if(reviewReport.get().getEro().equals(BTrue)&& requestReviewReportDto.getEro()){
                ero = true;
            }
            if(reviewReport.get().getSpoiler().equals(BTrue) && requestReviewReportDto.getSpoiler()){
                spoiler = true;
            }
            if(reviewReport.get().getEtc().equals(BTrue) && requestReviewReportDto.getEtc()){
                etc = true;
            }
            if(reviewReport.get().getCurse().equals(BTrue) && requestReviewReportDto.getCurse()){
                curse = true;
            }

            betweenRequestReviewReportDto = RequestReviewReportDto.builder().reviewId(reviewReport.get().getReview().getId())
                    .uid(reviewReport.get().getUser().getUid()).ero(reviewReport.get().getEro()).etc(reviewReport.get().getEtc()).spoiler(reviewReport.get().getSpoiler())
                    .curse(reviewReport.get().getCurse()).build();

            if(!requestReviewReportDto.equals(betweenRequestReviewReportDto)){
                int eroCount = 0;
                int spoilerCount = 0;
                int curseCount = 0;
                int etcCount = 0;
                reviewReport.get().setCategory(requestReviewReportDto.getEro(),requestReviewReportDto.getSpoiler(),requestReviewReportDto.getCurse(),requestReviewReportDto.getEtc());
                List<ReviewReport> targetReviewReport = reviewReportRepository.findByReview_Id(review.get().getId());
                for(int i =0; i < targetReviewReport.size(); i++){
                    if(targetReviewReport.get(i).getEro().equals(BTrue)){
                        eroCount = eroCount+1;
                    }
                    if(targetReviewReport.get(i).getSpoiler().equals(BTrue)){
                        spoilerCount = spoilerCount +1;
                    }
                    if(targetReviewReport.get(i).getCurse().equals(BTrue)){
                        curseCount = curseCount +1;
                    }
                    if(targetReviewReport.get(i).getEtc().equals(BTrue)){
                        etcCount = etcCount +1;
                    }
                }
                if(spoilerCount >= 2){
                    review.get().setSpoiler(BTrue);
                }
                if(eroCount >= 2 | curseCount >= 2 | etcCount >=2){
                    RequestDeleteReviewDto requestDeleteReviewDto = new RequestDeleteReviewDto(requestReviewReportDto.getUid(),requestReviewReportDto.getReviewId());
                    deleteReportReview(requestDeleteReviewDto);
                }

                return new ResponseEntity(HttpStatus.OK);
            }


            if(ero | spoiler | etc | curse){
                List<String> category = new ArrayList<String>();
                String message = "항목 으로 이미 신고 되었습니다.";
                if(spoiler){
                    category.add("스포일러");
                }
                if(ero){
                    category.add("음란성");
                }
                if(curse){
                    category.add("욕설 및 비방");
                }
                if(etc){
                    category.add("기타");
                }
                message =String.join(",",category) + message;




                return  new ResponseEntity(message,HttpStatus.BAD_REQUEST);

            }
            else{
                Integer eroCount = 0;
                Integer spoilerCount = 0;
                Integer curseCount = 0;
                Integer etcCount = 0;
                reviewReport.get().setCategory(requestReviewReportDto.getEro(),requestReviewReportDto.getSpoiler(),requestReviewReportDto.getCurse(),requestReviewReportDto.getEtc());
                List<ReviewReport> targetReviewReport = reviewReportRepository.findByReview_Id(review.get().getId());
                for(int i =0; i < targetReviewReport.size(); i++){
                    if(targetReviewReport.get(i).getEro().equals(BTrue)){
                        eroCount = eroCount+1;
                    }
                    if(targetReviewReport.get(i).getSpoiler().equals(BTrue)){
                        spoilerCount = spoilerCount +1;
                    }
                    if(targetReviewReport.get(i).getCurse().equals(BTrue)){
                        curseCount = curseCount +1;
                    }
                    if(targetReviewReport.get(i).getEtc().equals(BTrue)){
                        etcCount = etcCount +1;
                    }
                }
                if(spoilerCount >= 2){
                    review.get().setSpoiler(BTrue);
                }
                if(eroCount >= 2 | curseCount >= 2 | etcCount >=2){
                    RequestDeleteReviewDto requestDeleteReviewDto = new RequestDeleteReviewDto(requestReviewReportDto.getUid(),requestReviewReportDto.getReviewId());
                    deleteReportReview(requestDeleteReviewDto);
                }

                return new ResponseEntity(HttpStatus.OK);
            }

        }
    }

    public List<MyReviewResponseDto> isLikedCheck(MyReviewDto myReviewDto, List<Review> reviewList) {
        Optional<User> user = userRepository.findById(myReviewDto.getUid());
        List<MyReviewResponseDto> myReviewResponseDtoList  = new ArrayList<>();
        List<Boolean> isLike = new ArrayList<Boolean>();
        for (int i = 0; i < reviewList.size(); i++) {
            Optional<Likes> likes = likesRepository.findByUser_uidAndReview_id(user.get().getUid(), reviewList.get(i).getId());
            log.info("1");
            if(likes.isPresent()) {
                log.info("12");
                //종아요 목록 중에 좋아요 목록이 요청한 유저의 것인지 확인하고 이 요청한 유저가 좋아요 상태가 do인지 확인한다.
                List<Reply> reply = replyRepository.findByReview_Id(reviewList.get(i).getId());
                MyReviewResponseDto myReviewResponseDto = MyReviewResponseDto.builder()
                        .nickname(reviewList.get(i).getUser().getNickname())
                        .profile(reviewList.get(i).getUser().getProfile())
                        .uid(reviewList.get(i).getUser().getUid())
                        .workName(reviewList.get(i).getWork())
                        .workId(reviewList.get(i).getWorkid())
                        .time(reviewList.get(i).getCreateDate())
                        .reviewId(reviewList.get(i).getId())
                        .workCategory(reviewList.get(i).getCategory())
                        .spoiler(reviewList.get(i).getSpoiler())
                        .rating(reviewList.get(i).getRating())
                        .image(reviewList.get(i).getImage())
                        .comment(reviewList.get(i).getComment())
                        .lat(reviewList.get(i).getLat())
                        .lng(reviewList.get(i).getLng())
                        .likes(reviewList.get(i).getLikes())
                        .reply(reply)
                        .isLiked(true)
                        .build();
                myReviewResponseDto.setReply(reply);
                myReviewResponseDtoList.add(myReviewResponseDto);
            }
            else {
                List<Reply> reply = replyRepository.findByReview_Id(reviewList.get(i).getId());
                MyReviewResponseDto myReviewResponseDto = MyReviewResponseDto.builder()
                        .nickname(reviewList.get(i).getUser().getNickname())
                        .profile(reviewList.get(i).getUser().getProfile())
                        .uid(reviewList.get(i).getUser().getUid())
                        .workId(reviewList.get(i).getWorkid())
                        .workName(reviewList.get(i).getWork())
                        .time(reviewList.get(i).getCreateDate())
                        .reviewId(reviewList.get(i).getId())
                        .workCategory(reviewList.get(i).getCategory())
                        .spoiler(reviewList.get(i).getSpoiler())
                        .rating(reviewList.get(i).getRating())
                        .image(reviewList.get(i).getImage())
                        .comment(reviewList.get(i).getComment())
                        .lat(reviewList.get(i).getLat())
                        .lng(reviewList.get(i).getLng())
                        .likes(reviewList.get(i).getLikes())
                        .reply(reply)
                        .isLiked(false)
                        .build();
                myReviewResponseDto.setReply(reply);
                myReviewResponseDtoList.add(myReviewResponseDto);

            }
        }
        myReviewResponseDtoList.sort(new MyReviewResponseDtoDateComparator().reversed());

        return myReviewResponseDtoList;

    }

    public List<Review> myreview(MyReviewDto myReviewDto){
        List<Review> nullList = new ArrayList<Review>();
        if(emptyreview(myReviewDto.getUid())){
            return nullList;
        }
        else {
            List<Review> reviewList = reviewRepository.findByUser_uid(myReviewDto.getUid());
            return reviewList;
        }


    }





    public ResponseEntity workSearch(RequestWorkSearch requestWorkSearch){
        Long id = Long.parseLong(requestWorkSearch.getWorkId().toString());
        Optional<Image> image = imageRepository.findById(id);

        List<Review> reviewList = reviewRepository.findByWorkid(requestWorkSearch.getWorkId());
        MyReviewDto myReviewDto = new MyReviewDto(requestWorkSearch.getUid());



        String workid = requestWorkSearch.getWorkId().toString();
        ResponseWorkSearch responseWorkSearch =new ResponseWorkSearch();
        Movie movie = movieService.getById(workid);
        Float rating = reviewRepository.ratingAvg(requestWorkSearch.getWorkId());
        WorkInfo workInfo;
        if(image.isPresent()) {
            workInfo = new WorkInfo(image.get().getImage(),movie.getName(),movie.getCategory(),rating);
            responseWorkSearch.setWorkInfo(workInfo);
        }
        else {
            workInfo = new WorkInfo(movieService.getById(workid).getName(),movie.getCategory(),rating);
            responseWorkSearch.setWorkInfo(workInfo);
        }

        if(!reviewList.isEmpty()){
            responseWorkSearch.setReviews(isLikedCheck(myReviewDto,reviewList));
        }

        return new ResponseEntity<>(responseWorkSearch, HttpStatus.OK);
    }

    //public ResponseEntity<> SearchList()


    public List<Review> timeLineReview(MyReviewDto myReviewDto){

        List<Review> timeLine = new ArrayList<>();

        Optional<User> user = userRepository.findById(myReviewDto.getUid());
        if(user.isPresent()) {
            List<Follow> followList = followRepository.findByMyUid(user.get());
            for(int i = 0; i < followList.size() ; i++){
                List<Review> followReview = reviewRepository.findByUser_uid(followList.get(i).getTargetUid().getUid());
                timeLine.addAll(followReview);
            }
            if(!emptyreview(myReviewDto.getUid())){

                List<Review> review = reviewRepository.findByUser_uid(myReviewDto.getUid());

                timeLine.addAll(review);
            }
        }


        return timeLine;
    }


    private boolean emptyreview(Long uid){
        List<Review> review =reviewRepository.findByUser_uid(uid);
        return review.isEmpty();
    }








}
