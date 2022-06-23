package vamigo.vamigoPJ.Contorller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vamigo.vamigoPJ.DTO.Request.RequestReplyDto;
import vamigo.vamigoPJ.Service.ReplyService;

@Slf4j
@RestController
@RequestMapping("/review/reply")
@RequiredArgsConstructor
@Controller
public class ReplyController {
    @Autowired
    private final ReplyService replyService;

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity replyCreate(@RequestBody RequestReplyDto requestReplyDto){
        Long id = replyService.replyCreate(requestReplyDto);
        return new ResponseEntity(id, HttpStatus.OK);
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity replyDelete(@RequestBody RequestReplyDto requestReplyDto){
        String result = replyService.replyDelete(requestReplyDto);
        if(result == "Success"){
            return new ResponseEntity(HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
