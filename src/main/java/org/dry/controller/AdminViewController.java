package org.dry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/admin")
@Controller
public class AdminViewController {

    @GetMapping("/login")
    public String getLogin() {
        return "admin/login";
    }

    @GetMapping("/sign-up")
    public String getSignUp() {
        return "admin/sign-up";
    }

}
