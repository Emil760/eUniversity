package com.riverburg.eUniversity.model.entity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "teachers_disciplines")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TeachersDisciplineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private TeacherEntity teacherEntity;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "discipline_id")
    private DisciplineEntity disciplineEntity;
}
