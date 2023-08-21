package org.dry.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.dry.DryWebsiteApplication;
import org.dry.util.Util;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 메일은 잘 도착했는지 어떤지 SpringBoot 내부에선 확인이 어려우므로 실제로 쏴보도록 하자.
 */

class EmailServiceImplTest {
    // 실제 메일이 잘 가는지 확인용 메서드
    public static void testSendEmail(JavaMailSender javaMailSender) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo("mapzine123@naver.com"); // 여기에 받을 사람 메일 입력
            helper.setSubject(Util.createMailSubject());
            helper.setText(Util.createMailText(Util.getRandomCode()), true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        // 자바 Bean을 이용해서 테스트 해야해서 Application.class파일을 run 시킴
        // 이렇게 안하면 서버 호스트나 포트 등이 실행파일과 무관하게 local 에서 25번 포트로 작동함 -> connection error 발생
        ConfigurableApplicationContext context = SpringApplication.run(DryWebsiteApplication.class, args);
    
        // Bean으로 객체 생성
        JavaMailSender javaMailSender = context.getBean(JavaMailSender.class);
        testSendEmail(javaMailSender);

        context.close();
    }
}