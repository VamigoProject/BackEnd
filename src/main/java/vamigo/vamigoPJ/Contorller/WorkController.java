package vamigo.vamigoPJ.Contorller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vamigo.vamigoPJ.DTO.Request.RequestWorkSearch;
import vamigo.vamigoPJ.Service.ReviewService;

@Slf4j
@RestController
@RequestMapping("/work")
@RequiredArgsConstructor
@Controller
public class WorkController {
    @Autowired
    private final ReviewService reviewService;

    @PostMapping("/search")
    @ResponseBody
    public ResponseEntity workSearch(@RequestBody RequestWorkSearch requestWorkSearch){
        log.info("RequestWorkSearch = {}" , requestWorkSearch);

        return reviewService.workSearch(requestWorkSearch);
    }
}
