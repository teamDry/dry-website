package org.dry.controller;

import jakarta.servlet.http.HttpSession;
import org.dry.entity.Admin;
import org.dry.service.AdminService;
import org.dry.service.EmailService;
import org.dry.service.InviteCodesService;
import org.dry.vo.IdAndPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.RedisSessionProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admins")
@RestController
public class AdminRestController {
    private final InviteCodesService inviteCodesService;
    private final AdminService adminService;
    private final EmailService emailService;
    @Autowired
    public AdminRestController(AdminService adminService, EmailService emailService, InviteCodesService inviteCodesService) {
        this.adminService = adminService;
        this.emailService = emailService;
        this.inviteCodesService = inviteCodesService;
    }

    @PostMapping("/login") // Ajax에서 작성한 IdAndPassword (Json으로 넘어올 예정), session 등록을 위한 객체
    public ResponseEntity<Admin> login(@RequestBody IdAndPassword idAndPassword, HttpSession session) {
        Admin loginAdmin = adminService.login(idAndPassword); // 넘어온 걸로 객체 db에서 찾아서
        if(loginAdmin != null) { // 객체가 잘 찾아졌으면
            session.setAttribute("loginAdmin", loginAdmin); // session에 로그인 정보 객체 담고
            return ResponseEntity.ok(loginAdmin); // ok 응답에 로그인 정보 객체 같이 실어서 보냄
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 실패했으면 실패 상태코드를 보냄
            // build() : json객체로 만들기
        }
    }

    // 멤버에 대해 관리자 초대 메일 발송
    @GetMapping("/member/invite")
    public ResponseEntity<Boolean> inviteAdmin(HttpSession session) {
        int codeCount = inviteCodesService.countCode(); // 원래 코드 수
        // TODO: 로직 수정할 것. 로그인된 사람의 정보가 아닌, 신청한 사람의 정보로 해야하기 때문에
        // TODO: MemberMapper로 해당 멤버의 email을 가져올 수 있는 로직 작성 후 수정할 것
        Admin admin = (Admin) session.getAttribute("loginAdmin"); // session에서 해당 객체 가져오기
        if(admin != null) { // 가져온 객체가 있다면
            emailService.sendEmail(admin.getEmail()); // 그 객체의 이메일로 메일 전송
        }
        if(inviteCodesService.countCode() == codeCount + 1) {
            return ResponseEntity.ok().build(); // 코드 저장이 잘 됐으면 == 메일이 잘 갔으면 ok 반환
        } else {
            // TODO : 상황에 알맞는 상태 코드 작성 후 설정할 것.
            return ResponseEntity.badRequest().build(); // 코드 저장 안됐다 == 메일이 잘 안갔으면 badRequest 반환
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
    @GetMapping("/id/check")
    public ResponseEntity<Boolean> checkId(@RequestParam String id) {
        boolean isTaken = adminService.isIdTaken(id);
        return getCheckResult(isTaken);
    }

    // 닉네임 중복 검사
    @GetMapping("/nickname/check")
    public ResponseEntity<Boolean> checkNickname(@RequestParam String nickname) {
        boolean isTaken = adminService.isNicknameTaken(nickname);
        return getCheckResult(isTaken);
    }

    // 이메일 중복 검사
    @GetMapping("/email/check")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean isTaken = adminService.isEmailTaken(email);
        return getCheckResult(isTaken);
    }

    private ResponseEntity<Boolean> getCheckResult(boolean isTaken) {
        if(!isTaken) {
            return ResponseEntity.ok(isTaken);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}


