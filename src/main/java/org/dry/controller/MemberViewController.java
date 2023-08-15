package org.dry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberViewController {

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess() {
        return "member/loginSuccess";
    }

    @GetMapping("/sign-up")
    public String signUp() {
        return "member/sign-up";
    }
}
