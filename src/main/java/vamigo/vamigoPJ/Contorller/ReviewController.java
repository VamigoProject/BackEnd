package vamigo.vamigoPJ.Contorller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vamigo.vamigoPJ.DTO.Request.*;
import vamigo.vamigoPJ.Service.ReviewService;

@Slf4j
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Controller
public class ReviewController {
    @Autowired
    private final ReviewService reviewService;



    
    @PostMapping("/like")
    @ResponseBody
    public ResponseEntity reviewDoLikes(@RequestBody RequestDoLikeDto requestDoLikeDto){
        log.info("uid = {} reviewId = {}",requestDoLikeDto.getUid(),requestDoLikeDto.getReviewId() );
        if(reviewService.DoLike(requestDoLikeDto).equals("do")){
            return new ResponseEntity(HttpStatus.OK);
        }
        else return new ResponseEntity("잘못된 요청입니다.",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/unlike")
    @ResponseBody
    public ResponseEntity reviewUnDoLikes(@RequestBody RequestDoLikeDto requestDoLikeDto){
        log.info("uid = {} reviewId = {}",requestDoLikeDto.getUid(),requestDoLikeDto.getReviewId() );
        if(reviewService.UnDoLike(requestDoLikeDto).equals("undo")){
            return new ResponseEntity(HttpStatus.OK);
        }

        else return new ResponseEntity("잘못된 요청입니다.",HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity reviewUpdate(@RequestBody RequsetUpdateReviewDto requsetUpdateReviewDto){
        log.info("uid = {} reveiwId = {} rating = {} comment = {}", requsetUpdateReviewDto.getUid(), requsetUpdateReviewDto.getReviewId()
                ,requsetUpdateReviewDto.getRating(),requsetUpdateReviewDto.getComment(),requsetUpdateReviewDto);
        if(reviewService.updateReview(requsetUpdateReviewDto).equals("Success")){
            return new ResponseEntity("리뷰가 수정되었습니다.",HttpStatus.OK);
        }
        else return new ResponseEntity("잘못된 요청입니다.",HttpStatus.BAD_REQUEST);


    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity reviewDelete(@RequestBody RequestDeleteReviewDto requestDeleteReviewDto){
        log.info("uid = {} reviewid = {}",requestDeleteReviewDto.getUid(), requestDeleteReviewDto.getReviewId());
        if(reviewService.deleteReview(requestDeleteReviewDto).equals("Success")){
            return new ResponseEntity("리뷰가 정상적으로 삭제 되었습니다.", HttpStatus.OK);
        }
        else return new ResponseEntity("잘못된 요청입니다.",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/create")
    public ResponseEntity reviewcreate(@RequestBody RequestCreateReviewDto requestCreateReviewDto){
        log.info("uid = {} rating = {} workName = {} workCategory ={} comment = {}"
                ,requestCreateReviewDto.getUid(), requestCreateReviewDto.getRating(), requestCreateReviewDto.getWorkName(),requestCreateReviewDto.getWorkCategory(),requestCreateReviewDto.getComment(),requestCreateReviewDto.getLat(),requestCreateReviewDto.getLng());

        return new ResponseEntity<>(reviewService.create(requestCreateReviewDto),HttpStatus.OK);

    }

    @PostMapping("/report")
    @ResponseBody
    public ResponseEntity reviewReport(@RequestBody RequestReviewReportDto requestReviewReportDto){
        log.info("request = {}",requestReviewReportDto.getReviewId());
        return  reviewService.reviewReport(requestReviewReportDto);
    }




}
