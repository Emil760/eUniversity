package com.riverburg.eUniversity.model.entity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "exam_questions")
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class ExamQuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name = "question")
    private String questionEntity;

    @Column(name = "type")
    private String type;
}
