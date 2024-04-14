package com.riverburg.eUniversity.model.entity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "billet_questions")
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class BilletQuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "bilet_id")
    private BilletEntity billetEntity;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "question_id")
    private ExamQuestionEntity examQuestionEntity;

    @Column(name = "[order]")
    private Short order;

}
