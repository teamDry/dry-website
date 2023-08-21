package org.dry.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class Util {
    // 랜덤 코드 생성용 문자 모음
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    // 코드 글자 수
    private static final int URL_LENGTH = 24;

    // 랜덤으로 회원가입용 코드 만들기
    public static String getRandomCode() {
        Random rand = new Random();
        StringBuffer randomCode = new StringBuffer();
        for(int i = 0; i < URL_LENGTH; i++) {
            int randomIndex = rand.nextInt(CHARACTERS.length()); // CHARACTERS의 랜덤한 문자 인덱스 번호 추출
            char randomChar = CHARACTERS.charAt(randomIndex); // 인덱스 번호에 맞는 글자 추출
            randomCode.append(randomChar); // 추가
        }
        return randomCode.toString();
    }

    public static String createMailSubject() {
        return "DryWeb의 관리자가 되는걸 축하해. 쌍둥이들은 건강해?";
    }

    // 메일에 들어갈 내용
    // properties 파일로 정리하려 했으나, 한글이 깨지는 현상이 발생해 영어는 properties, 한글은 하드코딩
    // props의 key값은 편의를 위해 대충 작성
    public static String createMailText(String code) {
        Properties props = new Properties();
        StringBuffer stringBuffer = new StringBuffer();
        try {
            props.load(new FileInputStream("src/main/resources/properties/emailTemplate.properties"));
            stringBuffer.append(props.getProperty("a"));
            stringBuffer.append("당신은 1:1의 경쟁률을 뚫고 dryWeb의 관리자가 될 수 있는 영광스러운 기회를 얻으셨습니다.");
            stringBuffer.append(props.getProperty("b"));
            stringBuffer.append("\"관리자 승급 축하해.\"");
            stringBuffer.append(props.getProperty("c"));
            stringBuffer.append(code);
            stringBuffer.append(props.getProperty("d"));
            stringBuffer.append("이것은 관리자가 될 수 있는 링크입니다.\n");
            stringBuffer.append(props.getProperty("e"));
            stringBuffer.append("※주의! 만약 관리자가 됐다고 권력 남용시 무기징역 3년 또는 2개월치 연봉 50% 삭감등의 불이익이 따를 수 있습니다.");
            stringBuffer.append(props.getProperty("f"));
            return stringBuffer.toString();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
