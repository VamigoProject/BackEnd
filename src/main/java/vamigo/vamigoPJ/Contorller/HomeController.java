package vamigo.vamigoPJ.Contorller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vamigo.vamigoPJ.DTO.MyReviewDto;
import vamigo.vamigoPJ.DTO.Response.MyReviewResponseDto;
import vamigo.vamigoPJ.DTO.Response.TrendReview;
import vamigo.vamigoPJ.Service.ReviewService;
import vamigo.vamigoPJ.entity.Review;

import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
@Controller
public class HomeController {
    @Autowired
    private final ReviewService reviewService;
    @PostMapping("/home")
    @ResponseBody
    public ResponseEntity HomeTimeLine(@RequestBody MyReviewDto myReviewDto){
        List<Review> reviews = reviewService.timeLineReview(myReviewDto);
        if(!reviews.isEmpty()){
            List<MyReviewResponseDto> myReviewResponseDtoList;
            myReviewResponseDtoList = reviewService.isLikedCheck(myReviewDto,reviews);
            return new ResponseEntity(myReviewResponseDtoList, HttpStatus.OK);
        }
        else return new ResponseEntity("None",HttpStatus.OK);
    }

    @GetMapping("/trend")
    public List<TrendReview> TrendRanking(){

        return reviewService.RankingReview();
    }


}
