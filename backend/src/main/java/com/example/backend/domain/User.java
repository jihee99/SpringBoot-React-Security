package com.example.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TBL_USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ") // 만약 시퀀스 컬럼이 따로 있다면
    private Long seq;

    @Column(name = "USER_ID", nullable = false, length = 40, unique = true)
    private String userId;

    @Column(name = "USER_PWD", nullable = false, length = 100)
    private String password;

    @Column(name = "USER_NM", length = 20)
    private String name;

    @Column(name = "USER_ROLE", nullable = false, length = 1)
    private String role; // 'A' or 'G'

    @Column(name = "PROJ_ID")
    private UUID projectId;

    @Column(name = "REG_USER", nullable = false, length = 40)
    private String regUser;

    @Column(name = "REG_DTTM", nullable = false)
    private LocalDateTime regDttm;

    @Column(name = "MOD_USER", length = 40)
    private String modUser;

    @Column(name = "MOD_DTTM")
    private LocalDateTime modDttm;

    @PrePersist
    public void prePersist() {
        regDttm = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modDttm = LocalDateTime.now();
    }
}