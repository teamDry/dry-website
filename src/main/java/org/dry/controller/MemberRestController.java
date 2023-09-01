package org.dry.controller;

import lombok.extern.slf4j.Slf4j;
import org.dry.entity.Member;
import org.dry.service.MemberService;
import org.dry.vo.IdAndPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@Slf4j
public class MemberRestController {

    private final MemberService service;

    @Autowired
    public MemberRestController(MemberService service) {
        this.service = service;
    }

    @PostMapping("/sign-up")
    public Member signUp(@RequestBody Member member) {   // @Requestbody 쓰려면 jsondata로 받아와야 함, @ModelAttribute는 폼 데이터가 받아지지만, entity 설정을 setter로 해야 담아짐
        log.debug("Member value : " + member);
        Member newMember = service.insertMember(member);
        log.debug("return newMember" + newMember);
        return newMember;
    }
}
