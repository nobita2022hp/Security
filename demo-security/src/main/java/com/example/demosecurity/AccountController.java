package com.example.demosecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("account")
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @GetMapping("login")
    public String login(){
        return "login";
    }

    @GetMapping("user-info")
    public String loginSuccess(){
        return "user-info";
    }

    @PostMapping("handle-login")
    public String handleLogin(HttpServletRequest request){
        String name = request.getParameter("username");
        LOGGER.info("Name: " + name);
        return "user-info";
    }
}
