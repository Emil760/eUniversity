package com.riverburg.eUniversity.model.entity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "questions_variants")
@Setter
@Getter
@NoArgsConstructor
public class QuestionsVariantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private ExamQuestionEntity question;

    @Column(name = "variant")
    private String variant;

    @Column(name = "[order]")
    private Short order;

    @Column(name = "is_correct")
    private Boolean isCorrect;
}
