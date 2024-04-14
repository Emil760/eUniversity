package com.riverburg.eUniversity.model.entity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "students")
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity groupEntity;

    @Column(name = "ball")
    private Short ball;

    @Column(name = "is_paid")
    private Boolean isPaid;
}
