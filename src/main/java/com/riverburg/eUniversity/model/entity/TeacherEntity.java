package com.riverburg.eUniversity.model.entity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "teachers")
@Setter
@Getter
@NoArgsConstructor
public class TeacherEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

    @NonNull
    @ManyToOne()
    @JoinColumn(name = "academic_degree_id")
    private AcademicDegreeEntity academicDegreeEntity;
}