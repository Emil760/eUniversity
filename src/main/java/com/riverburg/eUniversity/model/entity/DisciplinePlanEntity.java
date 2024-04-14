package com.riverburg.eUniversity.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "disciplines_plan")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class DisciplinePlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "faculty_discipline_id")
    private FacultyDisciplineEntity facultyDisciplineEntity;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "edu_process_id")
    private EduProcessEntity eduProcessEntity;

    @Column(name = "count")
    private Short count;

    @Column(name = "grade")
    private Short grade;
}
