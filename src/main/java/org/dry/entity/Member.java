package org.dry.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@ToString
@Getter
@Table(indexes = {
        @Index(columnList = "id"),
        @Index(columnList = "nickname")
})
@EntityListeners(AuditingEntityListener.class) // Auditing 기능 활성화시 필요
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment
    private int member_no;

    @Column(nullable = false, unique = true, length = 45)
    private String id;

    @Setter
    @Column(nullable = false, length = 255)
    private String password;

    @Setter
    @Column(nullable = false, unique = true, length = 255)
    private String nickname;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Setter
    @Column(nullable = false)
    @ColumnDefault("1") // column default값 설정
    private int level;

    // metadata
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate // Auditing
    @Column(nullable = false)
    private LocalDateTime created_at;

    @Setter
    private LocalDateTime last_entered_at;

    protected Member() {}

    private Member(String id, String password, String nickname, String email) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }
    
    // Member 객체 생성 메서드
    public static Member of(String id, String password, String nickname, String email) {
        return new Member(id, password, nickname, email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member user)) return false;
        return member_no == user.member_no;
    }

    @Override
    public int hashCode() {
        return Objects.hash(member_no);
    }
}
