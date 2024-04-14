package com.riverburg.eUniversity.model.entity;

import com.riverburg.eUniversity.model.constant.Role;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "accounts")
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class AccountEntity {

    @Id
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @GeneratedValue(generator = "generator")
    @Column(columnDefinition = "uuid")
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(name = "login", unique = true)
    private String login;

    @NonNull
    @Column(name = "password", unique = true)
    private String password;

    @NonNull
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "mail", unique = true)
    private String mail;

    @NonNull
    @Column(name = "age")
    private Integer age;

    @NonNull
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @NonNull
    @Column(name = "is_active")
    private Boolean isActive;

    @NonNull
    @Column(name = "update_at")
    private Date updatedAt;

    @OneToOne
    @JoinColumn(name = "avatar_file_id")
    private FileEntity avatarFileEntity;

    @OneToOne
    @JoinColumn(name = "description_file_id")
    private FileEntity descriptionFileEntity;

    @OneToMany(mappedBy = "accountEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefreshTokenEntity> refreshTokenEntityList;
}
