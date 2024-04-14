package com.riverburg.eUniversity.model.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "nomination_history")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class NominationHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @ManyToOne()
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

    @NonNull
    @ManyToOne()
    @JoinColumn(name = "nomination_id")
    private NominationEntity nominationEntity;

    @NonNull
    @Column(name = "likes")
    private Integer likes;

    @NonNull
    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy:MM:dd")
    private Date date;
}
