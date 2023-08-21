package org.dry.controller;

import org.dry.service.InviteCodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/admins")
@Controller
public class AdminViewController {
    private final InviteCodesService inviteCodesService;

    @Autowired
    public AdminViewController(InviteCodesService inviteCodesService) {
        this.inviteCodesService = inviteCodesService;
    }

    @GetMapping("/login") // 로그인 view 화면
    public String login() {
        return "admin/login";
    }

    @GetMapping("/login/code") // 로그인 view 화면
    public String login(@RequestParam String code) { // 코드 정보를 가지고 회원가입을 했으면?
        inviteCodesService.removeCode(code); // 해당 코드 삭제 ( 이용불가 )
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

    @GetMapping("/sign-up") // 회원 가입 view 화면 (삭제 예정)
    public String getSignUp() {
        return "admin/sign-up";
    }

    // 코드를 가지고 회원가입 하러 옴
    @GetMapping("/member/sign-up")
    public String memberSignUp(@RequestParam String code) {
        if(inviteCodesService.isExist(code)) { // 만약 유효한 코드라면
            return "redirect:/admins/sign-up?code=" + code; //
        } else {
            return "redirect:/admins/loginFail"; // 아니면 에러 페이지
        }
    }

}
