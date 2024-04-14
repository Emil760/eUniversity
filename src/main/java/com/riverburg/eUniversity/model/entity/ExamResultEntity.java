package com.riverburg.eUniversity.model.entity;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "exam_results")
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class ExamResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private TeacherEntity teacherEntity;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "question_id")
    private ExamQuestionEntity examQuestionEntity;

    @NonNull
    @Column(name = "points")
    private Short points;
}
