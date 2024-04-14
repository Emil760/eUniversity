package com.riverburg.eUniversity.model.entity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "faculties_disciplines")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class FacultyDisciplineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private FacultyEntity facultyEntity;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "discipline_id")
    private DisciplineEntity disciplineEntity;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "degree_id")
    private DegreeEntity degreeEntity;

    @Column(name = "semester_number")
    private Short semesterNumber;

    @Builder.Default
    @NonNull
    @Column(name = "is_active")
    private Boolean isActive = true;
}
