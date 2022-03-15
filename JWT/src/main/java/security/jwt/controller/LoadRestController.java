package security.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import security.jwt.jwt.JwtTokenProvider;
import security.jwt.payload.LoginRequest;
import security.jwt.payload.LoginResponse;
import security.jwt.payload.RandomStuff;

@RestController
@RequestMapping("api")
public class LoadRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        var token = tokenProvider.generateToken((UserDetails) authentication.getPrincipal());
        return new LoginResponse(token);
    }

    @GetMapping("random")
    public RandomStuff randomStuff(){
        return new RandomStuff("JWT Hợp lệ mới có thể thấy được message này");
    }
}
