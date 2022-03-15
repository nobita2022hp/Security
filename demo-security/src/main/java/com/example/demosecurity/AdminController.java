package com.example.demosecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @GetMapping("index")
    public String index(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            LOGGER.info(authentication.getName());
            LOGGER.info(authentication.getPrincipal().toString());
        }
        return "admin";
    }
}
