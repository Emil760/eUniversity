package com.riverburg.eUniversity.model.entity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "exam_answers")
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class ExamAnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity studentEntity;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "question_id")
    private ExamQuestionEntity examQuestionEntity;

    @Column(name = "answer")
    private String answer;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private FileEntity fileEntity;
}
