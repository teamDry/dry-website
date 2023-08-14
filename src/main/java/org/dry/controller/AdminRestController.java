package org.dry.controller;

import jakarta.servlet.http.HttpSession;
import org.dry.entity.Admin;
import org.dry.service.AdminService;
import org.dry.vo.IdAndPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/admin")
@RestController
public class AdminRestController {
    private final AdminService adminService;
    @Autowired
    public AdminRestController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login") // Ajax에서 작성한 IdAandPassword (Json으로 넘어올 예정), session 등록을 위한 객체
    public ResponseEntity<Admin> login(@RequestBody IdAndPassword idAndPassword, HttpSession session) {
        Admin loginAdmin = adminService.login(idAndPassword); // 넘어온 걸로 객체 db에서 찾아서
        if(loginAdmin != null) { // 객체가 잘 찾아졌으면
            session.setAttribute("loginAdmin", loginAdmin); // session에 로그인 정보 객체 담고
            return ResponseEntity.ok(loginAdmin); // ok 응답에 로그인 정보 객체 같이 실어서 보냄
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 실패했으면 실패 상태코드를 보냄
            // .build() : json객체로 만들기
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Admin> signUp(@RequestBody Admin admin) {
        Admin signUpAdmin = adminService.signUp(admin);
        if(signUpAdmin != null) {
            return ResponseEntity.ok(signUpAdmin);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // id 중복 검사
    @GetMapping("/sign-up/check-id")
    public ResponseEntity<Boolean> checkId(@RequestParam String id) {
        boolean isTaken = adminService.isIdTaken(id);
        if(!isTaken) {
            return ResponseEntity.ok(isTaken);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 닉네임 중복 검사
    @GetMapping("/sign-up/check-nickname")
    public ResponseEntity<Boolean> checkNickname(@RequestParam String nickname) {
        boolean isTaken = adminService.isNicknameTaken(nickname);
        if(!isTaken) {
            return ResponseEntity.ok(isTaken);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 이메일 중복 검사
    @GetMapping("/sign-up/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean isTaken = adminService.isEmailTaken(email);
        if(!isTaken) {
            return ResponseEntity.ok(isTaken);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}


