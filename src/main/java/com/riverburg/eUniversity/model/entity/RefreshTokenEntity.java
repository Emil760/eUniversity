package com.riverburg.eUniversity.model.entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
@Setter
@Getter
@NoArgsConstructor
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity accountEntity;

    @Column(name = "token", unique = true)
    private String token;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @Column(name = "is_expired")
    private Boolean isExpired;
}
