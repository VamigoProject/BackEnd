package vamigo.vamigoPJ.Contorller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vamigo.vamigoPJ.Service.OAuthService;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KakaoController {

    @Autowired
    private OAuthService Oauth;

    @ResponseBody
    @GetMapping(value = "/api/oauth/kakao")
    public String login(@RequestParam("code")String code){
        String access_Token = Oauth.getKakaoAccessToken(code);
        System.out.println("controller access_Token :" + access_Token);
        return access_Token;

    }









}