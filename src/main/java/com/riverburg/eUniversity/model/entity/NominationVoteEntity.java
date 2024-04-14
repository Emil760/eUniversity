package com.riverburg.eUniversity.model.entity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "nomination_votes")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class NominationVoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @ManyToOne()
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

    @NonNull
    @ManyToOne()
    @JoinColumn(name = "teacher_id")
    private AccountEntity teacherEntity;

    @NonNull
    @ManyToOne()
    @JoinColumn(name = "nomination_id")
    private NominationEntity nominationEntity;
}
