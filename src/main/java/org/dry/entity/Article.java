package org.dry.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@ToString
@Getter
@Table(indexes = {
        @Index(columnList = "article_no"),
        @Index(columnList = "title"),
        @Index(columnList = "recommended_count"),
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int article_no;

    @Setter
    @Column(nullable = false, length = 1024)
    private String title;

    @Setter
    @Column(nullable = false, length = 65535)   // length = 65535로 하면 DB에 TEXT로 저장됨
    private String content;

    @Column(nullable = false, length = 255)     // 작성자
    private String nickname;

    @Column(nullable = false)   // 조회수
    private int view_count;

    @Column(nullable = false)   // 추천수
    private int recommended_count;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_at;

    @Setter
    @LastModifiedDate
    private LocalDateTime modified_at;

    protected Article() {}

    private Article(String title, String content, String nickname) {
        this.title = title;
        this.content = content;
        this.nickname = nickname;
    }

    public static Article of(String title, String content, String nickname) {
        return new Article(title, content, nickname);
    }
}
