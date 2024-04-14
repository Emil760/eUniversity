package com.riverburg.eUniversity.model.entity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "billets")
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class BilletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name = "number")
    private String number;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity groupEntity;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "discipline_id")
    private DisciplineEntity disciplineEntity;
}
