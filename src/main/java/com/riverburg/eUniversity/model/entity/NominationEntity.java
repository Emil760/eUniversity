package com.riverburg.eUniversity.model.entity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "nominations")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class NominationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name = "name", unique = true)
    private String name;

    @Builder.Default
    @NonNull
    @Column(name = "is_active")
    private Boolean isActive = true;
}
