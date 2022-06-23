package vamigo.vamigoPJ.Contorller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vamigo.vamigoPJ.DTO.Request.PasswordUpdateDto;
import vamigo.vamigoPJ.DTO.Request.RequestForgetPasswordDto;
import vamigo.vamigoPJ.DTO.Request.RequestMailAuthDto;
import vamigo.vamigoPJ.Service.UserService;

@Slf4j
@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
@Controller
public class PasswordController {
    @Autowired
    private final UserService userService;


    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity passwordUpdate(@RequestBody PasswordUpdateDto passwordUpdateDto){
        return userService.PasswordUpdate(passwordUpdateDto);
    }

    @PostMapping("/mailauth")
    @ResponseBody
    public ResponseEntity passwordChangeMailAuth(@RequestBody RequestMailAuthDto requestMailAuthDto){
        return userService.PasswordChangeMailAuth(requestMailAuthDto);
    }

    @PostMapping("/forget")
    @ResponseBody
    public ResponseEntity passwordForget(@RequestBody RequestForgetPasswordDto requestForgetPasswordDto){
        return userService.forgetPassword(requestForgetPasswordDto);
    }
    @PostMapping("/check")
    @ResponseBody
    public ResponseEntity mailDupCheck(@RequestBody RequestMailAuthDto requestMailAuthDto){
        if(!userService.userUnDuplicateMail(requestMailAuthDto.getMail())){
            return new ResponseEntity(HttpStatus.OK);
        }
        else return new ResponseEntity("회원가입 되어 있지 않은 메일입니다.",HttpStatus.BAD_REQUEST);
    }
}
