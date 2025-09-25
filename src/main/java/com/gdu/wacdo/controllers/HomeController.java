package com.gdu.wacdo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class HomeController {

    @GetMapping({"/login"})
    public String login() {
        return "login";
    }

    @PostMapping({"/login"})
    public String login_process() {
        return "index";
    }
}
