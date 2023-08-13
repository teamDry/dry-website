package org.dry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/admin")
@Controller
public class AdminViewController {
    @GetMapping("/login") // 로그인 view 화면
    public String login() {
        return "admin/login";
    }

    @GetMapping("/loginSuccess") // 로그인에 성공했을 때의 url
    public String loginSuccess() {
        return "admin/index";
    }

    @GetMapping("/loginFail") // 로그인에 실패했을 때의 url
    public String loginFail() {
        return "error/admin-login-failed";
    }

    @GetMapping("/sign-up") // 회원 가입 view 화면
    public String getSignUp() {
        return "admin/sign-up";
    }

}
