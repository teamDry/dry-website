package org.dry.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@Getter
@Table(indexes = {
        @Index(columnList = "id"),
        @Index(columnList = "nickname"),
        @Index(columnList = "created_at")
})
@EntityListeners(AuditingEntityListener.class) // Auditing 기능 활성화시 필요
@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int admin_no;

    @Column(nullable = false, unique = true, length=45)
    private String id;
    @Setter
    @Column(nullable = false, length=255)
    private String password;

    @Setter
    @Column(nullable = false, unique = true, length=255)
    private String nickname;

    @Setter
    @Column(nullable = false, unique = true, length=255)
    private String email;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_at;

    private LocalDateTime last_entered_at;

    protected Admin() {}

    private Admin(String id, String password, String nickname, String email) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    public static Admin of(String id, String password, String nickname, String email) {
        return new Admin(id, password, nickname, email);
    }
}
