package com.riverburg.eUniversity.model.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "admins")
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

}
