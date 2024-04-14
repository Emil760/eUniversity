package com.riverburg.eUniversity.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "account_mail_tokens")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountMailTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "is_accepted")
    private Boolean isAccepted;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

}