package vamigo.vamigoPJ.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${vamigopj.app.jwtSecret}")
    private String secretKey;

    private  long tokenVaildTime = 1000L * 60 * 30;

   // private final UserDetailsService
}
