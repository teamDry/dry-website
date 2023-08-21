package org.dry.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.dry.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * 이메일 보내는 로직 담당 서비스
 */
@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final InviteCodesService inviteCodesService;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, InviteCodesService inviteCodesService) {
        this.javaMailSender = javaMailSender;
        this.inviteCodesService = inviteCodesService;
    }

    @Override
    public void sendEmail(String email) {
        MimeMessage message = javaMailSender.createMimeMessage(); // 폰트 적용이 가능한 메세지 타입
        MimeMessageHelper helper; // MimeMessage의 생성 / 조작하는데 도움을 주는 도우미 클래스
        String code = Util.getRandomCode(); // 코드 랜덤 생성
        try {
            helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email); // 받는 사람 이메일
            helper.setSubject(Util.createMailSubject()); // 메일 제목
            helper.setText(Util.createMailText(code), true); // 메일 본문 / HTML 여부 ( 이거 true로 하면 html 형식으로 작성, 표시 가능 )
            javaMailSender.send(message); // 메일 보내기
            inviteCodesService.addCode(code); // 메일 보낸 후 코드 추가
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }
}
