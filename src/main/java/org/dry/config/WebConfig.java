package org.dry.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS(Cross Origin Resource Sharing) 설정
 * react - spring 서로 다른 orogin 사이의 통신을 허용 / 제한할 수 있음
 *
 * cross origin 문제는 서로 다른 origin 끼리는 통신할때 충돌이 발생하는 문제임
 * 이 보안정책 덕분에 신뢰할 수 없는 외부 리소스가 자동으로 차단되긴 함
 * 여기서 CORS는 특정 origin과의 통신을 허용하게끔 설정해주는 것임
 * 
 * origin은 프로토콜, 호스트, 포트번호가 같으면 같은 origin으로 인식
 * ex) http://localhost:8080 != https://localhost:8080 >> 프로토콜이 다름
 * ex) http://localhost:8080 != http://localhost:8081 >> 포트번호가 다름
 * ex) http://localhost:8080 != http://yourhost:8080 >> 호스트가 다름
 * ex) http://localhost:8080/api/members/1 == http://localhost:8080/api/admins/1 >> 프로토콜, 호스트, 포트번호가 같으니 같은 origin
 *
 * react로 웹페이지를 열면 http://localhost:3000이 origin이 됨
 * react origin(http://localhost:3000) 으로 spring origin (http://localhost:8080) 에 통신을 하면 포트번호가 다르니 충돌남
 * 그래서 이 설정으로 다른 origin과의 통신을 가능하게끔 해주는 거임
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/data") // 이 경로에 대해 CORS 정책 추가 TODO: 지금은 테스트용 api 경로임 추후 수정
                .allowedOrigins("http://localhost:3000") // 허용할 origin 지정
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP메서드 지정
                .allowedHeaders("*")    // 허용할 HTTP Header 지정 TODO: "*" 로하면 보안상 안좋으니 추후 수정
                .allowCredentials(true); // cookie나 HTTP인증 허용 할건지

        // CORS 정책 더 추가하려면 위 메서드를 아래에 또 추가하면됨
        // 이 외에도 옵션이 많은데 추후 필요하면 추가
        registry.addMapping("api/newData")
                .allowedOrigins("http://localhost:3000/main")
                .allowedMethods("GET")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
